-- MySQL dump
-- Table structure for table `time_slot`
CREATE TABLE `time_slot` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `day_of_week` VARCHAR(20) NOT NULL,
  `start_time` TIME NOT NULL,
  `status` ENUM('free', 'booked') NOT NULL,
  `name` VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table `time_slot`
LOCK TABLES `time_slot` WRITE;
/*!40000 ALTER TABLE `time_slot` DISABLE KEYS */;

INSERT INTO `time_slot` VALUES
(1,'0','15:00:00','free',NULL),
(2,'0','15:30:00','free',NULL),
(3,'0','16:00:00','free',NULL),
(4,'0','16:30:00','free',NULL),
(5,'1','15:00:00','free',NULL),
(6,'1','15:30:00','free',NULL),
(7,'1','16:00:00','free',NULL),
(8,'1','16:30:00','free',NULL),
(9,'2','15:00:00','free',NULL),
(10,'2','15:30:00','free',NULL),
(11,'2','16:00:00','free',NULL),
(12,'2','16:30:00','free',NULL),
(13,'3','15:00:00','free',NULL),
(14,'3','15:30:00','free',NULL),
(15,'3','16:00:00','free',NULL),
(16,'3','16:30:00','free',NULL),
(17,'4','15:00:00','free',NULL),
(18,'4','15:30:00','free',NULL),
(19,'4','16:00:00','free',NULL),
(20,'4','16:30:00','free',NULL);

/*!40000 ALTER TABLE `time_slot` ENABLE KEYS */;
UNLOCK TABLES;
