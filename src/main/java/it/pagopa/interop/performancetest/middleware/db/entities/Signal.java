package it.pagopa.interop.performancetest.middleware.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;

@DynamoDbBean
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Signal {

    public static final String COL_SIGNALID = "signalId" ;
    public static final String COL_INDEX_SIGNAL = "indexSignal";
    public static final String COL_OBJECTID = "objectId";
    public static final String COL_ESERVICEID = "eserviceId";
    public static final String COL_OBJECT_TYPE = "objectType";
    public static final String COL_SIGNAL_TYPE = "signalType";
    public static final String COL_TMS_INSERT = "tmsInsert";
    public static final String COL_NODE_PRODUCER = "nodeProducer";
    public static final String COL_NODE_CONSUMER = "nodeConsumer";


    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute(COL_ESERVICEID)}))
    private String eserviceId;

    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute(COL_INDEX_SIGNAL)}))
    private Long indexSignal;

    @Getter(onMethod = @__({@DynamoDbAttribute(COL_OBJECTID)}))
    private String objectId;

    @Getter(onMethod = @__({@DynamoDbAttribute(COL_SIGNALID)}))
    private String signalId;

    @Getter(onMethod = @__({@DynamoDbAttribute(COL_OBJECT_TYPE)}))
    private String objectType;

    @Getter(onMethod = @__({@DynamoDbAttribute(COL_SIGNAL_TYPE)}))
    private String signalType;

    @Getter(onMethod = @__({@DynamoDbAttribute(COL_TMS_INSERT)}))
    private Instant tmsInsert;

}
