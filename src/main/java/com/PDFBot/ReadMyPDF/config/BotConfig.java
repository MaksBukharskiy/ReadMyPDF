package com.PDFBot.ReadMyPDF.config;

import com.PDFBot.ReadMyPDF.mainBot.SimpleBot;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
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

            log.info("\n✅Telegram бот '{}' успешно зарегистрирован и запущен!", simpleBot.getBotUsername() + " ✅");

        } catch (TelegramApiException e) {
            log.info("\n❌Ошибка " + e.getMessage() + " ❌");
        }
    }

}

