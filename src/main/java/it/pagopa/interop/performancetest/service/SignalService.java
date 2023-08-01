package it.pagopa.interop.performancetest.service;

import it.pagopa.interop.performancetest.dto.SignalDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SignalService {

    Mono<SignalDTO> pushSignal(SignalDTO signal);
    Flux<SignalDTO> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType);
}
