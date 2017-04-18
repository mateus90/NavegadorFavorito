CREATE TABLE login (
 id integer NOT NULL PRIMARY KEY,
 usuario varchar(255) NOT NULL,
 senha varchar(255) NOT NULL
);
CREATE TABLE browser (
 id integer NOT NULL PRIMARY KEY,
 nome varchar(255) NOT NULL
);
CREATE TABLE usuario (
 id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
 browser_id integer NOT NULL,
 nome varchar(255) NOT NULL,
 FOREIGN KEY (browser_id) REFERENCES browser (id)
);