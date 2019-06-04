CREATE TABLE PRODUCT(
 ID NUMBER,
 CONSTRAINT PK_ID PRIMARY KEY (ID),
 NAME NVARCHAR2(50) NOT NULL,
 DESCRIPTION CLOB,
 PRICE NUMBER NOT NULL
);