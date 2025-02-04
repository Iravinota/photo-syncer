package com.ws.ps.service;


import com.ws.ps.dba.PhotoRepository;
import com.ws.ps.entity.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PhotoService
 *
 * @author Eric at 2025-01-25_20:30
 */
@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo uploadPhoto(Long userId, String filename, String path) {
        return photoRepository.uploadPhoto(userId, filename, path);
    }

    public List<Photo> getPhotoList(Long userId) {
        return photoRepository.getPhotoList(userId);
    }

    public void deletePhoto(Long photoId) {
        photoRepository.deletePhoto(photoId);
    }
}
