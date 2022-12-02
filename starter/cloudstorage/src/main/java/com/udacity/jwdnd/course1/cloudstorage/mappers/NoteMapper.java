package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.util.SqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @InsertProvider(type = SqlProvider.class, method = "insertNote")
    int insertNote(Note note);

    @UpdateProvider(type = SqlProvider.class, method = "updateNote")
    int updateNote(Note note);

    @SelectProvider(type = SqlProvider.class, method = "getNotesByUserId")
    List<Note> getNotesByUserId(int userId);

    @SelectProvider(type = SqlProvider.class, method = "getNoteById")
    Note getNoteById(int id);

    @DeleteProvider(type = SqlProvider.class, method = "deleteNoteById")
    int deleteNoteById(int id);
}
