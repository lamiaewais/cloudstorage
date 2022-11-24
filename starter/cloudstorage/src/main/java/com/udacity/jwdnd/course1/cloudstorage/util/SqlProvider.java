package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
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

    public String insertFile(File file) {
        return new SQL() {
            {
                INSERT_INTO("FILE");
                INTO_COLUMNS("filename", "contentType", "fileSize", "userId", "fileData");
                INTO_VALUES("#{filename}", "#{contentType}", "#{fileSize}", "#{userId}", "#{fileData}");
            }
        }.toString();
    }

    public String getFilesByUserId(int userId) {
        return new SQL() {
            {
                SELECT("*");
                FROM("FILE");
                WHERE("userId=#{userId}");
            }
        }.toString();
    }

    public String deleteFileById(int fileId) {
        return new SQL() {
            {
                DELETE_FROM("FILE");
                WHERE("fileId=#{fileId}");

            }
        }.toString();
    }

    public String getFileById(int fileId) {
        return new SQL() {
            {
                SELECT("*");
                FROM("FILE");
                WHERE("fileId=#{fileId}");
            }
        }.toString();
    }
}
