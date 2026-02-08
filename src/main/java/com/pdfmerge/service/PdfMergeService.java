package com.pdfmerge.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PdfMergeService {

    /**
     * Merges two PDF files into one
     * @param file1 First PDF file
     * @param file2 Second PDF file
     * @return Merged PDF as byte array
     * @throws IOException if there's an error reading or writing PDF files
     */
    public byte[] mergePdfs(MultipartFile file1, MultipartFile file2) throws IOException {
        try (PDDocument mergedDocument = new PDDocument()) {
            // Load first PDF
            PDDocument document1 = PDDocument.load(file1.getInputStream());
            
            // Load second PDF
            PDDocument document2 = PDDocument.load(file2.getInputStream());
            
            // Add all pages from first PDF
            List<PDPage> pages1 = document1.getPages();
            for (PDPage page : pages1) {
                mergedDocument.addPage(page);
            }
            
            // Add all pages from second PDF
            List<PDPage> pages2 = document2.getPages();
            for (PDPage page : pages2) {
                mergedDocument.addPage(page);
            }
            
            // Save to byte array
            mergedDocument.save(System.getProperty("java.io.tmpdir") + "/merged.pdf");
            document1.close();
            document2.close();
            
            // Read the merged PDF back as bytes
            java.nio.file.Path path = java.nio.file.Paths.get(System.getProperty("java.io.tmpdir") + "/merged.pdf");
            return java.nio.file.Files.readAllBytes(path);
        }
    }

    /**
     * Validates if the uploaded file is a valid PDF
     * @param file File to validate
     * @return true if valid PDF, false otherwise
     */
    public boolean isValidPdf(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        
        String contentType = file.getContentType();
        boolean isValidType = contentType != null && contentType.equals("application/pdf");
        
        boolean isValidPdf = true;
        try {
            PDDocument.load(file.getInputStream()).close();
        } catch (IOException e) {
            isValidPdf = false;
        }
        
        return isValidType && isValidPdf;
    }

}
