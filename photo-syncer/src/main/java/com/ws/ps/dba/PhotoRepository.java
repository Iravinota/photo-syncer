package com.ws.ps.dba;

import com.ws.ps.entity.Photo;
import com.ws.ps.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * PhotoRepository
 *
 * @author Eric at 2025-01-25_20:29
 */
@Repository
public class PhotoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PhotoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Photo uploadPhoto(Long userId, String filename, String path) {
        String sql = "INSERT INTO Photo (userId, filename, path) VALUES (?,?,?)";
        jdbcTemplate.update(sql, userId, filename, path);
        return findLastUploadedPhoto(userId);
    }

    public List<Photo> getPhotoList(Long userId) {
        String sql = "SELECT * FROM Photo WHERE userId =?";
        return jdbcTemplate.query(sql, new PhotoRowMapper(), userId);
    }

    public void deletePhoto(Long photoId) {
        String sql = "DELETE FROM Photo WHERE id =?";
        jdbcTemplate.update(sql, photoId);
    }

    private Photo findLastUploadedPhoto(Long userId) {
        String sql = "SELECT * FROM Photo WHERE userId =? ORDER BY id DESC LIMIT 1";
        List<Photo> photos = jdbcTemplate.query(sql, new PhotoRowMapper(), userId);
        return photos.isEmpty() ? null : photos.get(0);
    }

    private static class PhotoRowMapper implements RowMapper<Photo> {
        @Override
        public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Photo photo = new Photo();
            photo.setId(rs.getLong("id"));
            photo.setFilename(rs.getString("filename"));
            photo.setPath(rs.getString("path"));
            photo.setUpload_time(rs.getTimestamp("upload_time"));
            // 手动设置用户信息，这里简化处理，可根据需求完善
            Users user = new Users();
            user.setId(rs.getLong("userId"));
            photo.setUser(user);
            return photo;
        }
    }
}
