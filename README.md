## POSTECH_SOAT_SAGAPATTERN

### Serviços que fazem parte da solução:
* fiapstore-pedido-service
* fiapstore-cobranca-service-
* fiapstore-entrega-service

### Tecnologias utilizadas
* Linguagem: Java
* Framework: SpringBoot
* Mensageria: RabbitMQ
* Orquestração: Apache Camel

## Diagrama saga coreografada
<img src="https://github.com/eumagnun/fiap-store-pedido-service/blob/documentacao/apoio-padr%C3%A3o-saga-demo-coreografia.jpg?raw=true" width="50%" >

## Diagrama saga orquestrada
<img src="https://github.com/eumagnun/fiap-store-pedido-service/blob/documentacao/apoio-padr%C3%A3o-saga-demo-orquestraca%C3%A7%C3%A3o.jpg?raw=true" width="50%" >


Subindo o RabbitMQ
````
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management
````

Acessando o console do RabbitMQ
````
admin: http://localhost:15672/
user: guest
pass: guest
````
