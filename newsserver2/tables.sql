drop database if exists NewsviewAi;

create database NewsviewAi;

use NewsviewAi;

create table articles (
    id varchar(16) unique not null,
    url varchar(600) not null,
    website varchar(100),
    author varchar(100),
    title varchar(200),
    description varchar(600),
    image_path varchar(1000),
    published_date varchar(50),
    uploaded_at timestamp default current_timestamp on update current_timestamp, 
    primary key(id)
);

create table userinfo (
    id varchar(16) unique not null,
    email varchar(500) unique not null,
    name varchar(200) not null,
    nick_name varchar(100),
    primary key(id)
);
create table favarticles (
    fav_id varchar(16) unique not null,
    fav_email varchar(500) unique not null,
    uploaded_at timestamp default current_timestamp on update current_timestamp, 
    primary key(fav_email),
        constraint fk_email_articles
            foreign key(fav_email)
            references userinfo(email),
		constraint fk__id_articles
            foreign key(fav_id) 
            references articles(id)
);


