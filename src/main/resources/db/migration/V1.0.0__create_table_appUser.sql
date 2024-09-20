CREATE TABLE IF NOT EXISTS appUser(
    id BIGSERIAL PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    passwordHash TEXT NOT NULL,
    phone TEXT NOT NULL UNIQUE,    
    role TEXT,
    address TEXT,
    activated BOOLEAN,
    createdAt TIMESTAMP DEFAULT NOW()
);