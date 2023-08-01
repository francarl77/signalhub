package it.pagopa.interop.performancetest.service.impl;

import it.pagopa.interop.performancetest.entity.SignalEntity;
import it.pagopa.interop.performancetest.repository.SignalRepository;
import it.pagopa.interop.performancetest.service.QueueListenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class QueueListenerServiceImpl implements QueueListenerService {

    @Autowired
    private SignalRepository signalRepository;


    @Override
    @Transactional
    public Mono<SignalEntity> signalListener(SignalEntity signal) {
        log.info("Save signal into DB");
        return this.signalRepository.save(signal)
                .doOnSuccess(entity -> log.info("Signal saved"))
                .doOnError(ex -> log.error("Save signal in error {}", ex.getMessage(), ex));
    }


}
