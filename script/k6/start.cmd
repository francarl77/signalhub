podman-compose up -d influxdb
podman-compose run --rm k6 run /scripts/push_test.js -u500 -d10s -e TEST=1-mvc-sqs -e URL=http://internal-signalhub-lb-368682252.eu-south-1.elb.amazonaws.com/interop-signal-hub/push-signal-sqs
podman-compose run --rm k6 run /scripts/push_test.js -u500 -d10s -e TEST=1-webflux-sqs -e URL=http://internal-signalhub-lb-368682252.eu-south-1.elb.amazonaws.com/relational/push-signal


podman-compose run --rm k6 run /scripts/push_test.js -u500 -d10s -e TEST=1-webflux-sqs -e URL=http://localhost:8080/relational/push-signal
podman-compose run --rm k6 run /scripts/push_test.js -u100 -d5s -e TEST=1-webflux-sqs-ok -e URL=http://localhost:8080/relational/push-signal


k6 run .\script\k6\tests\push_test.js -u500 -d300s -e URL=http://localhost:8080/relational/push-signal --out json=script\k6\results\test2.json.gz
k6 run .\script\k6\tests\pull_test.js -u500 -d10s -e URL=http://localhost:8080/relational/pull-signal --out json=script\k6\results\test1.json.gz

SU BASTION
k6 run push_test.js -u500 -d10s -e TEST=1-webflux-sqs -e URL=http://internal-signalhub-lb-368682252.eu-south-1.elb.amazonaws.com/relational/push-signal
k6 run pull_test.js -u500 -d10s -e URL=http://internal-signalhub-lb-368682252.eu-south-1.elb.amazonaws.com/relational/pull-signal


kubectl -n dev port-forward service/postgres 5432:5432
kubectl -n dev rollout restart deployment/signalhub-deployment
kubectl -n dev exec -it postgres-7f8f6cb5f7-dz6np -- bash
