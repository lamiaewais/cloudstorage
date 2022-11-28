package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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

    public String getFileByFileName(String  filename) {
        return new SQL() {
            {
                SELECT("*");
                FROM("FILE");
                WHERE("filename=#{filename}");
            }
        }.toString();
    }

    public String insertNote(Note note) {
        return new SQL() {
            {
                INSERT_INTO("NOTE");
                INTO_COLUMNS("noteTitle, noteDescription, userId");
                INTO_VALUES("#{noteTitle}, #{noteDescription}, #{userId}");
            }
        }.toString();
    }

    public String updateNote(Note note) {
        return  new SQL() {
            {
                UPDATE("NOTE");
                SET("noteTitle=#{noteTitle}, noteDescription=#{noteDescription}");
                WHERE("noteId=#{noteId}");
            }
        }.toString();
    }

    public String getNotesByUserId(int userId) {
        return new SQL() {
            {
                SELECT("*");
                FROM("NOTE");
                WHERE("userId=#{userId}");
            }
        }.toString();
    }

    public String getNoteById(int noteId) {
        return new SQL() {
            {
                SELECT("*");
                FROM("NOTE");
                WHERE("noteId=#{noteId}");
            }
        }.toString();
    }

    public String deleteNoteById(int noteId) {
        return new SQL() {
            {
                DELETE_FROM("NOTE");
                WHERE("noteId=#{noteId}");
            }
        }.toString();
    }

    public String insertCredential(Credential credential) {
        return new SQL() {
            {
                INSERT_INTO("CREDENTIAL");
                INTO_COLUMNS("url, username, key, password, userid");
                INTO_VALUES("#{url}, #{username}, #{key}, #{password}, #{userid}");
            }
        }.toString();
    }

    public String getCredentials(int userId) {
        return new SQL() {
            {
                SELECT("*");
                FROM("CREDENTIAL");
                WHERE("userId=#{userId}");
            }
        }.toString();
    }

    public String deleteCredential(int credentialId) {
        return new SQL() {
            {
                DELETE_FROM("CREDENTIAL");
                WHERE("credentialId=#{credentialId}");
            }
        }.toString();
    }

    public String updateCredential(int credentialId) {
        return new SQL() {
            {
                UPDATE("CREDENTIAL");
                SET("url=#{url}, username=#{username}, key=#{key}, password=#{password}, userid=#{userid}");
                WHERE("credentialId=#{credentialId}");
            }
        }.toString();
    }
}
