package com.app.aisummarizer.util;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class TextExtractor {

    public static String extractFromPDF(MultipartFile file) {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            return "Error reading PDF: " + e.getMessage();
        }
    }

    public static String extractFromDocx(MultipartFile file) {
        try (XWPFDocument doc = new XWPFDocument(file.getInputStream())) {
            return doc.getParagraphs()
                      .stream()
                      .map(p -> p.getText())
                      .reduce("", (a, b) -> a + "\n" + b);
        } catch (IOException e) {
            return "Error reading DOCX: " + e.getMessage();
        }
    }

    public static String extractFromImage(MultipartFile file) {
        try {
            File tempFile = File.createTempFile("ocr_input", ".tmp");
            file.transferTo(tempFile);

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("tessdata"); // path to your tessdata folder
            tesseract.setLanguage("eng");

            return tesseract.doOCR(tempFile);
        } catch (IOException | TesseractException e) {
            return "OCR failed: " + e.getMessage();
        }
    }
}