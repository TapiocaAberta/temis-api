# TÊMIS-SERVER

![alt tag](http://files.deuseseherois.webnode.com.br/system_preview_detail_200000053-8ee8990dc5/T%C3%AAmis.jpg)

### Quem foi Têmis?

Têmis era a deusa guardiã dos juramentos dos homens e da lei, sendo que era costumeiro invocá-la nos julgamentos perante os magistrados.

Têmis empunha a balança, com que equilibra a razão com o julgamento, e/ou uma cornucópia. Seu nome significa "aquela que é posta, colocada".

Fonte: http://deuseseherois.webnode.com.br/products/t%C3%AAmis/

### O projeto

Neste projeto, criamos um "chupa-cabra" e raspamos as Leis Ordinárias presentes na página [http://www.ceaam.net/sjc/legislacao/](http://www.ceaam.net/sjc/legislacao/). Com esses dados, expomos esses dados através de uma API rest.

### O que usamos?

* Java 8.X;
* Java EE 7;
* Wildfly 10.X
* Maven 3.x;
* JPA;
* MySQL DB;
* JMS;
* JSoup

### Como executar?

Os passos para instalar e executar o projeto são descritos abaixo:

#### Executando o Wildfly

**Tenha em mente o uso de Maven e Wildfly (um guia para começar pode ser encontrado [aqui](http://aprendendo-javaee.blogspot.com.br/2014/01/ola-mundo-java-web-com-maven-e-wildfly.html). Após instalado Java 8, Maven e Wildfly.**

*Precisamos utilizar a configuração `standalone-full` por conta de utilizarmos `JMS` e ele pede esta configuração.*

```bash
Executando em Linux:  WILDFLY_HOME/bin/standalone.sh -c standalone-full.xml
Executando em Windows:  WILDFLY_HOME\bin\standalone.bat -c standalone-full.xml
```
#### Configurando a fila JMS e o Módulo MySQL

* H2

Você pode utilizar o H2 como banco, para isso basta alterar o arquivo **temis-api/src/resources/META-INF/persistence.xml** e comentar a linha do `MySQL` e descomentar a linha do `H2`, como mostrado a seguir:

```xml
<!--<jta-data-source>java:jboss/datasources/MySQLDS</jta-data-source>-->
<jta-data-source>java:jboss/datasources/ExampleDS</jta-data-source>
```
Caso opte por usar o H2 você pode apenas adicionar o fila executando este comando:

```bash
sh WILDFLY_HOME/bin/jboss-cli.sh -c --command="jms-queue add --queue-address=classificaLeiQueue --entries=java:/jms/queue/classifica"
```
* MySQL

**Não se esqueça de criar o database 'temis' no Mysql**

Os passos são bem simples, primeiro edite o arquivo `temis-api/config/configure-jms-mysql.cli` e alterar o path de `--resources=/caminho-para-o-connector/mysql-connector-java-5.1.41-bin.jar` para o path do connector, presente na pasta `conf` deste projeto, edite também o usuario e senha no arquivo:

```bash
...
/subsystem=datasources/data-source=mysql:add(driver-name=com.mysql, jndi-name="java:jboss/datasources/MySQLDS", enabled=true, connection-url="jdbc:mysql://localhost:3306/temis", username=root, password=root)
...
```

Com os passos acima completo, execute o seguinte comando:

```bash
sh WILDFLY_HOME/bin/jboss-cli.sh -c --file="temis-api/config/configure-jms-mysql.cli"
```

Caso tudo ocorra, você poderá ver a seguinte mensagem:

```bash
The batch executed successfully
The batch executed successfully
```
#### Deploy da aplicação

Primeiro, na pasta do projeto, execute o seguinte comando maven:

`mvn clean install`

Em seguida, faça o deploy:

```bash
sh WILDFLY_HOME/bin/jboss-cli.sh -c --command="deploy temis-api/target/ROOT.war"
```
Caso tudo ocorra bem você deverá ver nos logs a criação de vereadores e leis.

### URIs

Em desenvolvimento

contato: pedro-hos@outlook.com
