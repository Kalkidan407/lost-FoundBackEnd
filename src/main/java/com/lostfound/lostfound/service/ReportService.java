package com.lostfound.lostfound.service;


import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.lostfound.lostfound.dto.report.ReportRequest;
import com.lostfound.lostfound.dto.report.ReportResponse;
import com.lostfound.lostfound.model.Item;
import com.lostfound.lostfound.model.Report;
import com.lostfound.lostfound.model.User;
import com.lostfound.lostfound.repository.ItemRepository;
import com.lostfound.lostfound.repository.LocationRepository;
import com.lostfound.lostfound.repository.ReportRepository;
import com.lostfound.lostfound.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
  
  private final ReportRepository reportRepository;
  private final UserRepository userRepository;
  private final LocationRepository locationRepository;
  private final ItemRepository itemRepository;

  private ReportResponse toDTO(Report report) {
    ReportResponse dto = new ReportResponse();

    dto.setId(report.getId());

    dto.setMessage(report.getMessage());



    return dto;
  }


  private Report fromDTO(ReportRequest dto){
      Report report = new Report();
      report.setMessage(dto.getMessage());
      report.setReportType(dto.getReportType());
      
  
   User user = userRepository.findById(dto.getReportedBy())
            .orElseThrow(() -> new RuntimeException("User not found"));
    report.setUser(user);

  Item item =  itemRepository.findById(dto.getReportedItemId())
            .orElseThrow(() -> new RuntimeException("Item not found"));
    report.setItem(item);

      return report;
  }


  public Page<ReportResponse> getAllReports(String keyword, int page, int size) {
     int maxSize = 12;
     int safeSize = Math.min(size, maxSize);

     Page<Report> reports;

     Pageable pageable = PageRequest.of(
       page, safeSize, Sort.by("id").descending()

     );

if( keyword != null && !keyword.trim().isEmpty()){
  reports =  reportRepository.findByMessageContainingIgnoreCase(keyword, pageable);

} else{
  reports = reportRepository.findAll(pageable);
}

    return reports.map(this::toDTO);

  }


  public ReportResponse addReport(ReportRequest dto){
    Report report =fromDTO(dto);
    Report savedReport = reportRepository.save(report);
    return toDTO(savedReport);
  }


  public ReportResponse updateReport(Long id, ReportRequest dto) {

    Report report = reportRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Report not found"));

    report.setMessage(dto.getMessage());

    if (dto.getReportedItemId() != null) {
        Item item = itemRepository.findById(dto.getReportedItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));
        report.setItem(item);
    }

    if (dto.getReportedBy() != null) {
        User user = userRepository.findById(dto.getReportedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));
        report.setUser(user);
    }

    
    report.setReportType(dto.getReportType());

 
    Report saved = reportRepository.save(report);

 
    return toDTO(saved);
}

public ReportResponse getReportById(Long id) {
    return reportRepository.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new RuntimeException("Report not found"));
}

 
  public void deleteAllReport(){
    reportRepository.findAll().forEach(report -> {
      report.setDeleted(true);
      report.setDeletedAt(java.time.LocalDateTime.now());
      reportRepository.save(report);
    });
  }

  public void deleteReportById(Long id){
    Report report = reportRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
    report.setDeleted(true);
    report.setDeletedAt(java.time.LocalDateTime.now());
    reportRepository.save(report);
  }
    
    
}
