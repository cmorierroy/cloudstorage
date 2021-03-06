package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class NoteService
{
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper)
    {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note, int userId)
    {
        note.setUserId(userId);

        return noteMapper.insert(note);
    }

    public Note getNote(Integer noteId)
    {
        return noteMapper.getNote(noteId);
    }

    public int updateNote(Note note)
    {
        return noteMapper.update(note.getTitle(), note.getDescription(),note.getId());
    }

    public List<Note> getAllNotesForUserId(int userId)
    {
        return noteMapper.getALlNotesForUserId(userId);
    }

    public int deleteNote(Integer noteId, int userId)
    {
        return noteMapper.delete(noteId, userId);
    }


}
