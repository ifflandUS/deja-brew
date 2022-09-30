create database deja_brew;
use deja_brew;

create table app_user (
    app_user_id int primary key auto_increment,
    zipcode int not null,
    username unique varchar(30) not null,
    password_hash varchar(2048) not null,
    active bit not null default(0)
 );

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


create table visit (
    visit_id int primary key auto_increment,
    id int not null,
    brewery_id varchar(100) not null,
    visit_date date not null,
    constraint fk_visit_app_user_id
        foreign key(app_user_id)
        references app_user(app_user_id)
)

create table review(
    review_id int primary key auto_increment,
    id int not null,
    brewery_id varchar(100) not null,
    rating int not null,
    review varchar(1000) not null,
    constraint fk_review_app_user_id
            foreign key(app_user_id)
            references app_user(app_user_id)
)