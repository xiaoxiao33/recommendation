package com.se.service.impl;

import com.se.model.UserImage;
import com.se.repository.UserImageRepository;
import com.se.repository.UserImageRepositoryImpl;
import com.se.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    UserImageRepository userImageRepository;
    @Autowired
    UserImageRepositoryImpl userImageRepositoryImpl;

    @Override
    @Transactional
    public boolean saveImageFile(int uid, MultipartFile file) {
        try {
            UserImage userImage = UserImage.builder().id(uid).build();
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }
            userImage.setImage(byteObjects);
            userImageRepositoryImpl.save(userImage);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
