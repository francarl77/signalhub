package it.pagopa.interop.performancetest.middleware.sqs.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import it.pagopa.interop.performancetest.exception.PnGenericException;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.service.QueueListenerService;
import it.pagopa.interop.performancetest.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static it.pagopa.interop.performancetest.exception.ExceptionTypeEnum.MAPPER_ERROR;


import java.util.Map;

@Component
@Slf4j
public class QueueListener {

    @Autowired
    private QueueListenerService queueListenerService;

    @Autowired
    private ObjectMapper objectMapper;

    @SqsListener(value = "${aws.internal-queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
    public void pullFromDataLakeQueue(@Payload String node, @Headers Map<String, Object> headers){

        log.info(node);
        Signal signal = convertToObject(node, Signal.class);
        this.queueListenerService.signalListener(signal);
    }

    private <T> T convertToObject(String body, Class<T> tClass){
        T entity = Utility.jsonToObject(this.objectMapper, body, tClass);
        if (entity == null) throw new PnGenericException(MAPPER_ERROR, MAPPER_ERROR.getMessage());
        return entity;
    }

}
