package notification.service;

import notification.exception.NotificationHandlingException;
import notification.model.UserEventDTO;
import notification.template.NotificationHandler;
import notification.util.EmailApiService;
import com.resend.core.exception.ResendException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailApiService emailApiService;
    private final List<NotificationHandler>  notificationHandlers;

    public void sendEmail(UserEventDTO dto) throws ResendException {
        String message = notificationHandlers.stream()
                .filter(h ->  dto.eventType().equals(h.getType()))
                .findFirst()
                .map(h -> h.getMessage(dto))
                .orElseThrow(() -> new NotificationHandlingException("Handler not found"));

        System.out.println("_____________/n" + dto.email() + "/n_____________/n" + message);

//        emailApiService.sendNotification(dto.email(),  message);
    }
}
