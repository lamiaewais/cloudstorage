package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.jdbc.SQL;

public class SqlProvider {

    public String selectAllUsers() {
        return new SQL() {
            {
                SELECT("*");
                FROM("USERS");
            }
        }.toString();
    }

    public String insertUser(Users user) {
        return new SQL() {
            {
                INSERT_INTO("USERS");
                INTO_COLUMNS("username", "salt", "password", "firstname", "lastname");
                INTO_COLUMNS("#{username}", "#{salt}", "#{password}", "#{firstname}", "#{lastname}");
            }
        }.toString();
    }

    public String getUserByUsername(String username) {
        return new SQL() {
            {
                SELECT("*");
                FROM("USERS");
                WHERE("username=#{username}");
            }
        }.toString();
    }
}
