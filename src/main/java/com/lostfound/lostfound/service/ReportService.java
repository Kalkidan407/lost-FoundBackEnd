package com.lostfound.lostfound.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lostfound.lostfound.dto.ReportResponse;
import com.lostfound.lostfound.dto.UserResponse;
import com.lostfound.lostfound.model.Report;
import com.lostfound.lostfound.repository.ItemRepository;
import com.lostfound.lostfound.repository.LocationRepository;
import com.lostfound.lostfound.repository.ReportRepository;
import com.lostfound.lostfound.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
  private final ReportRepository reportRepository;
  private final UserRepository userRepo;
  private final LocationRepository locationRepo;
  private final ItemRepository itemRepo;

  private ReportResponse toDo(Report report){
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

  public List<Report> getAllReports(){
    return reportRepository.findAll();
  }


  public Report addReport(Report report){
    return reportRepository.save(report);
  }

  public Report updaReport(Long id, Report updateReport) {
    return reportRepository.findById(id)
          .map(report -> {
            report.setMessage(updateReport.getMessage());
            return  reportRepository.save(report);
          }).orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
  }

  public void deleteAllReport(){
    reportRepository.deleteAll();
  }

  public void deleteReportById(Long id){
    reportRepository.deleteById(id);
  }
    
    
}
