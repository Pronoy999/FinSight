alter database fin_sight set search_path to fin_sight;
drop table if exists tbl_recurring;

create table tbl_recurring
(
    recurring_id     integer primary key not null,
    account_id       integer             not null,
    user_guid        varchar(1000)       not null,
    nature           varchar(100),
    type             varchar(100),
    frequency        varchar(10)         not null,
    transfer_type    varchar(50)         not null,
    estimated_amount decimal(10, 2)      not null default 0.0,
    created_at       timestamp                    default current_timestamp,
    updated_at       timestamp                    default current_timestamp
);

ALTER TABLE tbl_recurring
    ADD CONSTRAINT fk_user_guid
        FOREIGN KEY (user_guid)
            REFERENCES fin_sight.tbl_user (guid)
            ON DELETE CASCADE;

ALTER table tbl_recurring
    add constraint fk_account_id
        foreign key (account_id)
            references fin_sight.tbl_accounts (account_id)
            on delete cascade;