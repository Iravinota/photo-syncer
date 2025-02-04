package com.ws.ps.controller;

import com.ws.ps.entity.Photo;
import com.ws.ps.entity.Users;
import com.ws.ps.service.PhotoService;
import com.ws.ps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * PhotoController
 *
 * @author Eric at 2025-01-25_20:58
 */
@RestController
public class PhotoController {
    @Autowired
    private UserService userService;
    @Autowired
    private PhotoService photoService;

    @PostMapping("/api/photo/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam Long userId, @RequestParam String filename, @RequestParam String path) {
        Optional<Users> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            Photo photo = photoService.uploadPhoto(user.getId(), filename, path);
            if (photo != null) {
                return new ResponseEntity<>("Photo uploaded successfully", HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>("Upload failed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/photo/list")
    public ResponseEntity<List<Photo>> getPhotoList(@RequestParam Long userId) {
        Optional<Users> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            List<Photo> photoList = photoService.getPhotoList(user.getId());
            return new ResponseEntity<>(photoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/api/photo/delete")
    public ResponseEntity<String> deletePhoto(@RequestParam Long photoId) {
        photoService.deletePhoto(photoId);
        return new ResponseEntity<>("Photo deleted successfully", HttpStatus.OK);
    }
}
