create table if not exists orders
(
    id            bigserial not null,
    customer_id   bigint    not null,
    employee_id   bigint    not null,
    creation_date timestamp not null,
    delivery_date timestamp,
    primary key (id)
);

alter table orders
    add constraint FK_customer foreign key (customer_id)
        references customers (id);

alter table orders
    add constraint FK_employee foreign key (employee_id)
        references employees (id);