package com.dawnsheedy.model.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

@MongoEntity(collection = "user")
public class User extends PanacheMongoEntity {
    @JsonIgnore
    public String auth0Identifier;
    public Profile profile;

    public User() {}

    public static User findByAuth0Id(String auth0Id) {
        List<User> found = list("auth0Identifier = ?1", auth0Id);
        if (found.size() == 0) return null;
        return found.getFirst();
    }

    public static User findById(String id) {
        return findById(new ObjectId(id));
    }
}
