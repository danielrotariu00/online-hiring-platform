package com.licenta.jobapplicationmicroservice.business.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileData {

    private String contentType;
    private String filename;
    private Long size;
}
