package it.pagopa.interop.performancetest.service;

import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.dto.SignalDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SignalService {

    Mono<SignalDTO> pushSignal(Signal signal);
    Flux<SignalDTO> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType);
}
