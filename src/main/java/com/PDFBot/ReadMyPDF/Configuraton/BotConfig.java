package com.PDFBot.ReadMyPDF.Configuraton;

import com.PDFBot.ReadMyPDF.ReadMyPdfApplication;
import com.PDFBot.ReadMyPDF.TestBot.SimpleBot;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BotConfig {

    private final SimpleBot simpleBot;

    @PostConstruct
    public void initBot(){
        try{
            TelegramBotsApi botApi = new TelegramBotsApi(DefaultBotSession.class);
            botApi.registerBot(simpleBot);

            log.info("\n✅ Telegram бот '{}' успешно зарегистрирован!", simpleBot.getBotUsername());

        } catch (TelegramApiException e) {
            log.info("\nError " + e.getMessage());
        }
    }

}

