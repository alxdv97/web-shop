create table store_products
(
    id          bigserial not null,
    name        varchar(100),
    price       double precision,
    order_id    bigint not null,
    position    varchar(20),
    primary key (id)
);

alter table store_products
    add constraint FK_order foreign key (order_id)
        references store_orders (id);