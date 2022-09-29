drop database if exists deja_brew_test;
create database deja_brew_test;
use deja_brew_test;

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
    constraint fk_visit_id
        foreign key(id)
        references app_user(id)
);

create table review(
    review_id int primary key auto_increment,
    id int not null,
    brewery_id varchar(100) not null,
    rating int not null,
    review varchar(1000) not null,
    constraint fk_review_app_user_id
            foreign key(id)
            references app_user(id)
);


delimiter $$

create procedure known_good_state()
begin
    -- reset all of the tables
    truncate table beer;

    -- insert values
     insert into app_user
    		values
            (1, "userone", "cc3a0280e4fc1415930899896574e118", 0),
            (2, "usertwo", "dd3a0280e4fc1415930899896574e118", 0),
            (3, "userthree", "ee3a0280e4fc1415930899896574e118", 0);

    insert into beer
        values
        (1, "Miller", 4.0, "American Pilsner", "madtree-brewing-cincinnati"),
        (2, "Shiner Cheer", 4.2, "Christmas Ale", "madtree-brewing-cincinnati"),
        (3, "Bud Light", 4.0, "American Pilsner", "madtree-brewing-cincinnati"),
        (4, "Bud Light", 4.0, "American Pilsner", "10-56-brewing-company-knox");

    insert into visit
        values
        (1, 1, "madtree-brewing-cincinnati", "2022-08-01"),
        (2, 2, "madtree-brewing-cincinnati", "2022-08-10"),
        (3, 3, "madtree-brewing-cincinnati", "2022-08-20"),
        (4, 3, "10-56-brewing-company-knox", "2022-08-25");

    insert into review
        values
        (1, 1, "madtree-brewing-cincinnati", 4, "Test Review"),
        (2, 2, "madtree-brewing-cincinnati", 3, "Testing Review"),
        (3, 3, "madtree-brewing-cincinnati", 5, "Test Review 3"),
        (4, 3, "10-56-brewing-company-knox", 2, "Test Review 4");

end$$
delimiter ;

call known_good_state();