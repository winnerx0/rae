package com.winnerezy.rae.controllers;

import com.winnerezy.rae.dto.SessionDTO;
import com.winnerezy.rae.models.Session;
import com.winnerezy.rae.responses.MessageResponse;
import com.winnerezy.rae.responses.SessionResponse;
import com.winnerezy.rae.services.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    @PostMapping("/")
    public ResponseEntity<Session> createSession(@Valid @RequestBody SessionDTO sessionDTO) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.createSession(sessionDTO.getName()));
    }

    @GetMapping("/")
    public ResponseEntity<List<Session>> getSessions(){
        return ResponseEntity.ok(sessionService.getSessions());
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<MessageResponse> createSession(@PathVariable String sessionId) throws IOException {
        return ResponseEntity.ok(new MessageResponse(sessionService.deleteSession(sessionId)));
    }
}
