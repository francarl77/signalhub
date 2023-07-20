package it.pagopa.interop.performancetest.middleware.db.dao;

import it.pagopa.interop.performancetest.middleware.db.entities.IndexSignalCounter;
import reactor.core.publisher.Mono;

public interface IndexSignalCounterDAO {


    Mono<IndexSignalCounter> get(String eserviceID);

    Mono<IndexSignalCounter> updateWithTransaction(IndexSignalCounter counter);


}
