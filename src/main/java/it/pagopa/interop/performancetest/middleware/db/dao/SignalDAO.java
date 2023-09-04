package it.pagopa.interop.performancetest.middleware.db.dao;

import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SignalDAO {

    Mono<Signal> pushSignal(Signal signal);
    Flux<Signal> pullSignal(Long lastSignalId, String eserviceId);
}
