package com.winnerezy.rae.controllers;

import com.winnerezy.rae.dto.FeedbackDTO;
import com.winnerezy.rae.responses.FeedbackResponse;
import com.winnerezy.rae.services.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/create")
    public ResponseEntity<FeedbackResponse> createFeedback(@RequestBody @Valid FeedbackDTO feedbackDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(new FeedbackResponse(feedbackService.createFeedback(feedbackDTO)));
    }
}
