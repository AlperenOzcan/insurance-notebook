CREATE DATABASE  IF NOT EXISTS `insurance_notebook_v3`;
USE `insurance_notebook_v3`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `gender` BIT DEFAULT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `customer_health_detail`;
CREATE TABLE `customer_health_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(3) DEFAULT NULL,
  `height` double precision DEFAULT NULL,
  `weight` double precision DEFAULT NULL,
  `had_cancer` BIT DEFAULT NULL,
  `had_heart_attack` BIT DEFAULT NULL,
  `has_diabetes` BIT DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `FK_DETAIL_idx` (`customer_id`),
  CONSTRAINT `FK_DETAIL_idx`FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `automobile_detail`;
CREATE TABLE `automobile_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `production_year` date DEFAULT NULL,
  `driver_experience` int(3) DEFAULT NULL,
  `car_used_kilometer` int(7) DEFAULT NULL,
  `health_detail_score` int(3) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,

  PRIMARY KEY (`id`),
  KEY `FK_DETAIL_idxx` (`customer_id`),
  CONSTRAINT `FK_DETAIL_idxx`FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `house_detail`;
CREATE TABLE `house_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `built_date` date DEFAULT NULL,
  `square_meters` int(5) DEFAULT NULL,
  `structure_type` enum('Type-1', 'Type-2', 'Type-3') DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  
  PRIMARY KEY (`id`),
  KEY `FK_DETAIL_idxx` (`customer_id`),
  CONSTRAINT `FK_DETAIL_idxxx`FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `insurance_quote`;
CREATE TABLE `insurance_quote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `insurance_type` enum('Health', 'Automobile', 'Earthquake') DEFAULT NULL,
  `acceptance` BIT DEFAULT NULL,
  `premium` double(10,2) DEFAULT NULL,
  `date` date default null,
  `detail_id` int(11) DEFAULT NULL,
  
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;


INSERT INTO `customer` VALUES
	(1,'Leslie','Andrews',1),
	(2,'Emma','Baumgarten',1);

INSERT INTO `customer_health_detail` VALUES
	(1,30,170,60,0,0,0,2),
	(2,20,173,60,0,0,0,1);
    
INSERT INTO `automobile_detail` VALUES
	(1,'2012-12-12',5,10,8,1);
    
INSERT INTO `house_detail` VALUES
	(1,'2011-11-11',100,'Type-2',2);

INSERT INTO `insurance_quote` VALUES
	(1,'Health',0,1000,'2021-11-16',1), -- 2
    (2,'Automobile',0,1000,'2021-11-17',1), -- 1
    (3,'Earthquake',0,1000,'2021-11-20',1), -- 2
	(4,'Health',1,1100,'2021-11-18',2), -- 1
	(5,'Automobile',0,1200,'2021-11-19',1); -- 1

