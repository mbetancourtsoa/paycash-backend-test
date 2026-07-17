INSERT INTO users (name, email) VALUES
    ('Ana Torres', 'ana.torres@example.com'),
    ('Luis Pérez', 'luis.perez@example.com');

INSERT INTO items (title, description) VALUES
    ('Auriculares Bluetooth', 'Auriculares inalámbricos con cancelación de ruido'),
    ('Teclado Mecánico', 'Teclado mecánico retroiluminado'),
    ('Mouse Gamer', 'Mouse óptico de alta precisión'),
    ('Silla Ergonómica', 'Silla de oficina sin reseñas todavía');

INSERT INTO reviews (rating, item_id, user_id) VALUES
    (2.0, 1, 1),
    (3.0, 1, 2),
    (4.5, 2, 1),
    (4.0, 2, 2),
    (1.5, 3, 1);
