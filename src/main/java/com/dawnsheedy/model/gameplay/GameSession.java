package com.dawnsheedy.model.gameplay;

import com.dawnsheedy.model.assets.Song;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.annotation.Nullable;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@MongoEntity(collection="gameSession")
public class GameSession extends PanacheMongoEntity {
    @Nullable
    public ObjectId ownerId;
    public Date startDateTime;
    @Nullable
    public Date endDateTime;
    public List<ObjectId> roundIds;
    public RemoteState remoteState;
    public boolean enrollmentAllowed;

    public GameSession() {
        this.roundIds = new ArrayList<ObjectId>();
        this.startDateTime = new Date();
        this.remoteState = new RemoteState();
        this.enrollmentAllowed = true;
    }

    /**
     * End the session and persist
     */
    public void endSession() {
        endDateTime = new Date();
        update();
    }

    public static GameSession findById(String sessionId) throws NotFoundException {
        GameSession resolvedSession = GameSession.findById(new ObjectId(sessionId));
        if (resolvedSession == null) {
            throw new NotFoundException();
        }
        return resolvedSession;
    }
}
