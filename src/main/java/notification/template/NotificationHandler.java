package notification.template;

import com.notification.model.UserEventDTO;
import com.notification.model.UserEventType;

public interface NotificationHandler {
    UserEventType getType();
    String getMessage(UserEventDTO event);
}
