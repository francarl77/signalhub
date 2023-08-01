package it.pagopa.interop.performancetest.service;

import it.pagopa.interop.performancetest.dto.SignalDTO;

import java.util.List;

public interface SignalService {

    SignalDTO pushSignal(SignalDTO signal);
    SignalDTO saveSignal(SignalDTO signal);
    List<SignalDTO> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType);
}
