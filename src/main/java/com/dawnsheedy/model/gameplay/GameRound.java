package com.dawnsheedy.model.gameplay;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.annotation.Nullable;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@MongoEntity(collection="gameRound")
public class GameRound extends PanacheMongoEntity {
    @Nullable
    public ObjectId songId;
    @Nullable
    public ObjectId chartId;
    @Nullable
    public Date startDateTime;
    @Nullable
    public Date endDateTime;
    public int score;

    public GameRound() {
        startDateTime = new Date();
        score = 0;
    }
}
