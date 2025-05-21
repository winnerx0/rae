package com.winnerezy.rae.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component

public class ImageConverter {

    public String convertToBase64(MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        return "data:" + file.getContentType() + ";base64," + Base64.encodeBase64String(fileContent);
    }
}
