package com.lostfound.lostfound.repository;

import org.springframework.stereotype.Repository;

import com.lostfound.lostfound.model.Item;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
 
    Page<Item> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Item> findByDeletedAtIsNullAndNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    Page<Item> findByDeletedAtIsNull(Pageable pageable);

    Optional<Item> findByIdAndDeletedAtIsNull(Long id);
}


