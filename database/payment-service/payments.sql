CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    transaction_id VARCHAR(255) NOT NULL UNIQUE,
    project_id BIGINT NOT NULL,
    payer_id BIGINT NOT NULL,
    payee_id BIGINT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    payment_method VARCHAR(50),
    upi_id VARCHAR(100),
    status VARCHAR(50) NOT NULL,
    payment_date TIMESTAMP,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Reset sequences
ALTER SEQUENCE payments_id_seq RESTART WITH 1;

CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    transaction_id VARCHAR(255) NOT NULL UNIQUE,
    project_id BIGINT NOT NULL,
    payer_id BIGINT NOT NULL,
    payee_id BIGINT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    payment_method VARCHAR(50),
    upi_id VARCHAR(100),
    status VARCHAR(50) NOT NULL,
    payment_date TIMESTAMP,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Reset sequence
ALTER SEQUENCE payments_id_seq RESTART WITH 1;

INSERT INTO payments (
    transaction_id, project_id, payer_id, payee_id, amount, currency, payment_method, upi_id, status, payment_date, description, created_at, updated_at
)
VALUES
-- Project 1
('TXN-2024-07-001', 1, 1, 21, 2500.00, 'USD', 'UPI', 'alex.dev@paytm', 'COMPLETED', '2024-07-19 10:30:00', 'Payment for Milestone 1: Initial Setup and Architecture', '2024-07-18 16:30:00', '2024-07-19 10:30:00'),
('TXN-2024-08-001', 1, 1, 21, 2500.00, 'USD', 'UPI', 'alex.dev@paytm', 'COMPLETED', '2024-08-15 11:00:00', 'Payment for Milestone 2: Frontend Development', '2024-08-14 18:00:00', '2024-08-15 11:00:00'),
('TXN-2024-08-002', 1, 1, 21, 1500.00, 'USD', 'UPI', 'alex.dev@paytm', 'COMPLETED', '2024-08-30 09:45:00', 'Payment for Milestone 3: Backend and Payment Integration', '2024-08-29 18:30:00', '2024-08-30 09:45:00'),
('TXN-2024-09-001', 1, 1, 21, 1000.00, 'USD', 'UPI', 'alex.dev@paytm', 'COMPLETED', '2024-09-16 14:20:00', 'Payment for Milestone 4: Testing and Deployment', '2024-09-15 17:00:00', '2024-09-16 14:20:00'),

-- Project 2
('TXN-2024-09-002', 2, 2, 22, 4500.00, 'USD', 'CREDIT_CARD', NULL, 'COMPLETED', '2024-10-01 10:15:00', 'Full payment for Mobile Banking App UI/UX Redesign', '2024-09-30 15:00:00', '2024-10-01 10:15:00'),

-- Project 3
('TXN-2024-11-001', 3, 3, 23, 1100.00, 'USD', 'UPI', 'carlos.writer@gpay', 'COMPLETED', '2024-11-11 09:30:00', 'Payment for Milestone 1: First 10 Articles', '2024-11-10 14:30:00', '2024-11-11 09:30:00'),
('TXN-2024-11-003', 3, 3, 23, 1100.00, 'USD', 'UPI', 'carlos.writer@gpay', 'PENDING', NULL, 'Payment for Milestone 2: Second 10 Articles (Pending completion)', '2024-11-08 10:00:00', '2024-11-08 10:00:00'),

-- Project 4
('TXN-2024-10-001', 4, 4, 24, 1000.00, 'USD', 'UPI', 'sophia.market@paytm', 'COMPLETED', '2024-10-19 11:45:00', 'Payment for Milestone 1: Strategy and Content Planning', '2024-10-18 15:30:00', '2024-10-19 11:45:00'),
('TXN-2024-11-004', 4, 4, 24, 1500.00, 'USD', 'CREDIT_CARD', NULL, 'PENDING', NULL, 'Payment for Milestone 2: Campaign Launch and Management (In Progress)', '2024-11-05 11:30:00', '2024-11-05 11:30:00'),

-- Project 5
('TXN-2024-07-002', 5, 5, 25, 3000.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-07-31 14:00:00', 'Payment for Milestone 1: Architecture Design', '2024-07-30 17:30:00', '2024-07-31 14:00:00'),
('TXN-2024-09-003', 5, 5, 25, 4000.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-09-09 10:30:00', 'Payment for Milestone 2: Service Implementation', '2024-09-08 19:00:00', '2024-09-09 10:30:00'),
('TXN-2024-10-002', 5, 5, 25, 3000.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-10-16 15:45:00', 'Payment for Milestone 3: Testing and Deployment', '2024-10-15 17:30:00', '2024-10-16 15:45:00'),

-- Project 6
('TXN-2024-09-004', 6, 6, 26, 6500.00, 'USD', 'CREDIT_CARD', NULL, 'COMPLETED', '2024-09-02 11:00:00', 'Full payment for Business Process Consulting', '2024-09-01 16:00:00', '2024-09-02 11:00:00'),

-- Project 7
('TXN-2024-08-003', 7, 7, 27, 4200.00, 'USD', 'UPI', 'ethan.analyst@phonepe', 'COMPLETED', '2024-08-31 10:20:00', 'Full payment for Sales Analytics Dashboard', '2024-08-30 17:00:00', '2024-08-31 10:20:00'),

-- Project 8
('TXN-2024-09-005', 8, 8, 28, 3500.00, 'USD', 'UPI', 'isabella.art@gpay', 'COMPLETED', '2024-09-21 09:15:00', 'Full payment for Brand Identity Design', '2024-09-20 14:30:00', '2024-09-21 09:15:00'),
('TXN-2024-10-007', 8, 8, 28, 500.00, 'USD', 'UPI', 'isabella.art@gpay', 'FAILED', NULL, 'Bonus payment - Transaction failed', '2024-10-01 10:00:00', '2024-10-01 10:15:00'),

-- Project 9
('TXN-2024-10-003', 9, 9, 29, 8500.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-10-06 14:30:00', 'Full payment for Cross-Platform Mobile App Development', '2024-10-05 18:45:00', '2024-10-06 14:30:00'),

-- Project 10
('TXN-2024-10-004', 10, 10, 30, 3000.00, 'USD', 'UPI', 'mia.photo@paytm', 'COMPLETED', '2024-10-26 11:00:00', 'Full payment for Product Photography Services', '2024-10-25 16:30:00', '2024-10-26 11:00:00'),

-- Project 11
('TXN-2024-08-004', 11, 11, 31, 9000.00, 'USD', 'BANK_TRANSFER', NULL, 'COMPLETED', '2024-08-16 10:45:00', 'Full payment for Cloud Migration to AWS', '2024-08-15 18:00:00', '2024-08-16 10:45:00'),

-- Project 13
('TXN-2024-09-006', 13, 13, 33, 5500.00, 'USD', 'CREDIT_CARD', NULL, 'COMPLETED', '2024-09-11 13:30:00', 'Full payment for Corporate Training Video Production', '2024-09-10 16:00:00', '2024-09-11 13:30:00'),

-- Project 14
('TXN-2024-10-005', 14, 14, 34, 1500.00, 'USD', 'UPI', 'ava.copy@gpay', 'COMPLETED', '2024-10-21 10:00:00', 'Full payment for SaaS Landing Page Copywriting', '2024-10-20 15:00:00', '2024-10-21 10:00:00'),

-- Project 15
('TXN-2024-10-006', 15, 15, 35, 2500.00, 'USD', 'UPI', 'mason.dev@phonepe', 'COMPLETED', '2024-10-29 09:30:00', 'Payment for Milestone 1: Design and Architecture', '2024-10-28 16:30:00', '2024-10-29 09:30:00'),

-- Project 16
('TXN-2024-11-002', 16, 16, 36, 1500.00, 'USD', 'CREDIT_CARD', NULL, 'COMPLETED', '2024-11-04 14:15:00', 'Payment for Milestone 1: User Research Phase', '2024-11-03 16:00:00', '2024-11-04 14:15:00'),

-- Refund
('TXN-2024-09-007', 2, 2, 22, 500.00, 'USD', 'CREDIT_CARD', NULL, 'REFUNDED', '2024-09-25 11:00:00', 'Partial refund due to minor issue', '2024-09-25 10:30:00', '2024-09-26 14:00:00');
