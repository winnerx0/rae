package com.winnerezy.rae.services.impl;

import com.winnerezy.rae.dto.GeminiErrorDTO;
import com.winnerezy.rae.models.Message;
import com.winnerezy.rae.models.Session;
import com.winnerezy.rae.repositories.MessageRepository;
import com.winnerezy.rae.repositories.SessionRepository;
import com.winnerezy.rae.exceptions.GeminiException;
import com.winnerezy.rae.responses.GeminiResponse;
import com.winnerezy.rae.services.AIService;
import com.winnerezy.rae.services.SessionService;
import com.winnerezy.rae.services.UserService;
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
public class AIServiceImpl implements AIService {

    private final MessageRepository messageRepository;
    private final WebClient webClient;
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;
    private final UserService userService;

    public AIServiceImpl(MessageRepository messageRepository, WebClient.Builder webClient, SessionRepository sessionRepository, SessionService sessionService, UserService userService){
        this.messageRepository = messageRepository;
        this.webClient = webClient.build();
        this.sessionRepository = sessionRepository;
        this.sessionService = sessionService;
        this.userService = userService;

    }

    @Value("${gemini.url}")
    private String geminiUrl;

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    @Override
    @Transactional
    public String getResponse(String sessionId, String message) {

        sessionService.createSession(sessionId);

        List<Message> messages = messageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);

        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException("Session Not Found"));

        messageRepository.save(new Message(message, "user", session));

        List<Map<String, Object>> contents = new ArrayList<>();

        contents.add(Map.of("parts", List.of(
                Map.of("text", String.format("You are a compassionate, professional therapist. The client’s name is %s—please address them by name. Your sole role is to support them through active listening: reflect their feelings, validate their experience, and ask thoughtful, open-ended questions. Do not offer advice, solutions, or coaching unless the client explicitly requests it. Do not adopt the role of a friend or coach—remain a calm, grounded, non-judgmental therapist. Use warm, conversational language (e.g., “I hear that feels difficult…,” “Can you say more about that?”), allow natural pauses, and acknowledge uncertainty where appropriate (“It seems like you’re feeling unsure…”). Wait for the client to share; follow their pace and signals before moving forward.\n", userService.getCurrentUser().getName()))), "role", "user"));

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

    @Override
    public List<Message> getMessages(String sessionId){
        return messageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);
    }

}
