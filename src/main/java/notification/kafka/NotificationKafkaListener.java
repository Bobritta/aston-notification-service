package notification.kafka;

import notification.model.UserEventDTO;
import notification.service.NotificationService;
import com.resend.core.exception.ResendException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationKafkaListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = {"user-created", "user-updated"}, groupId = "notification-group")
    public void listenUserCreated(UserEventDTO dto) throws ResendException {
        System.out.println("Получено уведомление для пользователя: " + dto.userId() + "тип: " + dto.eventType());
        notificationService.sendEmail(dto);
    }
}
