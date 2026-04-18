package notification.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    /**
     * Если DLT топик уже есть, Spring игнорирует этот бин.
     */
    @Bean
    public NewTopic userCreatedDlt() {
        return TopicBuilder.name("user-created.DLT")
                .partitions(1)
                .replicas(2) // Т.к. в докере 2 брокера
                .build();
    }

    /**
     * Настройка обработчика ошибок.
     */
    @Bean
    public DefaultErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(template);

        // Пытаемся 2 раза с паузой в 1 секунду, прежде чем отправить в DLT.
        return new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 2));
    }
}
