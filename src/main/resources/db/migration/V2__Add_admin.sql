insert into user (id, password, username, active)
values (1, '1', 'admin', true);

insert into user_roles (user_id, roles)
values (1, 'ADMIN');