package com.dawnsheedy.resource;

import com.dawnsheedy.model.Chart;
import com.dawnsheedy.model.Song;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/charts")
@RolesAllowed({"admin", "machine"})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChartResource {
    @Path("/{songId}")
    @POST
    public Response addChart(String songId, Chart chart) {
        Song song = getSong(songId);
        chart.persist();
        if (song.chartIds == null) {
            song.chartIds = new ArrayList<String>();
        }
        song.chartIds.add(chart.id.toString());
        song.update();
        return Response.created(URI.create("/charts/" + chart.id)).build();
    }

    @Path("/{songId}")
    @GET
    public List<Chart> getChart(String songId) {
        Song song = getSong(songId);
        return Chart.getBySong(song);
    }

    private Song getSong(String songId) throws NotFoundException {
        Song resolvedSong = Song.findById(new ObjectId(songId));
        if (resolvedSong == null) {
            throw new NotFoundException();
        }
        return resolvedSong;
    }
}
