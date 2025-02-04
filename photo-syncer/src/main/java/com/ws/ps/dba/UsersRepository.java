package com.ws.ps.dba;

import com.ws.ps.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * UsersRepository
 *
 * @author Eric at 2025-01-25_20:28
 */
@Repository
public class UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Users registerUser(String username, String password, String email) {
        String sql = "INSERT INTO Users (username, password, email) VALUES (?,?,?)";
        jdbcTemplate.update(sql, username, password, email);
        return findByUsername(username);
    }

    public Users loginUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username =? AND password =?";
        List<Users> users = jdbcTemplate.query(sql, new UsersRowMapper(), username, password);
        return users.isEmpty() ? null : users.get(0);
    }

    public Users findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username =?";
        List<Users> users = jdbcTemplate.query(sql, new UsersRowMapper(), username);
        return users.isEmpty() ? null : users.get(0);
    }

    public Optional<Users> getUserById(Long userId) {
        String sql = "select * from users where id =?";
        List<Users> users = jdbcTemplate.query(sql, new UsersRowMapper(), userId);
        return users.size() == 1 ? Optional.of(users.get(0)) : Optional.empty();
    }

    private static class UsersRowMapper implements RowMapper<Users> {
        @Override
        public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
            Users user = new Users();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setCreated_at(rs.getTimestamp("created_at"));
            return user;
        }
    }
}