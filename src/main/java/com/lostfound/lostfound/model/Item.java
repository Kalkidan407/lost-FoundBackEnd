package com.lostfound.lostfound.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table( name ="item")
@SQLRestriction("is_deleted = false")
public class Item {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  
  @Column(name = "is_deleted")
private boolean deleted = false;

    private LocalDateTime deletedAt;  


 @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
private User user;

  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(   mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Report> reports;

    @Column(nullable = false )
    private String name;

    @Column(nullable = false,  length = 2000)
    private String description;

     @Column(nullable = false, unique = true)
    private String photoUrl;

     @Enumerated(EnumType.STRING) 
     @Column(nullable = false, unique = true)
    private Status status;

  
}

