package com.lostfound.lostfound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lostfound.lostfound.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
    
}
