package it.pagopa.interop.performancetest.middleware.sqs.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.entity.SignalEntity;
import it.pagopa.interop.performancetest.exception.ExceptionTypeEnum;
import it.pagopa.interop.performancetest.exception.PnGenericException;
import it.pagopa.interop.performancetest.mapper.SignalMapper;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.service.QueueListenerService;
import it.pagopa.interop.performancetest.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class QueueListener {

    @Autowired
    private QueueListenerService queueListenerService;

    @Autowired
    private ObjectMapper objectMapper;


    @SqsListener("${aws.internal-queue-name}")
    public CompletableFuture<Void> listen(String node, @Headers Map<String, Object> headers){
        log.info("Received payload from queue: {}", node);
        SignalDTO signalDto = convertToObject(node, SignalDTO.class);
        if (signalDto.isDynamodb()) {
            Signal signal = SignalMapper.dynamoToSignal(signalDto);
            return this.queueListenerService.saveSignalToDynamoDb(signal).then().toFuture();
        } else {
            SignalEntity signal = SignalMapper.toSignalEntity(signalDto);
            return this.queueListenerService.saveSignalToDb(signal).then().toFuture();
        }


    }

    private <T> T convertToObject(String body, Class<T> tClass){
        T entity = Utility.jsonToObject(this.objectMapper, body, tClass);
        if (entity == null) throw new PnGenericException(ExceptionTypeEnum.MAPPER_ERROR, ExceptionTypeEnum.MAPPER_ERROR.getMessage());
        return entity;
    }
}
