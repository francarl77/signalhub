package it.pagopa.interop.performancetest.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.entity.SignalEntity;
import it.pagopa.interop.performancetest.mapper.SignalMapper;
import it.pagopa.interop.performancetest.middleware.sqs.producer.QueueProducer;
import it.pagopa.interop.performancetest.repository.SignalRepository;
import it.pagopa.interop.performancetest.service.SignalService;
import it.pagopa.interop.performancetest.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Qualifier("relational")
public class SignalRelationalService implements SignalService {

    @Autowired
    private QueueProducer queueProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<SignalDTO> pushSignal(SignalDTO signal) {
        this.queueProducer.send(SignalMapper.toSignalEntity(signal));
        return Mono.just(SignalMapper.toDTO(SignalMapper.toSignalEntity(signal)));
    }

    @Override
    public Mono<SignalEntity> pushSignalAsync(SignalDTO signal) {
        return this.queueProducer.sendAsync(SignalMapper.toSignalEntity(signal))
                .map(s -> Utility.jsonToObject(objectMapper, (((GenericMessage)s.message()).getPayload()).toString(), SignalEntity.class));
    }

    @Override
    public Flux<SignalDTO> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType) {
        return null;
    }
}
