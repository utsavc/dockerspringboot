CREATE DATABASE IF NOT EXISTS ecommerce;

USE ecommerce;

CREATE USER IF NOT EXISTS 'user'@'%' IDENTIFIED BY 'user@123';

GRANT ALL PRIVILEGES ON dockerspringboot.* TO 'user'@'%';

FLUSH PRIVILEGES;