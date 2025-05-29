package com.winnerezy.rae.controllers;

import com.winnerezy.rae.dto.MessageDTO;
import com.winnerezy.rae.dto.RegisterDTO;
import com.winnerezy.rae.responses.AIResponse;
import com.winnerezy.rae.responses.AuthResponse;
import com.winnerezy.rae.services.AIService;
import com.winnerezy.rae.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService){
        this.aiService = aiService;
    }

    @PostMapping("/{sessionId}")
    public ResponseEntity<String> ai(@Valid @PathVariable String sessionId, @Valid @ModelAttribute MessageDTO messageDTO) throws IOException {
        return ResponseEntity.ok(aiService.getResponse(sessionId, messageDTO.getMessage(), messageDTO.getFile()));

    }
}
