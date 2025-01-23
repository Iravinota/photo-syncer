# photo-syncer设计文档

**一、接口设计**：
- **用户注册接口**：
    - `POST /api/user/register`
    - 请求参数：`username`（用户名），`password`（密码），`email`（电子邮件）
    - 功能：允许新用户注册，将用户信息存储在数据库中。
    - 返回：注册成功或失败信息，以及用户唯一标识（如用户 ID）。
- **用户登录接口**：
    - `POST /api/user/login`
    - 请求参数：`username`（用户名），`password`（密码）
    - 功能：验证用户登录信息，返回登录状态和用户信息。
    - 返回：登录成功或失败信息，以及用户信息和身份认证令牌（如 JWT）。
- **照片上传接口**：
    - `POST /api/photo/upload`
    - 请求参数：`userId`（用户 ID），`photoFile`（照片文件）
    - 功能：接收用户上传的照片文件，将其存储在服务器的存储系统中，并将文件信息（如文件名、存储路径、用户 ID 等）存储在数据库中。
    - 返回：上传成功或失败信息。
- **照片列表接口**：
    - `GET /api/photo/list`
    - 请求参数：`userId`（用户 ID）
    - 功能：根据用户 ID 获取用户已上传的照片列表。
    - 返回：包含照片信息（如文件名、上传时间等）的列表。
- **照片删除接口**：
    - `DELETE /api/photo/delete`
    - 请求参数：`userId`（用户 ID），`photoId`（照片 ID）
    - 功能：根据用户 ID 和照片 ID 删除指定的照片文件，并从数据库中移除相应的记录。
    - 返回：删除成功或失败信息。

**二、数据库设计**：
- **用户表（User）**：
    | 字段名       | 数据类型         | 说明             |
    |--------------|------------------|------------------|
    | `id`          | `BIGINT`         | 用户唯一 ID       |
    | `username`    | `VARCHAR(50)`     | 用户名           |
    | `password`    | `VARCHAR(100)`    | 密码（可存储加密后的密码） |
    | `email`       | `VARCHAR(100)`    | 电子邮件         |
    | `created_at`  | `TIMESTAMP`      | 用户注册时间     |

- **照片表（Photo）**：
    | 字段名       | 数据类型         | 说明             |
    |--------------|------------------|------------------|
    | `id`          | `BIGINT`         | 照片唯一 ID       |
    | `userId`      | `BIGINT`         | 所属用户的 ID     |
    | `filename`    | `VARCHAR(200)`    | 照片文件名       |
    | `path`        | `VARCHAR(500)`    | 照片存储路径     |
    | `upload_time` | `TIMESTAMP`      | 上传时间         |


**三、Java 代码示例**：
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoSyncServer {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/photosync";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password";

    // 用户注册方法
    public static boolean registerUser(String username, String password, String email) {
        String sql = "INSERT INTO User (username, password, email, created_at) VALUES (?,?,?,NOW())";
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
        String sql = "SELECT * FROM User WHERE username =? AND password =?";
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
```
**代码解释**：
- `registerUser` 方法：使用 JDBC 连接数据库，将用户的注册信息插入到 `User` 表中，使用 `NOW()` 函数获取当前时间作为注册时间。
- `loginUser` 方法：通过用户名和密码查询 `User` 表，检查是否存在匹配的用户。
- `uploadPhoto` 方法：将用户上传的照片信息插入到 `Photo` 表中，包括用户 ID、文件名、存储路径和上传时间。
- `getPhotoList` 方法：根据用户 ID 从 `Photo` 表中查询该用户的所有照片信息。
- `deletePhoto` 方法：根据用户 ID 和照片 ID 删除 `Photo` 表中对应的照片记录。

请注意，上述代码仅为简单示例，实际应用中需要考虑以下几点：
- 密码存储应使用安全的哈希算法（如 BCrypt）进行加密，而不是明文存储。
- 接口应该使用 Java 的 Web 框架（如 Spring Boot）来实现，以处理 HTTP 请求和响应，提高开发效率和可维护性。
- 文件存储可以使用本地文件系统，但更推荐使用分布式文件系统（如 FastDFS、HDFS 等）或云存储（如 AWS S3、阿里云 OSS）。
- 异常处理需要更加完善，例如区分不同类型的异常并给出相应的错误响应。
- 对于接口的访问权限，需要引入身份认证和授权机制，例如使用 Spring Security 结合 JWT 进行保护。
- 考虑数据库连接池的使用，提高性能，如使用 HikariCP 或 Apache Commons DBCP。

以上是一个初步的设计和实现思路，你可以根据实际需求进一步完善和扩展。

