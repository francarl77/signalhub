package it.pagopa.interop.performancetest.service;

import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import reactor.core.publisher.Mono;

public interface QueueListenerService {

    Mono<Signal> signalListener(Signal data);

}
