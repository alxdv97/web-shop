create table orders_products
(
    id         bigserial not null,
    order_id   bigint    not null,
    product_id bigint    not null,
    primary key (id, order_id, product_id)
);

alter table orders_products
    add constraint FK_order foreign key (order_id)
        references orders (id);

alter table orders_products
    add constraint FK_product foreign key (product_id)
        references products (id);