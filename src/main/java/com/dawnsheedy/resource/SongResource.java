package com.dawnsheedy.resource;

import com.dawnsheedy.model.Song;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.net.URI;
import java.util.List;

@Path("/songs")
@RolesAllowed({"admin", "machine"})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SongResource {
    @GET
    public List<Song> getSongs() {
        return Song.listAll();
    }

    @POST
    public Response addSong(Song song) {
        song.persist();
        return Response.created(URI.create("/songs/" + song.id)).build();
    }

    @Path("/{songId}")
    @GET
    public Song songById(String songId) {
        return Song.findById(new ObjectId(songId));
    }
}
