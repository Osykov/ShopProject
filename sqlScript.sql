CREATE DATABASE shopsdatabase;

USE shopsdatabase;

CREATE TABLE Shops ( 
shop_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(150) NOT NULL);

CREATE TABLE Categories ( 
category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
name VARCHAR(150) NOT NULL,
shop_id INT, 
CONSTRAINT FK_Shops_Categories FOREIGN KEY(shop_id) REFERENCES Shops(shop_id));


CREATE TABLE Status (
status_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
status VARCHAR(15) NOT NULL);

CREATE TABLE Products (
product_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(150) NOT NULL,
price DECIMAL(11, 2) NOT NULL,
category_id INT,
CONSTRAINT FK_Categories_Products FOREIGN KEY(category_id) REFERENCES Categories(category_id),
status_id INT,
CONSTRAINT FK_Status_Products FOREIGN KEY(status_id) REFERENCES Status(status_id));

INSERT INTO Shops VALUES
('', 'Bookstore'),
('', 'Gamestore');

INSERT INTO Categories VALUES
('', 'Fiction', 1),
('', 'Adventure', 1),
('', 'History', 1),
('', 'Role-playing', 2),
('', 'Strategy', 2),
('', 'Sports', 2);

INSERT INTO Status VALUES
('', 'Available'),
('', 'Absent'),
('', 'Expected');
