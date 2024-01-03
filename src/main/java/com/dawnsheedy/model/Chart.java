package com.dawnsheedy.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

@MongoEntity(collection="chart")
public class Chart extends PanacheMongoEntity {
    public String label;
    public ChartDifficulty difficulty;
    public List<ChartEvent> events;

    public static List<Chart> getBySong(Song song) {
        List<ObjectId> objectIds = song.chartIds.stream().map(ObjectId::new).collect(Collectors.toList());
        return list("_id in ?1", objectIds);
    }
}
