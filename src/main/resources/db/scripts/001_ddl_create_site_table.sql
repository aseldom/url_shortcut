create table site (
    id serial primary key not null,
    login varchar unique not null,
    password varchar not null
);