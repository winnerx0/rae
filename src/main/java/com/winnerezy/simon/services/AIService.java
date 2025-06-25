package com.winnerezy.simon.services;

import com.winnerezy.simon.models.Message;

import java.util.List;

public interface AIService {

    String getResponse(String sessionId, String message);

    List<Message> getMessages(String sessionId);
}
