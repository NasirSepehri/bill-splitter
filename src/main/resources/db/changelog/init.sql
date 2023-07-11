-- liquibase formatted sql

-- changeset liquibase:1
create table client
(
    client_id  uuid         not null
        primary key,
    email      varchar(255) not null
        constraint uk_bfgjs3fem0hmjhvih80158x29
            unique,
    first_name varchar(100) not null,
    last_name  varchar(100) not null,
    password   varchar(255) not null,
    role       varchar(255)
        constraint client_role_check
            check ((role)::text = ANY
        ((ARRAY ['ROLE_USER':: character varying, 'ROLE_ADMIN':: character varying])::text[])
) ,
    username   varchar(255) not null
        constraint uk_ah5c1ribskm746956okm9283n
            unique
);


-- changeset liquibase:2
create index idxah5c1ribskm746956okm9283n
    on client (username);

-- changeset liquibase:3
create table event
(
    event_id     bigint       not null
        primary key,
    created_date timestamp(6) with time zone,
    description  varchar(255),
    name         varchar(100) not null,
    client_id    uuid         not null
        constraint fktpp6e5l87vdkxia1nfxv2r3il
            references client
);


-- changeset liquibase:4
create table cost
(
    cost_id          bigint       not null
        primary key,
    cost_amount      real         not null,
    cost_description varchar(500) not null,
    paid_by_username varchar(255),
    paid_by_uuid     uuid,
    event_id         bigint       not null
        constraint fk922u0roi2wbldf1kmdlxy49v1
            references event
);


-- changeset liquibase:5
create table event_member
(
    event_id bigint not null
        constraint fk8e54pd1io5a7gbp6x1io4vkft
            references event,
    username varchar(255),
    uuid     uuid
);

-- changeset liquibase:6
create table split_between_user
(
    cost_id  bigint not null
        constraint fk707gru6ke78n9i91jfjx7m9w3
            references cost,
    username varchar(255),
    uuid     uuid
);

-- changeset liquibase:7
create sequence cost_seq;

-- changeset liquibase:8
create sequence event_seq;


