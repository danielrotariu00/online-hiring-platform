package com.licenta.databasemicroservice.business.service;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

@Service
public class MimeContentService {
    private final Tika tika = new Tika();

    public String getMimeType(byte[] file) {
        return tika.detect(file);
    }

    public boolean isImage(String mimeType) {
        return mimeType.startsWith("image/");
    }

    public String getExtensionFromMimeType(String mimeType) {
        return MimeType.valueOf(mimeType).getSubtype();
    }
}