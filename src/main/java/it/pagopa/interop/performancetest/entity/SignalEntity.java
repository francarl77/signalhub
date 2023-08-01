package it.pagopa.interop.performancetest.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "signal_entity")
public class SignalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long indexSignal;

    private String signalId;

    private String objectId;

    private String eserviceId;

    private String objectType;

    private String signalType;

    @CreatedDate
    private Instant tmsInsert;


}
