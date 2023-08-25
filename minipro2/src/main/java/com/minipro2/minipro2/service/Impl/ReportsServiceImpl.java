package com.minipro2.minipro2.service.Impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.minipro2.minipro2.model.EligibilityDetails;
import com.minipro2.minipro2.repository.EligibilityDetailsRepo;
import com.minipro2.minipro2.request.SearchRequest;
import com.minipro2.minipro2.response.SearchResponse;
import com.minipro2.minipro2.service.ReportsService;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired(required=true)
    EligibilityDetailsRepo eligibilityDetailsRepo;

    @Override
    public List<String> getUniquePlanNames() {
        return eligibilityDetailsRepo.findAllPlanNames();
    }

    @Override
    public List<String> getUniquePlanStatuses() {
        return eligibilityDetailsRepo.findAllPlanStatus();
    }

    @Override
    public List<SearchResponse> search(SearchRequest request) {
        List<SearchResponse> response = new ArrayList<>();
        EligibilityDetails eligibilityDetail = new EligibilityDetails();
        String planName = request.getPlanName();
        if (planName != null && !planName.equals("")) {
            eligibilityDetail.setPlanName(planName);
        }

        String planStatus = request.getPlanStatus();
        if (planStatus != null && !planStatus.equals("")) {
            eligibilityDetail.setPlanStatus(planStatus);
        }

        LocalDate planStartDate = request.getPlanStartDate();
        if (planStartDate != null) {
            eligibilityDetail.setPlanStartDate(planStartDate);
        }

        LocalDate planEndDate = request.getPlanEndDate();
        if (planEndDate != null) {
            eligibilityDetail.setPlanEndDate(planEndDate);
        }
        Example<EligibilityDetails> example = Example.of(eligibilityDetail);
        List<EligibilityDetails> eligibilityDetailsList = eligibilityDetailsRepo.findAll(example);

        for (EligibilityDetails entity : eligibilityDetailsList) {
            SearchResponse sr = new SearchResponse();
            BeanUtils.copyProperties(entity, sr);
            response.add(sr);
        }
        return response;
    }

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {
        List<EligibilityDetails> eligibilityDetailsList = eligibilityDetailsRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Mobile");
        headerRow.createCell(2).setCellValue("Gender");
        headerRow.createCell(3).setCellValue("SSN");
         int i = 1;
         for(EligibilityDetails entity: eligibilityDetailsList){
            HSSFRow dataRow = sheet.createRow(i);
            dataRow.createCell(0).setCellValue(entity.getName());
            dataRow.createCell(1).setCellValue(String.valueOf(entity.getMobile()));
            dataRow.createCell(2).setCellValue(String.valueOf(entity.getGender()));
            dataRow.createCell(3).setCellValue(String.valueOf(entity.getSsn()));
            i++;
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    @Override
    public void generatePdf(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(11);
        font.setColor(java.awt.Color.BLACK);

        Paragraph p = new Paragraph("List of Users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] { 1.5f, 3.5f, 2.0f, 3.0f, 2.1f });
        table.setSpacingBefore(10);

        // for table header
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(java.awt.Color.GRAY);

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA);
        headerFont.setColor(java.awt.Color.WHITE);

        cell.setPhrase(new Phrase("Sr No.", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Mobile No.", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("SSN", font));
        table.addCell(cell);

        // for table data
        List<EligibilityDetails> eligibilityDetailsList = eligibilityDetailsRepo.findAll();
        eligibilityDetailsList.forEach(entity -> {
            table.addCell(String.valueOf(entity.getEligId()));
            table.addCell(entity.getName());
            table.addCell(String.valueOf(entity.getMobile()));
            table.addCell(String.valueOf(entity.getGender()));
            table.addCell(String.valueOf(entity.getSsn()));
        });

        document.add(table);
        document.close();
    }

}
