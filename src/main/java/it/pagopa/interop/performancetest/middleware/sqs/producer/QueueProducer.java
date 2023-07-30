package it.pagopa.interop.performancetest.middleware.sqs.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
public class QueueProducer {

    @Autowired
    private SqsTemplate sqsTemplate;

    private final ObjectMapper objectMapper;


    public QueueProducer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void send(Signal message) {
        this.sqsTemplate.send(
                MessageBuilder.withPayload(convertToJson(message)).build()
        );
    }


    private String convertToJson(Signal body){
        return Utility.objectToJson(this.objectMapper, body);
    }

}
