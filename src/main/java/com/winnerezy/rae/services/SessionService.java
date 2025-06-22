package com.winnerezy.rae.services;

import com.winnerezy.rae.models.Session;

import java.util.List;

public interface SessionService {

    void createSession(String sessionId);

    List<Session> getSessions();

    String deleteSession(String sessionId);
}
