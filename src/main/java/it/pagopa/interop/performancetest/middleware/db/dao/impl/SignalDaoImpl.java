package it.pagopa.interop.performancetest.middleware.db.dao.impl;

import it.pagopa.interop.performancetest.configs.aws.async.AwsConfigs;
import it.pagopa.interop.performancetest.middleware.db.dao.SignalDAO;
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

    private AwsConfigs props;

    public SignalDaoImpl(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient, DynamoDbAsyncClient dynamoDbAsyncClient, AwsConfigs awsConfigs) {
        super(dynamoDbEnhancedAsyncClient, dynamoDbAsyncClient, awsConfigs.getDynamodbSignalTable(), Signal.class);
        this.props = awsConfigs;
    }

    @Override
    public Mono<Signal> pushSignal(Signal signal) {
        return Mono.fromFuture(super.put(signal));
    }

    @Override
    public Flux<Signal> pullSignal(Long lastSignalId, String eserviceId) {
        QueryConditional conditional = CONDITION_GREATHER_THAN_EQUAL_TO.apply(keyBuild(eserviceId, lastSignalId));

        return this.getByFilter(conditional,null, null,null, props.getPullLimit().intValue());
    }
}
