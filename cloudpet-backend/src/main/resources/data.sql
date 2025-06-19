INSERT INTO repeat_strategy (type, interval_value, start_date) VALUES
('DAY', 1, '2025-06-19'),
('WEEK', 2, '2025-05-16'),
('MONTH', 1, '2025-05-19'),
('YEAR', 1, '2024-06-19');

INSERT INTO repeat_week (strategy_id) VALUES
(2);

INSERT INTO repeat_week_day (repeat_week_id, day_of_week) VALUES
(1, 'MON'),
(1, 'WED'),
(1, 'FRI');

INSERT INTO care_plan (plan_name, strategy_id) VALUES
('Daily Exercise', 1),
('Weekly Meeting', 2),
('Monthly Report', 3),
('Yearly Audit', 4);

INSERT INTO care_log (plan_id, record_date, is_done) VALUES
(1, '2025-06-19', true);