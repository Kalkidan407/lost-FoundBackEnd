package com.lostfound.lostfound.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lostfound.lostfound.dto.ReportRequest;
import com.lostfound.lostfound.dto.ReportResponse;
import com.lostfound.lostfound.dto.UserResponse;
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

  private ReportResponse toDo(Report report) {
    ReportResponse dto = new ReportResponse();
    dto.setId(report.getId());
    dto.setMessage(report.getMessage());

   if(report.getUser() != null){
    UserResponse u = new UserResponse();
    u.setId(report.getUser().getId());
    u.setName(report.getUser().getUsername());
    u.setEmail(report.getUser().getEmail());
    dto.setReportedBy(u);
   }


    return dto;
  }


  private Report fromDTO(ReportRequest dto){
      Report report = new Report();
      report.setMessage(dto.getMessage());
      report.setReportType(dto.getReportType());
      
   // Fetch and set User (reportedBy)
   User user = userRepository.findById(dto.getReportedById())
            .orElseThrow(() -> new RuntimeException("User not found"));
    report.setUser(user);

    // Fetch and set Item (reportedItem)
  Item item =  itemRepository.findById(dto.getReportedItemId())
            .orElseThrow(() -> new RuntimeException("Item not found"));
    report.setItem(item);

      return report;
  }



   // --------------------------
    // Service Methods
    // --------------------------


  public List<ReportResponse> getAllReports(){
    return reportRepository.findAll()
            .stream()
            .map(this::toDo)
            .collect(Collectors.toList());
  }


  public ReportResponse addReport(ReportRequest dto){
    Report report =fromDTO(dto);
    Report savedReport = reportRepository.save(report);
    return toDo(savedReport);
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

    if (dto.getReportedById() != null) {
        User user = userRepository.findById(dto.getReportedById())
                .orElseThrow(() -> new RuntimeException("User not found"));
        report.setUser(user);
    }

    
    report.setReportType(dto.getReportType());

 
    Report saved = reportRepository.save(report);

 
    return toDo(saved);
}

public ReportResponse getReportById(Long id) {
    return reportRepository.findById(id)
            .map(this::toDo)
            .orElseThrow(() -> new RuntimeException("Report not found"));
}



 
  public void deleteAllReport(){
    reportRepository.deleteAll();
  }

  public void deleteReportById(Long id){
    reportRepository.deleteById(id);
  }
    
    
}
