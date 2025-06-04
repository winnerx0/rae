package com.winnerezy.rae.services;

import com.winnerezy.rae.models.Session;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserService userService;

    public SessionService(SessionRepository sessionRepository, UserService userService){
        this.sessionRepository = sessionRepository;
        this.userService = userService;
    }

    public void createSession(String sessionId) {

        Optional<Session> existingSession = sessionRepository.findById(sessionId);

        if(existingSession.isPresent()){
            return;
        }

        List<Session> sessions = sessionRepository.findByUserIdOrderByCreatedAtAsc(userService.getCurrentUser().getId());

        Session session = new Session(sessionId, "Session " + (sessions.toArray().length + 1), userService.getCurrentUser());
        sessionRepository.save(session);
    }

    public List<Session> getSessions(){
        User user = userService.getCurrentUser();
        return sessionRepository.findByUserIdOrderByCreatedAtAsc(user.getId());
    }

    public String deleteSession(String sessionId){
        sessionRepository.deleteById(sessionId);
        return "Deleted Successfully";
    }
}
