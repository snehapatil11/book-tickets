package com.booktickets.controller;

import com.booktickets.exception.ResourceNotFoundException;
import com.booktickets.model.ShowSession;
import com.booktickets.repository.ShowSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ShowSessionController {

    @Autowired
    ShowSessionRepository showSessionRepository;

    @GetMapping("/sessions")
    public List<ShowSession> getAllShowSessions() {
        return showSessionRepository.findAll();
    }

    @PostMapping("/sessions")
    public ShowSession createShowSessions(@Valid @RequestBody ShowSession session) {
        return showSessionRepository.save(session);
    }

    @GetMapping("/sessions/{id}")
    public ShowSession getShowSessionById(@PathVariable(value = "id") Long sessionId) {
        return showSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowSession", "id", sessionId));
    }

    @PutMapping("/sessions/{id}")
    public ShowSession updateShowSession(@PathVariable(value = "id") Long sessionId,
                           @Valid @RequestBody ShowSession details) {

        ShowSession showSession = showSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowSession", "id", sessionId));

        showSession.setTime(details.getTime());
        showSession.setHall(details.getHall());

        ShowSession updatedSession = showSessionRepository.save(showSession);
        return updatedSession;
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<?> deleteShowSession(@PathVariable(value = "id") Long sessionId) {
        ShowSession showSession = showSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("ShowSession", "id", sessionId));

        showSessionRepository.delete(showSession);

        return ResponseEntity.ok().build();
    }
}
