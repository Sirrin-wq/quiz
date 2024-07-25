create schema if not exists quiz;

create table if not exists quiz
(
    id       bigint auto_increment primary key,
    question     varchar(255) not null,
    answer_one     varchar(30) not null,
    answer_two     varchar(30) not null,
    answer_three   varchar(30) not null,
    real_answer  varchar(30) not null
);