-- Insert into orders table
INSERT INTO orders (order_status, total_price)
VALUES
    ('PENDING', 1999.00),
    ('CONFIRMED', 4999.50),
    ('CONFIRMED', 2999.00),
    ('CANCELLED', 1499.00),
    ('PENDING', 799.00),
    ('CONFIRMED', 9999.00),
    ('PENDING', 459.00),
    ('PENDING', 2499.00),
    ('CONFIRMED', 1200.00),
    ('CANCELLED', 3500.00);

-- Insert into order_items table
INSERT INTO order_items (order_id, product_id, quantity)
VALUES
    (1, 2, 1),   -- Order 1, Product 2, Qty 1
    (1, 5, 2),   -- Order 1, Product 5, Qty 2
    (2, 3, 1),   -- Order 2, Product 3, Qty 1
    (3, 1, 4),   -- Order 3, Product 1, Qty 4
    (3, 7, 2),   -- Order 3, Product 7, Qty 2
    (4, 6, 1),   -- Order 4, Product 6, Qty 1
    (5, 4, 3),   -- Order 5, Product 4, Qty 3
    (6, 8, 2),   -- Order 6, Product 8, Qty 2
    (7, 9, 1),   -- Order 7, Product 9, Qty 1
    (8, 10, 5);  -- Order 8, Product 10, Qty 5
