USE chatapplication;

--
-- Table structure for table `groupchat`
--

DROP TABLE IF EXISTS `groupchat`;

CREATE TABLE `groupchat` (
  `GroupName` text NOT NULL,
  `Type` text NOT NULL,
  `Password` text NOT NULL,
  `Remarks` text NOT NULL
);


--
-- Table structure for table `myaccounts`
--

DROP TABLE IF EXISTS `myaccounts`;

CREATE TABLE `myaccounts` (
  `Username` text NOT NULL,
  `Password` text NOT NULL,
  `Remarks` text NOT NULL
);


--
-- Table structure for table `mychat`
--

DROP TABLE IF EXISTS `mychat`;

CREATE TABLE `mychat` (
  `Sender` text NOT NULL,
  `Chat` text NOT NULL,
  `Time` text NOT NULL,
  `Type` text NOT NULL,
  `ChatGroup` text NOT NULL,
  `Remarks` text NOT NULL
);


