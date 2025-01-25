package com.ws.demos;

import java.sql.*;

/**
 * PhotoSyncServer
 *
 * @author Eric at 2025-01-25_16:59
 */
public class PhotoSyncServer {
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/E:/codes/250418-photo-syncer/db/photo-syncer";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    // 用户注册方法
    public static boolean registerUser(String username, String password, String email) {
        String sql = "INSERT INTO Users (username, password, email, created_at) VALUES (?,?,?,NOW())";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 用户登录方法
    public static boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username =? AND password =?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 照片上传方法
    public static boolean uploadPhoto(long userId, String filename, String path) {
        String sql = "INSERT INTO Photo (userId, filename, path, upload_time) VALUES (?,?,?,NOW())";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setString(2, filename);
            pstmt.setString(3, path);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 照片列表获取方法
    public static ResultSet getPhotoList(long userId) {
        String sql = "SELECT * FROM Photo WHERE userId =?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 照片删除方法
    public static boolean deletePhoto(long userId, long photoId) {
        String sql = "DELETE FROM Photo WHERE userId =? AND id =?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, photoId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
