CREATE TABLE IF NOT EXISTS product(
    id BIGSERIAL PRIMARY KEY,
    sellerId BIGINT NOT NULL REFERENCES appUser(id),
    name TEXT NOT NULL,
    description TEXT,
    type TEXT NOT NULL,
    url TEXT NOT NULL,
    startingBid NUMERIC(10, 2) NOT NULL,
    auctionStartTime TIMESTAMP NOT NULL,
    auctionEndTime TIMESTAMP NOT NULL,
    status TEXT NOT NULL,
    isListed BOOLEAN,
    pickupLocation TEXT NOT NULL,
    createdAt TIMESTAMP DEFAULT NOW()
);