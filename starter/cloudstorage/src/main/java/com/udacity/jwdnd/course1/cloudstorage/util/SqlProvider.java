package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.jdbc.SQL;

public class SqlProvider {

    public String selectAllUsers() {
        return new SQL() {
            {
                SELECT("*");
                FROM("USER");
            }
        }.toString();
    }

    public String insertUser(User user) {
        return new SQL() {
            {
                INSERT_INTO("USER");
                INTO_COLUMNS("username", "salt", "password", "firstname", "lastname");
                INTO_VALUES("#{username}", "#{salt}", "#{password}", "#{firstname}", "#{lastname}");
            }
        }.toString();
    }

    public String getUserByUsername(String username) {
        return new SQL() {
            {
                SELECT("*");
                FROM("USER");
                WHERE("username=#{username}");
            }
        }.toString();
    }
}
