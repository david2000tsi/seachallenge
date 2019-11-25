## SEAChallenge

Implemetação do back-end utilizando Java com base nos dados informados na imagem do font-end.

### Rotas disponíveis:

```
GET     /create_basic_data
GET     /cargo/get/all
GET     /atividade/get/all
GET     /epi/get/all
GET     /funcionario/get/all
GET     /funcionario/get/all/{ativo,inativo}
GET     /funcionario/get/{id}
POST    /funcionario/save
POST    /funcionario/update
DELETE  /funcionario/delete/{id}
```

A rota '/create_basic_data' irá popular as tabelas de Cargos, Atividades e EPIs com dados básicos para testes. Não será inserido funcionários, isso everá ser feito utilizando o json de exemplo mais explicado a seguir.

Para as rotas POST save e update é necessário enviar um json com os dados do funcionario, conforme pode ser visto no exemplo dentro do diretório 'dev_files'.
O pacote json deverá estar codificado antes de ser enviado, dentro do diretório existe os exemplos do json puro e codificado para cada rota.

Existe também dentro de 'dev_files' um arquivo com as requisições salvas, onde podem ser testadas utilizando a aplicação Postman, para simular as requisições.

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

Antes de executar a aplicação verificar os dados de conexão do banco de dados dentro do arquivo de propriedades da aplicação;

Antes de iniciar a aplicação criar o banco de dados manualmente com o nome 'seachallenge';

Iniciar a aplicação, estará disponível no endereço: 'localhost:8090';

Após a inicialização da aplicação, com o banco de dados devidamente preparado, executar a rota '/create_basic_data' para inserir os dados básicos de teste;

Testar as outras rotas, como: '/cargo/get/all', '/atividade/get/all', '/epi/get/all', '/funcionario/get/all', '/funcionario/get/all/ativo', '/funcionario/get/all/inativo';

Inserir um funcionário: '/funcionario/save' utilizando a requisição POST com o json de exemplo;

Testar a busca do funcionário pelo id: '/funcionario/get/1';

Atualizar os dados do funcionário: '/funcionario/update' utilizando a requisição POST com o json de exemplo;

Deletar o funcionário pelo id: '/funcionario/delete/1';

Obs: Após a primeira rodade de teste caso necessário testar deverá ser atualizado os ids dentro dos pacotes json para funcionar novamente.

### Links Úteis:

Editor de json online:

https://jsoneditoronline.org/

Codificador de strings online:

https://onlinestringtools.com/url-encode-string

### A fazer:

Implementar uma pequena aplicação para interagir com a aplicação principal a fim de testes;

Utilizar docker para montar os ambientes de aplicação e banco de dados.
