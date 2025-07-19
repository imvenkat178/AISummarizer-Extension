package com.app.aisummarizer.service;

import com.app.aisummarizer.util.TextExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileParserService {

    public String extractText(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null) return "";

            if (fileName.endsWith(".pdf")) {
                return TextExtractor.extractFromPDF(file);
            } else if (fileName.endsWith(".docx")) {
                return TextExtractor.extractFromDocx(file);
            } else if (fileName.matches(".*\\.(jpg|jpeg|png|bmp)")) {
                return TextExtractor.extractFromImage(file);
            } else {
                return new String(file.getBytes()); // fallback raw text
            }
        } catch (Exception e) {
            return "Error parsing file: " + e.getMessage();
        }
    }
}