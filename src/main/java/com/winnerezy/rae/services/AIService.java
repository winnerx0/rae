package com.winnerezy.rae.services;

import com.winnerezy.rae.config.GeminiConfig;
import com.winnerezy.rae.exceptions.AIException;
import com.winnerezy.rae.models.Message;
import com.winnerezy.rae.repositories.MessageRepository;
import com.winnerezy.rae.responses.AIResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private final MessageRepository messageRepository;
    private final WebClient webClient;

    public AIService(MessageRepository messageRepository, WebClient.Builder webClient){
        this.messageRepository = messageRepository;
        this.webClient = webClient.build();
    }

    @Value("${gemini.url}")
    private String geminiUrl;

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    @Transactional
    public String getResponse(String message) {

        List<Message> messages = messageRepository.findAllByOrderByCreatedAtAsc();

        messageRepository.save(new Message(message));

        Map<String, Object> body = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                messages.stream().map((m) -> Map.of("text", m.getContent())),
                                Map.of("text", message)
                        })
                }
        );

        AIResponse response = webClient
                .post()
                .uri(geminiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).map(AIException::new))
                .bodyToMono(AIResponse.class)
                .block();

        messageRepository.save(new Message(response.getCandidates().getLast().getContent().getParts().getLast().getText()));

        return response.getCandidates().getLast().getContent().getParts().getLast().getText();
    }

}
