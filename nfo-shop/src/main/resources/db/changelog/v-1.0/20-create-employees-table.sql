create table employees
(
    id         bigserial   not null,
    email      varchar(50) not null unique,
    first_name varchar(50),
    last_name  varchar(50),
    phone      varchar(30),
    position   varchar(30),
    primary key (id)
);