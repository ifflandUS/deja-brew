create database deja_brew;
use deja_brew;

create table app_user (
    id int primary key auto_increment,
    username varchar(30) not null,
    password_hash varchar(2048) not null,
    active bit not null default(0)
 );

create table beer (
    beer_id int primary key auto_increment,
    beer_name varchar(50) not null,
    abv decimal(3,1) not null,
    type varchar(30) not null
);

create table beer_brewery (
    beer_id int not null,
    brewery_id varchar(100) not null,
    count int not null default(0),
    constraint pk_beer_brewery
            primary key (beer_id, brewery_id),
        constraint fk_beer_brewery_beer_id
            foreign key (beer_id)
            references beer(beer_id),
);