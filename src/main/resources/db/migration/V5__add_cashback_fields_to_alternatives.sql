ALTER TABLE alternatives
    ADD COLUMN is_cashback_available BOOLEAN,
    ADD COLUMN cashback_info TEXT;
