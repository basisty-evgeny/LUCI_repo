/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.5.5-10.1.13-MariaDB : Database - luci
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`luci` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `luci`;

/*Table structure for table `channel` */

DROP TABLE IF EXISTS `channel`;

CREATE TABLE `channel` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(255) DEFAULT NULL COMMENT 'channel name',
  `user_id` varchar(255) NOT NULL,
  `protocol` varchar(255) DEFAULT NULL COMMENT 'protocol',
  `destination` varchar(255) DEFAULT NULL,
  `jitter_buffers` bigint(20) DEFAULT NULL,
  `dynamic_jitter_buffers` bigint(20) DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  `bitrate` bigint(20) DEFAULT NULL,
  `stereo` tinyint(1) DEFAULT NULL,
  `talk_mode` tinyint(1) DEFAULT NULL,
  `samplerate` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

/*Data for the table `channel` */

insert  into `channel`(`id`,`channel_name`,`user_id`,`protocol`,`destination`,`jitter_buffers`,`dynamic_jitter_buffers`,`format`,`bitrate`,`stereo`,`talk_mode`,`samplerate`,`created_at`,`updated_at`) values (48,'sip_kdh','7','SIP','echo.lucilive.com:5042',100,123,'G711-A',56000,0,0,24000,'2018-08-22 01:38:22','2018-08-22 08:30:20'),(50,'icecast','7','Icecast','icecast.lucilive.com:5042',1200,20,'AAC-LD',56000,0,0,48000,'2018-08-22 08:32:58','2018-08-22 08:32:58'),(51,'myRTP','6','RTP','test RTP',1000,3000,'MP3',100,0,0,2100,'2018-08-22 08:38:55','2018-08-22 08:38:55'),(52,'myRTP','6','RTP','test RTP',1000,3000,'MP3',100,0,0,2100,'2018-08-22 08:44:11','2018-08-22 08:44:11'),(53,'myRTP','6','RTP','test RTP',1000,3000,'MP3',100,1,1,2100,'2018-08-22 08:52:05','2018-08-22 08:52:05'),(54,'myRTP','6','RTP','test RTP',1000,3000,'MP3',100,0,0,2100,'2018-08-22 08:52:57','2018-08-22 08:52:57'),(55,'myRTP','6','RTP','test RTP',1000,3000,'MP3',100,1,0,2100,'2018-08-22 08:53:27','2018-08-22 08:53:27');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `activation_code` varchar(255) NOT NULL,
  `role_id` bigint(10) NOT NULL COMMENT '0:user, 1:admin',
  `check_flag` int(10) NOT NULL COMMENT '0:break, 1:permit',
  `login_count` bigint(20) DEFAULT NULL COMMENT 'login count',
  `last_logout_at` timestamp NULL DEFAULT NULL,
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`,`email`),
  UNIQUE KEY `users_user_id_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`id`,`name`,`password`,`email`,`organization`,`activation_code`,`role_id`,`check_flag`,`login_count`,`last_logout_at`,`remember_token`,`created_at`,`updated_at`) values (3,'JangWeiDong','$2y$10$/WZzG4pv.0BJUSNECgtGAexcucvO.SvwY.pdtU264qeQ5TB1/8IUO','jwd@gmail.com','ID Company','6d111f7ca6c5c39c7ec6836bbab0bf1c',0,1,14,NULL,'a1tJjB8zSAXPEy2dkgQgDcNgzSGLguhAijAWMBajvrPDbqfiW9TRJxyb9ia6','2018-08-20 13:04:32','2018-08-21 06:30:03'),(4,'JangWeiDong','$2y$10$.b12ooSsyoZQz3EnJoov.eVHSMDRlwEHQc2qJZSDthrOgaI7Rk3rC','kimcso@hanasoft.com.cn','ID Company','6d111f7ca6c5c39c7ec6836bbab0bf1c',0,1,1,NULL,'a6qv7uEq9CCWdQMEAouDqdUsOO5TDysMozKnXEiOKT03oD6DlVhYBqoBLGak','2018-08-21 07:20:21','2018-08-21 07:20:40'),(5,'JangWeiDong','$2y$10$VD7YC3Tk5k4KAHNv2KK5bucC6znCMO9EfTIZoWqixjFaqRUmz0Sd.','kimcso1@gmail.com','ID Company','6d111f7ca6c5c39c7ec6836bbab0bf1c',0,1,2,NULL,'uTDPApArAiB2JsvPiXzsC6eswJx6kWmviKdh4LBHjqF6ier24wOyAxI9htir','2018-08-21 07:35:07','2018-08-21 07:37:48'),(6,'JangWeiDong','$2y$10$uu4yHixbD7aJLOj8NpiSoe2o2ESW5ZXuuInp0NG5wS3qsKCl76uEq','kimcso2@gmail.com','ID Company','6d111f7ca6c5c39c7ec6836bbab0bf1c',0,1,2,NULL,'CrBhSBvjIsTdyDRoH2downtCwCo16xZr7w9xCHVQZ3eDhOBiezOOxhqc92zX','2018-08-21 07:43:25','2018-08-22 08:38:52'),(7,'KimDaeHun','$2y$10$LqKf2rgpbFmehm1TyuNxgOIlL1l//Qev5MvTOOrSvJY8Xobz5AgWG','kdh@gmail.com','UIT','123123',0,1,21,NULL,'8Xq1KnqdwEmU6I6VSig5iIVOx0SKzFnpUbycyYh1rviaBhsFfMY20MQ6iDUz','2018-08-21 10:29:45','2018-08-22 08:28:42'),(8,'kh','$2y$10$Ih6rlCLvjSVxrdi/RKNS6..x/E77mgOAmtIijONmyTY7Z.ND/BaH.','kh@email.com','UIT','123123',0,1,0,NULL,NULL,'2018-08-22 08:44:15','2018-08-22 08:44:15'),(9,'rigs','$2y$10$av6x9BcfmuSKRNJ6AMGu6eyR2Zm4pu9GsgM.bN/ugvj66V9aP0uWS','rigs@live.hk','UIT','123123',0,1,2,NULL,'1Z0KcJPonFV57y1j9XVygwHpcC5x5tnhoOjTJU73rUGdhbhgT2HFyXOpZgfH','2018-08-22 08:45:16','2018-08-22 08:50:08');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
