package it.pagopa.interop.performancetest.repository;

import it.pagopa.interop.performancetest.entity.SignalEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.math.BigInteger;

public interface SignalRepository extends ReactiveCrudRepository<SignalEntity, Long> {

    @Query("select * from signal_entity s where s.eservice_id = :eServiceId and signal_id > :signalId order by signal_id asc")
    Flux<SignalEntity> findByEserviceIdAndSignalIdGreaterThan(String eServiceId, BigInteger signalId);

}
