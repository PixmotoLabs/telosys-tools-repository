CREATE SCHEMA IF NOT EXISTS TEST1;

SET SCHEMA TEST1 ;

DROP TABLE IF EXISTS customer ;
DROP TABLE IF EXISTS country ;

CREATE TABLE customer (
	code VARCHAR(5),
	country_code VARCHAR(2) NOT NULL,
	first_name VARCHAR(40),
	last_name VARCHAR(40),
	login VARCHAR(20) NOT NULL,
	password VARCHAR(20),
	age INTEGER,
	city VARCHAR(45),
	zip_code INTEGER,
	phone VARCHAR(20),
	reviewer SMALLINT,
	PRIMARY KEY(code)
);

CREATE TABLE country ( 
	code VARCHAR(2),
	name VARCHAR(45),
	PRIMARY KEY(code)
);
