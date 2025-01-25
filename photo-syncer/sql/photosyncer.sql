-- 创建 Users 表
CREATE TABLE IF NOT EXISTS Users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建 Photo 表
CREATE TABLE IF NOT EXISTS Photo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    filename VARCHAR(200) NOT NULL,
    path VARCHAR(500) NOT NULL,
    upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES Users(id)
);

-- 测试数据
-- 插入用户数据
INSERT INTO Users (username, password, email)
VALUES
('user1', 'password1', 'user1@example.com'),
('user2', 'password2', 'user2@example.com'),
('user3', 'password3', 'user3@example.com');

-- 插入照片数据
INSERT INTO Photo (userId, filename, path)
VALUES
((SELECT id FROM Users WHERE username = 'user1'), 'photo1.jpg', '/path/to/photo1'),
((SELECT id FROM Users WHERE username = 'user1'), 'photo2.jpg', '/path/to/photo2'),
((SELECT id FROM Users WHERE username = 'user2'), 'photo3.jpg', '/path/to/photo3'),
((SELECT id FROM Users WHERE username = 'user3'), 'photo4.jpg', '/path/to/photo4');
