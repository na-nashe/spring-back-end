CREATE TYPE pr_enum AS ENUM ('free', 'paid', 'freemium');

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    parent_id INTEGER REFERENCES categories(id),
    name VARCHAR(100) NOT NULL,
    icon VARCHAR(10)
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    category_id INTEGER NOT NULL REFERENCES categories(id),
    name VARCHAR(200) NOT NULL,
    emoji VARCHAR(10),
    origin VARCHAR(2)
);

CREATE TABLE aliases (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES products(id),
    name VARCHAR(200) NOT NULL
);

CREATE TABLE alternatives (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    origin VARCHAR(2),
    rating NUMERIC(3,2),
    pricing_model pr_enum,
    description TEXT,
    url VARCHAR(500),
    ai_generated BOOLEAN
);

CREATE TABLE product_alternatives (
    product_id INTEGER NOT NULL REFERENCES products(id),
    alternative_id INTEGER NOT NULL REFERENCES alternatives(id),
    PRIMARY KEY (product_id, alternative_id)
);