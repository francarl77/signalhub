package it.pagopa.interop.performancetest.middleware.sqs.consumer;

import io.awspring.cloud.sqs.annotation.SqsListener;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.service.QueueListenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class QueueListener {

    @Autowired
    private QueueListenerService queueListenerService;

    @SqsListener("${aws.sqs-endpoint}/${aws.internal-queue-name}")
    public void listen(Signal signal, @Headers Map<String, Object> headers){
        log.info("Received payload from queue: {}", signal);
        this.queueListenerService.signalListener(signal).then().subscribe();
    }
}
