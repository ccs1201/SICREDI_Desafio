# Desafio técnico Sicredi

#### CSouza 23/08/2022

### Stack Utilizada

* JAVA *17*
* Spring Boot *2.7.3*
* Spring Doc OpenAPI *1.6.10*
* FlyWayDB *9.1.6*
* ModelMapper *3.1 0*
* MySQL *8*
* MAVEN

# Como "Buildar" este projeto
Baixe o código fonte na IDE de sua preferência e importe o projeto pelo arquivo *pom.xml* disponível na raiz do projeto.

Tenha uma instância do SGDB *MySQL* ativa para conexão. 

*ATENÇÃO:* O fonte está configurado para criar uma base
de dados com nome *sicredi* automaticamente no startup, se isto for um problema altere a propriedade *spring.datasource.url*
removendo o parâmetro *createDatabaseIfNotExist=true* no arquivo *src/main/resources/configuration.properties*.

Da mesma forma para modificar a *URL* de conexão com o banco de dados altere a configuração do parâmetro 
*spring.datasource.url* com os dados necessários para conexão a sua data base.

Ao inicializar a aplicação o *FlyWay* irá checar, criar ou atualizar o schema conforme necessário.

O tomcat embbeded esta configurado para rodar na porta *8080* a documanetação da api está em *http://host:8080/docs.html*

##### PS. Um cooperado só poderá votar se estiver cadastrado, por isso insira alguns *CPFs* fake para poder testar o endpoint de votação. Ou Simplesmente remova o "-" (underscore) do arquivo *afterMigrate.sql* que encontra-se em: *src/main/resources/db/migration/testdata* não esquece de comental-lô novamente após o primeiro startup, pois o FlyWay carrega este arquivo todo vez que a aplicação for iniciada, como a coluna CPF tem uma restrição *UNIQUE* uma nova tentativa de inserção pelo FlyWay causá uma exception impedindo a aplicação de startar.

### That's it, have any question? Ficou com dúvidas follow me/siga-me linkedin ->  <a href="https://www.linkedin.com/in/ccs1201/">Cleber Souza<a/>


# Enjoy ;)
