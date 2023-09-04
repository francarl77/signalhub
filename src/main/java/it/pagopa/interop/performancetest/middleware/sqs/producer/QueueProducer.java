package it.pagopa.interop.performancetest.middleware.sqs.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.entity.SignalEntity;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.utils.Utility;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class QueueProducer {

    private SqsTemplate sqsTemplate;

    private final ObjectMapper objectMapper;

    public QueueProducer(ObjectMapper objectMapper, SqsTemplate sqsTemplate) {
        this.objectMapper = objectMapper;
        this.sqsTemplate = sqsTemplate;
    }

    public Mono<SendResult<Message<String>>> sendAsync(SignalDTO message) {
        return Mono.fromFuture(this.sqsTemplate.sendAsync(
                MessageBuilder.withPayload(convertToJson(message)).build()
        ));
    }

    private String convertToJson(SignalDTO body){
        return Utility.objectToJson(this.objectMapper, body);
    }

}
