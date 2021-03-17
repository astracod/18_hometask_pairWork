CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) not null unique
);

create table order_status
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) not null unique
);

create table users
(
    id       bigserial primary key,
    login    varchar(50)  not null unique,
    password varchar(255) not null,
    role_id  bigint       not null,
    phone    varchar(50)  not null,
    constraint user_role_fk foreign key (role_id) references roles (id)
);

create table orders
(
    id               bigserial primary key,
    author_user_id   bigint        not null,
    executor_user_id bigint,
    price            decimal(10,2) not null,
    name             varchar(100)  not null,
    description      TEXT,
    address          varchar(255)  not null,
    status_id        bigint        not null,
    constraint order_author_user_fk foreign key (author_user_id) references users (id),
    constraint order_executor_user_fk foreign key (executor_user_id) references users (id),
    constraint order_status_fk foreign key (status_id) references order_status (id)
);