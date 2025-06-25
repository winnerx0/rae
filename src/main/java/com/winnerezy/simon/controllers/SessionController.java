package com.winnerezy.simon.controllers;

import com.winnerezy.simon.models.Session;
import com.winnerezy.simon.responses.MessageResponse;
import com.winnerezy.simon.services.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Session>> getSessions(){
        return ResponseEntity.ok(sessionService.getSessions());
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<MessageResponse> deleteSession(@PathVariable String sessionId) {
        return ResponseEntity.ok(new MessageResponse(sessionService.deleteSession(sessionId)));
    }
}
