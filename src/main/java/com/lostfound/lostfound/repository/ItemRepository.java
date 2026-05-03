package com.lostfound.lostfound.repository;

import org.springframework.stereotype.Repository;

import com.lostfound.lostfound.model.Item;
import com.lostfound.lostfound.model.Status;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAll(Pageable pageable);
   
}


