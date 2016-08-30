# TÊMIS-SERVER

![alt tag](http://files.deuseseherois.webnode.com.br/system_preview_detail_200000053-8ee8990dc5/T%C3%AAmis.jpg)

### Quem foi Têmis?

Têmis era a deusa guardiã dos juramentos dos homens e da lei, sendo que era costumeiro invocá-la nos julgamentos perante os magistrados.

Têmis empunha a balança, com que equilibra a razão com o julgamento, e/ou uma cornucópia. Seu nome significa "aquela que é posta, colocada".

Fonte: http://deuseseherois.webnode.com.br/products/t%C3%AAmis/

### O projeto

Neste projeto, criamos um "chupa-cabra" e raspamos as Leis Ordinárias presentes na página [http://www.ceaam.net/sjc/legislacao/](http://www.ceaam.net/sjc/legislacao/). Com esses dados, expomos esses dados através de uma API rest.

### O que usamos?

* Java 8;
* Spring-boot;
* Maven;
* JPA;
* MongoDB;
* Camel;
* JSoup

### URIs

Api foi hospedada no Heroku, usando MLabs para armazenar os dados no Mongo. Todos serviços estão presentes em:
[http://temis-server.herokuapp.com/api/](http://temis-server.herokuapp.com/api/)

URI | Descrição | _links
:------------: | :-------------: | :------------:
http://temis-server.herokuapp.com/api/laws| Acessa todas as leis, paginadas, com tamanho de 20 | self, first, next e last |
http://temis-server.herokuapp.com/api/laws?page={n}| Acessa todas as leis, paginadas, de uma determinada página com tamanho de 20 | self, first, next e last |
http://temis-server.herokuapp.com/api/laws?page={n}&size={n}| Acessa todas as leis, paginadas, de uma determinada página e tamanho | self, first, next e last |
http://temis-server.herokuapp.com/api/laws/alderman/{name}| Acessa todas as leis, paginadas, com tamanho de 20 | self, first, next e last |
http://temis-server.herokuapp.com/api/laws/alderman/{name}?page={n}| Acessa todas as leis, paginadas, de uma determinada página com tamanho de 20 | self, first, next e last |
http://temis-server.herokuapp.com/api/laws/alderman?/{name}page={n}&size={n}| Acessa todas as leis, paginadas, de uma determinada página e tamanho | self, first, next e last |
http://temis-server.herokuapp.com/api/alderman| Acessa todas os vereadores. | leis (leis do vereador), self |

Método 	  | URI									  | Retorno 
:-----:	  | :-------------------------------------| :------------------
GET		    | /api/laws                           |  Status 200 e um JSON com uma lista Papers.

contato: [pedro-hos@outlook.com](pedro-hos@outlook.com)