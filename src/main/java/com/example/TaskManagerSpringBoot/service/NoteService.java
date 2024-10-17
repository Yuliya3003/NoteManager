package com.example.TaskManagerSpringBoot.service;


import com.example.TaskManagerSpringBoot.logging.LoggedExecution;
import com.example.TaskManagerSpringBoot.model.Note;
import com.example.TaskManagerSpringBoot.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    //Добавление заметки
    @LoggedExecution
    public Note createNote(Note note){
        return noteRepository.save(note);
    }

    //Просмотр всех заметок
    @LoggedExecution
    public List<Note> findAllNotes(){
        return noteRepository.findAll();
    }

    //Получение заметки по Id
    @LoggedExecution
    public Optional<Note> findById(Long id){
        return noteRepository.findById(id);
    }

    //Редактирование заметки
    @LoggedExecution
    public Note updateNote(Long id, Note updatedNote){
        return noteRepository.findById(id)
                .map(note -> {
                    note.setTitle(updatedNote.getTitle());
                    note.setContent(updatedNote.getContent());
                    note.setCreatedAt(updatedNote.getCreatedAt());
                    return noteRepository.save(note);
                }).orElseThrow(() -> new RuntimeException("Заметка не найдена"));
    }

    //Удаление заметки
    @LoggedExecution
    public void deleteNote(Long id){
        noteRepository.deleteById(id);
    }

    @LoggedExecution
    public List<Note> findAllNotesByTitleContainingIgnoreCase(String keyword){
        return noteRepository.findAllNotesByTitleContainingIgnoreCase(keyword);
    }

    @LoggedExecution
    public List<Note> findAllNotesByContentContainingIgnoreCase(String keywordContent){
        return noteRepository.findAllNotesByContentContainingIgnoreCase(keywordContent);
    }


}
