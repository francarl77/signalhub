package it.pagopa.interop.performancetest.repository;

import it.pagopa.interop.performancetest.entity.SignalEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SignalRepository extends ReactiveCrudRepository<SignalEntity, Long> {


}
