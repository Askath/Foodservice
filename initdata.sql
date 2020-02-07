-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.15 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for foodservice
DROP DATABASE IF EXISTS `foodservice`;
CREATE DATABASE IF NOT EXISTS `foodservice` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `foodservice`;

-- Dumping structure for table foodservice.configuration
DROP TABLE IF EXISTS `configuration`;
CREATE TABLE IF NOT EXISTS `configuration` (
  `configurationid` int(11) NOT NULL AUTO_INCREMENT,
  `confkey` varchar(64) NOT NULL,
  `confvalue` varchar(64) NOT NULL,
  PRIMARY KEY (`configurationid`)
) ENGINE=InnoDB AUTO_INCREMENT=686 DEFAULT CHARSET=utf8;

-- Dumping data for table foodservice.configuration: ~3 rows (approximately)
DELETE FROM `configuration`;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` (`configurationid`, `confkey`, `confvalue`) VALUES
	(447, 'reciept', 'ad'),
	(579, 'bank_email', 'Karsten.Mohr@sophos.com'),
	(685, 'time', '30');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;

-- Dumping structure for table foodservice.foodservice
DROP TABLE IF EXISTS `foodservice`;
CREATE TABLE IF NOT EXISTS `foodservice` (
  `foodserviceid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `link` varchar(255) NOT NULL DEFAULT 'resources/img/placeholder.png',
  `telephone` varchar(32) DEFAULT NULL,
  `hidden` bit(1) NOT NULL,
  `excluded` bit(1) NOT NULL,
  `minorderprice` int(11) DEFAULT NULL,
  PRIMARY KEY (`foodserviceid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- Dumping data for table foodservice.foodservice: ~13 rows (approximately)
DELETE FROM `foodservice`;
/*!40000 ALTER TABLE `foodservice` DISABLE KEYS */;
INSERT INTO `foodservice` (`foodserviceid`, `name`, `link`, `telephone`, `hidden`, `excluded`, `minorderprice`) VALUES
	(4, 'Entenhaus', 'entenhaus.pdf', '0231 9098130', b'0', b'0', 1300),
	(20, 'Marco', 'marco.pdf', '0231 5336906', b'0', b'0', 1000),
	(22, 'Döner Real', 'döner.pdf', '0231 98339617', b'0', b'0', 1500),
	(23, 'Grieche', 'Grieche.pdf', '0231 2494053', b'0', b'0', 0),
	(24, 'Palace India', 'placeholder.png', NULL, b'0', b'0', 0),
	(25, 'Sonstiges', 'placeholder.png', NULL, b'0', b'1', 0),
	(26, 'Ich brauche nichts', 'placeholder.png', NULL, b'0', b'1', 0),
	(27, 'Real', 'placeholder.png', NULL, b'0', b'1', 0),
	(28, 'Busche', 'placeholder.png', NULL, b'0', b'1', 0),
	(29, 'Grill-House', 'placeholder.png', NULL, b'0', b'1', 0),
	(30, 'La Stella', 'placeholder.png', '0231409725', b'0', b'0', 1000),
	(31, 'Grillrestaurant Platia', 'placeholder.png', '0231 444 7956', b'0', b'0', 2000),
	(32, 'Mr Wasabi', 'placeholder.png', 'https://www.mrwasabi-dortmund.de', b'0', b'0', 3000);
/*!40000 ALTER TABLE `foodservice` ENABLE KEYS */;

-- Dumping structure for table foodservice.order
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `orderid` int(11) NOT NULL AUTO_INCREMENT,
  `food` varchar(64) NOT NULL,
  `price` int(11) NOT NULL,
  `other` varchar(255) DEFAULT NULL,
  `orderprocess` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  PRIMARY KEY (`orderid`),
  KEY `FK_order_orderprocess` (`orderprocess`),
  KEY `FK_order_user` (`user`),
  CONSTRAINT `FK_order_orderprocess` FOREIGN KEY (`orderprocess`) REFERENCES `orderprocess` (`orderprocessid`),
  CONSTRAINT `FK_order_user` FOREIGN KEY (`user`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=1632 DEFAULT CHARSET=utf8;

-- Dumping data for table foodservice.order: ~0 rows (approximately)
DELETE FROM `order`;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;

-- Dumping structure for table foodservice.orderprocess
DROP TABLE IF EXISTS `orderprocess`;
CREATE TABLE IF NOT EXISTS `orderprocess` (
  `orderprocessid` int(11) NOT NULL AUTO_INCREMENT,
  `starttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `orderstarttime` timestamp NULL DEFAULT NULL,
  `orderendtime` timestamp NULL DEFAULT NULL,
  `orderedtime` timestamp NULL DEFAULT NULL,
  `deliveredtime` timestamp NULL DEFAULT NULL,
  `aborted` bit(1) DEFAULT b'0',
  `deliveryplannedtime` timestamp NULL DEFAULT NULL,
  `foodservice` int(11) DEFAULT NULL,
  `orderinguser` int(11) NOT NULL,
  `json` varbinary(4096) NOT NULL,
  PRIMARY KEY (`orderprocessid`),
  KEY `FK_orderprocess_foodservice` (`foodservice`),
  KEY `FK_orderprocess_user` (`orderinguser`),
  CONSTRAINT `FK_orderprocess_foodservice` FOREIGN KEY (`foodservice`) REFERENCES `foodservice` (`foodserviceid`),
  CONSTRAINT `FK_orderprocess_user` FOREIGN KEY (`orderinguser`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=1031 DEFAULT CHARSET=utf8;

-- Dumping data for table foodservice.orderprocess: ~0 rows (approximately)
DELETE FROM `orderprocess`;
/*!40000 ALTER TABLE `orderprocess` DISABLE KEYS */;
INSERT INTO `orderprocess` (`orderprocessid`, `starttime`, `orderstarttime`, `orderendtime`, `orderedtime`, `deliveredtime`, `aborted`, `deliveryplannedtime`, `foodservice`, `orderinguser`, `json`) VALUES
	(1, '2019-10-16 14:43:08', '2019-10-16 14:42:58', '2019-10-16 14:42:59', '2019-10-16 14:43:00', '2019-10-16 14:43:02', b'0', '2019-10-16 14:43:04', 4, 1, NULL);
/*!40000 ALTER TABLE `orderprocess` ENABLE KEYS */;

-- Dumping structure for table foodservice.passwordresettoken
DROP TABLE IF EXISTS `passwordresettoken`;
CREATE TABLE IF NOT EXISTS `passwordresettoken` (
  `tokenid` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `expirydate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`tokenid`),
  KEY `FK_passwordresettoken_user` (`user`),
  CONSTRAINT `FK_passwordresettoken_user` FOREIGN KEY (`user`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- Dumping data for table foodservice.passwordresettoken: ~0 rows (approximately)
DELETE FROM `passwordresettoken`;
/*!40000 ALTER TABLE `passwordresettoken` DISABLE KEYS */;
/*!40000 ALTER TABLE `passwordresettoken` ENABLE KEYS */;

-- Dumping structure for table foodservice.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(24) NOT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1;

-- Dumping data for table foodservice.role: ~2 rows (approximately)
DELETE FROM `role`;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`roleid`, `name`) VALUES
	(1, 'orderer'),
	(2, 'user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Dumping structure for table foodservice.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `initials` varchar(5) NOT NULL,
  `password` varbinary(255) NOT NULL,
  `salt` varbinary(1024) NOT NULL,
  `activated` bit(1) NOT NULL,
  `role` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `bank` bit(1) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  KEY `FK_user_role` (`role`),
  CONSTRAINT `FK_user_role` FOREIGN KEY (`role`) REFERENCES `role` (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8;

-- Dumping data for table foodservice.user: ~0 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`userid`, `initials`, `password`, `salt`, `activated`, `role`, `email`, `bank`) VALUES
	(1, 'rt', _binary 0x61727374726174, _binary 0x72736174, b'1', 1, NULL, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table foodservice.useractivationtoken
DROP TABLE IF EXISTS `useractivationtoken`;
CREATE TABLE IF NOT EXISTS `useractivationtoken` (
  `useractivationtokenid` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL DEFAULT '0',
  `token` varchar(255) NOT NULL DEFAULT '0',
  `expirydate` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`useractivationtokenid`),
  KEY `FK__user` (`user`),
  CONSTRAINT `FK__user` FOREIGN KEY (`user`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Dumping data for table foodservice.useractivationtoken: ~0 rows (approximately)
DELETE FROM `useractivationtoken`;
/*!40000 ALTER TABLE `useractivationtoken` DISABLE KEYS */;
/*!40000 ALTER TABLE `useractivationtoken` ENABLE KEYS */;

-- Dumping structure for table foodservice.vote
DROP TABLE IF EXISTS `vote`;
CREATE TABLE IF NOT EXISTS `vote` (
  `voteid` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `foodservice` int(11) NOT NULL,
  `orderprocess` int(11) NOT NULL,
  PRIMARY KEY (`voteid`),
  KEY `FK_vote_user` (`user`),
  KEY `FK_vote_foodservice` (`foodservice`),
  KEY `FK_vote_orderprocess` (`orderprocess`),
  CONSTRAINT `FK_vote_foodservice` FOREIGN KEY (`foodservice`) REFERENCES `foodservice` (`foodserviceid`),
  CONSTRAINT `FK_vote_orderprocess` FOREIGN KEY (`orderprocess`) REFERENCES `orderprocess` (`orderprocessid`),
  CONSTRAINT `FK_vote_user` FOREIGN KEY (`user`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=1352 DEFAULT CHARSET=utf8;

-- Dumping data for table foodservice.vote: ~0 rows (approximately)
DELETE FROM `vote`;
/*!40000 ALTER TABLE `vote` DISABLE KEYS */;
/*!40000 ALTER TABLE `vote` ENABLE KEYS */;

-- Dumping structure for table foodservice.votedservices
DROP TABLE IF EXISTS `votedservices`;
CREATE TABLE IF NOT EXISTS `votedservices` (
  `votedservicesid` int(11) NOT NULL,
  PRIMARY KEY (`votedservicesid`),
  CONSTRAINT `FK_votedservices_foodservice` FOREIGN KEY (`votedservicesid`) REFERENCES `foodservice` (`foodserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table foodservice.votedservices: ~0 rows (approximately)
DELETE FROM `votedservices`;
/*!40000 ALTER TABLE `votedservices` DISABLE KEYS */;
INSERT INTO `votedservices` (`votedservicesid`) VALUES
	(20);
/*!40000 ALTER TABLE `votedservices` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
