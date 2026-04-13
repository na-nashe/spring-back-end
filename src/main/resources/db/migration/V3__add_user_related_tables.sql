-- ——— 1. Users ———
CREATE TABLE users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username      VARCHAR(50)  NOT NULL UNIQUE,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    avatar        TEXT,
    joined        TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ——— 2. User Saved Alternatives (Favorites) ———
CREATE TABLE user_saved
(
    user_id        UUID    NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    alternative_id INTEGER NOT NULL REFERENCES alternatives (id) ON DELETE CASCADE,
    saved_at       TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, alternative_id)
);

-- ——— 3. Reviews ———
CREATE TABLE reviews
(
    id             SERIAL PRIMARY KEY,
    user_id        UUID         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    alternative_id INTEGER      NOT NULL REFERENCES alternatives (id) ON DELETE CASCADE,
    rating         SMALLINT     NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title          VARCHAR(200),
    content        TEXT,
    pros           TEXT[], 
    cons           TEXT[], 
    helpful        BOOLEAN               DEFAULT false,
    timestamp      TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ——— 4. Indexes ———
CREATE INDEX idx_reviews_alternative_id ON reviews (alternative_id);