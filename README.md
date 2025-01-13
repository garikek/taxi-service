# Required configuration

## Keycloak configuration

In file `C:\Windows\System32\drivers\etc\hosts`

Add line `127.0.0.1 keycloak`

## Stripe

1. Create stripe account
2. Get secret key

## Environmental variables

Add environmental variables from `docker-compose.yml`

# Launch app

```shell
docker-compose up
```

# Swagger

* [auth-service](http://localhost:8086/swagger-ui/index.html)
* [passenger-service](http://localhost:8081/swagger-ui/index.html)
* [driver-service](http://localhost:8082/swagger-ui/index.html)
* [ride-service](http://localhost:8083/swagger-ui/index.html)
* [rating-service](http://localhost:8084/swagger-ui/index.html)
* [payment-service](http://localhost:8085/swagger-ui/index.html)

# Dashboards

* [Grafana](http://localhost:3000)
* [Kibana](http://localhost:5601/app/home#/)
* [Keycloak](http://localhost:8180/)
* [Eureka](http://localhost:8761/)
* [RabbitMQ](http://localhost:15672/)
* [Prometheus](http://localhost:9090/query)
* [Elasticsearch](http://localhost:9200/)
* [Stripe](https://dashboard.stripe.com/test/workbench/overview)
