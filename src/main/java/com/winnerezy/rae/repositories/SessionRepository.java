package com.winnerezy.rae.repositories;

import com.winnerezy.rae.models.Message;
import com.winnerezy.rae.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {

    Optional<List<Session>> findByUserIdOrderByCreatedAtAsc(String userId);
}
