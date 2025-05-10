alter database fin_sight set search_path to fin_sight;
drop table if exists tbl_accounts cascade;
drop sequence if exists tbl_accounts_seq cascade;
create sequence tbl_accounts_seq;
create table tbl_accounts
(
    account_id   integer primary key not null default nextval('tbl_accounts_seq'),
    account_name varchar(200)        not null,
    account_type varchar(100),
    user_guid    varchar(1000)       not null,
    created      timestamp                    default current_timestamp,
    updated      timestamp                    default current_timestamp
);
ALTER TABLE tbl_accounts
    add constraint fk_user_guid
        foreign key (user_guid)
            references fin_sight.tbl_user (guid)
            ON DELETE cascade;