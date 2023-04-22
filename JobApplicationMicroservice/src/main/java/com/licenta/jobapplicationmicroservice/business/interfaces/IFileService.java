package com.licenta.jobapplicationmicroservice.business.interfaces;

import com.licenta.jobapplicationmicroservice.business.model.FileData;
import org.springframework.core.io.Resource;

import java.util.List;

public interface IFileService {
    Resource download(String filesPath, String filename);

    List<FileData> list(String filesPath);
}
