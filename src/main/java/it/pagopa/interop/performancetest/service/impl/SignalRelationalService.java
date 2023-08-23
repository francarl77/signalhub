package it.pagopa.interop.performancetest.service.impl;


import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.mapper.SignalMapper;
import it.pagopa.interop.performancetest.middleware.sqs.producer.QueueProducer;
import it.pagopa.interop.performancetest.repository.SignalRepository;
import it.pagopa.interop.performancetest.service.SignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Qualifier("relational")
public class SignalRelationalService implements SignalService {

    @Autowired
    private SignalRepository signalRepository;
    @Autowired
    private QueueProducer queueProducer;

    @Override
    public Mono<SignalDTO> pushSignal(SignalDTO signal) {
        this.queueProducer.send(SignalMapper.toSignalEntity(signal));
        return Mono.just(SignalMapper.toDTO(SignalMapper.toSignalEntity(signal))).log();
    }

    @Override
    public Flux<SignalDTO> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType) {
        return null;
    }
}
