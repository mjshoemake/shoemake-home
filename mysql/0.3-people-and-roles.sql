-- Add columns to users table
ALTER TABLE `test`.`users` ADD COLUMN `description` VARCHAR(120) NULL DEFAULT NULL  AFTER `password` , ADD COLUMN `login_enabled` CHAR NULL  AFTER `description` , ADD COLUMN `dob` DATETIME NOT NULL  AFTER `login_enabled` ;

-- Update users table data
UPDATE `test`.`users` SET `description`='Dad', `login_enabled`='Y', `dob`='1972-09-09 00:00:00' WHERE `user_pk`='1';UPDATE `test`.`users` SET `description`='Mom', `login_enabled`='Y', `dob`='1973-02-22 00:00:00' WHERE `user_pk`='2';INSERT INTO `test`.`users` (`user_pk`, `username`, `fname`, `lname`, `password`, `description`, `login_enabled`, `dob`) VALUES (3, '', 'Caleb', 'Shoemake', 'tAP5Xr5BAAD+jsJWz4on', 'Son', 'N', '2004-09-07 00:00:00');

-- Creating quotes table
CREATE  TABLE `test`.`user_quotes` (  `user_quotes_pk` INT(11) NOT NULL ,  `quote_date` DATETIME NOT NULL ,  `quote` VARCHAR(1000) NOT NULL ,  `user_pk` INT(11) NOT NULL ,  PRIMARY KEY (`user_quotes_pk`) ,  INDEX `user_pk` (`user_pk` ASC) );

-- Create family members table
CREATE  TABLE `test`.`family_members` (  `family_member_pk` INT NOT NULL ,  `fname` VARCHAR(20) NOT NULL ,  `lname` VARCHAR(20) NOT NULL ,  `description` VARCHAR(255) NULL ,  `dob` DATETIME NULL ,  PRIMARY KEY (`family_member_pk`) ,  INDEX `lname + fname` (`lname` ASC, `fname` ASC) ,  INDEX `dob` (`dob` ASC) )COMMENT = 'The family members (dad, mom, etc.).';

-- Drop unnecessary columns from users table
ALTER TABLE `test`.`users` DROP COLUMN `dob` , DROP COLUMN `description` ;
ALTER TABLE `test`.`family_members` CHANGE COLUMN `family_member_pk` `family_member_pk` INT(11) NOT NULL AUTO_INCREMENT  , ADD UNIQUE INDEX `family_member_pk_UNIQUE` (`family_member_pk` ASC) ;
-- Add family members

INSERT INTO `test`.`family_members` (`fname`, `lname`, `description`, `dob`) VALUES ('Mike', 'Shoemake', 'Dad', '1972-09-09');

