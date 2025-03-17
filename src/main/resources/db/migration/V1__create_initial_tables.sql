-- Function to update the modified_at column when a row is updated
CREATE OR REPLACE FUNCTION update_modified_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.modified_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create the jewels table
CREATE TABLE jewels (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    details VARCHAR(1000) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER update_jewels_modified_at
BEFORE UPDATE ON jewels
FOR EACH ROW
EXECUTE FUNCTION update_modified_at();

-- Create the auctions table
CREATE TABLE auctions (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    start_price DOUBLE PRECISION NOT NULL,
    current_price DOUBLE PRECISION NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    jewel_id BIGINT NOT NULL,
    FOREIGN KEY (jewel_id) REFERENCES jewels(id) ON DELETE CASCADE
);

CREATE TRIGGER update_auctions_modified_at
BEFORE UPDATE ON auctions
FOR EACH ROW
EXECUTE FUNCTION update_modified_at();

-- Create the offers table
CREATE TABLE offers (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    offer_price DOUBLE PRECISION NOT NULL,
    state BOOLEAN NOT NULL DEFAULT TRUE,
    auction_id BIGINT NOT NULL,
    FOREIGN KEY (auction_id) REFERENCES auctions(id) ON DELETE CASCADE
);

CREATE TRIGGER update_offers_modified_at
BEFORE UPDATE ON offers
FOR EACH ROW
EXECUTE FUNCTION update_modified_at();

-- Create the orders table
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE PRECISION NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'PAID', 'CANCELED'))
);

CREATE TRIGGER update_orders_modified_at
BEFORE UPDATE ON orders
FOR EACH ROW
EXECUTE FUNCTION update_modified_at();