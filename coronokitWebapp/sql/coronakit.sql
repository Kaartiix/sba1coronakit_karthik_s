DROP DATABASE coronakit;
CREATE DATABASE coronakit;
Use coronakit;
CREATE TABLE productmaster (`id` INT NOT NULL primary key,  `productname` VARCHAR(50) NOT NULL,`cost` VARCHAR(50) NOT NULL,`productdescription` VARCHAR(100));
INSERT INTO productmaster values (1001,'Mask','30','Face mask');
INSERT INTO products values (1002,'Sanitizer','99','Hand sanitizer');
CREATE TABLE Kit ( id INT NOT NULL primary key, coronakitId INT NOT NULL, productID INT NOT NULL, quantity INT NOT NULL, amount decimal(9,2) NOT NULL );


