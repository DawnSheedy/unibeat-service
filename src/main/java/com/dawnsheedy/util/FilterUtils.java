package com.dawnsheedy.util;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class FilterUtils {
    public static Bson getFilterForId(String id) { return Filters.and(Filters.eq("_id", new ObjectId(id))); }
}
