CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE items (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(1000)
);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    rating DOUBLE PRECISION,
    item_id BIGINT REFERENCES items(id),
    user_id BIGINT REFERENCES users(id)
);
