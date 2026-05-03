package notification.template;

import notification.model.UserEventDTO;
import notification.model.UserEventType;
import org.springframework.stereotype.Component;

@Component
public class UpdateHandler implements NotificationHandler{
    @Override
    public UserEventType getType() {
        return UserEventType.UPDATE;
    }

    @Override
    public String getMessage(UserEventDTO event) {
        return String.format("Здравствуйте, %s! Ваш аккаунт на сайте был изменён. " +
                "Если Вы не вносили изменения, пожалуйста, свяжитесь с поддержкой"
                , event.userName());
    }
}
