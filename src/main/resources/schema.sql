--create table if not exists tb_task (
--    id bigserial not null,
--    email_author varchar not null,
--    name varchar not null,
--    description varchar not null,
--    priority varchar not null,
--    email_executor varchar not null,
--    date_task date not null,
--    time_create timestamp not null,
--    state int not null,
--    time_update  timestamp not null,
--    primary key (id)
--);

create table if not exists employee (
    id bigserial not null,
    first_name varchar not null,
    last_name varchar not null,
    father_name varchar not null,
    rank varchar not null,
    phone_number varchar not null,
    birthday date not null,
    email varchar not null,
    telegram varchar not null,
    work_beginning_date date not null,
    photo bigserial,
    primary key (id)
);

create table if not exists offer (
    id bigserial not null,
    title varchar not null,
    description varchar not null,
    state smallint not null,
    date_published date not null,
    author_id bigserial REFERENCES employee (id),
    primary key (id)
);

create table if not exists offer_photo (
    id bigserial not null,
    photo bigserial,
    offer_id bigserial REFERENCES offer (id),
    primary key (id)
);

create table if not exists offer_rate (
    id bigserial not null,
    offer_rate_type varchar not null,
    offer_id bigserial REFERENCES offer (id),
    author_id bigserial REFERENCES employee (id),
    primary key (id)
);