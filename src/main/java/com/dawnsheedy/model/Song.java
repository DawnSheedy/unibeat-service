package com.dawnsheedy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.annotation.Nullable;

import java.util.List;

@MongoEntity(collection="song")
public class Song extends PanacheMongoEntity {
    public String title;
    public String artist;
    public double tempo;
    @JsonIgnore
    public List<String> chartIds;
    @JsonIgnore
    public String bannerId;
    @JsonIgnore
    public String indexTrackId;
    @JsonIgnore
    public String trackId;
}
