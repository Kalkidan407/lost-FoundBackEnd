package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lostfound.lostfound.dto.ReportRequest;
import com.lostfound.lostfound.dto.ReportResponse;
import com.lostfound.lostfound.model.Report;
import com.lostfound.lostfound.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

    public final ReportService reportService;
    

//@RequestBody ItemRequest dto

    @PostMapping("/create")
    public ResponseEntity<ReportResponse> createReport(@RequestBody ReportRequest dto){
      ReportResponse create = reportService.addReport(dto);
        return ResponseEntity.ok(create);
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports(){
        return  ResponseEntity.ok(reportService.getAllReports());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deteteById (@PathVariable Long id){
       reportService.deleteReportById(id);
       return ResponseEntity.ok("Report deleted successfully");
    }

  @DeleteMapping("/deleteAll")
  public void deleteAllReport(){
    reportService.deleteAllReport();
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ReportResponse> updaReport( @PathVariable Long id,
                                                   @RequestBody ReportRequest dto){
                                            ReportResponse updated = reportService.updateReport(id, dto);        
    return ResponseEntity.ok(updated);
  }
    
}
