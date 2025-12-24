package com.PDFBot.ReadMyPDF.Configuraton;

import com.PDFBot.ReadMyPDF.ReadMyPdfApplication;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
public class BotConfig {

    private final ReadMyPdfApplication telegramBot;

    @PostConstruct
    public void initBot(){
        try{
            TelegramBotsApi botApi = new TelegramBotsApi(DefaultBotSession.class);

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}

