CREATE TABLE time_slot (
    id INT AUTO_INCREMENT PRIMARY KEY,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status ENUM('free', 'booked') NOT NULL,
    name VARCHAR(255)
);

INSERT INTO time_slot (day_of_week, start_time, end_time, status, name)
VALUES

    (0, '15:00:00', '15:30:00', 'free', ''),
    (0, '15:30:00', '16:00:00', 'free', ''),
    (0, '16:00:00', '16:30:00', 'free', ''),
    (0, '16:30:00', '17:00:00', 'free', ''),

    (1, '15:00:00', '15:30:00', 'free', ''),
    (1, '15:30:00', '16:00:00', 'free', ''),
    (1, '16:00:00', '16:30:00', 'free', ''),
    (1, '16:30:00', '17:00:00', 'free', ''),

    (2, '15:00:00', '15:30:00', 'free', ''),
    (2, '15:30:00', '16:00:00', 'free', ''),
    (2, '16:00:00', '16:30:00', 'free', ''),
    (2, '16:30:00', '17:00:00', 'free', ''),

    (3, '15:00:00', '15:30:00', 'free', ''),
    (3, '15:30:00', '16:00:00', 'free', ''),
    (3, '16:00:00', '16:30:00', 'free', ''),
    (3, '16:30:00', '17:00:00', 'free', ''),

    (4, '15:00:00', '15:30:00', 'free', ''),
    (4, '15:30:00', '16:00:00', 'free', ''),
    (4, '16:00:00', '16:30:00', 'free', ''),
    (4, '16:30:00', '17:00:00', 'free', '');

/*to insert status change for hall table */
UPDATE time_slot SET status = 'free' WHERE id > 0 AND status = 'booked';

/* to insert status change for one row */
UPDATE time_slot
SET status = 'free'
WHERE id = 1 AND dayOfWeek = 1 AND startTime = '10:00:00' AND endTime = '09:00:00' AND status = 'booked';
