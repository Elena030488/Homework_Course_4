create table car (
id bigserial primary key,
brand text,
model text,
price bigserial
);

create table person (
id bigserial primary key,
first_name text,
age int,
driver_licence bool,
car_idbigserial,
foreign key (car_id) references car(id)
);
