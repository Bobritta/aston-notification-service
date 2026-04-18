package notification.model;

public record UserEventDTO(
        Long id,
        Long userId,
        String userName,
        String email,
        UserEventType eventType
) {
}
