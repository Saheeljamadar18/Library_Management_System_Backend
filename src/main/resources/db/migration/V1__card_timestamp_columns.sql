-- Manual fallback if auto-migration on startup did not run (e.g. no ALTER privilege)
ALTER TABLE card
    ADD COLUMN created_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6);
ALTER TABLE card
    ADD COLUMN updated_date DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
        ON UPDATE CURRENT_TIMESTAMP(6);
