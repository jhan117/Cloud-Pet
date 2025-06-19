DROP DATABASE IF EXISTS `railway`;
CREATE DATABASE IF NOT EXISTS `railway`;
USE `railway`;

CREATE TABLE repeat_strategy (
    strategy_id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(10) NOT NULL CHECK (type IN ('DAY', 'WEEK', 'MONTH', 'YEAR')),
    interval_value INT NOT NULL,
    start_date DATE NOT NULL
);

CREATE TABLE repeat_week (
    repeat_week_id INT PRIMARY KEY AUTO_INCREMENT,
    strategy_id INT NOT NULL,
    FOREIGN KEY (strategy_id) REFERENCES repeat_strategy(strategy_id) ON DELETE CASCADE
);

CREATE TABLE repeat_week_day (
    repeat_week_day_id INT PRIMARY KEY AUTO_INCREMENT,
    repeat_week_id INT NOT NULL,
    day_of_week VARCHAR(3) NOT NULL CHECK (day_of_week IN ('MON','TUE','WED','THU','FRI','SAT','SUN')),
    FOREIGN KEY (repeat_week_id) REFERENCES repeat_week(repeat_week_id) ON DELETE CASCADE
);

CREATE TABLE care_plan (
    plan_id INT PRIMARY KEY AUTO_INCREMENT,
    plan_name VARCHAR(50) NOT NULL,
    strategy_id INT NOT NULL,
    FOREIGN KEY (strategy_id) REFERENCES repeat_strategy(strategy_id) ON DELETE CASCADE
);

CREATE TABLE care_log (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    plan_id INT NOT NULL,
    record_date DATE NOT NULL,
    is_done BOOLEAN NOT NULL,
    FOREIGN KEY (plan_id) REFERENCES care_plan(plan_id) ON DELETE CASCADE
);