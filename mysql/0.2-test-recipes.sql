-- Feature:  Test recipes
INSERT INTO `test`.`meals` (`name`) VALUES ('Test');


ALTER TABLE `test`.`recipes` ADD COLUMN `favorite` VARCHAR(3) NOT NULL  AFTER `serving_size` ;

-- Update existing recipes
update test.recipes set favorite='Yes' where recipes_pk > 0;




lakatosrenee@yahoo.com, 