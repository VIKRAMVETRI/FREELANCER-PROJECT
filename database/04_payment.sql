-- ========================================
-- Clear old data
-- ========================================
DELETE FROM transaction_history;
ALTER SEQUENCE transaction_history_id_seq RESTART WITH 1;

DELETE FROM payments;
ALTER SEQUENCE payments_id_seq RESTART WITH 1;

-- ========================================
-- PAYMENTS
-- ========================================

-- 20 Projects with payments (some completed, some pending, some failed/refunded)
INSERT INTO payments (transaction_id, project_id, payer_id, payee_id, amount, currency, payment_method, upi_id, status, payment_date, description, created_at, updated_at) VALUES
-- Project 1 (4 milestones) - COMPLETED
('TXN-2024-07-001', 1, 1, 21, 2500.00, 'USD', 'UPI', 'alex.dev@paytm', 'COMPLETED', '2024-07-19 10:30:00', 'Milestone 1: Initial Setup', '2024-07-18 16:30:00', '2024-07-19 10:30:00'),
('TXN-2024-08-001', 1, 1, 21, 2500.00, 'USD', 'UPI', 'alex.dev@paytm', 'COMPLETED', '2024-08-15 11:00:00', 'Milestone 2: Frontend Development', '2024-08-14 18:00:00', '2024-08-15 11:00:00'),
('TXN-2024-08-002', 1, 1, 21, 1500.00, 'USD', 'UPI', 'alex.dev@paytm', 'COMPLETED', '2024-08-30 09:45:00', 'Milestone 3: Backend & Payment Integration', '2024-08-29 18:30:00', '2024-08-30 09:45:00'),
('TXN-2024-09-001', 1, 1, 21, 1000.00, 'USD', 'UPI', 'alex.dev@paytm', 'COMPLETED', '2024-09-16 14:20:00', 'Milestone 4: Testing & Deployment', '2024-09-15 17:00:00', '2024-09-16 14:20:00'),

-- Project 2 (1 milestone) - COMPLETED
('TXN-2024-09-002', 2, 2, 22, 4500.00, 'USD', 'CREDIT_CARD', NULL, 'COMPLETED', '2024-10-01 10:15:00', 'Full payment for Mobile Banking App UI/UX Redesign', '2024-09-30 15:00:00', '2024-10-01 10:15:00'),

-- Project 3 (2 milestones) - 1 COMPLETED, 1 PENDING
('TXN-2024-11-001', 3, 3, 23, 1100.00, 'USD', 'UPI', 'carlos.writer@gpay', 'COMPLETED', '2024-11-11 09:30:00', 'Milestone 1: First 10 Articles', '2024-11-10 14:30:00', '2024-11-11 09:30:00'),
('TXN-2024-11-003', 3, 3, 23, 1100.00, 'USD', 'UPI', 'carlos.writer@gpay', 'PENDING', NULL, 'Milestone 2: Second 10 Articles (Pending)', '2024-11-08 10:00:00', '2024-11-08 10:00:00'),

-- Project 4 (2 milestones) - 1 COMPLETED, 1 PENDING
('TXN-2024-10-001', 4, 4, 24, 1000.00, 'USD', 'UPI', 'sophia.market@paytm', 'COMPLETED', '2024-10-19 11:45:00', 'Milestone 1: Strategy & Content Planning', '2024-10-18 15:30:00', '2024-10-19 11:45:00'),
('TXN-2024-11-004', 4, 4, 24, 1500.00, 'USD', 'CREDIT_CARD', NULL, 'PENDING', NULL, 'Milestone 2: Campaign Launch (Pending)', '2024-11-05 11:30:00', '2024-11-05 11:30:00'),

-- Project 5 (3 milestones) - COMPLETED
('TXN-2024-07-002', 5, 5, 25, 3000.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-07-31 14:00:00', 'Milestone 1: Architecture Design', '2024-07-30 17:30:00', '2024-07-31 14:00:00'),
('TXN-2024-09-003', 5, 5, 25, 4000.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-09-09 10:30:00', 'Milestone 2: Service Implementation', '2024-09-08 19:00:00', '2024-09-09 10:30:00'),
('TXN-2024-10-002', 5, 5, 25, 3000.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-10-16 15:45:00', 'Milestone 3: Testing & Deployment', '2024-10-15 17:30:00', '2024-10-16 15:45:00'),

-- Project 6 (1 milestone) - COMPLETED
('TXN-2024-09-004', 6, 6, 26, 6500.00, 'USD', 'CREDIT_CARD', NULL, 'COMPLETED', '2024-09-02 11:00:00', 'Full payment for Business Process Consulting', '2024-09-01 16:00:00', '2024-09-02 11:00:00'),

-- Project 7 (1 milestone) - COMPLETED
('TXN-2024-08-003', 7, 7, 27, 4200.00, 'USD', 'UPI', 'ethan.analyst@phonepe', 'COMPLETED', '2024-08-31 10:20:00', 'Full payment for Sales Analytics Dashboard', '2024-08-30 17:00:00', '2024-08-31 10:20:00'),

-- Project 8 (2 milestones) - 1 COMPLETED, 1 FAILED
('TXN-2024-09-005', 8, 8, 28, 3500.00, 'USD', 'UPI', 'isabella.art@gpay', 'COMPLETED', '2024-09-21 09:15:00', 'Milestone 1: Brand Identity Design', '2024-09-20 14:30:00', '2024-09-21 09:15:00'),
('TXN-2024-10-007', 8, 8, 28, 500.00, 'USD', 'UPI', 'isabella.art@gpay', 'FAILED', NULL, 'Bonus payment - Transaction failed', '2024-10-01 10:00:00', '2024-10-01 10:15:00'),

-- Project 9 (1 milestone) - COMPLETED
('TXN-2024-10-003', 9, 9, 29, 8500.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-10-06 14:30:00', 'Full payment for Cross-Platform Mobile App', '2024-10-05 18:45:00', '2024-10-06 14:30:00'),

-- Project 10 (1 milestone) - COMPLETED
('TXN-2024-10-004', 10, 10, 30, 3000.00, 'USD', 'UPI', 'mia.photo@paytm', 'COMPLETED', '2024-10-26 11:00:00', 'Full payment for Product Photography', '2024-10-25 16:30:00', '2024-10-26 11:00:00'),

-- Project 11 (1 milestone) - COMPLETED
('TXN-2024-08-004', 11, 11, 31, 9000.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-08-16 10:45:00', 'Full payment for Cloud Migration to AWS', '2024-08-15 18:00:00', '2024-08-16 10:45:00'),

-- Project 12 (1 milestone) - COMPLETED
('TXN-2024-09-008', 12, 12, 32, 4800.00, 'USD', 'UPI', 'olivia.design@paytm', 'COMPLETED', '2024-09-18 10:00:00', 'Full payment for eCommerce Website Design', '2024-09-17 15:30:00', '2024-09-18 10:00:00'),

-- Project 13 (1 milestone) - COMPLETED
('TXN-2024-09-006', 13, 13, 33, 5500.00, 'USD', 'CREDIT_CARD', NULL, 'COMPLETED', '2024-09-11 13:30:00', 'Full payment for Corporate Training Video', '2024-09-10 16:00:00', '2024-09-11 13:30:00'),

-- Project 14 (1 milestone) - COMPLETED
('TXN-2024-10-005', 14, 14, 34, 1500.00, 'USD', 'UPI', 'ava.copy@gpay', 'COMPLETED', '2024-10-21 10:00:00', 'Full payment for SaaS Landing Page Copywriting', '2024-10-20 15:00:00', '2024-10-21 10:00:00'),

-- Project 15 (1 milestone) - IN_PROGRESS
('TXN-2024-10-006', 15, 15, 35, 2500.00, 'USD', 'UPI', 'mason.dev@phonepe', 'COMPLETED', '2024-10-29 09:30:00', 'Milestone 1: Design & Architecture', '2024-10-28 16:30:00', '2024-10-29 09:30:00'),

-- Project 16 (1 milestone) - IN_PROGRESS
('TXN-2024-11-002', 16, 16, 36, 1500.00, 'USD', 'CREDIT_CARD', NULL, 'COMPLETED', '2024-11-04 14:15:00', 'Milestone 1: User Research Phase', '2024-11-03 16:00:00', '2024-11-04 14:15:00'),

-- Project 17 (1 milestone) - COMPLETED
('TXN-2024-10-008', 17, 17, 37, 3800.00, 'USD', 'UPI', 'liam.marketing@paytm', 'COMPLETED', '2024-10-22 11:00:00', 'Full payment for Marketing Strategy Plan', '2024-10-21 16:00:00', '2024-10-22 11:00:00'),

-- Project 18 (1 milestone) - COMPLETED
('TXN-2024-10-009', 18, 18, 38, 5000.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-10-23 14:30:00', 'Full payment for Mobile App Design', '2024-10-22 17:00:00', '2024-10-23 14:30:00'),

-- Project 19 (1 milestone) - COMPLETED
('TXN-2024-10-010', 19, 19, 39, 3200.00, 'USD', 'UPI', 'sophia.tech@paytm', 'COMPLETED', '2024-10-24 10:00:00', 'Full payment for Tech Documentation', '2024-10-23 15:30:00', '2024-10-24 10:00:00'),

-- Project 20 (1 milestone) - REFUNDED
('TXN-2024-09-007', 20, 20, 40, 500.00, 'USD', 'CREDIT_CARD', NULL, 'REFUNDED', '2024-09-25 11:00:00', 'Partial refund due to minor issue', '2024-09-25 10:30:00', '2024-09-26 14:00:00');

-- ========================================
-- TRANSACTION HISTORY
-- ========================================
-- ========================================
-- TRANSACTION HISTORY FOR ALL PAYMENTS
-- ========================================

-- Clear old data
DELETE FROM transaction_history;
ALTER SEQUENCE transaction_history_id_seq RESTART WITH 1;

-- ========================================
-- Project 1 (4 milestones) - COMPLETED
-- ========================================
INSERT INTO transaction_history (payment_id, status_change, notes, changed_at) VALUES
(1, 'CREATED -> PENDING', 'Payment initiated', '2024-07-18 16:30:00'),
(1, 'PENDING -> COMPLETED', 'Milestone 1: Initial Setup completed via UPI', '2024-07-19 10:30:00'),
(2, 'CREATED -> PENDING', 'Payment initiated', '2024-08-14 18:00:00'),
(2, 'PENDING -> COMPLETED', 'Milestone 2: Frontend Development completed via UPI', '2024-08-15 11:00:00'),
(3, 'CREATED -> PENDING', 'Payment initiated', '2024-08-29 18:30:00'),
(3, 'PENDING -> COMPLETED', 'Milestone 3: Backend & Payment Integration completed via UPI', '2024-08-30 09:45:00'),
(4, 'CREATED -> PENDING', 'Payment initiated', '2024-09-15 17:00:00'),
(4, 'PENDING -> COMPLETED', 'Milestone 4: Testing & Deployment completed via UPI', '2024-09-16 14:20:00'),

-- ========================================
-- Project 2 (1 milestone) - COMPLETED
-- ========================================
(5, 'CREATED -> PENDING', 'Payment initiated', '2024-09-30 15:00:00'),
(5, 'PENDING -> COMPLETED', 'Full payment for Mobile Banking App UI/UX Redesign via Credit Card', '2024-10-01 10:15:00'),

-- ========================================
-- Project 3 (2 milestones) - 1 COMPLETED, 1 PENDING
-- ========================================
(6, 'CREATED -> PENDING', 'Payment initiated', '2024-11-10 14:30:00'),
(6, 'PENDING -> COMPLETED', 'Milestone 1: First 10 Articles via UPI', '2024-11-11 09:30:00'),
(7, 'CREATED -> PENDING', 'Payment initiated', '2024-11-08 10:00:00'),
-- PENDING, no COMPLETED entry yet
(7, 'PENDING', 'Milestone 2: Second 10 Articles (Pending)', '2024-11-08 10:00:00'),

-- ========================================
-- Project 4 (2 milestones) - 1 COMPLETED, 1 PENDING
-- ========================================
(8, 'CREATED -> PENDING', 'Payment initiated', '2024-10-18 15:30:00'),
(8, 'PENDING -> COMPLETED', 'Milestone 1: Strategy & Content Planning via UPI', '2024-10-19 11:45:00'),
(9, 'CREATED -> PENDING', 'Payment initiated', '2024-11-05 11:30:00'),
-- PENDING, no COMPLETED yet
(9, 'PENDING', 'Milestone 2: Campaign Launch (Pending)', '2024-11-05 11:30:00'),

-- ========================================
-- Project 5 (3 milestones) - COMPLETED
-- ========================================
(10, 'CREATED -> PENDING', 'Payment initiated', '2024-07-30 17:30:00'),
(10, 'PENDING -> COMPLETED', 'Milestone 1: Architecture Design via Bank Transfer', '2024-07-31 14:00:00'),
(11, 'CREATED -> PENDING', 'Payment initiated', '2024-09-08 19:00:00'),
(11, 'PENDING -> COMPLETED', 'Milestone 2: Service Implementation via Bank Transfer', '2024-09-09 10:30:00'),
(12, 'CREATED -> PENDING', 'Payment initiated', '2024-10-15 17:30:00'),
(12, 'PENDING -> COMPLETED', 'Milestone 3: Testing & Deployment via Bank Transfer', '2024-10-16 15:45:00'),

-- ========================================
-- Project 6 (1 milestone) - COMPLETED
-- ========================================
(13, 'CREATED -> PENDING', 'Payment initiated', '2024-09-01 16:00:00'),
(13, 'PENDING -> COMPLETED', 'Full payment for Business Process Consulting via Credit Card', '2024-09-02 11:00:00'),

-- ========================================
-- Project 7 (1 milestone) - COMPLETED
-- ========================================
(14, 'CREATED -> PENDING', 'Payment initiated', '2024-08-30 17:00:00'),
(14, 'PENDING -> COMPLETED', 'Full payment for Sales Analytics Dashboard via UPI', '2024-08-31 10:20:00'),

-- ========================================
-- Project 8 (2 milestones) - 1 COMPLETED, 1 FAILED
-- ========================================
(15, 'CREATED -> PENDING', 'Payment initiated', '2024-09-20 14:30:00'),
(15, 'PENDING -> COMPLETED', 'Milestone 1: Brand Identity Design via UPI', '2024-09-21 09:15:00'),
(16, 'CREATED -> PENDING', 'Payment initiated', '2024-10-01 10:00:00'),
(16, 'PENDING -> FAILED', 'Bonus payment transaction failed due to insufficient funds', '2024-10-01 10:15:00'),

-- ========================================
-- Project 9 (1 milestone) - COMPLETED
-- ========================================
(17, 'CREATED -> PENDING', 'Payment initiated', '2024-10-05 18:45:00'),
(17, 'PENDING -> COMPLETED', 'Full payment for Cross-Platform Mobile App via Bank Transfer', '2024-10-06 14:30:00'),

-- ========================================
-- Project 10 (1 milestone) - COMPLETED
-- ========================================
(18, 'CREATED -> PENDING', 'Payment initiated', '2024-10-25 16:30:00'),
(18, 'PENDING -> COMPLETED', 'Full payment for Product Photography via UPI', '2024-10-26 11:00:00'),

-- ========================================
-- Project 11 (1 milestone) - COMPLETED
-- ========================================
(19, 'CREATED -> PENDING', 'Payment initiated', '2024-08-15 18:00:00'),
(19, 'PENDING -> COMPLETED', 'Full payment for Cloud Migration via Bank Transfer', '2024-08-16 10:45:00'),

-- ========================================
-- Project 12 (1 milestone) - COMPLETED
-- ========================================
(20, 'CREATED -> PENDING', 'Payment initiated', '2024-09-17 15:30:00'),
(20, 'PENDING -> COMPLETED', 'Full payment for eCommerce Website Design via UPI', '2024-09-18 10:00:00'),

-- ========================================
-- Project 13 (1 milestone) - COMPLETED
-- ========================================
(21, 'CREATED -> PENDING', 'Payment initiated', '2024-09-10 16:00:00'),
(21, 'PENDING -> COMPLETED', 'Full payment for Corporate Training Video via Credit Card', '2024-09-11 13:30:00'),

-- ========================================
-- Project 14 (1 milestone) - COMPLETED
-- ========================================
(22, 'CREATED -> PENDING', 'Payment initiated', '2024-10-20 15:00:00'),
(22, 'PENDING -> COMPLETED', 'Full payment for SaaS Landing Page Copywriting via UPI', '2024-10-21 10:00:00'),

-- ========================================
-- Project 15 (1 milestone) - IN_PROGRESS
-- ========================================
(23, 'CREATED -> PENDING', 'Payment initiated', '2024-10-28 16:30:00'),
(23, 'PENDING -> COMPLETED', 'Milestone 1: Design & Architecture via UPI', '2024-10-29 09:30:00'),

-- ========================================
-- Project 16 (1 milestone) - IN_PROGRESS
-- ========================================
(24, 'CREATED -> PENDING', 'Payment initiated', '2024-11-03 16:00:00'),
(24, 'PENDING -> COMPLETED', 'Milestone 1: User Research Phase via Credit Card', '2024-11-04 14:15:00'),

-- ========================================
-- Project 17 (1 milestone) - COMPLETED
-- ========================================
(25, 'CREATED -> PENDING', 'Payment initiated', '2024-10-21 16:00:00'),
(25, 'PENDING -> COMPLETED', 'Full payment for Marketing Strategy Plan via UPI', '2024-10-22 11:00:00'),

-- ========================================
-- Project 18 (1 milestone) - COMPLETED
-- ========================================
(26, 'CREATED -> PENDING', 'Payment initiated', '2024-10-22 17:00:00'),
(26, 'PENDING -> COMPLETED', 'Full payment for Mobile App Design via Bank Transfer', '2024-10-23 14:30:00'),

-- ========================================
-- Project 19 (1 milestone) - COMPLETED
-- ========================================
(27, 'CREATED -> PENDING', 'Payment initiated', '2024-10-23 15:30:00'),
(27, 'PENDING -> COMPLETED', 'Full payment for Tech Documentation via UPI', '2024-10-24 10:00:00'),

-- ========================================
-- Project 20 (1 milestone) - REFUNDED
-- ========================================
(28, 'CREATED -> PENDING', 'Payment initiated', '2024-09-25 10:30:00'),
(28, 'PENDING -> COMPLETED', 'Payment processed successfully via Credit Card', '2024-09-25 11:00:00'),
(28, 'COMPLETED -> REFUNDED', 'Partial refund processed due to minor issue', '2024-09-26 14:00:00');


