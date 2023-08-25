package com.minipro2.minipro2.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.minipro2.minipro2.request.SearchRequest;
import com.minipro2.minipro2.response.SearchResponse;


public interface ReportsService {

    public List<String> getUniquePlanNames();

    public List<String> getUniquePlanStatuses();

    public List<SearchResponse> search(SearchRequest request);

    public void generateExcel(HttpServletResponse response) throws IOException;

    public void generatePdf(HttpServletResponse response) throws IOException;

}
