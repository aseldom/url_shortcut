create table url (
    id serial primary key not null,
    url varchar unique not null,
    hash_code varchar unique not null,
    counter int,
    site_id int not null references site(id)
);