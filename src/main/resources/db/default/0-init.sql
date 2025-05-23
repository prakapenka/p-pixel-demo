CREATE TABLE "USER" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(500) NOT NULL UNIQUE,
    date_of_birth DATE,
    password VARCHAR(500) NOT NULL CHECK (char_length(password) >= 8)
);

CREATE TABLE ACCOUNT (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES "USER"(id) ON DELETE CASCADE ,
    balance DECIMAL(15,2) DEFAULT 0 CHECK (balance >= 0) -- 9,999,999,999,999.99 fits all money
);

CREATE TABLE EMAIL_DATA (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES "USER"(id) ON DELETE CASCADE ,
    email VARCHAR(200) NOT NULL UNIQUE CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$') -- regular
    -- email format
);

CREATE TABLE PHONE_DATA (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES "USER"(id) ON DELETE CASCADE,
    phone VARCHAR(13) NOT NULL UNIQUE CHECK (phone ~ '^\d{1,15}$') -- 79207865432
)




