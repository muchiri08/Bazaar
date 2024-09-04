CREATE TABLE IF NOT EXISTS bid(
    id BIGSERIAL PRIMARY KEY,
    bidderId BIGSERIAL REFERENCES appUser(id),
    productId BIGSERIAL REFERENCES product(id),
    bidAmount NUMERIC(10, 2) NOT NULL,
    updatedAt TIMESTAMP,
    version SMALLINT,
    createdAt TIMESTAMP DEFAULT NOW()
);