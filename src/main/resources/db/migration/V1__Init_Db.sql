create table category
(
    id     integer not null auto_increment,
    status bit,
    title  varchar(255),
    primary key (id)
);

create table hibernate_sequence
(
    next_val bigint
);

insert into hibernate_sequence
values (1);

insert into hibernate_sequence
values (1);

create table message
(
    id       bigint        not null,
    filename varchar(255),
    tag      varchar(255),
    text     varchar(2048) not null,
    user_id  bigint,
    primary key (id)
);

create table user
(
    id              bigint       not null,
    activation_code varchar(255),
    active          bit          not null,
    email           varchar(255),
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);

create table user_roles
(
    user_id bigint not null,
    roles   varchar(255)
);

alter table message
    add constraint message_user_id_fk foreign key (user_id) references user (id);

alter table user_roles
    add constraint roles_user_fk foreign key (user_id) references user (id);