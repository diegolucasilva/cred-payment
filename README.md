# cred-payment


### Event Sourcing



### CQRS (Command Query Responsibility Segregation)

CRQS é uma padrão de arquitetura onde é segregado a responsabilidade de escrita e leitura da aplicação. 


### Axon framework
O Axon é um framework que facilita a utilização do padrão CQRS com event store.

#### Inicio do fluxo de criar ordem

**order-service**
1. Uma request para criar uma ordem de transferencia é feita para o **order-service**;
2. OrderCommandController recebe a requisição e publica o CreateOrderCommand no event bus;
3. O Agregate Order, cria um OrderCreatedEvent a partir do comando CreateOrderCommand e publica o evento no event bus;
4. O Agregate Order publica o evento no event store;

![cqrs-axon](https://user-images.githubusercontent.com/13988994/129987745-0515f4e6-573a-4294-a461-ad0462001ab2.png)



