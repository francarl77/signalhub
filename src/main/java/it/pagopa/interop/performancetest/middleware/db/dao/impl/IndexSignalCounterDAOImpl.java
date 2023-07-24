package it.pagopa.interop.performancetest.middleware.db.dao.impl;

import it.pagopa.interop.performancetest.configs.aws.async.AwsConfigs;
import it.pagopa.interop.performancetest.middleware.db.dao.IndexSignalCounterDAO;
import it.pagopa.interop.performancetest.middleware.db.dao.common.BaseDAO;
import it.pagopa.interop.performancetest.middleware.db.entities.IndexSignalCounter;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;


@Repository
public class IndexSignalCounterDAOImpl extends BaseDAO<IndexSignalCounter> implements IndexSignalCounterDAO {


    protected IndexSignalCounterDAOImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient, DynamoDbAsyncClient dynamoDbAsyncClient, AwsConfigs awsConfigs) {
        super(dynamoDbEnhancedAsyncClient, dynamoDbAsyncClient, awsConfigs.getDynamodbIndexSignalCounterTable(), IndexSignalCounter.class);
    }

    @Override
    public Mono<IndexSignalCounter> get(String eserviceID) {
        return Mono.fromFuture(super.get(eserviceID, null).thenApply(item -> item));
    }

    @Override
    public Mono<IndexSignalCounter> updateEntity(IndexSignalCounter counter) {
        counter.setMaxIndexSignal(null);
        return Mono.fromFuture(super.update(counter).thenApply(item -> item));
    }
}
