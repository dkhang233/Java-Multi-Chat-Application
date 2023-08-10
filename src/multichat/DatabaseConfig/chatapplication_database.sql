USE chatapplication;

--
-- Table structure for table `myaccounts`
--

DROP TABLE IF EXISTS myaccounts;

CREATE TABLE myaccounts (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  	Username text NOT NULL,
  	Password text NOT NULL,
  	Remarks text NOT NULL
);


--
-- Table structure for table `mychat`
--

DROP TABLE IF EXISTS mychat;

CREATE TABLE  mychat (
	id INT NOT NULL ,
	Sender text NOT NULL,
	Chat text NOT NULL,
	Time text NOT NULL,
	Type text NOT NULL,
	ChatGroup text NOT NULL,
	FOREIGN KEY (id) REFERENCES myaccounts(id)
);


