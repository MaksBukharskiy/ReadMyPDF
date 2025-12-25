package com.PDFBot.ReadMyPDF.mainBot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class SimpleBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Override
    public String getBotToken(){
        return botToken;
    }

    @Override
    public String getBotUsername(){
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            String username = update.getMessage().getFrom().getUserName();

            log.info("\nId: " + chatId + "\nGet message: " + message + "\nFrom: " + username);

            String response;
            if (message.equals("/start")) {
                response = String.format("👋 Привет, %s! Я ReadMyPDF бот!\nОтправь мне PDF файл 📎", username);

                sendMessage(chatId, response);
            }
        }
    }

    private void sendMessage(Long chatId, String message){
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try{
            execute(sendMessage);
            log.info("\nMessage: " + message + "\nIs sent");
        } catch (TelegramApiException e) {
            log.error("\n❌ Error: {}", e.getMessage());
        }

    }

}
