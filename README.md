Customer Rewards Project:

This project is spring boot application that calculates rewards points for customers based on their transactions.
Customers gets reward points based on the amount spent on each transaction. The application exposes RESTful endpoint
to retrieve the reward points for a given customer within the given time range.

Features:
* Calculate customer reward points based on transactions amount
* Retrieve monthly and total reward points for a customer
* Handle custom exceptions for better error management
* Log important actions and error messages.

Technologies Used:
* Spring Boot
* Spring Data JPA
* H2 database 
* Junit and Mockito
* java8
* maven

Repo Details: https://github.com/KasammaCh-repo/CustomerRewards-repo/tree/main

API Endpoints:

URL: http://localhost:8082/rewards/getRewardPoints/10001?startDate=2025-01-21&endDate=2025-05-21
Method : GET
Parameters:
1. customerId(path variable) - The id of the customer
2. startDate - tha start date of the time frame
3. endDate - the end date of the time frame

Response:

{
"customerId": "10001",
"totalPoints": 4200,
"monthlyPoints": {
"MARCH": 850,
"FEBRUARY": 1850,
"APRIL": 1500
},
"transactionsList": [
{
"id": 1,
"customerId": "10001",
"amount": 1000.0,
"date": "2025-02-21"
},
{
"id": 2,
"customerId": "10001",
"amount": 500.0,
"date": "2025-03-21"
},
{
"id": 3,
"customerId": "10001",
"amount": 700.0,
"date": "2025-04-21"
},
{
"id": 4,
"customerId": "10001",
"amount": 200.0,
"date": "2025-04-15"
}
]
}

Exception Handling:

This application has custom exception handling for the following scenarios
* CustomerNotFoundException : Throws when the given customer not found
* InvalidDaterangeException: throws when the start date is after the end date

Logging:
Application used SLF4J for logging important actions and errors.
