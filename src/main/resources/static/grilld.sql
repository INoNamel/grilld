DROP DATABASE IF EXISTS `grilld`;
CREATE DATABASE IF NOT EXISTS `grilld` DEFAULT CHARACTER SET = 'utf8mb4';
USE `grilld`;
CREATE TABLE `meal_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `dish_list` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `meal_ref` int DEFAULT NULL,
  `description` varchar(150) NOT NULL,
  `price` double NOT NULL,
  `available` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `meal_ref` (`meal_ref`),
  CONSTRAINT `dish_list_ibfk_1` FOREIGN KEY (`meal_ref`) REFERENCES `meal_type` (`id`)
);
CREATE TABLE `restaurant_list` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `guests_max` smallint NOT NULL,
  `address_town` varchar(150) NOT NULL,
  `address_street` varchar(150) NOT NULL,
  `workday_open` varchar(8) DEFAULT NULL,
  `workday_closed` varchar(8) DEFAULT NULL,
  `weekend_open` varchar(8) DEFAULT NULL,
  `weekend_closed` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `client_list` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `tlf` varchar(10) NOT NULL,
  `drowssap` varchar(150) NOT NULL,
  `joined` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tlf` (`tlf`)
);
CREATE TABLE `terces` (
  `name` varchar(50) NOT NULL,
  `type` set('admin','employee') NOT NULL DEFAULT 'employee',
  `drowssap` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`)
);
CREATE TABLE `order_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ordered_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `restaurant_ref` tinyint DEFAULT NULL,
  `guests_amount` int unsigned NOT NULL DEFAULT '1',
  `ordered_for` datetime NOT NULL,
  `client_ref` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `restaurant_ref` (`restaurant_ref`),
  KEY `order_table_ibfk_4` (`client_ref`),
  CONSTRAINT `order_table_ibfk_1` FOREIGN KEY (`restaurant_ref`) REFERENCES `restaurant_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `order_table_ibfk_2` FOREIGN KEY (`client_ref`) REFERENCES `client_list` (`tlf`) ON DELETE SET NULL ON UPDATE SET NULL
);
CREATE TABLE `order_takeaway` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ordered_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `restaurant_ref` tinyint DEFAULT NULL,
  `client_ref` varchar(10) DEFAULT NULL,
  `status` enum('0','1','2','3') NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `restaurant_ref` (`restaurant_ref`),
  KEY `client_ref` (`client_ref`),
  CONSTRAINT `order_takeaway_ibfk_1` FOREIGN KEY (`restaurant_ref`) REFERENCES `restaurant_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `order_takeaway_ibfk_2` FOREIGN KEY (`client_ref`) REFERENCES `client_list` (`tlf`) ON DELETE SET NULL ON UPDATE SET NULL
);
CREATE TABLE `order_takeaway_list` (
  `row_id` int NOT NULL AUTO_INCREMENT,
  `takeaway_ref` int DEFAULT NULL,
  `dish_ref` int DEFAULT '1',
  PRIMARY KEY (`row_id`),
  UNIQUE KEY `row_id_UNIQUE` (`row_id`),
  KEY `dish_id` (`dish_ref`),
  KEY `order_takeaway_list_ibfk_1` (`takeaway_ref`),
  CONSTRAINT `order_takeaway_list_ibfk_1` FOREIGN KEY (`takeaway_ref`) REFERENCES `order_takeaway` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_takeaway_list_ibfk_2` FOREIGN KEY (`dish_ref`) REFERENCES `dish_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
);
INSERT INTO `terces` VALUES ('admin','admin','20f3765880a5c269b747e1e906054a4b4a3a991259f1e16b5dde4742cec2319a');
INSERT INTO `meal_type` VALUES (1,'breakfast'),(2,'second breakfast'),(3,'brunch'),(4,'lunch'),(5,'tea'),(6,'dinner'),(7,'supper'),(8,'snack');