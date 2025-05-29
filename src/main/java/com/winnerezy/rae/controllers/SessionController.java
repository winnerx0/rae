package com.winnerezy.rae.controllers;

import com.winnerezy.rae.dto.SessionDTO;
import com.winnerezy.rae.responses.SessionResponse;
import com.winnerezy.rae.services.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    @PostMapping("/")
    public ResponseEntity<SessionResponse> createSession(@Valid @RequestBody SessionDTO sessionDTO){
        return ResponseEntity.ok(new SessionResponse(sessionService.createSession(sessionDTO.getName())));
    }
}
