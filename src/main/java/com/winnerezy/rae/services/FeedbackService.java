package com.winnerezy.rae.services;

import com.winnerezy.rae.dto.FeedbackDTO;
import com.winnerezy.rae.repositories.FeedbackRespository;

public interface FeedbackService {

    String createFeedback(FeedbackDTO feedbackDTO);

}
