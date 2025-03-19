-- Function to update the modified_at column when a row is updated
CREATE OR REPLACE FUNCTION update_modified_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.modified_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--------------------------------------------------
-- Existing tables and triggers
--------------------------------------------------

-- Create the users table
CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  address VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL CHECK (role IN ('ADMIN', 'USER')),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Trigger to update modified_at in users
CREATE TRIGGER update_users_modified_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_at();

-- Create the credit_cards table
CREATE TABLE credit_cards (
  id BIGSERIAL PRIMARY KEY,
  card_number VARCHAR(255) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  expiration_date VARCHAR(255) NOT NULL,
  user_id BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Trigger to update modified_at in credit_cards
CREATE TRIGGER update_credit_cards_modified_at
    BEFORE UPDATE ON credit_cards
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_at();

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

-- Trigger to update modified_at in jewels
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

-- Trigger to update modified_at in auctions
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
  user_id BIGINT NOT NULL,
  FOREIGN KEY (auction_id) REFERENCES auctions(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Trigger to update modified_at in offers
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
    user_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'PAID', 'CANCELED')),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Trigger to update modified_at in orders
CREATE TRIGGER update_orders_modified_at
    BEFORE UPDATE ON orders
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_at();

--------------------------------------------------
-- Tables for Cart, CartItem, and WishList models
--------------------------------------------------

-- Create the carts table
CREATE TABLE carts (
  id BIGSERIAL PRIMARY KEY,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  user_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Trigger to update updated_at in carts
CREATE TRIGGER update_carts_modified_at
    BEFORE UPDATE ON carts
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_at();

-- Create the cart_items table
CREATE TABLE cart_items (
  id BIGSERIAL PRIMARY KEY,
  quantity INT NOT NULL,
  jewel_id BIGINT NOT NULL,
  cart_id BIGINT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (jewel_id) REFERENCES jewels(id) ON DELETE CASCADE,
  FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE
);

-- Trigger to update updated_at in cart_items
CREATE TRIGGER update_cart_items_modified_at
    BEFORE UPDATE ON cart_items
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_at();

-- Create the wishlists table
-- Nota: La entidad WishList originalmente se mapea a "cartItems", pero se recomienda usar una tabla separada.
CREATE TABLE wishlists (
                           id BIGSERIAL PRIMARY KEY,
                           user_id BIGINT NOT NULL UNIQUE,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TRIGGER update_wishlists_modified_at
    BEFORE UPDATE ON wishlists
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_at();