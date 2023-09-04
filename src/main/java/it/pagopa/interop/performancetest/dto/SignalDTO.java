package it.pagopa.interop.performancetest.dto;

import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;

@Data
public class SignalDTO {

    private BigInteger signalId;
    private BigInteger indexSignal;
    private String objectId;
    private String eserviceId;
    private String objectType;
    private String signalType;
    private Instant tmsInsert;
    private boolean dynamodb = false;

}
