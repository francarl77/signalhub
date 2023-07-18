package it.pagopa.interop.performancetest.middleware.db.dao.impl;

import it.pagopa.interop.performancetest.configs.aws.async.AwsConfigs;
import it.pagopa.interop.performancetest.middleware.db.dao.IndexSignalCounterDAO;
import it.pagopa.interop.performancetest.middleware.db.dao.SignalDAO;
import it.pagopa.interop.performancetest.middleware.db.dao.common.BaseDAO;
import it.pagopa.interop.performancetest.middleware.db.entities.IndexSignalCounter;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactPutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactWriteItemsEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class SignalDaoImpl extends BaseDAO<Signal> implements SignalDAO {

    @Autowired
    private IndexSignalCounterDAO indexSignalCounterDAO;

    public SignalDaoImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient, DynamoDbAsyncClient dynamoDbAsyncClient, AwsConfigs awsConfigs) {
        super(dynamoDbEnhancedAsyncClient, dynamoDbAsyncClient, awsConfigs.getDynamodbSignalTable(), Signal.class);
    }

    @Override
    public Mono<Signal> pushSignal(Signal signal) {
        return this.indexSignalCounterDAO.get(signal.getEserviceId())
                .switchIfEmpty(Mono.just(new IndexSignalCounter(signal.getEserviceId(), -1L)))
                .flatMap(indexSignalCounter ->  {
                    TransactWriteItemsEnhancedRequest.Builder builder =
                            TransactWriteItemsEnhancedRequest.builder();

                    signal.setSignalId(UUID.randomUUID().toString());
                    signal.setIndexSignal(indexSignalCounter.getMaxIndexSignal() + 1);
                    signal.setTmsInsert(Instant.now());

                    this.addPutTransaction(builder, signal);
                    this.indexSignalCounterDAO.updateWithTransaction(builder, indexSignalCounter);

                    return Mono.fromFuture(super.putWithTransact(builder.build()).thenApply(item -> signal));
                });
    }

    @Override
    public Flux<Signal> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType) {
        QueryConditional conditional = CONDITION_EQUAL_TO.apply(keyBuild(eserviceId, indexSignal));
        Map<String, AttributeValue> attributes = new HashMap<>();
        attributes.put(":eserviceId", AttributeValue.builder().s(eserviceId).build());
        attributes.put(":indexSignal", AttributeValue.builder().s(String.valueOf(indexSignal)).build());
        return this.getByFilter(conditional,null, attributes,null);
    }


    private void addPutTransaction(TransactWriteItemsEnhancedRequest.Builder builder, Signal signal) {
        TransactPutItemEnhancedRequest<Signal> updateItemEnhancedRequest =
                TransactPutItemEnhancedRequest.builder(Signal.class)
                        .item(signal)
                        .build();
        builder.addPutItem(this.dynamoTable, updateItemEnhancedRequest);
    }
}
