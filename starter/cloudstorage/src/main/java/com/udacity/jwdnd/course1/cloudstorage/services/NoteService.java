package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void insertNote(Note note) {
        noteMapper.insertNote(note);
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public List<Note> getNotesByUserId(int userId) {
        return noteMapper.getNotesByUserId(userId);
    }

    public Note getNoteById(int noteId) {
        return noteMapper.getNoteById(noteId);
    }
}
