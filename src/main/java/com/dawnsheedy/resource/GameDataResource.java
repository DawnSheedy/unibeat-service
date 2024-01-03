package com.dawnsheedy.resource;

import com.dawnsheedy.model.GameDataType;
import com.dawnsheedy.model.Song;
import com.dawnsheedy.util.S3FormData;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.bson.types.ObjectId;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

@Path("/data")
@RolesAllowed({"admin", "machine"})
public class GameDataResource extends S3Resource {
    @Inject
    S3Client s3;

    @GET
    @Path("/{songId}/{gameDataType}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(String songId, GameDataType gameDataType) {
        Song song = Song.findById(new ObjectId(songId));
        String key = null;
        switch (gameDataType) {
            case Banner -> key = song.bannerId;
            case Track -> key = song.trackId;
            case IndexTrack -> key = song.indexTrackId;
        }
        if (key == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(buildGetRequest(key));
        Response.ResponseBuilder response = Response.ok(objectBytes.asByteArray());
        response.header("Content-Disposition", "attachment;filename=" + key);
        response.header("Content-Type", objectBytes.response().contentType());
        return response.build();
    }

    @POST
    @Path("/{songId}/{gameDataType}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadData(S3FormData formData, String songId, GameDataType gameDataType) throws IOException {
        // Song not provided or doesn't exist
        Song song = Song.findById(new ObjectId(songId));
        if (song == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        // Put to s3
        PutObjectRequest putRequest = buildPutRequest(formData, song, gameDataType);
        PutObjectResponse putResponse = s3.putObject(putRequest, RequestBody.fromFile(formData.file));

        // Set correct field depending on type provided
        switch (gameDataType) {
            case Banner -> song.bannerId = putRequest.key();
            case Track -> song.trackId = putRequest.key();
            case IndexTrack -> song.indexTrackId = putRequest.key();
        }

        // Update song entry
        song.update();

        // Respond
        if (putResponse != null) {
            return Response.ok().status(Status.CREATED).build();
        } else {
            return Response.serverError().build();
        }
    }
}
