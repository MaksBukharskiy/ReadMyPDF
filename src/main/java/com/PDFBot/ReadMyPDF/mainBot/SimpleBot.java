package com.PDFBot.ReadMyPDF.mainBot;

import com.PDFBot.ReadMyPDF.service.Rate.RateLimiter;
import com.PDFBot.ReadMyPDF.service.message.MessageService;
import com.PDFBot.ReadMyPDF.service.pdf.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    private final MessageService messageService;
    private final PdfService pdfService;
    private final RateLimiter rateLimiter;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                var msg = update.getMessage();
                Long chatId = msg.getChatId();
                Long userId = msg.getFrom().getId();
                String username = msg.getFrom().getUserName();

                log.info("📨 От @{}: {}", username,
                        msg.hasText() ? msg.getText() :
                                "Файл: " + (msg.hasDocument() ?
                                        msg.getDocument().getFileName() : "другой тип"));

                if (!rateLimiter.canMakeRequests(userId)) {
                    sendMessage(chatId, "❌ Лимит: 50 запросов в день исчерпан");
                    return;
                }

                if (msg.hasText()) {
                    handleTextCommand(chatId, userId, username, msg.getText());
                }

                if (msg.hasDocument()) {
                    handleDocument(chatId,userId, username, msg.getDocument());
                }

            }
        } catch (Exception e) {
            log.error("🔥 Ошибка: {}", e.getMessage());
        }
    }

    private void handleTextCommand(Long chatId, Long userId, String username, String text) {
        String response;

        switch (text) {
            case "/start":
                response = messageService.getWelcomeMessage(username);
                break;

            case "/help":
                response = messageService.getHelpMessage();
                break;

            case "/status":
                response = messageService.getStatusMessage();
                break;

            case "/3861":
                response = messageService.getAdminMessage();
                break;

            case "/limits":
                response = rateLimiter.getStats(userId);
                break;

            default:
                response = messageService.getDefaultMessage();
        }

        rateLimiter.inrementRequest(userId);

        sendMessage(chatId, response);
    }

    private void handleDocument(Long chatId, Long userId, String username, Document document) {
        try {
            String fileName = document.getFileName();
            String fileId = document.getFileId();

            log.info("🔄 Обрабатываю файл: {}, от @{}", fileName, username);

            if (fileName == null || !fileName.toLowerCase().endsWith(".pdf")) {
                sendMessage(chatId, "❌ Отправьте PDF файл (.pdf)");
                return;
            }

            byte[] fileBytes = downloadTelegramFile(fileId);
            double sizeMB = fileBytes.length / (1024.0 * 1024.0);

            log.info("✅ Файл скачан: {}, размер: {} MB",
                    fileName, String.format("%.2f", sizeMB));

            rateLimiter.inrementRequest(userId);

            sendMessage(chatId, messageService.getDocumentReceivedMessage(
                    fileName, username, sizeMB
            ));

            String extractedText = pdfService.extractFromPdf(fileBytes);

            log.info("📊 Текст извлечен: {} символов", extractedText.length());

            if (extractedText.isEmpty()) {
                sendMessage(chatId, "⚠️ PDF пуст или не содержит текста");
            } else {
                sendTextResult(chatId, fileName, extractedText);
            }

        } catch (Exception e) {
            log.error("💥 Ошибка обработки PDF: {}", e.getMessage());
            sendMessage(chatId, messageService.getErrorMessage(e.getMessage()));
        }
    }

    private void sendTextResult(Long chatId, String fileName, String text) {
        if (text.length() <= 4000) {
            String message = String.format(
                    "📄 **Текст из %s:**\n\n%s\n\n✅ Извлечено: %d символов",
                    fileName, text, text.length()
            );
            sendMessage(chatId, message);
        } else {
            sendMessage(chatId, String.format(
                    "📄 **Текст из %s** (сокращенный):\n\n%s...\n\n" +
                            "⚠️ Текст слишком длинный (%d символов).",
                    fileName, text.substring(0, 4000), text.length()
            ));

            sendMessage(chatId, "ℹ️ Полный текст можно получить отправив меньший файл");
        }
    }

    private byte[] downloadTelegramFile(String fileId) throws Exception {
        GetFile getFile = new GetFile();
        getFile.setFileId(fileId);

        org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);

        String fileUrl = "https://api.telegram.org/file/bot" +
                getBotToken() + "/" + file.getFilePath();

        log.info("🌐 Скачиваю файл: {}", fileUrl);

        try (ReadableByteChannel channel = Channels.newChannel(new URL(fileUrl).openStream());
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(8192);

            while (channel.read(buffer) != -1) {
                buffer.flip();
                byte[] array = new byte[buffer.remaining()];
                buffer.get(array);
                outputStream.write(array);
                buffer.clear();
            }

            return outputStream.toByteArray();
        }
    }

    private void sendMessage(Long chatId, String text) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText(text);

            execute(message);
            log.info("📤 Отправлено сообщение в чат {}", chatId);

        } catch (TelegramApiException e) {
            log.error("❌ Ошибка отправки: {}", e.getMessage());
        }
    }
}