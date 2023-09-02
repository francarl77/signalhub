package it.pagopa.interop.performancetest.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.Instant;

@Table
@Getter
@Setter
public class SignalEntity {

    @Id
    private BigInteger indexSignal;

    private BigInteger signalId;

    private String objectId;

    private String eserviceId;

    private String objectType;

    private String signalType;

    @CreatedDate
    private Instant tmsInsert;


}
