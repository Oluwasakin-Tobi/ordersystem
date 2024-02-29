# ordersystem

The application has 4 modules

1. cloud config server: This is thee server for the spring cloud config that the clients will have to talk to. It is exposed on port 8888

2. ordertaking: This module exposes an authenticated REST API /orders where an order is being placed. The order is being validated before it is published to the orderapproval module
3. orderapproval: This module listens to messages published from the ordertaking mobule, which in turn persists the order in the database and publish a message for ordernotification module to pick up. It also exposes two authenticated REST APIs, one is for a configured agent to authorize an order, and the other endpoint if to fetch all orders in the system.
4. ordernotification: This module listens to messages from orderapproval module and sends an email via SMTP to an agent.

POSTMAN COLLECTION
https://documenter.getpostman.com/view/8629878/2sA2rGte4G

