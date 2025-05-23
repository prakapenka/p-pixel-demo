ALTER TABLE "user"
    ADD CONSTRAINT chk_user_dateofbirth_min
        CHECK (date_of_birth >= DATE '0001-01-01');
