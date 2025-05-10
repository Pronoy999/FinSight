alter database fin_sight set search_path to fin_sight;
drop table if exists txn_category cascade;
CREATE TABLE txn_category
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(100)                                                 NOT NULL,
    nature VARCHAR(10) CHECK (nature IN ('Credit', 'Debit', 'Neutral')) NOT NULL
);

INSERT INTO txn_category (name, nature)
VALUES ('Income', 'Credit'),
       ('Expenses', 'Debit'),
       ('Investments', 'Credit'),
       ('Transfers', 'Neutral'),
       ('Loans', 'Credit'),
       ('Taxes & Fees', 'Debit'),
       ('Charity & Donations', 'Debit'),
       ('Insurance', 'Debit');