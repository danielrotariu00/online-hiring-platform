package com.licenta.jobapplicationmicroservice.business.service;

import com.licenta.jobapplicationmicroservice.business.interfaces.IFileService;
import com.licenta.jobapplicationmicroservice.business.model.FileData;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FileService implements IFileService {

    @Override
    public Resource download(String filesPath, String filename) {
        try {
            Path file = Paths.get(filesPath)
                    .resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<FileData> list(String filesPath) {
        try {
            Path root = Paths.get(filesPath);

            if (Files.exists(root)) {
                return Files.walk(root, 1)
                        .filter(path -> !path.equals(root))
                        .filter(path -> path.toFile()
                                .isFile())
                        .collect(Collectors.toList())
                        .stream()
                        .map(this::pathToFileData)
                        .collect(Collectors.toList());
            }

            return Collections.emptyList();
        } catch (IOException e) {
            throw new RuntimeException("Could not list the files!");
        }
    }

    private FileData pathToFileData(Path path) {
        FileData fileData = new FileData();
        String filename = path.getFileName()
                .toString();
        fileData.setFilename(filename);

        try {
            fileData.setContentType(Files.probeContentType(path));
            fileData.setSize(Files.size(path));
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }

        return fileData;
    }
}