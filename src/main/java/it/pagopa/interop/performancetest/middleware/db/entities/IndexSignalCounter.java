package it.pagopa.interop.performancetest.middleware.db.entities;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbAtomicCounter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IndexSignalCounter {
    public static final String COL_INDEX_SIGNAL = "maxIndexSignal";
    public static final String COL_ESERVICEID = "eserviceId";


    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute(COL_ESERVICEID)}))
    private String eserviceId;

    @Getter(onMethod = @__({@DynamoDbAtomicCounter, @DynamoDbAttribute(COL_INDEX_SIGNAL)}))
    private Long maxIndexSignal;


}
