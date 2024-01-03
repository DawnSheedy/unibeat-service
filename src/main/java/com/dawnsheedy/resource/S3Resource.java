package com.dawnsheedy.resource;

import com.dawnsheedy.model.GameDataType;
import com.dawnsheedy.model.Song;
import com.dawnsheedy.util.FileUtils;
import com.dawnsheedy.util.S3FormData;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

abstract public class S3Resource {
    @ConfigProperty(name = "bucket.name")
    String bucketName;

    protected PutObjectRequest buildPutRequest(S3FormData formData, Song song, GameDataType type) throws IOException {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(getKeyFromSong(song, type))
                .contentType(FileUtils.getMimetype(formData.file))
                .build();
    }

    protected GetObjectRequest buildGetRequest(String objectKey) {
        return GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
    }

    private String getKeyFromSong(Song song, GameDataType type) {
        return song.id + "-data-" + type;
    }
}
