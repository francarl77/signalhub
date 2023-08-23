aws --profile default --region us-east-1 --endpoint-url http://localstack:4566 \
    sqs create-queue \
    --queue-name signalhub-sqs


