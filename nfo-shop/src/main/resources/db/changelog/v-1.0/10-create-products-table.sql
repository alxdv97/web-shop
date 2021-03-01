create table products
(
    id          bigserial not null,
    name        varchar(100),
    price       double precision,
    description text,
    primary key (id)
);