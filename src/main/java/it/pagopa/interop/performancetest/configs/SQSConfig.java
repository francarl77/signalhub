package it.pagopa.interop.performancetest.configs;

import com.amazonaws.auth.WebIdentityTokenCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import it.pagopa.interop.performancetest.configs.aws.async.AwsConfigs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
public class SQSConfig {

    private final AwsConfigs awsConfigs;

    public SQSConfig(AwsConfigs awsConfigs) {
        this.awsConfigs = awsConfigs;
    }

    @Bean
    public AmazonSQSAsync amazonSQS() {
        if (StringUtils.hasText(awsConfigs.getSqsEndpoint())) {
            return AmazonSQSAsyncClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsConfigs.getSqsEndpoint(), awsConfigs.getRegionCode()))
                    .withCredentials(WebIdentityTokenCredentialsProvider.create())
                    .build();
        } else {
            return AmazonSQSAsyncClientBuilder.standard()
                    .withRegion(awsConfigs.getRegionCode())
                    .build();
        }
    }
}