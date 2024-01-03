package com.dawnsheedy.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

public class ChartEvent {
    public int tick;
    public ChartEventType type;
    public int eventDetail;

    public ChartEvent() {};
}
