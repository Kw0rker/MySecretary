create SCHEMA `mysecretary` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
create TABLE `mysecretary`.`bot_users` (
  `email` VARCHAR(30) NULL,
  `accessToken` VARCHAR(45) NOT NULL,
  `id` INT NOT NULL,
  `reply` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`id`));
