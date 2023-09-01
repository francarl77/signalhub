package it.pagopa.interop.performancetest.service;

import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.entity.SignalEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SignalService {

    Mono<SignalDTO> pushSignal(SignalDTO signal);
    Mono<SignalEntity> pushSignalAsync(SignalDTO signal);
    Flux<SignalDTO> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType);
}
