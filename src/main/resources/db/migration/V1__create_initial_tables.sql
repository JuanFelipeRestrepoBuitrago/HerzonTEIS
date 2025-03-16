CREATE TABLE auctions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    start_price DOUBLE NOT NULL,
    current_price DOUBLE NOT NULL
);

CREATE TABLE offers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    offer_price DOUBLE NOT NULL,
    state BOOLEAN NOT NULL DEFAULT TRUE,
    auction_id BIGINT NOT NULL,
    FOREIGN KEY (auction_id) REFERENCES auctions(id)
);

CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL,
    status BOOLEAN NOT NULL DEFAULT FALSE
);