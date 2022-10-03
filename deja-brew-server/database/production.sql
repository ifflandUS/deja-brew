create database deja_brew;
use deja_brew;

create table app_user (
    app_user_id int primary key auto_increment,
    zipcode int not null,
    username varchar(30) unique not null,
    password_hash varchar(2048) not null,
    disabled bit not null default(0)
 );

 create table app_role (
     app_role_id int primary key auto_increment,
     `name` varchar(50) not null unique
 );

 create table app_user_role (
     app_user_id int not null,
     app_role_id int not null,
     constraint pk_app_user_role
         primary key (app_user_id, app_role_id),
     constraint fk_app_user_role_user_id
         foreign key (app_user_id)
         references app_user(app_user_id),
     constraint fk_app_user_role_role_id
         foreign key (app_role_id)
         references app_role(app_role_id)
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
    app_user_id int not null,
    brewery_id varchar(100) not null,
    visit_date date not null,
    constraint fk_visit_app_user_id
        foreign key(app_user_id)
        references app_user(app_user_id)
);

create table visit_beer (
    visit_beer_id int primary key auto_increment,
    visit_id int not null,
    beer_id int not null,
    beer_count int not null,
     constraint pk_visit_beer
         primary key (visit_id, beer_id),
     constraint fk_visit_beer_visit_id
         foreign key (visit_id)
         references visit(visit_id),
     constraint fk_visit_beer_beer_id
         foreign key (beer_id)
         references beer(beer_id)
);

create table review(
    review_id int primary key auto_increment,
    app_user_id int not null,
    brewery_id varchar(100) not null,
    rating int not null,
    review varchar(1000) not null,
    constraint fk_review_app_user_id
            foreign key(app_user_id)
            references app_user(app_user_id)
);

insert into app_role (`name`)
    values
    ("USER")
