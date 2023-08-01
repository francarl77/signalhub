package it.pagopa.interop.performancetest.service.impl;


import it.pagopa.interop.performancetest.configs.aws.async.AwsConfigs;
import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.mapper.SignalMapper;
import it.pagopa.interop.performancetest.middleware.db.dao.IndexSignalCounterDAO;
import it.pagopa.interop.performancetest.middleware.db.dao.SignalDAO;
import it.pagopa.interop.performancetest.middleware.db.entities.IndexSignalCounter;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.middleware.sqs.producer.QueueProducer;
import it.pagopa.interop.performancetest.service.SignalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@Qualifier("not-relational")
public class SignalServiceImpl implements SignalService {

    @Autowired
    private SignalDAO signalDAO;
    @Autowired
    private IndexSignalCounterDAO indexSignalCounterDAO;
    @Autowired
    private QueueProducer queueProducer;
    @Autowired
    private AwsConfigs awsConfigs;

    @Override
    public Mono<SignalDTO> pushSignal(Signal signal){
        signal.setIndexSignal(null);
        return this.indexSignalCounterDAO.updateEntity(new IndexSignalCounter(signal.getEserviceId(), 0L))
                .map(indexSignalCounter -> {
                    signal.setSignalId(UUID.randomUUID().toString());
                    signal.setIndexSignal(indexSignalCounter.getMaxIndexSignal());
                    signal.setTmsInsert(Instant.now());
                    signal.setNodeProducer(awsConfigs.getPodNameMs());
                    return signal;
                })
                .doOnSuccess(queueProducer::send)
                .map(SignalMapper::signalDtoMapper);
    }

    @Override
    public Flux<SignalDTO> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType) {
        return this.signalDAO.pullSignal(indexSignal,eserviceId,signalType,objectType)
                .map(SignalMapper::signalDtoMapper)
                .delayElements(Duration.ofMillis(100L));
    }


}
