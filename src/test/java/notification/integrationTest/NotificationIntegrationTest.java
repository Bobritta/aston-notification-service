package notification.integrationTest;

import com.resend.Resend;
import com.resend.services.emails.Emails;
import com.resend.services.emails.model.CreateEmailOptions;
import notification.Application;
import notification.model.UserEventDTO;
import notification.model.UserEventType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
@EmbeddedKafka(partitions = 2, topics = {"user-created", "user-updated"})
class NotificationIntegrationTest {

    @MockBean
    private Resend resend;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void shouldProcessUserCreatedEventAndSendEmail() {
        // 1. Given
        UserEventDTO event = new UserEventDTO(33L, 22L, "Ivan" ,"test-receiver@mail.com", UserEventType.REGISTRATION);
        Emails emailsMock = mock(Emails.class);
        when(resend.emails()).thenReturn(emailsMock);

        // 2. When
        kafkaTemplate.send("user-created", event);

        // 3. Then
        // Awaitility, так как чтение из Kafka — асинхронное
        await().atMost(Duration.ofSeconds(5)).untilAsserted(() -> {
            ArgumentCaptor<CreateEmailOptions> captor = ArgumentCaptor.forClass(CreateEmailOptions.class);

            verify(resend.emails()).send(captor.capture());

            CreateEmailOptions capturedOptions = captor.getValue();

            assertEquals("test-receiver@mail.com", capturedOptions.getTo().get(0));
            assertTrue(capturedOptions.getHtml().contains("Ivan"));
            assertTrue(capturedOptions.getHtml().contains("успешно создан"));
        });
    }
}
