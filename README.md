# Promotion_Engine


## Data Model
1.	PRODUCT

	a.	productid (int) 
	
	b.	productname (varchar)
	
	c.	price (double)
	
2.	PROMOTION
	
	a.	promotionid (int)
	
	b.	productid (int) – references PRODUCT.productid
	
	c.	ptype – flat/percentage (nvarchar)
	
	d.	discount (double)
	
	e.	quantity (int)

Please connect to MySql with credentials root/root123 and port 3306. If the server is running on another port, please make the required change in the Repository Classes. Once connected to the server, execute the following queries to recreate the database. 

DROP SCHEMA PROMOTION_ENGINE;

CREATE SCHEMA PROMOTION_ENGINE;

USE PROMOTION_ENGINE;

CREATE TABLE PRODUCT(PRODUCTID INTEGER NOT NULL auto_increment, PRODUCTNAME VARCHAR(100) unique, PRICE DOUBLE,PRIMARY KEY(PRODUCTID));

CREATE TABLE PROMOTION(PROMOTIONID INTEGER NOT NULL,PRODUCTID INTEGER, PTYPE VARCHAR(10), DISCOUNT DOUBLE,PRIMARY KEY(PROMOTIONID,PRODUCTID),QUANTITY INTEGER,FOREIGN KEY (PRODUCTID) REFERENCES PRODUCT(PRODUCTID));

## Classes
1.	POJO Classes

	•	Product – Holds product data to be persisted to the DB and fetched from the DB.

	•	Promotion – Holds promotion data to be persisted to the DB and fetched from the DB.

	•	Cart – Holds cart details that are provided by the user. No DB persistence. 

2.	Repository Classes – Service Classes which interact with the Database

	•	ProductRepository – Service class that maps Product objects to the PRODUCT table
	
	•	PromotionRepository - Service class that maps Promotion objects to the PROMOTION table
	
	•	CartRepository – Service class that calculates Cart item price based on products present in cart and Promotion and Product details fetched from the DB.

3.	Resource Classes
	
	•	ProductResource – Consumes or Produces the REST API for Products

	•	PromotionResource – Consumes or Produces the REST API for Promotions

	•	CartResource - Consumes or Produces the REST API for Cart

## REST API

1.	Create Products – POST: http://localhost:8080/PromotionEngine/webapi/product/


		Payload for Product A:
		{
			"productName":"A",
			"price":50.0
		}

		Payload for Product B:
		{
			"productName":"B",
			"price":30.0
		}

		Payload for Product C:
		{
			"productName":"C",
			"price":20.0
		}

		Payload for Product D:
		{
			"productName":"D",
			"price":15.0
		}

2.	Get List of Products - GET: http://localhost:8080/PromotionEngine/webapi/product/

3.	Get Product by Name – GET: http://localhost:8080/PromotionEngine/webapi/product/{productname}
Example: http://localhost:8080/PromotionEngine/webapi/product/A

4.	Delete Product by Name – DELETE: http://localhost:8080/PromotionEngine/webapi/product/{productname}
Example: http://localhost:8080/PromotionEngine/webapi/product/A

5.	Create Promotion – POST : http://localhost:8080/PromotionEngine/webapi/promotion/

		Payload for Promotion of A considering productId of A is 4:

		[
			{
			"productId":4,
			"promotionType":"flat",
			"discount":130.0,
			"quantity":3
			}
		]

		Payload for Promotion of B considering productId of B is 5:

		[
			{
				"productId":5,
				"promotionType":"flat",
				"discount":45.0,
				"quantity":2
			}
		]

		Payload for Promotion of  C+D considering productId of C is 6 and productId of D is 7 :
		[
			{
				"productId":6,
				"promotionType":"flat",
				"discount":0.0,
				"quantity":1
			},
			{
				"productId":7,
				"promotionType":"flat",
				"discount":30.0,
				"quantity":1
			}
		]

6.	Get List of Promotions - GET : http://localhost:8080/PromotionEngine/webapi/promotion/

7.	Get Promotions by ID - GET : http://localhost:8080/PromotionEngine/webapi/promotion/{promotionid}
Example: http://localhost:8080/PromotionEngine/webapi/promotion/1

8.	Delete Promotions by ID – DELETE: http://localhost:8080/PromotionEngine/webapi/promotion/{promotionid}
Example: http://localhost:8080/PromotionEngine/webapi/promotion/1

9.	Submit Cart Items and Calculate Final Price after Applying Promotions – POST: http://localhost:8080/PromotionEngine/webapi/checkout

		Example Payload for Scenario 1:

		[
			{
				"productId":4,
				"quantity":1
			},
			{
				"productId":5,
				"quantity":1
			},
			{
				"productId":6,
				"quantity":1
			}
		]

		Example Payload for Scenario 2:
		[
			{
				"productId":4,
				"quantity":5
			},
			{
				"productId":5,
				"quantity":5
			},
			{
				"productId":6,
				"quantity":1
			}
		]

		Example Payload for Scenario 3:

		[
			{
				"productId":4,
				"quantity":3
			},
			{
				"productId":5,
				"quantity":5
			},
			{
				"productId":6,
				"quantity":1
			},
			{
				"productId":7,
				"quantity":1
			}
		]


## Assumptions:
1.	One product can be part of one promotion only (Mutually Exclusive).
2.	If multiple products are part of one promotion, multiple entries are made into the PROMOTION table with the same promotionid. Price is stored as 0 for all but the last entry of the promotion which has the Discounted Price.
3.	For CartResourceTest, the data for promotions and products are already added in the DB. 

## Points to Note:
1.	Payloads for Cart items have been given according to the Scenarios provided in the Requirement Document.
2.	IDs for Product Table are auto-generated. After creating products via REST API, the payload for Promotions must be provided with the correct productid.
3.	Use the command - **mvn jetty:run** to start the server.
