package com.lostfound.lostfound.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lostfound.lostfound.model.Report;
import com.lostfound.lostfound.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
  private final ReportRepository reportRepository;

  public List<Report> getAllReports(){
    return reportRepository.findAll();
  }

  public Report addReport(Report report){
    return reportRepository.save(report);
  }

  public Report updaReport(Long id, Report updateReport){
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
