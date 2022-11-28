package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential>getCredentialsByUserId(int userId) {
        return credentialMapper.getCredentialsByUserId(userId);
    }

    public void insertCredential(Credential credential) {
        credentialMapper.insertCredential(credential);
    }

    public void updateCredential(Credential credential) {
        credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(int credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }
}
