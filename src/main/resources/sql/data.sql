delete  from user where 'username' like 'admin';
delete from authority ;
delete from user_authority where 'username' ='admin';

INSERT INTO `user` (`username`, `activation_status`, `dob`, `email`, `first_name`, `last_name`, `num`, `password`) VALUES
('admin', b'1', '2017-07-01 00:00:00', 'test@test.com', 'admin', 'admin', 1, 'b8f57d6d6ec0a60dfe2e20182d4615b12e321cad9e2979e0b9f81e0d6eda78ad9b6dcfe53e4e22d1');

INSERT INTO `authority` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO `user_authority` (`username`, `authority`) VALUES
('admin', 'ROLE_ADMIN');