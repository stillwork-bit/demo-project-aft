create table test_msg
(
    id          integer not null,
    topic       varchar,
    create_date date,
    event_id    varchar,
    json        varchar
);

alter table test_msg
    owner to postgres;