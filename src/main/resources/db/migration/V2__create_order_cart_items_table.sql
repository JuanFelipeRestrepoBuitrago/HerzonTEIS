-- Create join table for many-to-many relationship
CREATE TABLE order_cart_items (
    order_id BIGINT NOT NULL,
    cart_item_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, cart_item_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (cart_item_id) REFERENCES cart_items(id) ON DELETE CASCADE
);

CREATE INDEX idx_order_cart_items_order ON order_cart_items(order_id);
CREATE INDEX idx_order_cart_items_cart_item ON order_cart_items(cart_item_id);