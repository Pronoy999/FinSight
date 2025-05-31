alter database fin_sight set search_path to fin_sight;
create table if not exists fin_sight.tbl_user
(
    guid         varchar(1000) not null primary key,
    first_name   varchar(1000) not null,
    last_name    varchar(1000) not null,
    email_id     varchar(1000) not null,
    phone_number varchar(10)   not null,
    age          int           not null,
    created      timestamp     not null default current_timestamp,
    updated      timestamp     not null default current_timestamp
);

ALTER TABLE tbl_user
    ALTER COLUMN phone_number DROP NOT NULL;
ALTER TABLE tbl_user
    ALTER COLUMN age DROP NOT NULL;