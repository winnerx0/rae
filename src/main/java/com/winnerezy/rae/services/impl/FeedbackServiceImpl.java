package com.winnerezy.rae.services.impl;

import com.winnerezy.rae.dto.FeedbackDTO;
import com.winnerezy.rae.models.Feedback;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.FeedbackRespository;
import com.winnerezy.rae.services.FeedbackService;
import com.winnerezy.rae.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRespository feedbackRespository;
    private final UserService userService;

    public FeedbackServiceImpl(FeedbackRespository feedbackRespository, UserService userService) {
        this.feedbackRespository = feedbackRespository;
        this.userService = userService;
    }

    @Override
    public String createFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();

        User user = userService.getCurrentUser();

        feedback.setMessage(feedbackDTO.getMessage());
        feedback.setStars(feedbackDTO.getStars());
        feedback.setUser(user);

        feedbackRespository.save(feedback);

        return "Feedback Sent Successfully";
    }
}
