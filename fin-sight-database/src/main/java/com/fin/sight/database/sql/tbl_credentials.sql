alter database fin_sight set search_path to fin_sight;
drop sequence if exists tbl_credentials_seq cascade;
create sequence tbl_credentials_seq;
drop table if exists tbl_credentials;
create table tbl_credentials
(
    id                  integer primary key not null default nextval('tbl_credentials_seq'),
    email_id            varchar(1000)       not null,
    password            varchar(1000)       not null,
    user_guid           varchar(1000)       not null,
    is_third_party_sign boolean             not null default false,
    is_active           boolean                      default true not null,
    created             timestamp                    default current_timestamp,
    updated             timestamp                    default current_timestamp
);

ALTER TABLE tbl_credentials
    add constraint fk_user_guid
        foreign key (user_guid)
            references fin_sight.tbl_user (guid)
            ON DELETE cascade;

ALTER TABLE tbl_credentials
    ALTER COLUMN password DROP NOT NULL;