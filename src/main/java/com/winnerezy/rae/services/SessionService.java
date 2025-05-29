package com.winnerezy.rae.services;

import com.winnerezy.rae.models.Session;
import com.winnerezy.rae.repositories.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository){
        this.sessionRepository = sessionRepository;
    }

    public String createSession(String name){
        Session session = new Session(name);
        sessionRepository.save(session);
        return session.getId();
    }
}
