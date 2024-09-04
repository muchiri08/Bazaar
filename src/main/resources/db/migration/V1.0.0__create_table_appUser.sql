CREATE TABLE IF NOT EXISTS appUser(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL,
    passwordHash TEXT NOT NULL,
    phone TEXT NOT NULL,
    role TEXT,
    address TEXT,
    activated BOOLEAN,
    createdAt TIMESTAMP DEFAULT NOW()
);