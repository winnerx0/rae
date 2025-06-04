package com.winnerezy.rae.services;

import com.winnerezy.rae.dto.GeminiErrorDTO;
import com.winnerezy.rae.models.Message;
import com.winnerezy.rae.models.Session;
import com.winnerezy.rae.repositories.MessageRepository;
import com.winnerezy.rae.repositories.SessionRepository;
import com.winnerezy.rae.exceptions.GeminiException;
import com.winnerezy.rae.responses.GeminiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AIService {

    private final MessageRepository messageRepository;
    private final WebClient webClient;
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;

    public AIService(MessageRepository messageRepository, WebClient.Builder webClient, SessionRepository sessionRepository, SessionService sessionService){
        this.messageRepository = messageRepository;
        this.webClient = webClient.build();
        this.sessionRepository = sessionRepository;
        this.sessionService = sessionService;

    }

    @Value("${gemini.url}")
    private String geminiUrl;

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    @Transactional
    public String getResponse(String sessionId, String message) {

        sessionService.createSession(sessionId);

        List<Message> messages = messageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);

        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException("Session Not Found"));

        messageRepository.save(new Message(message, "user", session));

        List<Map<String, Object>> contents = new ArrayList<>();

        contents.add(Map.of("parts", List.of(
                Map.of("text", "You are a compassionate, professional therapist. Your only role is to support me as a therapist would — through active listening, reflective responses, and thoughtful, open-ended questions. Do not provide advice, solutions, or coaching unless I ask. Do not act as a friend, coach, or advisor — just a calm, trained therapist helping me process my thoughts. Stay grounded, empathetic, and non-judgmental and please act like a real human. Let’s begin when I’m ready.")
        ), "role", "user"));

        for (Message m : messages) {
            contents.add(Map.of("parts", List.of(
                    Map.of("text", m.getContent())
            ), "role", m.getRole()));
        }

        contents.add(Map.of("parts", List.of(
                Map.of("text", message)
        ), "role", "user"));

        Map<String, Object> body = Map.of("contents", contents);

        GeminiResponse response = webClient
                .post()
                .uri(geminiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(GeminiErrorDTO.class).map((err) -> new GeminiException(err.getError().getMessage(), err.getError().getCode())))
                .bodyToMono(GeminiResponse.class)
                .block();

        messageRepository.save(new Message(response.getCandidates().getLast().getContent().getParts().getLast().getText(), "model", session));

        return response.getCandidates().getLast().getContent().getParts().getLast().getText();
    }

    public List<Message> getMessages(String sessionId){
        return messageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);
    }

}
