package it.pagopa.interop.performancetest.service;

import it.pagopa.interop.performancetest.entity.SignalEntity;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import reactor.core.publisher.Mono;

public interface QueueListenerService {

    Mono<SignalEntity> signalListener(SignalEntity data);

}
