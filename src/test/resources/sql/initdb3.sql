-- CREATE SCHEMA IF NOT EXISTS TEST3;
DROP ALL OBJECTS;
-- SET SCHEMA TEST3 ;

-- TEST WITH 2 FK REFERENCING THE SAME TABLE AND A COLUM HAVING THE EXPECTED LINK NAME "teacher"
-- AND AN INVERSE SIDE ALSO HAVING A LINK NAME IN THE COLUMNS ( "listOfStudent" ) 

CREATE TABLE teacher (
  code INTEGER NOT NULL,
  name VARCHAR(40),
  student VARCHAR(20),
  list_Of_Student VARCHAR(10),
  PRIMARY KEY(code)
);

CREATE TABLE student (
  id INTEGER NOT NULL ,
  first_name VARCHAR(40),
  last_name VARCHAR(40),
  teacher_code1 INTEGER,
  teacher_code2 INTEGER,
  teacher VARCHAR(20),
  PRIMARY KEY(id),
  FOREIGN KEY(teacher_code1) REFERENCES teacher(code),
  FOREIGN KEY(teacher_code2) REFERENCES teacher(code)
);
