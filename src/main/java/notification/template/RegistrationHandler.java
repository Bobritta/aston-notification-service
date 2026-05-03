package notification.template;

import notification.model.UserEventDTO;
import notification.model.UserEventType;
import org.springframework.stereotype.Component;

@Component
public class RegistrationHandler implements NotificationHandler {
    @Override
    public UserEventType getType() {
        return UserEventType.REGISTRATION;
    }

    @Override
    public String getMessage(UserEventDTO event) {
        return String.format("Здравствуйте, %s! Ваш аккаунт на сайте был успешно создан.", event.userName());
    }
}
