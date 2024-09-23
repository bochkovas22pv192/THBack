
create table if not exists tb_task (
    id bigserial not null,
    email_author varchar not null,
    name varchar not null,
    description varchar not null,
    priority varchar not null,
    email_executor varchar not null,
    date_task date not null,
    time_create timestamp not null,
    state int not null,
    time_update  timestamp not null,
    primary key (id)
);