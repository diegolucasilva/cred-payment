# cred-payment


### Event Sourcing



### CQRS (Command Query Responsibility Segregation)

CRQS é uma padrão de arquitetura onde é segregado a responsabilidade de escrita e leitura da aplicação. 


Query handler
Utiliza a base 


### Axon framework
O Axon é um framework que facilita a utilização do padrão CQRS com event store.

#### Inicio do fluxo de criar ordem

**order-service**
1. Uma request para criar uma ordem de transferencia é feita para o **order-service**;
2. OrderCommandController recebe a requisição e publica o CreateOrderCommand no event bus;
3. O Agregate Order, cria um OrderCreatedEvent a partir do comando CreateOrderCommand e publica o evento no event bus;
4. O Agregate Order publica o evento no event store;




