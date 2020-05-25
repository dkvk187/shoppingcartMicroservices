===================================================================================
							ShoppingCartMicroservices
===================================================================================


--Java 1.8
--Spring Boot 2.3.0
--spring-boot-starter-activemq
--spring-boot-starter-data-jpa
--Junit 5
--h2


-- In-memory activemq used for queue
-- In-memory h2 database used to persist queue message

===============================================
	Post services to send message to queue
===============================================

				============
				Request URL:
				============
				http://localhost:8081/rest/mapping/shoppingDetails

				================
				Request Headers:
				================
				Content-Type: application/json

				=============
				Request Body:
				=============

				{
				  "id": 5,
				  "name":"USB 4GB Samsung",
				  "category": "Electronics",
				  "price": 45.00
				}
				


