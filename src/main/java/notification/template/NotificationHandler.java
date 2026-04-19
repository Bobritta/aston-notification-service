package notification.template;

import notification.model.UserEventDTO;
import notification.model.UserEventType;

public interface NotificationHandler {
    UserEventType getType();
    String getMessage(UserEventDTO event);
}
