package notification.template;

import com.notification.model.UserEventDTO;
import com.notification.model.UserEventType;

public class DeletingHandler implements NotificationHandler{

    @Override
    public UserEventType getType() {
        return UserEventType.DELETING;
    }

    @Override
    public String getMessage(UserEventDTO event) {
        return String.format("Здравствуйте, %s! Ваш аккаунт был удалён." +
                "Вы можете восстановить его в любое время. Или можете создать новый с тем же адресом электронной почты" +
                ", но в таком случае первый аккаунт будет стёрт " +
                "и не будет подлежать восстановлению", event.userName());
    }
}
