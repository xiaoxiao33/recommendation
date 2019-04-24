package com.se.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public boolean saveImageFile(int uid, MultipartFile file);
}
