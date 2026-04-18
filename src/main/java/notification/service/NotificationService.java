package notification.service;

import com.notification.exception.NotificationHandlingException;
import com.notification.model.UserEventDTO;
import com.notification.template.NotificationHandler;
import com.notification.util.EmailApiService;
import com.resend.core.exception.ResendException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailApiService emailApiService;
    List<NotificationHandler>  notificationHandlers;

    public void sendEmail(UserEventDTO dto) throws ResendException {
        String message = notificationHandlers.stream()
                .filter(h -> h.getType() == dto.eventType())
                .findFirst()
                .map(h -> h.getMessage(dto))
                .orElseThrow(() -> new NotificationHandlingException("Handler not found"));

        emailApiService.sendNotification(dto.email(),  message);
    }
}
