package com.winnerezy.rae.services;

import com.winnerezy.rae.exceptions.AIException;
import com.winnerezy.rae.models.Message;
import com.winnerezy.rae.repositories.MessageRepository;
import com.winnerezy.rae.responses.AIResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
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
    public String getResponse(String sessionId, String message, MultipartFile multipartFile) throws IOException {

        File file = File.createTempFile("audio", ".ogg");

        multipartFile.transferTo(file);

        Path path = Paths.get(file.toURI());

        List<Message> messages = messageRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);

        messageRepository.save(new Message(message, "user"));

        List<Map<String, Object>> contents = new ArrayList<>();

        for (Message m : messages) {
            contents.add(Map.of("parts", List.of(
                    Map.of("text", m.getContent())
            ), "role", m.getRole()));
        }

        contents.add(Map.of("parts", List.of(
                Map.of("inlineData", Map.of(
                        "mimeType", "audio/ogg",
                        "data", Base64.getEncoder().encodeToString(Files.readAllBytes(path))
                ))
        ), "role", "user"));

        contents.add(Map.of("parts", List.of(
                Map.of("text", message)
        ), "role", "user"));
        Map<String, Object> body = Map.of("contents", contents);

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

        messageRepository.save(new Message(response.getCandidates().getLast().getContent().getParts().getLast().getText(), "model"));

        return response.getCandidates().getLast().getContent().getParts().getLast().getText();
    }

}
