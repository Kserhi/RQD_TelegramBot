SET GLOBAL time_zone = '+3:00';
select * from militari;
select * from users;
delete from militari where numbers between 4 and 10;
delete from users where numbers between 40 and 47;
SELECT * FROM users WHERE numbers = (SELECT MAX(numbers) FROM users WHERE teleqramId = 624392060);
