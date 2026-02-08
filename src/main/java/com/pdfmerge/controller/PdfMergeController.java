package com.pdfmerge.controller;

import com.pdfmerge.service.PdfMergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfMergeController {

    @Autowired
    private PdfMergeService pdfMergeService;

    /**
     * Endpoint to merge two PDF files
     * @param file1 First PDF file
     * @param file2 Second PDF file
     * @return Merged PDF file as download
     */
    @PostMapping("/merge")
    public ResponseEntity<?> mergePdfs(
            @RequestParam("file1") MultipartFile file1,
            @RequestParam("file2") MultipartFile file2) {
        
        // Validate input files
        if (file1 == null || file1.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("First PDF file is required");
        }
        
        if (file2 == null || file2.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Second PDF file is required");
        }
        
        // Validate file types
        if (!pdfMergeService.isValidPdf(file1)) {
            return ResponseEntity.badRequest()
                    .body("First file is not a valid PDF");
        }
        
        if (!pdfMergeService.isValidPdf(file2)) {
            return ResponseEntity.badRequest()
                    .body("Second file is not a valid PDF");
        }
        
        try {
            // Merge PDFs
            byte[] mergedPdf = pdfMergeService.mergePdfs(file1, file2);
            
            // Return merged PDF as file download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "merged.pdf");
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(mergedPdf);
                    
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error merging PDFs: " + e.getMessage());
        }
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("PDF Merge Service is running");
    }

}
