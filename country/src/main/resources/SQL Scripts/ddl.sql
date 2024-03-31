CREATE TABLE country(
    id INTEGER,
    name VARCHAR(128),
    code VARCHAR(3),
    createdBy VARCHAR(10),
    createdDate DATETIME,
    modifiedBy VARCHAR(10),
    modifiedDate DATETIME,
    PRIMARY KEY (id)
);