package com.app.aisummarizer.controller;

import com.app.aisummarizer.service.FileParserService;
import com.app.aisummarizer.service.GeminiSummarizerService;
import com.app.aisummarizer.service.HuggingFaceSummarizerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SummarizerController {

    @Autowired
    private FileParserService fileParser;

    @Autowired
    private HuggingFaceSummarizerService summarizerService;

    // For raw text summarization (optional)
    @PostMapping(value = "/summarize/text", consumes = MediaType.TEXT_PLAIN_VALUE)
    public String summarizeText(@RequestBody String text) {
        return summarizerService.summarize(text);
    }

    // ✅ For file upload summarization
    @PostMapping(value = "/summarize/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String summarizeFile(@RequestParam("file") MultipartFile file) {
        String content = fileParser.extractText(file);
        return summarizerService.summarize(content);
    }
}