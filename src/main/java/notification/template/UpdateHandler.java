package notification.template;

import com.notification.model.UserEventDTO;
import com.notification.model.UserEventType;

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
