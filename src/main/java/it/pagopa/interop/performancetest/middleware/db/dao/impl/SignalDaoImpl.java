package it.pagopa.interop.performancetest.middleware.db.dao.impl;

import it.pagopa.interop.performancetest.configs.aws.async.AwsConfigs;
import it.pagopa.interop.performancetest.middleware.db.dao.common.BaseDAO;
import it.pagopa.interop.performancetest.middleware.db.entities.Signal;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Repository
public class SignalDaoImpl extends BaseDAO<Signal> implements SignalDAO {


    public SignalDaoImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient, DynamoDbAsyncClient dynamoDbAsyncClient, AwsConfigs awsConfigs) {
        super(dynamoDbEnhancedAsyncClient, dynamoDbAsyncClient, awsConfigs.getDynamodbSignalTable(), Signal.class);
    }

    @Override
    public Mono<Signal> pushSignal(Signal signal) {
        //signal.setIndexSignal(null);
        return Mono.fromFuture(this.updateFromEntity(signal).thenApply(item -> item));
    }

    @Override
    public Flux<Signal> pullSignal(Long indexSignal, String eserviceId, String signalType, String objectType) {
        QueryConditional conditional = CONDITION_EQUAL_TO.apply(keyBuild(eserviceId, indexSignal));
        return this.getByFilter(conditional,Signal.ESERVICE_INDEX,null,null);
    }
}
