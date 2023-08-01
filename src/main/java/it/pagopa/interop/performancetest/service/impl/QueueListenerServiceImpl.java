package it.pagopa.interop.performancetest.service.impl;

import it.pagopa.interop.performancetest.configs.aws.async.AwsConfigs;
import it.pagopa.interop.performancetest.middleware.db.dao.SignalDAO;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.service.QueueListenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class QueueListenerServiceImpl implements QueueListenerService {

    @Autowired
    private SignalDAO signalDAO;
    @Autowired
    private AwsConfigs awsConfigs;

    @Override
    public Mono<Signal> signalListener(Signal signal) {
        signal.setNodeConsumer(awsConfigs.getPodNameMs());
        log.info("Save signal into DB");
        return this.signalDAO.pushSignal(signal)
                .doOnSuccess(entity -> log.info("Signal saved"))
                .doOnError(ex -> log.error("Save signal in error {}", ex.getMessage(), ex));
    }


}
