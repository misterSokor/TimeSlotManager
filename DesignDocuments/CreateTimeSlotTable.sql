-- MySQL dump
-- Table structure for table `time_slot`
CREATE TABLE time_slot (
    id INT AUTO_INCREMENT PRIMARY KEY,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    status ENUM('free', 'booked') NOT NULL,
    name VARCHAR(255),
    cancellation_status BOOLEAN
); ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table `time_slot`
LOCK TABLES `time_slot` WRITE;
/*!40000 ALTER TABLE `time_slot` DISABLE KEYS */;


INSERT INTO time_slot (day_of_week, start_time, status, name, cancellation_status)
VALUES

    (0, '15:00:00', 'free', NULL, 0),
    (0, '15:30:00', 'free', NULL, 0),
    (0, '16:00:00', 'free', NULL, 0),
    (0, '16:30:00', 'free', NULL, 0),

    (1, '15:00:00', 'free', NULL, 0),
    (1, '15:30:00', 'free', NULL, 0),
    (1, '16:00:00', 'free', NULL, 0),
    (1, '16:30:00', 'free', NULL, 0),

    (2, '15:00:00', 'free', NULL, 0),
    (2, '15:30:00', 'free', NULL, 0),
    (2, '16:00:00', 'free', NULL, 0),
    (2, '16:30:00', 'free', NULL, 0),

    (3, '15:00:00', 'free', NULL, 0),
    (3, '15:30:00', 'free', NULL, 0),
    (3, '16:00:00', 'free', NULL, 0),
    (3, '16:30:00', 'free', NULL, 0),

    (4, '15:00:00', 'free', NULL, 0),
    (4, '15:30:00', 'free', NULL, 0),
    (4, '16:00:00', 'free', NULL, 0),
    (4, '16:30:00', 'free', NULL, 0);

/*!40000 ALTER TABLE `time_slot` ENABLE KEYS */;
UNLOCK TABLES;
