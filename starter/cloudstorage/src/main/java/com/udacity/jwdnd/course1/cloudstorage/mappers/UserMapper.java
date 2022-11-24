package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.util.SqlProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import java.util.List;

@Mapper
public interface UserMapper {

    @SelectProvider(value = SqlProvider.class, method = "selectAllUsers")
    List<User> getUsers();

    @InsertProvider(value = SqlProvider.class, method = "insertUser")
    int insertUser(User user);

    @SelectProvider(type = SqlProvider.class, method = "getUserByUsername")
    User getUserByUsername(String username);
}
