# Comandi per la creazione del cluster

export AWS_PROFILE=interop-dev
export AWS_DEFAULT_PROFILE=interop-dev
eksctl create cluster --name dev-signalhub-cluster -f signalhub-fargate-profile.yaml

kubectl create secret docker-registry ghcr-login-secret --docker-server=https://ghcr.io \
    --docker-username=francarl77 --docker-password=xxx --docker-email=xxx -n dev

eksctl utils associate-iam-oidc-provider --cluster dev-signalhub-cluster --approve

curl -O https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/v2.4.7/docs/install/iam_policy.json
aws iam create-policy \
    --policy-name AWSLoadBalancerControllerIAMPolicy \
    --policy-document file://iam_policy.json

eksctl create iamserviceaccount \
    --cluster=dev-signalhub-cluster \
    --namespace=kube-system \
    --name=aws-load-balancer-controller \
    --role-name AmazonEKSLoadBalancerControllerRole \
    --attach-policy-arn=arn:aws:iam::#aws account id#:policy/AWSLoadBalancerControllerIAMPolicy \
    --approve

helm install aws-load-balancer-controller eks/aws-load-balancer-controller -n kube-system \
    --set clusterName=dev-signalhub-cluster --set serviceAccount.create=false \
    --set serviceAccount.name=aws-load-balancer-controller \
    --set region=eu-south-1 --set vpcId=#vpc-id#

eksctl create iamserviceaccount --name signalhub-serviceaccount --namespace dev \
    --cluster dev-signalhub-cluster \
    --attach-policy-arn arn:aws:iam::aws:policy/AmazonDynamoDBFullAccess \
    --approve

kubectl apply -n dev -f microsvc-deployment.yaml
kubectl apply -n dev -f signalhub-service.yaml
kubectl apply -n dev -f signalhub-ingress.yaml

init_dynamodb.sh

# Per connettersi esternamente dal bastion

$ kubectl -n dev get ingress
NAME                CLASS    HOSTS   ADDRESS                                                        PORTS   AGE
signalhub-ingress   <none>   *       internal-signalhub-lb-368682252.eu-south-1.elb.amazonaws.com   80      4s


Lanciare sulla proprio macchina

aws --profile interop-dev ssm start-session --target "i-00debf9c2a006d2ea" --document-name AWS-StartPortForwardingSessionToRe
moteHost --parameters "{\"portNumber\":[\"80\"],\"localPortNumber\":[\"8888\"],\"host\":[\"internal-signalhub-lb-368682252.eu-s
outh-1.elb.amazonaws.com\"]}
