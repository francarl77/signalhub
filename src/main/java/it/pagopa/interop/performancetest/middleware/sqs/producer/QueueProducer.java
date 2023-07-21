package it.pagopa.interop.performancetest.middleware.sqs.producer;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;

public class QueueProducer {

    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${aws.internal-queue-name}")
    private String destinationName;

    @Autowired
    public QueueProducer(AmazonSQSAsync amazonSQSAsync) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    public void send(String message) {
        this.queueMessagingTemplate.send(destinationName, MessageBuilder.withPayload(message).build());
    }

}
