package com.winnerezy.rae.repositories;

import com.winnerezy.rae.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRespository extends JpaRepository<Feedback, String> {
}
