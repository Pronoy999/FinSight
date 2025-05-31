-- Ensure you’re using the correct schema
ALTER DATABASE fin_sight SET search_path TO fin_sight;

-- Drop sequence and table if they exist
DROP SEQUENCE IF EXISTS tbl_tran_log_seq CASCADE;
CREATE SEQUENCE tbl_tran_log_seq;

DROP TABLE IF EXISTS tbl_tran_log;

-- Updated table definition
CREATE TABLE IF NOT EXISTS tbl_tran_log
(
    id                  INTEGER PRIMARY KEY     DEFAULT nextval('tbl_tran_log_seq'),
    user_guid           VARCHAR(1000)  NOT NULL,
    year                INTEGER        NOT NULL,
    month               INTEGER        NOT NULL,
    date                INTEGER        NOT NULL,
    account_id          INTEGER        NOT NULL,
    txn_category_id     INTEGER        NOT NULL,
    txn_sub_category_id INTEGER,
    txn_frequency       VARCHAR(50)    NOT NULL,
    recurring_id        INTEGER                 DEFAULT -1,
    transfer_type       VARCHAR(10)    NOT NULL,
    txn_amount          DECIMAL(10, 2) NOT NULL,
    created             TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated             TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Foreign key constraints

-- User GUID
ALTER TABLE tbl_tran_log
    ADD CONSTRAINT fk_user_guid
        FOREIGN KEY (user_guid)
            REFERENCES fin_sight.tbl_user (guid)
            ON DELETE CASCADE;

-- Account ID
ALTER TABLE tbl_tran_log
    ADD CONSTRAINT fk_account_id
        FOREIGN KEY (account_id)
            REFERENCES fin_sight.tbl_accounts (account_id)
            ON DELETE CASCADE;

-- Txn Category
ALTER TABLE tbl_tran_log
    ADD CONSTRAINT fk_txn_category
        FOREIGN KEY (txn_category_id)
            REFERENCES txn_category (id)
            ON DELETE CASCADE;

-- Txn Subcategory
ALTER TABLE tbl_tran_log
    ADD CONSTRAINT fk_txn_sub_category
        FOREIGN KEY (txn_sub_category_id)
            REFERENCES txn_sub_category (id)
            ON DELETE CASCADE;

ALTER TABLE tbl_tran_log
    ADD CONSTRAINT fk_recurring_id
        FOREIGN KEY (recurring_id)
            REFERENCES tbl_recurring (recurring_id)
            ON DELETE CASCADE;