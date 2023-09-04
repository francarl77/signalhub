aws --profile default --region us-east-1 --endpoint-url http://localstack:4566 \
    sqs create-queue \
    --queue-name signalhub-sqs

aws --profile interop-dev --region eu-south-1 \
    dynamodb delete-table \
    --table-name Signal

# aws --profile interop-dev --region eu-south-1 \
aws --profile default --region us-east-1 --endpoint-url http://localstack:4566 \
    dynamodb create-table \
    --table-name Signal \
    --attribute-definitions \
        AttributeName=signalId,AttributeType=N \
        AttributeName=eserviceId,AttributeType=S \
    --key-schema \
        AttributeName=eserviceId,KeyType=HASH \
        AttributeName=signalId,KeyType=RANGE \
    --provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5

#aws --profile interop-dev --region eu-south-1 \
aws --profile default --region us-east-1 --endpoint-url http://localstack:4566 \
    dynamodb create-table \
    --table-name IndexSignalCounter \
    --attribute-definitions \
        AttributeName=eserviceId,AttributeType=S \
    --key-schema \
        AttributeName=eserviceId,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=10,WriteCapacityUnits=5

aws --profile interop-dev dynamodb scan --table-name Signal --select "COUNT"