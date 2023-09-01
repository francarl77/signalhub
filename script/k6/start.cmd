podman-compose up -d influxdb
podman-compose run --rm k6 run /scripts/load_test.js -u500 -d10s -e TEST=1-mvc-sqs -e URL=http://internal-signalhub-lb-368682252.eu-south-1.elb.amazonaws.com/interop-signal-hub/push-signal-sqs
podman-compose run --rm k6 run /scripts/load_test.js -u500 -d10s -e TEST=1-webflux-sqs -e URL=http://internal-signalhub-lb-368682252.eu-south-1.elb.amazonaws.com/relational/push-signal


podman-compose run --rm k6 run /scripts/load_test.js -u500 -d10s -e TEST=1-webflux-sqs -e URL=http://localhost:8080/relational/push-signal
podman-compose run --rm k6 run /scripts/load_test.js -u100 -d5s -e TEST=1-webflux-sqs-ok -e URL=http://localhost:8080/relational/push-signal-async