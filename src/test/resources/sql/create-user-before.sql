delete from user_role;
delete from usr;

insert into usr(id, username, password, active) values
(1, 'demmax93', '$2a$08$1kL.GPrFahJ.iBMnsMgdgOjYnPpQzXB/L7zTVaW2OYhMPyzo3Oxya', true);

insert into user_role(user_id, roles) values
(1, 'ADMIN'), (1, 'USER');