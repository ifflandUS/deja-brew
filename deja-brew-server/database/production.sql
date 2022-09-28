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
    type varchar(30) not null,
    brewery_id varchar(100)
);
