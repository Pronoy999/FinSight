alter
database fin_sight set search_path to fin_sight;
drop table if exists tbl_credentials;
create table tbl_credentials
(
    id         integer primary key    not null,
    email_id   varchar(1000)          not null,
    password   varchar(1000)          not null,
    user_guid       varchar(1000)          not null,
    is_active  boolean   default true not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

ALTER TABLE tbl_credentials
    add constraint fk_user_guid
        foreign key (user_guid)
            references fin_sight.tbl_user (guid)
            ON DELETE cascade;