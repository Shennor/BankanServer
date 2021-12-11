drop database if exists bankan;
create database bankan;

use bankan;

create table auth_info
(
    id                integer primary key auto_increment,
    login             varchar(20) not null unique,
    password_hash     varchar(255) not null
);

create table user_info
(
    id                integer primary key auto_increment,
    auth_id           integer not null,
    name              varchar(40) not null,
    registration_date date not null default (curdate()),

    foreign key (auth_id) references auth_info (id)
);

create table card
(
    id            integer primary key auto_increment,
    name          varchar(20) not null,
    color         integer     not null default (0),
    creation_data date        not null default (curdate()),
    deadline      date,
    creator_id    integer     not null,
    card_content  json        not null,

    foreign key (creator_id) references user_info (id)
);

create table card_to_assigned_user
(
    id      integer primary key auto_increment,
    user_id integer not null,
    card_id integer not null,
    foreign key (user_id) references user_info (id),
    foreign key (card_id) references card (id)
);

create table list
(
    id            integer primary key auto_increment,
    name          varchar(20) not null,
    creation_data date        not null default (curdate())
);

create table list_to_card_mapping
(
    id                    integer primary key auto_increment,
    list_id               integer not null,
    card_id               integer not null,
    index_of_card_in_list integer not null,
    foreign key (list_id) references list (id),
    foreign key (card_id) references card (id)
);

create table board
(
    id            integer primary key auto_increment,
    name          varchar(20) not null,
    description   mediumtext  not null,
    creation_data date        not null default (curdate())
);

create table board_to_list_mapping
(
    id                     integer primary key auto_increment,
    board_id               integer not null,
    list_id                integer not null,
    index_of_list_in_board integer not null,
    foreign key (board_id) references board (id),
    foreign key (list_id) references list (id)
);

create table board_to_assigned_user
(
    id       integer primary key auto_increment,
    user_id  integer not null,
    board_id integer not null,
    foreign key (user_id) references user_info (id),
    foreign key (board_id) references board (id)
);

create table user_to_table_mapping
(
    id                          integer primary key auto_increment,
    user_id                     integer not null,
    board_id                    integer not null,
    index_of_board_in_workspace integer not null,
    foreign key (user_id) references user_info (id),
    foreign key (board_id) references card (id)
);

create table user_settings
(
    id       integer primary key auto_increment,
    user_id  integer not null unique,
    settings json    not null,
    foreign key (user_id) references user_info (id)
);
