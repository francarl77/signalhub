package it.pagopa.interop.performancetest.mapper;

import it.pagopa.interop.performancetest.entity.SignalEntity;
import it.pagopa.interop.performancetest.dto.SignalDTO;

import java.util.UUID;


public class SignalMapper {

    public static SignalEntity toSignalEntity(SignalDTO signalDTO) {
        SignalEntity signal = new SignalEntity();
        signal.setSignalId(UUID.randomUUID().toString());
        signal.setEserviceId(signalDTO.getEserviceId());
        signal.setObjectId(signalDTO.getObjectId());
        signal.setObjectType(signalDTO.getObjectType());
        signal.setSignalType(signalDTO.getSignalType());

        return signal;
    }

    public static SignalDTO toDTO(SignalEntity signal) {
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
}
