package com.PDFBot.ReadMyPDF.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public String getWelcomeMessage(String username) {
        return String.format(
                messageSource.getMessage("bot.start.message", null, Locale.getDefault()),
                username
        );
    }

    public String getHelpMessage() {
        return messageSource.getMessage("bot.help.message", null, Locale.getDefault());
    }

    public String getStatusMessage() {
        return messageSource.getMessage("bot.status.message", null, Locale.getDefault());
    }

    public String getDefaultMessage() {
        return messageSource.getMessage("bot.default.message", null, Locale.getDefault());
    }

    public String getDocumentReceivedMessage(String fileName, String username, double sizeMB) {
        return String.format(
                messageSource.getMessage("bot.document.received", null, Locale.getDefault()),
                fileName, username, sizeMB
        );
    }

    public String getDocumentSuccessMessage(String fileName, int pages, int chars, int words, String text) {
        // Если текст слишком длинный - обрезаем
        int maxTextLength = 2000;
        String displayText = text;

        if (text.length() > maxTextLength) {
            displayText = text.substring(0, maxTextLength) + "...";
            String truncationNote = String.format(
                    messageSource.getMessage("bot.document.truncated", null, Locale.getDefault()),
                    maxTextLength
            );
            displayText += truncationNote;
        }

        return String.format(
                messageSource.getMessage("bot.document.success", null, Locale.getDefault()),
                fileName, pages, chars, words, displayText
        );
    }

    public String getErrorMessage(String error) {
        return String.format(
                messageSource.getMessage("bot.document.error", null, Locale.getDefault()),
                error
        );
    }
}