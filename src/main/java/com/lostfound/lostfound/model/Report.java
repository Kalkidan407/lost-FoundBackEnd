package com.lostfound.lostfound.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


@ManyToOne
@JoinColumn(name = "user_id")
@OnDelete(action = OnDeleteAction.SET_NULL)
private User user;

@ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

@Column(nullable = false)
private String message;

private LocalDateTime createdAt = LocalDateTime.now();



 

    
}
