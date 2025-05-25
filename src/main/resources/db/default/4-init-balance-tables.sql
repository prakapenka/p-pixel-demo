CREATE TABLE INITIAL_ACCOUNT_BALANCE
(
    user_id         BIGINT PRIMARY KEY REFERENCES "user" (id) ON DELETE CASCADE,
    initial_balance DECIMAL(15, 2) NOT NULL CHECK (initial_balance >= 0),
    max_balance     DECIMAL(15, 2) NOT NULL CHECK (max_balance >= 0)
);
