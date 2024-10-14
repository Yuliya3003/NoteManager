package com.example.TaskManagerSpringBoot.controller;

import com.example.TaskManagerSpringBoot.model.Note;
import com.example.TaskManagerSpringBoot.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note){return ResponseEntity.ok(noteService.createNote(note));}

    @GetMapping
    public ResponseEntity<List<Note>> findAllNotes(){return ResponseEntity.ok(noteService.findAllNotes());}

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Note>> findById(@PathVariable Long id){return ResponseEntity.ok(noteService.findById(id));}

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note){
        return ResponseEntity.ok(noteService.updateNote(id, note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id){
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();}

    @GetMapping("/search")
    public ResponseEntity<List<Note>> searchNotes(@RequestParam(required = false) String keywordTitle,
                                                  @RequestParam(required = false) String keywordContent){
        if (keywordContent != null){
            return ResponseEntity.ok(noteService.findAllNotesByContentContainingIgnoreCase(keywordContent));
        } else if (keywordTitle != null){
            return ResponseEntity.ok(noteService.findAllNotesByTitleContainingIgnoreCase(keywordTitle));
        } else {return ResponseEntity.ok(noteService.findAllNotes());}
    }
}
