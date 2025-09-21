-- ------------------------------
-- Users
-- ------------------------------
INSERT INTO users (id, login, password, email) VALUES
                                                   (1, 'john_doe', 'password123', 'john.doe@example.com'),
                                                   (2, 'jane_smith', 'secret456', 'jane.smith@example.com');

-- ------------------------------
-- Clients
-- ------------------------------
INSERT INTO clients (id, client_id, user_id, first_name, middle_name, last_name, date_of_birth, document_type, document_id, document_prefix, document_suffix) VALUES
                                                                                                                                                                  (1, '770100000001', 1, 'John', 'A', 'Doe', '1985-06-15', 'PASSPORT', 'AB123456', '77', '01'),
                                                                                                                                                                  (2, '770200000002', 2, 'Jane', 'B', 'Smith', '1990-11-20', 'INT_PASSPORT', 'CD987654', '77', '02');

-- ------------------------------
-- Products
-- ------------------------------
INSERT INTO products (id, name, key, create_date, product_id) VALUES
                                                                  (1, 'Debit Card Standard', 'DC', '2025-01-01', 'DC1'),
                                                                  (2, 'Credit Card Platinum', 'CC', '2025-03-01', 'CC2'),
                                                                  (3, 'Savings Account', 'AC', '2025-02-15', 'AC3');

-- ------------------------------
-- Client Products
-- ------------------------------
INSERT INTO client_products (id, client_id, product_id, open_date, close_date, status) VALUES
                                                                                           (1, 1, 1, '2025-01-01', NULL, 'ACTIVE'),
                                                                                           (2, 1, 3, '2025-03-01', NULL, 'ACTIVE'),
                                                                                           (3, 2, 2, '2025-02-15', NULL, 'ACTIVE');

-- ------------------------------
-- Blacklist Registry
-- ------------------------------
INSERT INTO blacklist_registry (id, document_type, document_id, blacklisted_at, reason, blacklist_expiration_date) VALUES
                                                                                                                       (1, 'PASSPORT', 'ZZ112233', NOW(), 'Fraud', '2026-01-01'),
                                                                                                                       (2, 'INT_PASSPORT', 'YY445566', NOW(), 'Lost document', NULL);
