package com.PDFBot.ReadMyPDF.service.pdf;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class PdfService {

    public String extractTextFromPdf(byte[] pdfBytes) throws Exception {
        if (!isPdf(pdfBytes)) {
            throw new Exception("Это не PDF файл");
        }
        if (pdfBytes.length > 10 * 1024 * 1024) {
            throw new Exception("Файл слишком большой (максимум 10MB)");
        }

        try (PDDocument document = Loader.loadPDF(pdfBytes)) {
            if (document.isEncrypted()) {
                throw new Exception("PDF зашифрован");
            }

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            log.info("Извлечено {} страниц, {} символов",
                    document.getNumberOfPages(), text.length());

            return text.trim();

        } catch (IOException e) {
            throw new Exception("Ошибка чтения PDF: " + e.getMessage());
        }
    }

    private boolean isPdf(byte[] fileBytes) {
        if (fileBytes == null || fileBytes.length < 5) {
            return false;
        }

        return fileBytes[0] == 0x25 &&
                fileBytes[1] == 0x50 &&
                fileBytes[2] == 0x44 &&
                fileBytes[3] == 0x46 &&
                fileBytes[4] == 0x2D;
    }
}