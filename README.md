# My Spring Boot Bookstore Application

This is a README file for  My SpringBoot Bookstore Application project.

## Source Code

The source code for this application is available in the [GitHub repository](https://github.com/Aamaka/bookStore).

To obtain the source code, you can either clone the repository using Git:

```shell
git clone https://github.com/Aamaka/bookStore
````

## Development Environment Setup
To set up the development environment for this application, follow these steps:

1. Install Java Development Kit (JDK) version 17.

2. Install an Integrated Development Environment (IDE) such as IntelliJ IDEA or Eclipse.

3. Ensure that you have Apache Maven installed as the build tool.

## Running the Application Locally
To run the application locally, follow these steps:

1. Open the project in your chosen IDE.

2. Build the project using Maven to download the required dependencies.

````shell
    mvn clean install
````
3. Configure the database connection details in the application's configuration file (application.properties).

````properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.banner.image.location=banner.txt
pay_stack_initialize_url=https://api.paystack.co/transaction/initialize
pay_stack_verification_url=https://api.paystack.co/transaction/verify/
secretKey=your_paystack_secretKey
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
````
4. Start the application by running the main class (Application.java or similar) from your IDE.



## Database Setup

### Schema
1. Install and configure MySQL on your machine or server.

2. Create a new database for the application:

```sql
  CREATE DATABASE bookstore;
```
3. Switch to the newly created database:
 ```sql
    USE bookstore;
```
4. Execute the SQL scripts to create the necessary tables:
```sql

CREATE TABLE admin (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255) UNIQUE,
                       password VARCHAR(255),
                       role VARCHAR(50),
                       createdDate VARCHAR(255),
                       modifiedDate VARCHAR(255)
);

CREATE TABLE customer (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255),
                          email VARCHAR(255) UNIQUE,
                          password VARCHAR(255),
                          role VARCHAR(50),
                          createdDate VARCHAR(255),
                          modifiedDate VARCHAR(255),
                          gender VARCHAR(20),
                          phoneNumber VARCHAR(255) UNIQUE,
                          address VARCHAR(255)
);


CREATE TABLE book (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      price DECIMAL(10, 2),
                      author VARCHAR(255),
                      quantityOfBooksAvailable INT,
                      isbn VARCHAR(255),
                      datePublished VARCHAR(255),
                      category VARCHAR(255),
                      createdDate VARCHAR(255),
                      modifiedDate VARCHAR(255)
);

CREATE TABLE cart (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      customerId INT,
                      numberOfItem INT,
                      totalBookCost DECIMAL(10, 2),
                      totalDeliveryCost DECIMAL(10, 2),
                      totalCost DECIMAL(10, 2),
                      createdDate VARCHAR(255),
                      modifiedDate VARCHAR(255)
);

CREATE TABLE cart_item (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           bookId INT,
                           quantity INT,
                           subTotal DECIMAL(10, 2),
                           unitCost DECIMAL(10, 2),
                           cartId INT,
                           createdDate VARCHAR(255),
                           modifiedDate VARCHAR(255)
);
CREATE TABLE orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        numberOfItem INT,
                        totalBookCost DECIMAL(10, 2),
                        totalDeliveryCost DECIMAL(10, 2),
                        totalCost DECIMAL(10, 2),
                        createdDate VARCHAR(255),
                        modifiedDate VARCHAR(255)
);
CREATE TABLE order_items (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             order_id INT,
                             book_id INT,
                             quantity INT,
                             FOREIGN KEY (order_id) REFERENCES orders(id),
                             FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE review_and_rating (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   comment VARCHAR(255),
                                   rating INT,
                                   createdDate VARCHAR(255),
                                   customerId INT,
                                   bookId INT
);


```