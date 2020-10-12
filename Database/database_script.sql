-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema recipe
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema recipe
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `recipe` ;
USE `recipe` ;

-- -----------------------------------------------------
-- Table `recipe`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipe`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_username` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_username_UNIQUE` (`user_username` ASC) VISIBLE,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipe`.`recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipe`.`recipe` (
  `recipe_id` INT NOT NULL AUTO_INCREMENT,
  `recipe_name` VARCHAR(45) NOT NULL,
  `recipe_instructions` TEXT NOT NULL,
  `recipe_time_num` FLOAT NOT NULL,
  `recipe_time_measurement` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`recipe_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipe`.`ingredients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipe`.`ingredients` (
  `ingredient_id` INT NOT NULL AUTO_INCREMENT,
  `ingredient_name` VARCHAR(45) NULL,
  `recipe_id` INT NOT NULL,
  PRIMARY KEY (`ingredient_id`, `recipe_id`),
  INDEX `fk_ingredients_recipe1_idx` (`recipe_id` ASC) VISIBLE,
  CONSTRAINT `fk_ingredients_recipe1`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `recipe`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipe`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipe`.`category` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`category_id`, `user_id`),
  INDEX `fk_category_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `recipe`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipe`.`user_has_recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipe`.`user_has_recipe` (
  `user_recipe_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `recipe_id` INT NOT NULL,
  PRIMARY KEY (`user_recipe_id`, `user_id`, `recipe_id`),
  INDEX `fk_user_has_recipe_recipe1_idx` (`recipe_id` ASC) VISIBLE,
  INDEX `fk_user_has_recipe_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_recipe_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `recipe`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_recipe_recipe1`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `recipe`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipe`.`category_has_recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipe`.`category_has_recipe` (
  `category_recipe_id` INT NOT NULL AUTO_INCREMENT,
  `category_id` INT NOT NULL,
  `recipe_id` INT NOT NULL,
  PRIMARY KEY (`category_recipe_id`, `category_id`, `recipe_id`),
  INDEX `fk_category_has_recipe_recipe1_idx` (`recipe_id` ASC) VISIBLE,
  INDEX `fk_category_has_recipe_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_has_recipe_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `recipe`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_category_has_recipe_recipe1`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `recipe`.`recipe` (`recipe_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
