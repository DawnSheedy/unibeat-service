package com.dawnsheedy.resource;

import com.dawnsheedy.bean.RequestContext;
import com.dawnsheedy.model.gameplay.GameSession;
import com.dawnsheedy.model.gameplay.RemoteState;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/session")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionResource {
    @Context
    SecurityContext ctx;

    @Inject
    RequestContext requestContext;

    @POST
    @RolesAllowed({"machine"})
    public GameSession createSession() {
        GameSession session = new GameSession();
        session.persist();
        return session;
    }

    @Path("/{sessionId}")
    @GET
    public GameSession getSessionById(String sessionId) {
        return requestContext.getGameSession();
    }

    @GET
    @Path("/{sessionId}/claim")
    @RolesAllowed({"user", "machine"})
    public GameSession claimSession(String sessionId) {
        GameSession session = requestContext.getGameSession();

        // Session is already claimed, pretend it doesn't exist
        if (session.ownerId != null) {
            throw new NotFoundException();
        }

        // Set ownerId to user identifier
        session.ownerId = requestContext.getUser().id;
        session.update();
        return session;
    }

    @GET
    @Path("/{sessionId}/remote")
    public RemoteState getRemoteState(String sessionId) {
        return requestContext.getGameSession().remoteState;
    }

    @PUT
    @Path("/{sessionId}/remote")
    public RemoteState updateRemoteState(String sessionId, RemoteState newState) {
        GameSession session = requestContext.getGameSession();
        session.remoteState = newState;
        session.update();
        return session.remoteState;
    }
}
