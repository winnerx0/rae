package com.winnerezy.rae.services.impl;

import com.winnerezy.rae.models.Session;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.SessionRepository;
import com.winnerezy.rae.services.SessionService;
import com.winnerezy.rae.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final UserService userService;

    public SessionServiceImpl(SessionRepository sessionRepository, UserService userService){
        this.sessionRepository = sessionRepository;
        this.userService = userService;
    }

    @Override
    public void createSession(String sessionId) {

        Optional<Session> existingSession = sessionRepository.findById(sessionId);

        if(existingSession.isPresent()){
            return;
        }

        List<Session> sessions = sessionRepository.findByUserIdOrderByCreatedAtAsc(userService.getCurrentUser().getId());

        Session session = new Session(sessionId, "Session " + (sessions.toArray().length + 1), userService.getCurrentUser());
        sessionRepository.save(session);
    }

    @Override
    public List<Session> getSessions(){
        User user = userService.getCurrentUser();
        return sessionRepository.findByUserIdOrderByCreatedAtAsc(user.getId());
    }

    @Override
    public String deleteSession(String sessionId){
        sessionRepository.deleteById(sessionId);
        return "Deleted Successfully";
    }
}
