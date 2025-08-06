package com.corona.documentmanager.service;

import com.corona.documentmanager.dto.UploadProgressDTO;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UploadProgressService {
    private final Map<String, UploadProgressDTO> progressMap = new ConcurrentHashMap<>();

    public void updateProgress(String fileName, UploadProgressDTO progress) {
        progressMap.put(fileName, progress);
    }

    public UploadProgressDTO getProgress(String fileName) {
        return progressMap.get(fileName);
    }

    public void removeProgress(String fileName) {
        progressMap.remove(fileName);
    }
}
