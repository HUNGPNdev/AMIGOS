# ValueStreamer Backend Server
This repository contains the backend part of the ValueStreamer application.
* Java 11
* MySQL 8 + MSSQL


# Database
## Liquibase

This project uses liquibase to set up and version the database.
### Contexts
### Liquibase handling
The project contains the Liquibase Maven plugin which wraps up some of the functionality of Liquibase.db-changelog-master.xml

Liquibase Maven can be configured in multiple ways. The project uses configuration via the file `src/main/resources/db/db-changelog-master.xml`


**Document Api**
-  Refer to: http://localhost:8081/swagger-ui.html