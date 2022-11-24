package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationService implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
