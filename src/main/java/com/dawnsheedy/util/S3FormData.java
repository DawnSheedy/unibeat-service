package com.dawnsheedy.util;

import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.io.File;

public class S3FormData {
    @RestForm("file")
    public File file;
}
