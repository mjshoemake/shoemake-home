-- Users, Roles
CREATE  TABLE `mshoemake`.`users` (
  `user_pk` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(20) NULL ,
  `fname` VARCHAR(20) NOT NULL COMMENT '		' ,
  `lname` VARCHAR(20) NULL ,
  `password` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`user_pk`) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC));
  
CREATE  TABLE `mshoemake`.`roles` (
  `role_pk` INT NOT NULL AUTO_INCREMENT ,
  `role_name` VARCHAR(45) NOT NULL ,
  `read_permissions` CHAR(1) NOT NULL ,
  `write_permissions` CHAR(1) NOT NULL ,
  `delete_permissions` CHAR(1) NOT NULL ,
  PRIMARY KEY (`role_pk`) );

CREATE  TABLE `mshoemake`.`user_role_assn` (
  `user_pk` INT NOT NULL ,
  `role_pk` INT NOT NULL ,
  INDEX `fk-user-pk` (`user_pk` ASC) ,
  INDEX `fk-role-pk` (`role_pk` ASC) );

-- Recipes, Meals, Cookbooks
CREATE  TABLE `mshoemake`.`recipes` (
  `recipes_pk` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `directions_filename` VARCHAR(45) NULL ,
  `servings` VARCHAR(30) NULL ,
  `cookbook_pk` INT NULL ,
  `nutrition` VARCHAR(100) NULL ,
  `picture_filename` VARCHAR(45) NULL ,
  `meals_pk` INT NULL ,
  `meal_categories_pk` INT NULL ,
  `notes` VARCHAR(200) NULL ,
  PRIMARY KEY (`recipes_pk`) ,
  UNIQUE INDEX `recipes_pk_UNIQUE` (`recipes_pk` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) );

CREATE  TABLE `mshoemake`.`meal_categories` (
  `meal_categories_pk` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  PRIMARY KEY (`meal_categories_pk`) ,
  UNIQUE INDEX `meal_categories_pk_UNIQUE` (`meal_categories_pk` ASC) ,
  INDEX `meal_categories_name` (`name` ASC) );

CREATE  TABLE `mshoemake`.`meals` (
  `meals_pk` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NULL ,
  PRIMARY KEY (`meals_pk`) ,
  UNIQUE INDEX `meals_pk_UNIQUE` (`meals_pk` ASC) ,
  INDEX `meals_name` (`name` ASC) );

CREATE  TABLE `mshoemake`.`cookbooks` (
  `cookbooks_pk` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(80) NOT NULL ,
  PRIMARY KEY (`cookbooks_pk`) ,
  UNIQUE INDEX `cookbook_pk_UNIQUE` (`cookbooks_pk` ASC) ,
  UNIQUE INDEX `cookbook_name_UNIQUE` (`name` ASC) );

ALTER TABLE `mshoemake`.`recipes` 
  ADD CONSTRAINT `meals_fk`
  FOREIGN KEY (`meals_pk` )
  REFERENCES `mshoemake`.`meals` (`meals_pk` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `cookbooks_fk`
  FOREIGN KEY (`cookbook_pk` )
  REFERENCES `mshoemake`.`cookbooks` (`cookbooks_pk` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `meal_categories_fk`
  FOREIGN KEY (`meal_categories_pk` )
  REFERENCES `mshoemake`.`meal_categories` (`meal_categories_pk` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `meals_fk` (`meals_pk` ASC) 
, ADD INDEX `cookbooks_fk` (`cookbook_pk` ASC) 
, ADD INDEX `meal_categories_fk` (`meal_categories_pk` ASC);

CREATE  TABLE `mshoemake`.`meal_feedback` (
  `meal_feedback_pk` INT NOT NULL AUTO_INCREMENT ,
  `user_pk` INT NOT NULL ,
  `fork_rating` INT NOT NULL ,
  `comments` VARCHAR(255) NULL ,
  PRIMARY KEY (`meal_feedback_pk`) ,
  UNIQUE INDEX `meal_feedback_pk_UNIQUE` (`meal_feedback_pk` ASC) );

INSERT INTO `mshoemake`.`meals` (`name`) VALUES ('Breakfast');
INSERT INTO `mshoemake`.`meals` (`name`) VALUES ('Lunch');
INSERT INTO `mshoemake`.`meals` (`name`) VALUES ('Dinner');
INSERT INTO `mshoemake`.`meals` (`name`) VALUES ('Snack');
INSERT INTO `mshoemake`.`meals` (`name`) VALUES ('Dessert');

INSERT INTO `mshoemake`.`user_role_assn` (user_pk, role_pk) VALUES (1, 1);

ALTER TABLE `mshoemake`.`recipes` 
  ADD COLUMN `created_on` DATE NOT NULL  AFTER `notes` , 
  ADD COLUMN `created_by` INT NOT NULL  AFTER `created_on` ;

INSERT INTO `mshoemake`.`cookbooks` (`cookbooks_pk`, `name`) VALUES (1, 'Personal Cookbook');
INSERT INTO `mshoemake`.`cookbooks` (`cookbooks_pk`, `name`) VALUES (2, 'The Gluten-Free Bible');
INSERT INTO `mshoemake`.`cookbooks` (`cookbooks_pk`, `name`) VALUES (3, 'Gluten Free and Easy');
INSERT INTO `mshoemake`.`cookbooks` (`cookbooks_pk`, `name`) VALUES (4, '30 Minute Meals For Dummies');
INSERT INTO `mshoemake`.`cookbooks` (`cookbooks_pk`, `name`) VALUES (5, 'Lazy Day Cookin\'');

ALTER TABLE `mshoemake`.`recipes` 
  ADD COLUMN `calories_per_serving` INT NULL  AFTER `created_by` ,
  ADD COLUMN `serving_size` VARCHAR(20) NULL  AFTER `calories_per_serving` ;
  
ALTER TABLE `mshoemake`.`recipes`
  CHANGE COLUMN `created_by` `created_by` INT(11) NULL  ;
  
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Beverage');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Chicken');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Dessert');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Egg');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Fish');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Fruit');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Ground Meat');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Pasta');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Potatoes');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Rice');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Snacks');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Soup/Chili');
INSERT INTO `mshoemake`.`meal_categories` (`name`) VALUES ('Vegetable');

DELETE FROM `mshoemake`.`meals` WHERE `meals_pk`='5';
DELETE FROM `mshoemake`.`meals` WHERE `meals_pk`='4';

ALTER TABLE `mshoemake`.`recipes` 
  ADD COLUMN `ingredients` VARCHAR(400) NULL  AFTER `calories_per_serving` , 
  CHANGE COLUMN `directions_filename` `directions_filename` VARCHAR(400) NULL DEFAULT NULL  ;
  
ALTER TABLE `mshoemake`.`recipes` DROP COLUMN `created_on` ;

ALTER TABLE `mshoemake`.`recipes`
  DROP COLUMN `directions_filename` , 
  ADD COLUMN `directions` VARCHAR(600) NULL DEFAULT NULL AFTER `ingredients` ;
  
ALTER TABLE `mshoemake`.`users` CHANGE COLUMN `password` `password` VARCHAR(50) NOT NULL  ;

CREATE  TABLE `mshoemake`.`gold_coin_sheet` (
  `gold_coin_sheet_pk` INT UNSIGNED NOT NULL ,
  `start_date` DATE NOT NULL ,
  `coin1` BIT NULL ,
  `coin2` BIT NULL ,
  `coin3` BIT NULL ,
  `coin4` BIT NULL ,
  `coin5` BIT NULL ,
  `coin6` BIT NULL ,
  `coin7` BIT NULL ,
  `coin8` BIT NULL ,
  `coin9` BIT NULL ,
  `coin10` BIT NULL ,
  `coin11` BIT NULL ,
  `coin12` BIT NULL ,
  `coin13` BIT NULL ,
  `coin14` BIT NULL ,
  `coin15` BIT NULL ,
  `coin16` BIT NULL ,
  `coin17` BIT NULL ,
  `coin18` BIT NULL ,
  `coin19` BIT NULL ,
  `coin20` BIT NULL ,
  `completed` DATE NULL ,
  `value` DECIMAL(6,2) NULL ,
  PRIMARY KEY (`gold_coin_sheet_pk`) );

INSERT INTO `mshoemake`.`users`
(`username`,
`fname`,
`lname`,
`password`)
VALUES
("mshoemake",
 "Mike",
 "Shoemake",
 "tAP5Xr5BAAD+jsJWz4on");

 INSERT INTO `mshoemake`.`users`
(`username`,
`fname`,
`lname`,
`password`)
VALUES
("mrshoemake",
 "Michelle",
 "Shoemake",
 "tAP5Xr5BAADmms9Cls1nfgw=");

ALTER TABLE `mshoemake`.`recipes`
  CHANGE COLUMN `ingredients` `ingredients` VARCHAR(1201) NULL DEFAULT NULL,
  CHANGE COLUMN `directions` `directions` VARCHAR(1201) NULL DEFAULT NULL,
  CHANGE COLUMN `nutrition` `nutrition` VARCHAR(1200) NULL DEFAULT NULL,
  CHANGE COLUMN `servings` `servings` VARCHAR(75) NULL DEFAULT NULL;

UPDATE `test`.`users` SET `username`='mjshoemake', `password`='tAP5Xr5BAAD+jsJWz4on' WHERE `user_pk`='1';
UPDATE `test`.`users` SET `password`='Y7X1Egw6CfXdZtSzGCCE+Q==' WHERE `user_pk`='2';


