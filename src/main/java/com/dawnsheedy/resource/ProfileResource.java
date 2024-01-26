package com.dawnsheedy.resource;

import com.dawnsheedy.bean.RequestContext;
import com.dawnsheedy.model.identity.Profile;
import com.dawnsheedy.model.identity.User;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Authenticated
@Path("/profile")
public class ProfileResource {
    @Inject
    RequestContext requestContext;

    @GET
    @RolesAllowed({"user", "admin"})
    public Profile getAuthenticatedUserProfile() {
        return requestContext.getUser().profile;
    }

    @PUT
    @RolesAllowed({"user", "admin"})
    public Profile updateAuthenticatedUserProfile(Profile newProfile) {
        User authenticatedUser = requestContext.getUser();
        authenticatedUser.profile = newProfile;
        authenticatedUser.update();
        return authenticatedUser.profile;
    }

    @GET
    @Path("/{userId}")
    @RolesAllowed("machine")
    public Profile getUserProfile(String userId) {
        User foundProfile = User.findById(userId);
        if (foundProfile == null) {
            throw new NotFoundException();
        }
        return foundProfile.profile;
    }
}
