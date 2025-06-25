package com.winnerezy.simon.repositories;

import com.winnerezy.simon.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {

    List<Session> findByUserIdOrderByCreatedAtAsc(String userId);
}
