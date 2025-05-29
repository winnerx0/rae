package com.winnerezy.rae.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private final String name;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "session")
    List<Message> messages;
}
