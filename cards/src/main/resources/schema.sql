CREATE TABLE IF NOT EXISTS `card` (
    `card_id` int AUTO_INCREMENT  PRIMARY KEY,
    `card_number` varchar(20) not null ,
    `card_type` varchar(20) not null,
    `total_limit` int default 0,
    `amount_used` int default 0,
    `available_amount` int default 0,
    `mobile_number` varchar(20) NOT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);