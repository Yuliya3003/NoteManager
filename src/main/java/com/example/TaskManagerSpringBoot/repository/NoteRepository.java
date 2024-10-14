package com.example.TaskManagerSpringBoot.repository;

import com.example.TaskManagerSpringBoot.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    /**
     * @param Id идентификатор заметки
     * @return заметка, обернутая в Optional
     */
    Optional<Note> findById(Long Id);

    /**
     * Ищет заметки, у которых в заголовке есть ключевое слово
     * @param keywordTitle ключевое слово в заголовке
     * @return список заметок, содержащие в названии ключевое слово игнорируя регистр
     */
    List<Note> findAllNotesByTitleContainingIgnoreCase(String keywordTitle);

    /**
     * Ищет заметки, у которых в содержании есть ключевое слово
     * @param keywordContent ключевое слово в содержании
     * @return список заметок, у которых в содержании есть ключевое слово игнорируя регистр
     */
    List<Note> findAllNotesByContentContainingIgnoreCase(String keywordContent);

}
