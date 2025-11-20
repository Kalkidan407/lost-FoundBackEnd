package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lostfound.lostfound.model.Report;
import com.lostfound.lostfound.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

    public final ReportService reportService;
    
    @PostMapping("/create")
    public Report createReport(@RequestBody Report report){
        return reportService.addReport(report);
    }

    @GetMapping
    public List<Report> getAllReports(){
        return reportService.getAllReports();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReportById(@PathVariable Long id){
        reportService.deleteReportById(id);
    }

  @DeleteMapping("/deleteAll")
  public void deleteAllReport(){
    reportService.deleteAllReport();
  }

  @PutMapping("/update/{id}")
  public Report updaReport(@PathVariable Long id, @RequestBody Report updaReport){
    return reportService.updaReport(id, updaReport);
  }
    
}
