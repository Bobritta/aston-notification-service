package notification.template;

import com.notification.model.UserEventDTO;
import com.notification.model.UserEventType;

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
