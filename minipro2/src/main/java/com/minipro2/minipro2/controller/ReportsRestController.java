package com.minipro2.minipro2.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.minipro2.minipro2.request.SearchRequest;
import com.minipro2.minipro2.response.SearchResponse;
import com.minipro2.minipro2.service.ReportsService;

@RestController
public class ReportsRestController {
    @Autowired(required=true)
    private ReportsService reportsService;

    @GetMapping("/plans")
    public ResponseEntity<List<String>> getPlanNames(){
        return new ResponseEntity<>(reportsService.getUniquePlanNames(), HttpStatus.OK);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getPlanStatus(){
        return new ResponseEntity<>(reportsService.getUniquePlanStatuses(), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request){
        return new ResponseEntity<>(reportsService.search(request), HttpStatus.OK);
    }
    
    @GetMapping("/excel")
    public void excelReport(HttpServletResponse response) throws Exception{
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=data.xlsx";

        response.setHeader(headerKey, headerValue);
        reportsService.generateExcel(response);

    }

    @GetMapping("/pdf")
    public void pdfReport(HttpServletResponse response) throws Exception{
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=data.pdf";

        response.setHeader(headerKey, headerValue);
        reportsService.generatePdf(response);
        
    }
}
