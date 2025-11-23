package com.lostfound.lostfound.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table( name ="item")
public class Item {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")

private User user;


  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Report> reports;



    @Column(nullable = false, unique = true)
    private String name;

 
    @Column(nullable = false,  length = 2000)
    private String description;

   @Column(nullable = false, unique = true)
    private String locationFound;

     @Column(nullable = false, unique = true)
    private String photoUrl;

     @Column(nullable = false, unique = true)
    private boolean status;


  
  
}
