create table if not exists user_subscriptions
(
    channel_id    bigint not null,
    subscriber_id bigint not null,

    constraint user_subscriptions_pk
        primary key (channel_id, subscriber_id),
    constraint table_name_user_id_fk
        foreign key (channel_id) references user (id),
    constraint table_name_user_id_fk_2
        foreign key (subscriber_id) references user (id)
);