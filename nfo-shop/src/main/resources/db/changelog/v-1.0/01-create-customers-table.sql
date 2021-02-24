create table customers
(
    id         bigserial   not null,
    email      varchar(50) not null unique,
    first_name varchar(50),
    last_name  varchar(50),
    phone      varchar(30),
    address    varchar(500),
    primary key (id)
);