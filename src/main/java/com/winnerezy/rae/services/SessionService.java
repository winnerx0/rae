package com.winnerezy.rae.services;

import com.winnerezy.rae.models.Session;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserService userService;

    public SessionService(SessionRepository sessionRepository, UserService userService){
        this.sessionRepository = sessionRepository;
        this.userService = userService;
    }

    public Session createSession(String name) {

        Session session = new Session(name, userService.getCurrentUser());
        sessionRepository.save(session);
        return session;
    }

    public List<Session> getSessions(){
        User user = userService.getCurrentUser();
        return sessionRepository.findByUserIdOrderByCreatedAtAsc(user.getId()).orElseThrow(() -> new EntityNotFoundException("No Sessions Available"));
    }
}
