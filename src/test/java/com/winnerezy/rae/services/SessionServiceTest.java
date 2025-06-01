package com.winnerezy.rae.services;

import com.winnerezy.rae.models.Session;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private UserService userService;

    @Mock
    private SessionRepository sessionRepository;

    @Test
    void createSession_shouldReturnSession(){

        User user = new User();

        Session session = new Session("Test", user);

        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        Mockito.when(sessionRepository.save(Mockito.any())).thenReturn(session);

        Session saved = sessionService.createSession("Test");

        assertEquals("Test", saved.getName());

    }
}
