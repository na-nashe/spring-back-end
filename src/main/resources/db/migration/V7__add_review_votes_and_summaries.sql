-- ——— Cleanup ———
-- Orphan enum created out-of-band by Hibernate ddl-auto=update; the real column
-- (alternatives.pricing_model) uses pricing_model_enum. Unused, so drop it.
-- CASCADE removes the two leftover varchar<->pricingmodel casts Hibernate added.
DROP TYPE IF EXISTS pricingmodel CASCADE;

-- ——— Review Summaries ———
-- One AI-generated summary of all reviews per alternative.
CREATE TABLE review_summaries
(
    alternative_id INTEGER PRIMARY KEY REFERENCES alternatives (id) ON DELETE CASCADE,
    summary        TEXT        NOT NULL,
    updated_at     TIMESTAMPTZ NOT NULL
);

-- ——— Review Votes ———
-- Up/down votes on individual reviews. value: 1 = like, -1 = dislike.
-- One vote per (review, user).
CREATE TABLE review_votes
(
    review_id INTEGER  NOT NULL REFERENCES reviews (id) ON DELETE CASCADE,
    user_id   UUID     NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    value     SMALLINT NOT NULL CHECK (value IN (-1, 1)),
    PRIMARY KEY (review_id, user_id)
);
