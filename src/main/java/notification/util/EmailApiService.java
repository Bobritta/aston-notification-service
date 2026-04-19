package notification.util;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailApiService {
    private final Resend resend;

    @Value("${notification.email.from}")
    private String from;

    @Value("${notification.email.test_email}")
    private String testEmail;

    @Retryable(
            retryFor = { ResendException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2.0)
    )
    public void sendNotification(String email, String message) throws ResendException {
        log.debug("Sending email to: {}/n{}", email, message);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(from)
                .to(testEmail)
                .subject("Уведомление системы")
                .html(message)
                .build();

        try {
            resend.emails().send(params);
            log.info("Email sent successfully");
        } catch (ResendException e) {
            log.warn("Failed to send email, will retry if attempts left. Error: {}", e.getMessage());
            throw e;
        }
    }

    @Recover
    public void recover(ResendException e, String email, String message) {
        log.error("All retry attempts failed for email: {}. Error: {}", email, e.getMessage());
    }
}
