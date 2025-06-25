package com.winnerezy.simon.repositories;

import com.winnerezy.simon.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRespository extends JpaRepository<Feedback, String> {
}
