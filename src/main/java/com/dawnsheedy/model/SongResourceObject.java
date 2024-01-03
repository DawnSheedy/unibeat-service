package com.dawnsheedy.model;

import software.amazon.awssdk.services.s3.model.S3Object;

public class SongResourceObject {
    private String objectKey;

    public SongResourceObject() {}

    public static SongResourceObject from(S3Object s3Object) {
        SongResourceObject file = new SongResourceObject();
        if (s3Object != null) {
            file.setObjectKey(s3Object.key());
        }
        return file;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getObjectKey() {
        return objectKey;
    }
}
