podman-compose up -d influxdb
podman-compose run --rm k6 run /scripts/load_test.js -u500 -d10s -e TESTNUMBER=2 -e URL=http://internal-signalhub-lb-368682252.eu-south-1.elb.amazonaws.com/interop-signal-hub/push-signal-sqs
podman-compose run --rm k6 run /scripts/load_test.js -u500 -d10s -e TESTNUMBER=2 -e URL=http://internal-signalhub-lb-368682252.eu-south-1.elb.amazonaws.com/relational/push-signal