package com.mca.recruitment.ai;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeParserService {

    public String extractTextFromResume(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IOException("Uploaded file is empty");
        }

        String contentType = file.getContentType();
        String originalFileName = file.getOriginalFilename();

        boolean isPdf =
                "application/pdf".equalsIgnoreCase(contentType)
                || hasExtension(originalFileName, ".pdf");

        boolean isText =
                "text/plain".equalsIgnoreCase(contentType)
                || hasExtension(originalFileName, ".txt");

        if (isPdf) {
            return extractTextFromPdf(file);
        }

        if (isText) {
            return new String(
                    file.getBytes(),
                    StandardCharsets.UTF_8
            );
        }

        throw new IOException(
                "Only PDF and TXT resume files are supported"
        );
    }

    private String extractTextFromPdf(MultipartFile file)
            throws IOException {

        try (PDDocument document =
                     Loader.loadPDF(file.getBytes())) {

            PDFTextStripper textStripper =
                    new PDFTextStripper();

            String extractedText =
                    textStripper.getText(document);

            if (extractedText == null
                    || extractedText.isBlank()) {

                throw new IOException(
                        "No readable text was found in the PDF"
                );
            }

            return extractedText.trim();
        }
    }

    private boolean hasExtension(
            String fileName,
            String extension) {

        return fileName != null
                && fileName.toLowerCase()
                           .endsWith(extension);
    }
}