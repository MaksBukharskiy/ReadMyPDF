package com.PDFBot.ReadMyPDF.service.pdf;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class PdfService {
    private static final int FILE_MAX_SIZE = 10 * 1024 * 1024;

    public String extractFromPdf(byte[] pdf) throws Exception{

            if (pdf == null || pdf.length == 0) {
                log.info("❌ Файл пустой ❌");
                throw new Exception("❌Файл пустой ❌");
            }

            if (pdf.length > FILE_MAX_SIZE) {
                log.info("❌ Слишком большой файл ❌");
                throw new Exception("❌Файл слишком большой. Максимум 10 MB ❌");
            }

        try(InputStream inputStream = new ByteArrayInputStream(pdf)){
            PDDocument document = PDDocument.load(inputStream);

            if(document.isEncrypted()){
                log.info("❌ Документ зашифрован ❌");
                throw new Exception("❌ Документ зашифрован ❌");
            }

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            log.info("✅ Успешно обработан PDF: {} страниц, {} символов ✅",
                    document.getNumberOfPages(), text.length());

            return cleanText(text);
        }
        catch (Exception e){
            log.info("❌Ошибка чтения PDF ❌");
            throw new Exception("❌Ошибка чтения PDF: " + e.getMessage() + " ❌");
        }

    }

    private String cleanText(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        text = text.replaceAll("\\s+", " ");
        text = text.replaceAll("\n{3,}", "\n\n");
        return text.trim();
    }
}