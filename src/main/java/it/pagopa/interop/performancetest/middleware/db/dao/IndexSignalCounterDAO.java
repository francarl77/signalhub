package it.pagopa.interop.performancetest.middleware.db.dao;

import it.pagopa.interop.performancetest.middleware.db.entities.IndexSignalCounter;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactPutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactWriteItemsEnhancedRequest;

public interface IndexSignalCounterDAO {


    Mono<IndexSignalCounter> get(String eserviceID);

    void updateWithTransaction(TransactWriteItemsEnhancedRequest.Builder builder, IndexSignalCounter counter);


}
