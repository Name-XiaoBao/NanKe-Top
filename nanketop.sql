/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 80030
Source Host           : localhost:3306
Source Database       : nanketop

Target Server Type    : MYSQL
Target Server Version : 80030
File Encoding         : 65001

Date: 2023-12-14 19:56:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `img` varchar(255) NOT NULL COMMENT '轮播图片',
  `url` varchar(255) NOT NULL COMMENT '跳转链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='轮播图';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `avatar` longtext NOT NULL,
  PRIMARY KEY (`id`,`username`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COMMENT='用户表';

-- ----------------------------
-- Table structure for user_participatein
-- ----------------------------
DROP TABLE IF EXISTS `user_participatein`;
CREATE TABLE `user_participatein` (
  `id` int NOT NULL AUTO_INCREMENT,
  `participating_uid` bigint NOT NULL COMMENT '参与人员的UID',
  `participating_users` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '参与的用户',
  `vote_uid` bigint NOT NULL COMMENT '参与的投票的UID',
  `picture` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参与的图片',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容描述',
  `number_of_votes` int DEFAULT '0' COMMENT '票数',
  PRIMARY KEY (`id`,`participating_uid`,`participating_users`,`vote_uid`),
  KEY `vote_uid` (`vote_uid`),
  KEY `vote_username` (`participating_users`),
  KEY `participating_uid_index` (`participating_uid`),
  CONSTRAINT `vote_uid` FOREIGN KEY (`vote_uid`) REFERENCES `vote` (`uid`),
  CONSTRAINT `vote_username` FOREIGN KEY (`participating_users`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='参与人员';

-- ----------------------------
-- Table structure for vote
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_username` varchar(100) NOT NULL COMMENT '用户表的用户账号',
  `uid` bigint NOT NULL COMMENT 'UID',
  `cover` longtext NOT NULL COMMENT '封面',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `deadline` datetime NOT NULL COMMENT '投票截止时间',
  `registration_time` datetime NOT NULL COMMENT '报名时间',
  `end_registration_time` datetime DEFAULT NULL COMMENT '报名截止时间',
  `vote_num` int NOT NULL COMMENT '投票次数',
  `backgroundMusic` longtext COMMENT '背景音乐',
  `rule` longtext NOT NULL COMMENT '投票规则',
  `views` int NOT NULL COMMENT '浏览量',
  `votes` int NOT NULL COMMENT '累计票数',
  PRIMARY KEY (`id`,`views`,`votes`,`user_username`,`uid`),
  KEY `user_username` (`user_username`),
  KEY `user_username_2` (`user_username`,`uid`),
  KEY `uid` (`uid`),
  CONSTRAINT `user_username` FOREIGN KEY (`user_username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COMMENT='投票列表';

-- ----------------------------
-- Table structure for voter
-- ----------------------------
DROP TABLE IF EXISTS `voter`;
CREATE TABLE `voter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ip_address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '投票者的IP地址',
  `vote_uid` bigint NOT NULL COMMENT '投票的UID',
  `participating_uid` bigint NOT NULL COMMENT '参与人员的UID',
  PRIMARY KEY (`id`),
  KEY `vote_uid` (`vote_uid`),
  KEY `participating_uid` (`participating_uid`),
  CONSTRAINT `participating_uid_fk` FOREIGN KEY (`participating_uid`) REFERENCES `user_participatein` (`participating_uid`),
  CONSTRAINT `vote_uid_fk` FOREIGN KEY (`vote_uid`) REFERENCES `vote` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='投票';
