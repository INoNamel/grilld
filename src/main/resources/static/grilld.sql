CREATE schema `grilld`;
Use grilld;
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
(1, 'Valera', '63871233', '65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5'),
(2, 'Ivan', '23412455', '65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5'),
(3, 'vasya', '22222222', '65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5'),
(4, 'ahmed', '66666666', '9af2921d3fd57fe886c9022d1fcc055d53a79e4032fa6137e397583884e1a5de');

-- --------------------------------------------------------

--
-- Table structure for table `dish_list`
--

CREATE TABLE `dish_list` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) COLLATE latin1_bin NOT NULL,
  `meal_ref` int(10) UNSIGNED DEFAULT NULL,
  `description` varchar(150) COLLATE latin1_bin NOT NULL,
  `price` double UNSIGNED NOT NULL,
  `available` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `dish_list`
--

INSERT INTO `dish_list` (`id`, `name`, `meal_ref`, `description`, `price`, `available`) VALUES
(1, 'vafli', 1, 'as das dqw dasd qwd asd ', 22, 1),
(2, 'pon4iki', 2, 'qw qd qw dq12313 1 3213 1as dasd ', 65, 1),
(3, 'Kruasani', 4, 'dqwwdasdasd asd asd qwe123 asd 12dasqwd a', 16, 1),
(4, 'omaewa mo', 4, 'shindeiru', 33, 0),
(6, 'dasd qw 123 12', 5, '132 asd as dawe qw', 15, 1),
(7, '55123123', 3, 'asdas', 123213, 1);

-- --------------------------------------------------------

--
-- Table structure for table `meal_type`
--

CREATE TABLE `meal_type` (
  `id` int(10) UNSIGNED NOT NULL,
  `type` varchar(50) COLLATE latin1_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `meal_type`
--

INSERT INTO `meal_type` (`id`, `type`) VALUES
(1, 'breakfast'),
(2, 'second breakfast'),
(3, 'brunch'),
(4, 'lunch'),
(5, 'tea'),
(6, 'dinner'),
(7, 'supper'),
(8, 'snack');

-- --------------------------------------------------------

--
-- Table structure for table `order_table`
--

CREATE TABLE `order_table` (
  `id` int(10) UNSIGNED NOT NULL,
  `ordered_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `restaurant_ref` tinyint(3) UNSIGNED DEFAULT NULL,
  `guests_amount` set('1','2','3','4','5','6','7') COLLATE latin1_bin NOT NULL DEFAULT '1',
  `ordered_for` datetime NOT NULL,
  `client_ref` varchar(10) COLLATE latin1_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `order_table`
--

INSERT INTO `order_table` (`id`, `ordered_on`, `restaurant_ref`, `guests_amount`, `ordered_for`, `client_ref`) VALUES
(1, '2019-11-27 18:16:25', 1, '2', '2019-12-27 12:13:00', '63871233'),
(2, '2019-11-23 18:16:25', 1, '3', '2019-12-17 15:20:00', '63871233');

-- --------------------------------------------------------

--
-- Table structure for table `order_takeaway`
--

CREATE TABLE `order_takeaway` (
  `id` int(10) UNSIGNED NOT NULL,
  `ordered_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `restaurant_ref` tinyint(3) UNSIGNED DEFAULT NULL,
  `client_ref` varchar(10) COLLATE latin1_bin DEFAULT NULL,
  `status` enum('0','1','2','3') COLLATE latin1_bin NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `order_takeaway`
--

INSERT INTO `order_takeaway` (`id`, `ordered_on`, `restaurant_ref`, `client_ref`, `status`) VALUES
(1, '2019-12-02 00:23:01', 1, '63871233', '3'),
(4, '2019-12-08 21:46:02', 2, '22222222', '1'),
(5, '2019-12-08 21:46:03', 2, '22222222', '2'),
(6, '2019-12-09 01:37:28', 2, '63871233', '0'),
(7, '2019-12-09 02:20:11', 1, '63871233', '3'),
(8, '2019-12-09 02:21:11', 1, '63871233', '2');

-- --------------------------------------------------------

--
-- Table structure for table `order_takeaway_list`
--

CREATE TABLE `order_takeaway_list` (
  `row_id` int(10) UNSIGNED NOT NULL,
  `takeaway_ref` int(10) UNSIGNED DEFAULT NULL,
  `dish_ref` int(10) UNSIGNED DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `order_takeaway_list`
--

INSERT INTO `order_takeaway_list` (`row_id`, `takeaway_ref`, `dish_ref`) VALUES
(1, 1, 1),
(2, 1, 1),
(3, 1, 2),
(4, 4, 3),
(5, 4, 3),
(6, 4, 3),
(7, 4, 4),
(8, 4, 4),
(9, 4, 4),
(10, 4, 4),
(11, 4, 4),
(12, 5, 4),
(13, 6, 4),
(14, 6, 4),
(15, 6, 4),
(16, 6, 6),
(17, 7, 1),
(18, 7, 1),
(19, 7, 1),
(20, 7, 1),
(21, 8, 6);

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_list`
--

CREATE TABLE `restaurant_list` (
  `id` tinyint(3) UNSIGNED NOT NULL,
  `guests_max` smallint(5) UNSIGNED NOT NULL,
  `address_town` varchar(150) COLLATE latin1_bin NOT NULL,
  `address_street` varchar(150) COLLATE latin1_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `restaurant_list`
--

INSERT INTO `restaurant_list` (`id`, `guests_max`, `address_town`, `address_street`) VALUES
(1, 14, 'Hellerup', 'Tuborg Havnepark 15, 2900'),
(2, 22, 'København', 'Strandvejen 203, 2900');

-- --------------------------------------------------------

--
-- Table structure for table `restaurant_time`
--

CREATE TABLE `restaurant_time` (
  `restaurant_ref` tinyint(3) UNSIGNED NOT NULL,
  `workday_open` varchar(8) COLLATE latin1_bin NOT NULL,
  `workday_closed` varchar(8) COLLATE latin1_bin NOT NULL,
  `weekend_open` varchar(8) COLLATE latin1_bin NOT NULL,
  `weekend_closed` varchar(8) COLLATE latin1_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `restaurant_time`
--

INSERT INTO `restaurant_time` (`restaurant_ref`, `workday_open`, `workday_closed`, `weekend_open`, `weekend_closed`) VALUES
(1, '10:00:00', '22:00:00', '12:00:00', '23:30:00'),
(2, '09:00:00', '23:00:00', '11:00:00', '23:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `terces`
--

CREATE TABLE `terces` (
  `name` varchar(50) COLLATE latin1_bin NOT NULL,
  `type` set('admin','employee') COLLATE latin1_bin NOT NULL DEFAULT 'employee',
  `drowssap` varchar(150) COLLATE latin1_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

--
-- Dumping data for table `terces`
--

INSERT INTO `terces` (`name`, `type`, `drowssap`) VALUES
('Issa', 'employee', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5'),
('admin', 'admin', '20f3765880a5c269b747e1e906054a4b4a3a991259f1e16b5dde4742cec2319a');

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
  ADD PRIMARY KEY (`id`),
  ADD KEY `meal_ref` (`meal_ref`);

--
-- Indexes for table `meal_type`
--
ALTER TABLE `meal_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_table`
--
ALTER TABLE `order_table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `restaurant_ref` (`restaurant_ref`),
  ADD KEY `order_table_ibfk_4` (`client_ref`);

--
-- Indexes for table `order_takeaway`
--
ALTER TABLE `order_takeaway`
  ADD PRIMARY KEY (`id`),
  ADD KEY `restaurant_ref` (`restaurant_ref`),
  ADD KEY `client_ref` (`client_ref`);

--
-- Indexes for table `order_takeaway_list`
--
ALTER TABLE `order_takeaway_list`
  ADD PRIMARY KEY (`row_id`),
  ADD KEY `dish_id` (`dish_ref`),
  ADD KEY `order_takeaway_list_ibfk_1` (`takeaway_ref`);

--
-- Indexes for table `restaurant_list`
--
ALTER TABLE `restaurant_list`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restaurant_time`
--
ALTER TABLE `restaurant_time`
  ADD PRIMARY KEY (`restaurant_ref`);

--
-- Indexes for table `terces`
--
ALTER TABLE `terces`
  ADD PRIMARY KEY (`name`),
  ADD UNIQUE KEY `name` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `client_list`
--
ALTER TABLE `client_list`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `dish_list`
--
ALTER TABLE `dish_list`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `meal_type`
--
ALTER TABLE `meal_type`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `order_table`
--
ALTER TABLE `order_table`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `order_takeaway`
--
ALTER TABLE `order_takeaway`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `order_takeaway_list`
--
ALTER TABLE `order_takeaway_list`
  MODIFY `row_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `restaurant_list`
--
ALTER TABLE `restaurant_list`
  MODIFY `id` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `dish_list`
--
ALTER TABLE `dish_list`
  ADD CONSTRAINT `dish_list_ibfk_1` FOREIGN KEY (`meal_ref`) REFERENCES `meal_type` (`id`);

--
-- Constraints for table `order_table`
--
ALTER TABLE `order_table`
  ADD CONSTRAINT `order_table_ibfk_3` FOREIGN KEY (`restaurant_ref`) REFERENCES `restaurant_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `order_table_ibfk_4` FOREIGN KEY (`client_ref`) REFERENCES `client_list` (`tlf`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Constraints for table `order_takeaway`
--
ALTER TABLE `order_takeaway`
  ADD CONSTRAINT `order_takeaway_ibfk_2` FOREIGN KEY (`restaurant_ref`) REFERENCES `restaurant_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `order_takeaway_ibfk_3` FOREIGN KEY (`client_ref`) REFERENCES `client_list` (`tlf`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Constraints for table `order_takeaway_list`
--
ALTER TABLE `order_takeaway_list`
  ADD CONSTRAINT `order_takeaway_list_ibfk_1` FOREIGN KEY (`takeaway_ref`) REFERENCES `order_takeaway` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `order_takeaway_list_ibfk_2` FOREIGN KEY (`dish_ref`) REFERENCES `dish_list` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Constraints for table `restaurant_time`
--
ALTER TABLE `restaurant_time`
  ADD CONSTRAINT `restaurant_time_ibfk_1` FOREIGN KEY (`restaurant_ref`) REFERENCES `restaurant_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;