# Backend Template for SEPM Group Phase

## How to run it

### Start the backed
`mvn spring-boot:run`

### Start the backed with test data
If the database is not clean, the test data won't be inserted

`mvn spring-boot:run -Dspring-boot.run.profiles=generateData`

### Access the database
If a direct view on the data saved in the database is needed, do the following steps:
 - Start the Backend Application
 - Open http://localhost:8080/v1/h2-console/
 - Use the JDBC-URL: jdbc:h2:./backend/database/db
 - Enter the login credentials as configured in application.yml