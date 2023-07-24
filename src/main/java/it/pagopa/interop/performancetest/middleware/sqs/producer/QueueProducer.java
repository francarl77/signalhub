package it.pagopa.interop.performancetest.middleware.sqs.producer;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import it.pagopa.interop.performancetest.configs.aws.async.AwsConfigs;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
public class QueueProducer {

    private final QueueMessagingTemplate queueMessagingTemplate;

    private final AwsConfigs awsConfigs;
    private final ObjectMapper objectMapper;

    @Autowired
    public QueueProducer(AmazonSQSAsync amazonSQSAsync, AwsConfigs awsConfigs, ObjectMapper objectMapper) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
        this.awsConfigs = awsConfigs;
        this.objectMapper = objectMapper;
    }

    public void send(Signal message) {
        this.queueMessagingTemplate.send(
                awsConfigs.getInternalQueueName(),
                MessageBuilder.withPayload(convertToJson(message)).build()
        );
    }


    private String convertToJson(Signal body){
        return Utility.objectToJson(this.objectMapper, body);
    }

}
