package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
    private final UsersService usersService;
    private final HashService hashService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private AuthenticationService(UsersService usersService, HashService hashService) {
        this.usersService = usersService;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        boolean isUserExist = usersService.getUser(username) != null;

        if (isUserExist) {
            User user = usersService.getUser(username);
            String encodedSalt = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);

            if (hashedPassword.equals(user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            } else {
                logger.debug("invalid password");
            }

        } else {
            logger.debug("user not exist");
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
