package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UsersService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UsersService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public Users getUser(String username) {
        return userMapper.getUserByUsername(username);
    }

    public boolean isUsernameAvailable(String username) {
        return  userMapper.getUserByUsername(username) == null;
    }

    public int addUser(String username, String password) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(password, encodedSalt);

        return userMapper.insertUser(
                new Users(
                        null,
                        username,
                        encodedSalt,
                        hashedPassword,
                        null,
                        null
                )
        );
    }
}
