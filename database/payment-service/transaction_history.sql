CREATE TABLE transaction_history (
    id SERIAL PRIMARY KEY,
    payment_id BIGINT NOT NULL,
    status_change VARCHAR(50) NOT NULL,
    notes TEXT,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment
        FOREIGN KEY (payment_id)
        REFERENCES payments (id)
        ON DELETE CASCADE
);

delete from transaction_history;

ALTER SEQUENCE transaction_history_id_seq RESTART WITH 1;

-- Transaction history for completed payments
INSERT INTO transaction_history (payment_id, status_change, notes, changed_at) VALUES
(1, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-07-19 10:30:00'),
(2, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-08-15 11:00:00'),
(3, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-08-30 09:45:00'),
(4, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-09-16 14:20:00'),
(5, 'PENDING -> COMPLETED', 'Payment processed successfully via Credit Card', '2024-10-01 10:15:00'),
(6, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-11-11 09:30:00'),
(7, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-10-19 11:45:00'),
(8, 'PENDING -> COMPLETED', 'Payment processed successfully via Bank Transfer', '2024-07-31 14:00:00'),
(9, 'PENDING -> COMPLETED', 'Payment processed successfully via Bank Transfer', '2024-09-09 10:30:00'),
(10, 'PENDING -> COMPLETED', 'Payment processed successfully via Bank Transfer', '2024-10-16 15:45:00'),
(11, 'PENDING -> COMPLETED', 'Payment processed successfully via Credit Card', '2024-09-02 11:00:00'),
(12, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-08-31 10:20:00'),
(13, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-09-21 09:15:00'),
(14, 'PENDING -> COMPLETED', 'Payment processed successfully via Bank Transfer', '2024-10-06 14:30:00'),
(15, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-10-26 11:00:00'),
(16, 'PENDING -> COMPLETED', 'Payment processed successfully via Bank Transfer', '2024-08-16 10:45:00'),
(17, 'PENDING -> COMPLETED', 'Payment processed successfully via Credit Card', '2024-09-11 13:30:00'),
(18, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-10-21 10:00:00'),
(19, 'PENDING -> COMPLETED', 'Payment processed successfully via UPI', '2024-10-29 09:30:00'),
(20, 'PENDING -> COMPLETED', 'Payment processed successfully via Credit Card', '2024-11-04 14:15:00');

-- Transaction history for pending payments
INSERT INTO transaction_history (payment_id, status_change, notes, changed_at) VALUES
(21, 'CREATED -> PENDING', 'Payment initiated, awaiting milestone completion', '2024-11-08 10:00:00'),
(22, 'CREATED -> PENDING', 'Payment initiated, awaiting milestone completion', '2024-11-05 11:30:00');

-- Transaction history for failed payment
INSERT INTO transaction_history (payment_id, status_change, notes, changed_at) VALUES
(23, 'CREATED -> PENDING', 'Payment initiated', '2024-10-01 10:00:00'),
(23, 'PENDING -> FAILED', 'Payment failed - Insufficient funds in account', '2024-10-01 10:15:00');

-- Transaction history for refunded payment
INSERT INTO transaction_history (payment_id, status_change, notes, changed_at) VALUES
(24, 'CREATED -> PENDING', 'Payment initiated', '2024-09-25 10:30:00'),
(24, 'PENDING -> COMPLETED', 'Payment processed successfully', '2024-09-25 11:00:00'),
(24, 'COMPLETED -> REFUNDED', 'Partial refund processed due to minor design adjustments', '2024-09-26 14:00:00');

-- Additional history entries for some payments showing processing steps
INSERT INTO transaction_history (payment_id, status_change, notes, changed_at) VALUES
(1, 'Payment verification', 'UPI transaction verified with bank', '2024-07-19 10:29:00'),
(5, 'Payment verification', 'Credit card authorization successful', '2024-10-01 10:14:00'),
(14, 'Payment verification', 'Bank transfer confirmation received', '2024-10-06 14:25:00');

