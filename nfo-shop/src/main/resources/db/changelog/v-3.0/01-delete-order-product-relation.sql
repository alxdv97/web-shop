alter table orders_products
    drop constraint FK_order;
alter table orders_products
    drop constraint FK_product;

drop table orders_products;