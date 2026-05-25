package com.lostfound.lostfound.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;


import com.lostfound.lostfound.dto.report.ReportRequest;
import com.lostfound.lostfound.dto.report.ReportResponse;

import com.lostfound.lostfound.service.ReportService;

import lombok.RequiredArgsConstructor;

 import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportItemController  {

    public final ReportService reportService;
    
    @PostMapping("/create")
     @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReportResponse> createReport(@RequestBody ReportRequest dto){
      ReportResponse create = reportService.addReport(dto);
        return ResponseEntity.ok(create);
    }

    @GetMapping
    public Page<ReportResponse> getAllReports(
       @RequestParam(required = false) String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ){
        return  reportService.getAllReports(
        keyword,
          page,
          size
        );
    }

    @DeleteMapping("/delete/{id}")
 //   @PreAuthorize("hasRole('USER')")
     @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deteteById (@PathVariable Long id){
       reportService.deleteReportById(id);
       return ResponseEntity.ok("Report deleted successfully");
    }

  @DeleteMapping("/deleteAll")
 // @PreAuthorize("hasRole('USER')")
   @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAllReport(){
    reportService.deleteAllReport();
  }

  @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<ReportResponse> updaReport( @PathVariable Long id,
                                                   @RequestBody ReportRequest dto){
                                            ReportResponse updated = reportService.updateReport(id, dto);        
    return ResponseEntity.ok(updated);
  }
    
}


