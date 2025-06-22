package com.winnerezy.rae.services;

import com.winnerezy.rae.models.Message;

import java.util.List;

public interface AIService {

    String getResponse(String sessionId, String message);

    List<Message> getMessages(String sessionId);
}
