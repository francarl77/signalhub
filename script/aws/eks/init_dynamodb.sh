aws --region eu-south-1 \
    dynamodb create-table \
    --table-name Signal \
    --attribute-definitions \
        AttributeName=indexSignal,AttributeType=N \
        AttributeName=eserviceId,AttributeType=S \
    --key-schema \
        AttributeName=eserviceId,KeyType=HASH \
        AttributeName=indexSignal,KeyType=RANGE \
    --provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5

aws --region eu-south-1 \
    dynamodb create-table \
    --table-name IndexSignalCounter \
    --attribute-definitions \
        AttributeName=eserviceId,AttributeType=S \
    --key-schema \
        AttributeName=eserviceId,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5