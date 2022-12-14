drop database if exists deja_brew_test;
create database deja_brew_test;
use deja_brew_test;

create table app_user (
    app_user_id int primary key auto_increment,
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
    app_user_id int not null,
    brewery_id varchar(100) not null,
    visit_date date not null,
    constraint fk_visit_app_user_id
        foreign key (app_user_id)
        references app_user(app_user_id)

);

create table visit_beer (
    visit_beer_id int primary key auto_increment,
    visit_id int not null,
    beer_id int not null,
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
            foreign key (app_user_id)
            references app_user(app_user_id)
);


delimiter $$

create procedure known_good_state()
begin
    -- reset all of the tables
    delete from review;
    alter table review auto_increment = 1;
    delete from visit_beer;
    alter table visit_beer auto_increment = 1;
    delete from visit;
    alter table visit auto_increment = 1;
    delete from beer;
    alter table beer auto_increment = 1;
    delete from app_user;
    alter table app_user auto_increment = 1;

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
	
    insert into visit_beer
		values
        (1, 1, 1),
        (2, 2, 2),
        (3, 2, 1);
    
    insert into review
        values
        (1, 1, "madtree-brewing-cincinnati", 4, "Test Review"),
        (2, 2, "madtree-brewing-cincinnati", 3, "Testing Review"),
        (3, 3, "madtree-brewing-cincinnati", 5, "Test Review 3"),
        (4, 3, "10-56-brewing-company-knox", 2, "Test Review 4");

end$$
delimiter ;

call known_good_state();