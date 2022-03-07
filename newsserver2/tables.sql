drop database if exists NewsviewAi;

create database NewsviewAi;

use NewsviewAi;

create table articles (
    id varchar(100) unique not null,
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

create unique index url on articles (url);

create table userinfo (
    id varchar(100) unique not null,
    email varchar(500) unique not null,
    name varchar(200) not null,
    nickname varchar(100),
    primary key(id, email)
);




create table favarticles (
    id varchar(100) not null,
    email varchar(500) not null,
    uploaded_at timestamp default current_timestamp on update current_timestamp, 
    primary key(email),
        constraint fk_email_articles
            foreign key(email)
            references userinfo(email)
);

create unique index id on favarticles (id);