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

create table visit (
    visit_id int primary key auto_increment,
    id int not null,
    brewery_id varchar(100) not null,
    visit_date date not null,
    constraint fk_visit_app_user_id
        foreign key(id)
        references app_user(id)
)

create table review(
    review_id int primary key auto_increment,
    id int not null,
    brewery_id varchar(100) not null,
    rating int not null,
    review varchar(1000) not null,
    constraint fk_review_app_user_id
            foreign key(id)
            references app_user(id)
)