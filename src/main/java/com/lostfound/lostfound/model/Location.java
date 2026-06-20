package com.lostfound.lostfound.model;

import java.time.LocalDateTime;
import java.util.List;
import com.lostfound.lostfound.model.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;


@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location")
@SQLRestriction("is_deleted = false")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String latitude;
    
    @Column(nullable = false)
    private String longitude;
    
    @Column(length = 500)
    private String address;
    
    @Column(length = 500)
    private String description;
    
    @Column(length = 50)
    private String timezone;
    
    @Column(name = "is_verified")
    private boolean verified = false;

    @Column(name = "is_deleted")
    private boolean deleted = false;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Item> items;
    
}
