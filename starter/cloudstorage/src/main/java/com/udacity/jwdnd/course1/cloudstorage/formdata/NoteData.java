package com.udacity.jwdnd.course1.cloudstorage.formdata;

public class NoteData {
    private String noteId;
    private String noteTitle;
    private String noteDescription;

    public NoteData(String noteId, String noteTitle, String noteDescription) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }


    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
