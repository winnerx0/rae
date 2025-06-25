package com.winnerezy.simon.controllers;

import com.winnerezy.simon.dto.MessageDTO;
import com.winnerezy.simon.models.Message;
import com.winnerezy.simon.responses.MessageResponse;
import com.winnerezy.simon.services.AIService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService){
        this.aiService = aiService;
    }

    @PostMapping("/{sessionId}")
    public ResponseEntity<MessageResponse> aiMessage(@PathVariable String sessionId, @Valid @RequestBody MessageDTO messageDTO) {
        String message = aiService.getResponse(sessionId, messageDTO.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(message));

    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String sessionId){
        return ResponseEntity.ok(aiService.getMessages(sessionId));
    }
}
