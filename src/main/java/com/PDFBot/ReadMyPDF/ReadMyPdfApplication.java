package com.PDFBot.ReadMyPDF;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ReadMyPdfApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReadMyPdfApplication.class, args);

        log.info("\n✅✅✅✅✅✅✅✅✅✅✅✅✅");

        System.out.println("""
            \n====================================
            ✅ ReadMyPDF Bot запущен успешно!
            📞 Телеграм бот: @ReadMyPdfBot
            🌐 Сервер: http://localhost:8080
            ====================================
            """);


        log.info("✅✅✅✅✅✅✅✅✅✅✅✅✅");
    }
}
