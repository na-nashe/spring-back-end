CREATE TABLE refresh_tokens
(
    refresh_token VARCHAR(255) PRIMARY KEY,
    user_id       UUID        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    expired_at    TIMESTAMPTZ NOT NULL
);
