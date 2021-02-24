create table products
(
    id          bigserial not null,
    name        varchar(100),
    price       int,
    description text,
    primary key (id)
);