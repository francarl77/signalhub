package it.pagopa.interop.performancetest.service;

import it.pagopa.interop.performancetest.dto.SignalDTO;
import it.pagopa.interop.performancetest.entity.SignalEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface SignalService {

    Mono<SignalDTO> pushSignalAsync(SignalDTO signal);
    Flux<SignalDTO> pullSignal(Long lastSignalId, String eserviceId, String signalType, String objectType);
}
