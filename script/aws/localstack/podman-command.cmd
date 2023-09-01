podman run -d -p "4566-4583:4566-4583" -e SERVICES=lambda,s3,sqs,dynamodb -v ./credentials:/root/.aws/credentials -v ./init.sh:/docker-entrypoint-initaws.d/make-storages.sh --name localstack localstack/localstack:latest
podman exec -it localstack bash
aws --profile default --region us-east-1 --endpoint-url http://localstack:4566 \
    sqs create-queue \
    --queue-name internal-queue
aws --profile default --region us-east-1 --endpoint-url http://localstack:4566     sqs get-queue-attributes --queue-url http://localhost:4566/000000000000/internal-queue --attribute-names All

podman run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=m0mtZ7p2DxoqE8IOqzx9 -e POSTGRES_DB=signal_hub_db -d postgres