package it.pagopa.interop.performancetest.mapper;

import it.pagopa.interop.performancetest.entity.SignalEntity;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import it.pagopa.interop.performancetest.dto.SignalDTO;

import java.math.BigInteger;
import java.util.UUID;


public class SignalMapper {

    public static SignalEntity toSignalEntity(SignalDTO signalDTO) {
        SignalEntity signal = new SignalEntity();
        signal.setSignalId(signalDTO.getSignalId());
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

    public static SignalDTO dynamoToDTO(Signal signal) {
        SignalDTO signalDTO = new SignalDTO();

        signalDTO.setSignalId(BigInteger.valueOf(signal.getSignalId()));
        signalDTO.setIndexSignal(BigInteger.valueOf(signal.getIndexSignal()));
        signalDTO.setEserviceId(signal.getEserviceId());
        signalDTO.setObjectId(signal.getObjectId());
        signalDTO.setObjectType(signal.getObjectType());
        signalDTO.setTmsInsert(signal.getTmsInsert());
        signalDTO.setSignalType(signal.getSignalType());

        return signalDTO;
    }

    public static Signal dynamoToSignal(SignalDTO signalDTO) {
        Signal signal = new Signal();

        signal.setSignalId(signalDTO.getSignalId().longValue());
        signal.setIndexSignal(signalDTO.getIndexSignal().longValue());
        signal.setEserviceId(signalDTO.getEserviceId());
        signal.setObjectId(signalDTO.getObjectId());
        signal.setObjectType(signalDTO.getObjectType());
        signal.setTmsInsert(signalDTO.getTmsInsert());
        signal.setSignalType(signalDTO.getSignalType());

        return signal;
    }
}
