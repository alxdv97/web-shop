create table if not exists store_orders
(
    id           bigint    not null,
    customer_id  bigint    not null,
    employee_id  bigint    not null,
    delivered    timestamp,
    last_changed timestamp not null,
    status       varchar(20),
    primary key (id)
);