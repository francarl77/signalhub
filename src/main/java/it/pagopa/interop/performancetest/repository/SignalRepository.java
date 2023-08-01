package it.pagopa.interop.performancetest.repository;

import it.pagopa.interop.performancetest.entity.SignalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignalRepository extends JpaRepository<SignalEntity, Long> {


}
