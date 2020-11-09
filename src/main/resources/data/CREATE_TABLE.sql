CREATE TABLE IF NOT EXISTS costumers (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    age INTEGER NOT NULL CHECK (age > 0),
    country CHAR(2) NOT NULL,
    app_user SMALLINT references app_users(id)
);

CREATE TABLE IF NOT EXISTS app_users (
    id SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    costumer SMALLINT references costumers(id),
    name VARCHAR NOT NULL,
    price INTEGER NOT NULL,
    brand VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS stores (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    country CHAR(2) NOT NULL,
    location VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS item_to_store (
    item SMALLINT references items(id),
    store SMALLINT references stores(id),
    PRIMARY KEY(item, store)
);