create table users
(
    id bigserial not null,
    username varchar(50) not null unique,
    primary key (id)
)