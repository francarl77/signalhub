package it.pagopa.interop.performancetest.configs.aws.async;

import io.awspring.cloud.sqs.config.SqsBootstrapConfiguration;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.utils.StringUtils;

import java.net.URI;

@Import(SqsBootstrapConfiguration.class)
@Configuration
@Slf4j
public class AwsServicesClientsConfig {

    private final AwsConfigs props;

    public AwsServicesClientsConfig(AwsConfigs props) {
        this.props = props;
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return configureBuilder( DynamoDbClient.builder(), props.getDynamodbEndpoint());
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return configureBuilder(SqsAsyncClient.builder(), props.getSqsEndpoint());
    }

    @Bean
    public SqsTemplate sqsTemplate() {
        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient())
                .configure(options -> options.defaultQueue(props.getInternalQueueName()))
                .build();
    }

    @Bean
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory() {
        return SqsMessageListenerContainerFactory
                .builder()
                .configure(options -> options
                        .maxConcurrentMessages(10)
                        .maxMessagesPerPoll(10))
                .sqsAsyncClient(sqsAsyncClient())
                .build();
    }

    private <C> C configureBuilder(AwsClientBuilder<?, C> builder, String endpoint) {
        if( props != null ) {

            String profileName = props.getProfileName();
            if( StringUtils.isNotBlank( profileName ) ) {
                builder.credentialsProvider( ProfileCredentialsProvider.create( profileName ) );
            } else {
                log.debug("Using WebIdentityTokenFileCredentialsProvider");
                builder.credentialsProvider( WebIdentityTokenFileCredentialsProvider.create() );
            }

            String regionCode = props.getRegionCode();
            if( StringUtils.isNotBlank( regionCode )) {
                log.debug("Setting region to: {}", regionCode);
                builder.region( Region.of( regionCode ));
            }

            if( StringUtils.isNotBlank( endpoint )) {
                builder.endpointOverride(URI.create(endpoint));
            }

        }

        return builder.build();
    }

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        return configureBuilder( DynamoDbAsyncClient.builder(), props.getDynamodbEndpoint() );
    }


    @Bean
    public DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient( DynamoDbAsyncClient baseAsyncClient) {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient( baseAsyncClient )
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient( DynamoDbClient baseClient ) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient( baseClient )
                .build();
    }

}
