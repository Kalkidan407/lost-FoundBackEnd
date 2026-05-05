package com.lostfound.lostfound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lostfound.lostfound.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
       Page<Report> findAll(Pageable pageable);
    Page<Report> findByReportType(String keyword. Pageable pageable)
    
}
