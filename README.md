# Mini Autorizador - API

Esta API permite realizar as seguintes ações

* a criação de cartões (todo cartão deverá ser criado com um saldo inicial de R$500,00)
* a obtenção de saldo do cartão
* a autorização de transações realizadas usando os cartões previamente criados como meio de pagamento

## Requerimentos

* Java openJDK17
* MySQL 5.7

## Configuração

### Variáveis de ambiente:

  * `DB_URL` (url do banco de dados)
  * `DB_USERNAME` (usuário do banco de dados)
  * `DB_PASSWORD` (senha do banco de dados)
  * `SHOW_SQL` (`true` para exibir queries no log)


### Banco de dados

  * O banco de dados pode ser executado usando `docker`
    ```shell
    docker-compose ./docker/docker-compose.yml up -d
    ```
## Documentação

### Serviços
```
POST /cartoes (criar cartão)
GET /cartoes/{numerocartao} (ver saldo do cartão)
POST /transacoes (realizar transações)
```
