package com.winnerezy.rae.services;

import com.winnerezy.rae.models.Session;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.MessageRepository;
import com.winnerezy.rae.repositories.SessionRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class AIServiceTest {

//    @Mock
//    private MessageRepository messageRepository;
//
//    @Mock
//    private WebClient webClient;
//
//    @Mock
//    private SessionRepository sessionRepository;
//
//    @InjectMocks
//    private AIService aiService;
//
//    void getResponse_shouldReturnMessage() throws IOException {
//
//        User user = new User();
//
//        Session session = new Session("Test", user);
//
//        assert
//
//        Mockito.when(aiService.getResponse(session.getId(), "Example")).thenReturn("")
//    }
}
