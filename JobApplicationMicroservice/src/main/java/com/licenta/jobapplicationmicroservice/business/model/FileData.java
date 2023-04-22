package com.licenta.jobapplicationmicroservice.business.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileData {

    private String contentType;
    private String filename;
    private Long size;
}
