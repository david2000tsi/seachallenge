## SEAChallenge

Implemetação do back-end utilizando Java com base nos dados informados na imagem do front-end.

### Criação do container de banco de dados

Dentro da pasta raiz do projeto, executar o seguinte comando para criar o container do banco de dados postgres:

```
docker-compose up --build -d
```

### Compilação da aplicação

Abrir um terminal dentro do diretório do código fonte do projeto 'source/';

Compilar:

```mvn clean install```

Executar:

```
java -jar target/seachallenge-0.0.1-SNAPSHOT.jar
```

### Rotas disponíveis

```
GET     /test/create-data
GET     /cargo/get
GET     /atividade/get
GET     /epi/get
GET     /funcionario/get
GET     /funcionario/get/{ativo=true/false}
GET     /funcionario/get/{id}
POST    /funcionario/create
POST    /funcionario/create-atividade-api
PUT     /funcionario/update
DELETE  /funcionario/delete/{id}
```

A rota '/test/create-data' irá popular as tabelas de Cargos, Atividades e EPIs com dados básicos para testes.

Para as rotas POST/PUT (create e update) é necessário enviar um json no corpo da requisição (body request) com as informações.

Dentro do diretório 'dev_files' um arquivo com as requisições salvas, onde podem ser testadas utilizando a aplicação Postman, para simular as requisições.

### Ambiente

Desenvolvido em ambiente Linux utilizando:

```
Java 1.8.0_191
Maven 3.6.2
Spring Tool Suite 4 v4.3.2.RELEASE
PostgreSQL 9.5.14
Postman 7.12.0
```

### Ordem de execução do teste

Antes de executar a aplicação iniciar primeiro o container do banco de dados;

Iniciar a aplicação, estará disponível no endereço: 'localhost:8090';

Após a inicialização da aplicação, com o banco de dados devidamente preparado, executar a rota '/test/create-data' para inserir os dados básicos de teste;

Testar as outras rotas, como: '/cargo/get', '/atividade/get', '/epi/get', '/funcionario/get', '/funcionario/get?ativo=true', '/funcionario/get?ativo=false';

Inserir um funcionário: '/funcionario/create' utilizando a requisição POST (body request);

Testar a busca do funcionário pelo id: '/funcionario/get/1';

Atualizar os dados do funcionário: '/funcionario/update' utilizando a requisição PUT (body request);

Deletar o funcionário pelo id: '/funcionario/delete/1';

### Links Úteis

Editor de json online:

https://jsoneditoronline.org/

Codificador de strings online:

https://onlinestringtools.com/url-encode-string

### A fazer

Implementar uma pequena aplicação para interagir com a aplicação principal a fim de testes;

Utilizar docker para montar o ambiente da aplicação.
