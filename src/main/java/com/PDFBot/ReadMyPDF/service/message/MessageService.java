package com.PDFBot.ReadMyPDF.service.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Value("${bot.start.message}")
    private String startMessage;

    @Value("${bot.help.message}")
    private String helpMessage;

    @Value("${bot.status.message}")
    private String statusMessage;

    @Value("${bot.default.message}")
    private String defaultMessage;

    @Value("${bot.document.received}")
    private String documentReceived;

    @Value("${bot.document.error}")
    private String documentError;

    @Value("${bot.admin.welcome}")
    private String adminWelcome;

    public String getWelcomeMessage(String username) {
        return String.format(startMessage, username);
    }

    public String getAdminMessage() {
        return adminWelcome;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String getDocumentReceivedMessage(String fileName, String username, double sizeMB) {
        return String.format(documentReceived, fileName, username, sizeMB);
    }

    public String getErrorMessage(String error) {
        return String.format(documentError, error);
    }
}