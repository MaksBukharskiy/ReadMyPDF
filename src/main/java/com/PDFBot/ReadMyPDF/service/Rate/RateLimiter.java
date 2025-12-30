package com.PDFBot.ReadMyPDF.service.Rate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RateLimiter {

    private static class UserData {
        LocalDateTime date;
        int requests;

        UserData() {
            this.date = LocalDateTime.now();
            this.requests = 0;
        }
    }

    private final Map<Long, UserData> userData = new HashMap<>();

    public Boolean canMakeRequests(Long userId) {
        UserData data = getUserData(userId);

        if (data.date.getDayOfYear() != LocalDateTime.now().getDayOfYear()) {
            data.date = LocalDateTime.now();
            data.requests = 0;
            log.info("Сброшен счетчик для пользователя {} (новый день)", userId);
        }

        if (data.requests >= 3) {
            log.info("Пользователь {} превысил лимит: {} запросов", userId, data.requests);
            return false;
        }

        return true;

    }

    public void inrementRequest(Long userId) {
        UserData data = getUserData(userId);
        data.requests++;
    }

    private UserData getUserData(Long userId) {
        return userData.computeIfAbsent(userId, k -> new UserData());
    }

    public String getStats(Long userId) {
        UserData data = getUserData(userId);

        return String.format(
                "📊 **Ваши лимиты:**\n• Запросов сегодня: %d/3",
                data.requests
        );
    }
}