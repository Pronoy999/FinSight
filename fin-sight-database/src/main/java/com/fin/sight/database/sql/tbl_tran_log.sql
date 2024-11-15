alter database fin_sight set search_path to fin_sight;
drop sequence if exists tbl_tran_log_seq;
create sequence tbl_tran_log_seq;
create table if not exists tbl_tran_log
(
    id               integer primary key     default nextval('tbl_tran_log_seq'),
    user_guid        varchar(1000)  not null,
    year             integer        not null,
    month            integer        not null,
    date             integer        not null,
    account_name     varchar(500)   not null,
    txn_category     varchar(300)   not null,
    txn_sub_category varchar(300)   not null,
    txn_nature       varchar(300)   not null,
    txn_frequency    varchar(50)    not null,
    transfer_type    varchar(10)    not null,
    estimated_amount decimal(10, 2) not null,
    actual_amount    decimal(10, 2) not null,
    created          timestamp      not null default current_timestamp,
    updated          timestamp      not null default current_timestamp
);

ALTER TABLE tbl_tran_log
    ADD CONSTRAINT fk_user_guid
        FOREIGN KEY (user_guid)
            REFERENCES fin_sight.tbl_user (guid)
            ON DELETE CASCADE;