package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.util.SqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @SelectProvider(type = SqlProvider.class, method = "getCredentialsByUserId")
    List<Credential> getCredentialsByUserId(int userId);

    @SelectProvider(type = SqlProvider.class, method = "getCredentialById")
    Credential getCredentialById(int credentialId);

    @InsertProvider(type = SqlProvider.class, method = "insertCredential")
    int insertCredential(Credential credential);

    @UpdateProvider(type = SqlProvider.class, method = "updateCredential")
    int updateCredential(Credential credential);

    @DeleteProvider(type = SqlProvider.class, method = "deleteCredential")
    int deleteCredential(int credentialId);
}
