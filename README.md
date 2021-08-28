# cred-payment


### Event Sourcing



### CQRS (Command Query Responsibility Segregation)

CRQS é um padrão de arquitetura que consiste em separar a responsabilidade de uma aplicação em escrita e leitura:
 Se um método faz alguma modificação no estado de um entidade ele é denominado um command. 
Se um método retorna um valor, ele é denominado uma query.
Geralmente há uma separação física dos modelos, que podem ser gerados com tecnologias diferentes otimizadas para cada cenário. Esse tipo de padrão traz mais complexidade ao domínio e deve ser usado quando fazer sentindo em contextos e partes específicas de uma aplicação.

#### Vantagens: 

A segregação de responsabilidade entre uma aplicação que faz escrita e outra só leitura, permite que elas sejam escaladas independentemente. Também é possível otimizar o modelo de leitura com views especifica, usando um banco desnormalizado por exemplo, enquanto o modelo de escrita concentra as regras de negocio e integridade da aplicação.

#### Desvantagens:
Devido a consistência eventual, pode haver atraso na hora das consultas;
A complexidade pode aumentar significamente. Na utilização de eventos, devem ser considerados tratativas de eventos de compensação em caso de falhas.


### Axon framework
O Axon é um framework que facilita a utilização do padrão CQRS com event store.

#### Inicio do fluxo de criar ordem

**order-service**
1. Uma request para criar uma ordem de transferencia é feita para o **order-service**;
2. OrderCommandController recebe a requisição e publica o CreateOrderCommand no event bus;
3. O Agregate Order, cria um OrderCreatedEvent a partir do comando CreateOrderCommand e publica o evento no event bus;
4. O Agregate Order publica o evento no event store;

![cqrs-axon](https://user-images.githubusercontent.com/13988994/129987745-0515f4e6-573a-4294-a461-ad0462001ab2.png)



