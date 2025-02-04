package com.ws.ps.service;

import com.ws.ps.dba.UsersRepository;
import com.ws.ps.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserService
 *
 * @author Eric at 2025-01-25_20:29
 */
@Service
public class UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users registerUser(String username, String password, String email) {
        return usersRepository.registerUser(username, password, email);
    }

    public Users loginUser(String username, String password) {
        return usersRepository.loginUser(username, password);
    }

    public Optional<Users> getUserById(Long userId) {
        return usersRepository.getUserById(userId);
    }
}
