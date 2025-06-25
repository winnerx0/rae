package com.winnerezy.simon.services;

import com.winnerezy.simon.models.Session;

import java.util.List;

public interface SessionService {

    void createSession(String sessionId);

    List<Session> getSessions();

    String deleteSession(String sessionId);
}
