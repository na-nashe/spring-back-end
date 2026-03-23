-- ——— Enums ———
CREATE TYPE pr_enum AS ENUM ('free', 'paid', 'freemium');

-- ——— Core Tables ———
CREATE TABLE categories
(
    id        SERIAL PRIMARY KEY,
    parent_id INTEGER REFERENCES categories (id),
    name      VARCHAR(100) NOT NULL,
    icon      VARCHAR(10)
);

CREATE TABLE products
(
    id          SERIAL PRIMARY KEY,
    category_id INTEGER      NOT NULL REFERENCES categories (id),
    name        VARCHAR(200) NOT NULL,
    emoji       VARCHAR(10),
    origin      VARCHAR(2) DEFAULT 'RU'
);

CREATE TABLE aliases
(
    id         SERIAL PRIMARY KEY,
    product_id INTEGER      NOT NULL REFERENCES products (id),
    name       VARCHAR(200) NOT NULL
);

CREATE TABLE alternatives
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(150) NOT NULL,
    origin        VARCHAR(2),
    rating        NUMERIC(3, 2),
    pricing_model pr_enum,
    description   TEXT,
    url           VARCHAR(500),
    ai_generated  BOOLEAN DEFAULT false
);

CREATE TABLE product_alternatives
(
    product_id     INTEGER NOT NULL REFERENCES products (id),
    alternative_id INTEGER NOT NULL REFERENCES alternatives (id),
    PRIMARY KEY (product_id, alternative_id)
);

-- ——— User & Interaction Tables ———
CREATE TABLE users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username      VARCHAR(50) UNIQUE  NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    avatar        TEXT,
    joined        TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reviews
(
    id             SERIAL PRIMARY KEY,
    user_id        UUID     NOT NULL REFERENCES users (id),
    alternative_id INTEGER  NOT NULL REFERENCES alternatives (id),
    rating         SMALLINT NOT NULL,
    title          VARCHAR(200),
    content        TEXT,
    pros           TEXT[],
    cons           TEXT[],
    helpful        BOOLEAN     DEFAULT true,
    timestamp      TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    edited_at      TIMESTAMPTZ,

    CONSTRAINT chk_reviews_rating CHECK (rating >= 1 AND rating <= 5)
);

CREATE TABLE user_saved
(
    user_id        UUID REFERENCES users (id),
    alternative_id INTEGER REFERENCES alternatives (id),
    saved_at       TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, alternative_id)
);