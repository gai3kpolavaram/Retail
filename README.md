# Retail
Simple rest APIs for merchants to manage offers 

Assumptions:
1. Merchant is already Authenticated and Authorized
2. A different controller inserts the merchant-id(long) as a part of URI for the calls made. 
3. 1 to many relationships between the merchant and offers
4. The offers created are in memory and are not persisted. The offers get dropped off when the application is restarted. 

Goto the [check-out] directory

Run the following command to build:
./gradlew build

Run the following command to start the application

java -jar build/libs/retail-offer-0.1.0.jar

For sample calls refer the postman collection in PostmanCollection folder.

URI:
POST: http://localhost:8080/merchant/{mechantID}/offer
Request Headers: 
Content-Type:application/json
Accept-Language:application/json

Request body:
{

  "name": "[offername]",
  "description": "[offer description]",
  "currency": "[valid currency code - java.util.Currency]",
  "offerPrice": [offer price]

}

Success Response Code
201

Response Header: 

Location : [URI of the created resource]


