package it.pagopa.interop.performancetest.mapper;

import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.dto.SignalDTO;


public class SignalMapper {

    public static SignalDTO signalDtoMapper(Signal signal) {
        SignalDTO signalDTO = new SignalDTO();

        signalDTO.setSignalId(signal.getSignalId());
        signalDTO.setIndexSignal(signal.getIndexSignal());
        signalDTO.setEserviceId(signal.getEserviceId());
        signalDTO.setObjectId(signal.getObjectId());
        signalDTO.setObjectType(signal.getObjectType());
        signalDTO.setTmsInsert(signal.getTmsInsert());
        signalDTO.setSignalType(signal.getSignalType());

        return signalDTO;
    }

    public static Signal toSignalFromDto(SignalDTO signalDTO) {
        Signal signal = new Signal();

        signal.setSignalId(signalDTO.getSignalId());
        signal.setIndexSignal(signalDTO.getIndexSignal());
        signal.setEserviceId(signalDTO.getEserviceId());
        signal.setObjectId(signalDTO.getObjectId());
        signal.setObjectType(signalDTO.getObjectType());
        signal.setTmsInsert(signalDTO.getTmsInsert());
        signal.setSignalType(signalDTO.getSignalType());

        return signal;
    }
}
