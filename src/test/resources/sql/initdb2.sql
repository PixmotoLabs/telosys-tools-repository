-- CREATE SCHEMA IF NOT EXISTS TEST2;

-- SET SCHEMA TEST2 ;
DROP ALL OBJECTS;

-- TEST WITH 2 FK REFERENCING THE SAME TABLE
-- DROP TABLE IF EXISTS teacher ;
-- DROP TABLE IF EXISTS student ;

CREATE TABLE teacher (
  code INTEGER NOT NULL,
  name VARCHAR(40),
  PRIMARY KEY(code)
);

CREATE TABLE student (
  id INTEGER NOT NULL ,
  first_name VARCHAR(40),
  last_name VARCHAR(40),
  teacher_code1 INTEGER,
  teacher_code2 INTEGER,
  PRIMARY KEY(id),
  FOREIGN KEY(teacher_code1) REFERENCES teacher(code),
  FOREIGN KEY(teacher_code2) REFERENCES teacher(code)
);
