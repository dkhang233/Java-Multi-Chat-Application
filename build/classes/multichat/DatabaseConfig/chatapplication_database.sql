CREATE DATABASE chatapplication;
USE chatapplication;

--
-- Table structure for table `myaccounts`
--

DROP TABLE IF EXISTS myaccounts;

CREATE TABLE myaccounts (
  	Username text NOT NULL,
  	Password text NOT NULL,
  	Remarks text NOT NULL,
  	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY
);


--
-- Table structure for table `mychat`
--

DROP TABLE IF EXISTS mychat;

CREATE TABLE  mychat (
	Username text NOT NULL,
	Chat text NOT NULL,
	Time text NOT NULL,
	Type text NOT NULL,
	ChatGroup text NOT NULL,
	id INT NOT NULL ,
	FOREIGN KEY (id) REFERENCES myaccounts(id)
);


