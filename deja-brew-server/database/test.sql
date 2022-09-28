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


delimiter $$

create procedure known_good_state()
begin
    -- reset all of the tables
    truncate table beer;

    -- insert values
    insert into beer
        values
        (1, "Miller", 4.0, "American Pilsner", "madtree-brewing-cincinnati"),
        (2, "Shiner Cheer", 4.2, "Christmas Ale", "madtree-brewing-cincinnati"),
        (3, "Bud Light", 4.0, "American Pilsner", "madtree-brewing-cincinnati"),
        (4, "Bud Light", 4.0, "American Pilsner", "10-56-brewing-company-knox");

end$$
delimiter ;

call known_good_state();