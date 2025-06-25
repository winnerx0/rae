package com.winnerezy.simon.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    String id;

    @Column(nullable = false)
    int stars;

    @Column(nullable = false)
    String message;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties("name")
    User user;
}
