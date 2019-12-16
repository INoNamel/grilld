CREATE DATABASE  IF NOT EXISTS `grilld` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `grilld`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: grilld
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client_list`
--

DROP TABLE IF EXISTS `client_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `client_list` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `tlf` varchar(10) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `drowssap` varchar(150) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tlf` (`tlf`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_list`
--

LOCK TABLES `client_list` WRITE;
/*!40000 ALTER TABLE `client_list` DISABLE KEYS */;
INSERT INTO `client_list` VALUES (1,'Valera','63871233','65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5'),(2,'Ivan','23412455','65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5'),(3,'vasya','22222222','65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5'),(4,'ahmed','66666666','9af2921d3fd57fe886c9022d1fcc055d53a79e4032fa6137e397583884e1a5de');
/*!40000 ALTER TABLE `client_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish_list`
--

DROP TABLE IF EXISTS `dish_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dish_list` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `meal_ref` int(10) unsigned DEFAULT NULL,
  `description` varchar(150) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `price` double unsigned NOT NULL,
  `available` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `meal_ref` (`meal_ref`),
  CONSTRAINT `dish_list_ibfk_1` FOREIGN KEY (`meal_ref`) REFERENCES `meal_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish_list`
--

LOCK TABLES `dish_list` WRITE;
/*!40000 ALTER TABLE `dish_list` DISABLE KEYS */;
INSERT INTO `dish_list` VALUES (1,'vafli',1,'as das dqw dasd qwd asd ',22,1),(2,'pon4iki',2,'qw qd qw dq12313 1 3213 1as dasd ',65,1),(3,'Kruasani',4,'dqwwdasdasd asd asd qwe123 asd 12dasqwd a',16,1),(4,'omaewa mo',4,'shindeiru',33,0),(6,'dasd qw 123 12',5,'132 asd as dawe qw',15,1),(9,'ordlsdf',4,'wq wda sd',23,1);
/*!40000 ALTER TABLE `dish_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meal_type`
--

DROP TABLE IF EXISTS `meal_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `meal_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meal_type`
--

LOCK TABLES `meal_type` WRITE;
/*!40000 ALTER TABLE `meal_type` DISABLE KEYS */;
INSERT INTO `meal_type` VALUES (1,'breakfast'),(2,'second breakfast'),(3,'brunch'),(4,'lunch'),(5,'tea'),(6,'dinner'),(7,'supper'),(8,'snack');
/*!40000 ALTER TABLE `meal_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_table`
--

DROP TABLE IF EXISTS `order_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_table` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ordered_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `restaurant_ref` tinyint(3) unsigned DEFAULT NULL,
  `guests_amount` int(10) unsigned NOT NULL DEFAULT '1',
  `ordered_for` datetime NOT NULL,
  `client_ref` varchar(10) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `restaurant_ref` (`restaurant_ref`),
  KEY `order_table_ibfk_4` (`client_ref`),
  CONSTRAINT `order_table_ibfk_3` FOREIGN KEY (`restaurant_ref`) REFERENCES `restaurant_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `order_table_ibfk_4` FOREIGN KEY (`client_ref`) REFERENCES `client_list` (`tlf`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_table`
--

LOCK TABLES `order_table` WRITE;
/*!40000 ALTER TABLE `order_table` DISABLE KEYS */;
INSERT INTO `order_table` VALUES (1,'2019-11-27 18:16:25',1,2,'2019-12-27 12:13:00','63871233'),(2,'2019-11-23 18:16:25',1,4,'2019-12-17 15:20:00','63871233'),(3,'2019-12-09 15:31:24',3,1,'2019-12-16 19:46:00','63871233'),(4,'2019-12-09 15:33:35',3,1,'2019-12-29 11:30:00','63871233'),(8,'2019-12-16 12:23:22',3,2,'2019-12-16 12:38:00','63871233'),(9,'2019-12-16 12:47:38',2,7,'2019-12-16 13:02:00','63871233'),(12,'2019-12-16 12:58:39',2,7,'2019-12-16 13:13:00','63871233');
/*!40000 ALTER TABLE `order_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_takeaway`
--

DROP TABLE IF EXISTS `order_takeaway`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_takeaway` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ordered_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `restaurant_ref` tinyint(3) unsigned DEFAULT NULL,
  `client_ref` varchar(10) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL,
  `status` enum('0','1','2','3') CHARACTER SET latin1 COLLATE latin1_bin NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `restaurant_ref` (`restaurant_ref`),
  KEY `client_ref` (`client_ref`),
  CONSTRAINT `order_takeaway_ibfk_2` FOREIGN KEY (`restaurant_ref`) REFERENCES `restaurant_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `order_takeaway_ibfk_3` FOREIGN KEY (`client_ref`) REFERENCES `client_list` (`tlf`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_takeaway`
--

LOCK TABLES `order_takeaway` WRITE;
/*!40000 ALTER TABLE `order_takeaway` DISABLE KEYS */;
INSERT INTO `order_takeaway` VALUES (1,'2019-12-02 00:23:01',1,'63871233','3'),(4,'2019-12-08 21:46:02',2,'22222222','1'),(5,'2019-12-08 21:46:03',2,'22222222','2'),(6,'2019-12-09 01:37:28',2,'63871233','0'),(7,'2019-12-09 02:20:11',1,'63871233','3'),(8,'2019-12-09 02:21:11',1,'63871233','2'),(9,'2019-12-09 15:30:40',3,'63871233','3'),(10,'2019-12-16 12:15:40',1,'63871233','2');
/*!40000 ALTER TABLE `order_takeaway` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_takeaway_list`
--

DROP TABLE IF EXISTS `order_takeaway_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_takeaway_list` (
  `row_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `takeaway_ref` int(10) unsigned DEFAULT NULL,
  `dish_ref` int(10) unsigned DEFAULT '1',
  PRIMARY KEY (`row_id`),
  UNIQUE KEY `row_id_UNIQUE` (`row_id`),
  KEY `dish_id` (`dish_ref`),
  KEY `order_takeaway_list_ibfk_1` (`takeaway_ref`),
  CONSTRAINT `order_takeaway_list_ibfk_1` FOREIGN KEY (`takeaway_ref`) REFERENCES `order_takeaway` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_takeaway_list_ibfk_2` FOREIGN KEY (`dish_ref`) REFERENCES `dish_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_takeaway_list`
--

LOCK TABLES `order_takeaway_list` WRITE;
/*!40000 ALTER TABLE `order_takeaway_list` DISABLE KEYS */;
INSERT INTO `order_takeaway_list` VALUES (1,1,1),(2,1,1),(3,1,2),(4,4,3),(5,4,3),(6,4,3),(7,4,4),(8,4,4),(9,4,4),(10,4,4),(11,4,4),(12,5,4),(13,6,4),(14,6,4),(15,6,4),(16,6,6),(17,7,1),(18,7,1),(19,7,1),(20,7,1),(21,8,6),(22,9,1),(23,9,1),(24,10,6);
/*!40000 ALTER TABLE `order_takeaway_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_list`
--

DROP TABLE IF EXISTS `restaurant_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `restaurant_list` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `guests_max` smallint(5) unsigned NOT NULL,
  `address_town` varchar(150) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `address_street` varchar(150) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `workday_open` varchar(8) COLLATE latin1_bin DEFAULT NULL,
  `workday_closed` varchar(8) COLLATE latin1_bin DEFAULT NULL,
  `weekend_open` varchar(8) COLLATE latin1_bin DEFAULT NULL,
  `weekend_closed` varchar(8) COLLATE latin1_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_list`
--

LOCK TABLES `restaurant_list` WRITE;
/*!40000 ALTER TABLE `restaurant_list` DISABLE KEYS */;
INSERT INTO `restaurant_list` VALUES (1,14,'Hellerup','Tuborg Havnepark 15, 2900','11:00','23:30','12:00','22:00'),(2,8,'København','Strandvejen 203, 2900','11:00','23:30','11:00','23:00'),(3,10,'Odense','Læssøegade 215','08:30','21:00','10:30','19:30'),(4,12,'Ålborg','Qeasd asd 12','10:10','22:30','10:15','22:45');
/*!40000 ALTER TABLE `restaurant_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terces`
--

DROP TABLE IF EXISTS `terces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `terces` (
  `name` varchar(50) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `type` set('admin','employee') CHARACTER SET latin1 COLLATE latin1_bin NOT NULL DEFAULT 'employee',
  `drowssap` varchar(150) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terces`
--

LOCK TABLES `terces` WRITE;
/*!40000 ALTER TABLE `terces` DISABLE KEYS */;
INSERT INTO `terces` VALUES ('Issa','employee','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5'),('admin','admin','20f3765880a5c269b747e1e906054a4b4a3a991259f1e16b5dde4742cec2319a');
/*!40000 ALTER TABLE `terces` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
