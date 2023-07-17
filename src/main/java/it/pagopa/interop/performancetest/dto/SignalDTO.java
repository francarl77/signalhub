package it.pagopa.interop.performancetest.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class SignalDTO {

    private String signalId;
    private Long indexSignal;
    private String objectId;
    private String eserviceId;
    private String objectType;
    private String signalType;
    private Instant tmsInsert;

}
