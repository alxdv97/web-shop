alter table orders
    drop constraint FK_customer;
alter table orders
    drop constraint FK_employee;

drop table orders;