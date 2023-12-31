package it.pagopa.interop.performancetest.configs.aws.async;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("aws")
public class AwsConfigs {

    private String profileName;
    private String regionCode;
    private String bucketName;
    private String sqsEndpoint;
    private String dynamodbSignalTable;
    private String dynamodbIndexSignalCounterTable;
    private String internalQueueName;
    private String podNameMs;
}
