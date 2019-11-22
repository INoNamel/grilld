-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 22, 2019 at 01:51 AM
-- Server version: 10.1.33-MariaDB
-- PHP Version: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `grilld`
--

-- --------------------------------------------------------

--
-- Table structure for table `client_list`
--

CREATE TABLE `client_list` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) COLLATE latin1_bin NOT NULL,
  `tlf` varchar(10) COLLATE latin1_bin NOT NULL,
  `drowssap` varchar(150) COLLATE latin1_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `client_list`
--

INSERT INTO `client_list` (`id`, `name`, `tlf`, `drowssap`) VALUES
(1, 'Valera', '63871233', '762341d12');

-- --------------------------------------------------------

--
-- Table structure for table `dish_list`
--

CREATE TABLE `dish_list` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) COLLATE latin1_bin NOT NULL,
  `description` varchar(150) COLLATE latin1_bin NOT NULL,
  `price` double UNSIGNED NOT NULL,
  `ingredients` varchar(150) COLLATE latin1_bin NOT NULL,
  `available` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `dish_list`
--

INSERT INTO `dish_list` (`id`, `name`, `description`, `price`, `ingredients`, `available`) VALUES
(1, 'vafli', 'as das dqw dasd qwd asd ', 22, 'tofu;fish;milk', 1),
(2, 'pon4iki', 'qw qd qw dq12313 1 3213 1as dasd ', 65, 'testo;hueta;sukker', 1);

-- --------------------------------------------------------

--
-- Table structure for table `order_invoice`
--

CREATE TABLE `order_invoice` (
  `id` int(11) UNSIGNED NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `client_ref` int(10) UNSIGNED DEFAULT NULL,
  `status` set('0','1','2','3') COLLATE latin1_bin NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `order_invoice`
--

INSERT INTO `order_invoice` (`id`, `date_time`, `client_ref`, `status`) VALUES
(1, '2019-11-22 21:16:31', 1, '0');

-- --------------------------------------------------------

--
-- Table structure for table `order_list`
--

CREATE TABLE `order_list` (
  `row_id` int(10) UNSIGNED NOT NULL,
  `invoice_ref` int(11) UNSIGNED DEFAULT NULL,
  `dish_ref` int(11) UNSIGNED DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `order_list`
--

INSERT INTO `order_list` (`row_id`, `invoice_ref`, `dish_ref`) VALUES
(1, 1, 1),
(2, 1, 1),
(3, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `terces`
--

CREATE TABLE `terces` (
  `id` int(11) UNSIGNED NOT NULL,
  `name` varchar(50) COLLATE latin1_bin NOT NULL,
  `type` set('admin','employee') COLLATE latin1_bin NOT NULL DEFAULT 'employee',
  `drowssap` varchar(50) COLLATE latin1_bin NOT NULL DEFAULT '12345'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `terces`
--

INSERT INTO `terces` (`id`, `name`, `type`, `drowssap`) VALUES
(1, 'admin', 'admin', '54321'),
(2, 'Issa', 'employee', '12345');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client_list`
--
ALTER TABLE `client_list`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tlf` (`tlf`);

--
-- Indexes for table `dish_list`
--
ALTER TABLE `dish_list`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_invoice`
--
ALTER TABLE `order_invoice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client_id` (`client_ref`);

--
-- Indexes for table `order_list`
--
ALTER TABLE `order_list`
  ADD PRIMARY KEY (`row_id`),
  ADD KEY `invoice_id` (`invoice_ref`),
  ADD KEY `dish_id` (`dish_ref`);

--
-- Indexes for table `terces`
--
ALTER TABLE `terces`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `client_list`
--
ALTER TABLE `client_list`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `dish_list`
--
ALTER TABLE `dish_list`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `order_invoice`
--
ALTER TABLE `order_invoice`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `order_list`
--
ALTER TABLE `order_list`
  MODIFY `row_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `terces`
--
ALTER TABLE `terces`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order_invoice`
--
ALTER TABLE `order_invoice`
  ADD CONSTRAINT `order_invoice_ibfk_1` FOREIGN KEY (`client_ref`) REFERENCES `client_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Constraints for table `order_list`
--
ALTER TABLE `order_list`
  ADD CONSTRAINT `order_list_ibfk_1` FOREIGN KEY (`invoice_ref`) REFERENCES `order_invoice` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `order_list_ibfk_2` FOREIGN KEY (`dish_ref`) REFERENCES `dish_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
