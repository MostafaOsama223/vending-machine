CREATE TABLE IF NOT EXISTS "sellers" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "buyers" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    deposit INTEGER NOT NULL DEFAULT 0 CHECK (deposit >= 0 AND deposit % 5 = 0),
);

INSERT INTO "sellers" (id, username, password) VALUES (1, 'seller_1', '{noop}password');
INSERT INTO "sellers" (id, username, password) VALUES (2, 'seller_2', '{noop}password');

INSERT INTO "buyers" (id, username, password, deposit) VALUES (1, 'buyer', '{noop}password', 1000);