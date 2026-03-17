/*
 Navicat Premium Data Transfer

 Source Server         : CHLORINE
 Source Server Type    : MySQL
 Source Server Version : 80025 (8.0.25)
 Source Host           : localhost:3306
 Source Schema         : manager

 Target Server Type    : MySQL
 Target Server Version : 80025 (8.0.25)
 File Encoding         : 65001

 Date: 03/02/2026 11:13:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for back_notice
-- ----------------------------
DROP TABLE IF EXISTS `back_notice`;
CREATE TABLE `back_notice`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `text` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of back_notice
-- ----------------------------
INSERT INTO `back_notice` VALUES (1, 'Test!!!');
INSERT INTO `back_notice` VALUES (2, 'TTT!');
INSERT INTO `back_notice` VALUES (3, 'English!!!');
INSERT INTO `back_notice` VALUES (4, 'TTTTTTTTTTT<br>SSSSSSSSSSSSSSSSSSSSSSSSSSSSSS');
INSERT INTO `back_notice` VALUES (5, '中文测试！！！');
INSERT INTO `back_notice` VALUES (6, '目前作者对常规页面，echarts地图开发，threejs模型基础展示这几块内容比较熟悉，对脑图，关系图的开发有所了解，尝试过开发比较基础的甘特图功能，后续会逐步深入更多高阶内容的研发，<br><br>本站系统采用了vue底层框架来开发的开源项目，目前使用的vue版本为vue3版本，项目的页面跳转是使用了vue-router来进行的页面跳转，项目里面使用了echarts作为基本的图表展示，使用了elementui框架作为基本的组件开发。<br><br>该项目集成了RBAC权限管理以及基本的一些项目图标展示，并且集成了一些可单独打开查看的组件演示和页面案例演示，并且在项目里面还集成了聊天相关的业务组件。      <br><br>项目目前的状态管理是使用了App.vue里面的内容，也就是$root.data或者$root.xxx方法来进行的项目全局的状态管理。<br><br>目前这就是项目的比较核心的内容，后面还会集成更多或者自己去开发更多有意思或者实用的内容出来');

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `grade` int NULL DEFAULT NULL COMMENT '年级',
  `name` int NULL DEFAULT NULL COMMENT '班级号',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '班级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, 2020, 2002, '备注1');
INSERT INTO `class` VALUES (2, 2020, 2003, '备注2');
INSERT INTO `class` VALUES (3, 2020, 2008, '备注3');
INSERT INTO `class` VALUES (4, 2020, 2004, '备注4');
INSERT INTO `class` VALUES (5, 2020, 2005, '备注5');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '课程名1', '备注1');
INSERT INTO `course` VALUES (2, '课程名2', '备注2');
INSERT INTO `course` VALUES (3, '课程名3', '备注3');
INSERT INTO `course` VALUES (4, '课程名4', '备注4');
INSERT INTO `course` VALUES (5, '课程名5', '备注5');

-- ----------------------------
-- Table structure for download
-- ----------------------------
DROP TABLE IF EXISTS `download`;
CREATE TABLE `download`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_id` int NULL DEFAULT NULL COMMENT '文件id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `time` datetime NULL DEFAULT NULL COMMENT '下载时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_download_file_id`(`file_id` ASC) USING BTREE,
  INDEX `IDX_download_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FK_download_file_id` FOREIGN KEY (`file_id`) REFERENCES `flie` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_download_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '下载记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of download
-- ----------------------------
INSERT INTO `download` VALUES (1, 5, 3, '2024-02-09 16:35:00');
INSERT INTO `download` VALUES (2, 5, 2, '2024-02-10 00:47:09');
INSERT INTO `download` VALUES (3, 5, 4, '2024-02-11 06:19:52');

-- ----------------------------
-- Table structure for file_comment
-- ----------------------------
DROP TABLE IF EXISTS `file_comment`;
CREATE TABLE `file_comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_id` int NULL DEFAULT NULL COMMENT '文件id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论',
  `pid` int NULL DEFAULT NULL COMMENT '父级评论',
  `time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  `likes` int NULL DEFAULT NULL COMMENT '点赞数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_file_comment_file_id`(`file_id` ASC) USING BTREE,
  INDEX `IDX_file_comment_user_id`(`user_id` ASC) USING BTREE,
  INDEX `IDX_file_comment_pid`(`pid` ASC) USING BTREE,
  CONSTRAINT `FK_file_comment_file_id` FOREIGN KEY (`file_id`) REFERENCES `flie` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_file_comment_pid` FOREIGN KEY (`pid`) REFERENCES `file_comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_file_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_comment
-- ----------------------------
INSERT INTO `file_comment` VALUES (1, 3, 3, '评论1', NULL, '2024-02-11 16:35:00', 892);
INSERT INTO `file_comment` VALUES (2, 3, 3, '评论2', NULL, '2024-02-12 00:47:09', 57);
INSERT INTO `file_comment` VALUES (3, 3, 4, '评论3', 1, '2024-02-13 06:19:52', 598);
INSERT INTO `file_comment` VALUES (4, 3, 2, '评论4', 1, '2024-02-15 04:07:16', 476);
INSERT INTO `file_comment` VALUES (5, 3, 1, '评论5', NULL, '2024-02-16 22:07:19', 761);

-- ----------------------------
-- Table structure for file_star
-- ----------------------------
DROP TABLE IF EXISTS `file_star`;
CREATE TABLE `file_star`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `file_id` int NULL DEFAULT NULL COMMENT '文件id',
  `star` double NULL DEFAULT NULL COMMENT '评分星级',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_file_star_user_id`(`user_id` ASC) USING BTREE,
  INDEX `IDX_file_star_file_id`(`file_id` ASC) USING BTREE,
  CONSTRAINT `FK_file_star_file_id` FOREIGN KEY (`file_id`) REFERENCES `flie` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_file_star_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评级信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_star
-- ----------------------------
INSERT INTO `file_star` VALUES (1, 4, 3, 4);
INSERT INTO `file_star` VALUES (2, 3, 3, 3);
INSERT INTO `file_star` VALUES (3, 5, 3, 5);

-- ----------------------------
-- Table structure for flie
-- ----------------------------
DROP TABLE IF EXISTS `flie`;
CREATE TABLE `flie`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `floder_id` int NULL DEFAULT NULL COMMENT '目录id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `privacy_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '隐私程度',
  `star` double NULL DEFAULT NULL COMMENT '星级',
  `version` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '版本',
  `upload_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `state` bit(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_flie_user_id`(`user_id` ASC) USING BTREE,
  INDEX `IDX_flie_floder_id`(`floder_id` ASC) USING BTREE,
  CONSTRAINT `FK_flie_floder_id` FOREIGN KEY (`floder_id`) REFERENCES `floder` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_flie_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flie
-- ----------------------------
INSERT INTO `flie` VALUES (1, 1, 1, '文件名1', '类型1', '标签1', '隐私程度1', NULL, '版本1', '2024-02-09 16:35:00', b'0');
INSERT INTO `flie` VALUES (2, 1, 1, '文件名2', '类型2', '标签2', '隐私程度2', NULL, '版本1', '2024-02-10 00:47:09', b'0');
INSERT INTO `flie` VALUES (3, 1, 1, '文件名3', '类型3', '标签3', '隐私程度3', NULL, '版本1', '2024-02-11 06:19:52', b'0');
INSERT INTO `flie` VALUES (4, 1, 1, '文件名1', '类型1', '标签4', '隐私程度4', NULL, '版本2', '2024-02-13 04:07:16', b'0');
INSERT INTO `flie` VALUES (5, 1, 1, '文件名1', '类型1', '标签5', '隐私程度5', NULL, '版本3', '2024-02-14 22:07:19', b'0');

-- ----------------------------
-- Table structure for floder
-- ----------------------------
DROP TABLE IF EXISTS `floder`;
CREATE TABLE `floder`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '目录id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目录名',
  `pid` int NULL DEFAULT NULL COMMENT '父目录id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_floder_pid`(`pid` ASC) USING BTREE,
  INDEX `IDX_floder_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FK_floder_pid` FOREIGN KEY (`pid`) REFERENCES `floder` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_floder_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '目录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of floder
-- ----------------------------
INSERT INTO `floder` VALUES (1, '目录1', NULL, 1, '2024-02-11 16:35:00');
INSERT INTO `floder` VALUES (2, '目录2', NULL, 1, '2024-02-12 00:47:09');
INSERT INTO `floder` VALUES (3, '目录1.1', 1, 1, '2024-02-13 06:19:52');
INSERT INTO `floder` VALUES (4, '目录3', NULL, 1, '2024-02-15 04:07:16');
INSERT INTO `floder` VALUES (5, '目录2.1', 2, 1, '2024-02-16 22:07:19');

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `follower_id` int NULL DEFAULT NULL COMMENT '关注者ID',
  `following_id` int NULL DEFAULT NULL COMMENT '被关注者ID',
  `time` datetime NULL DEFAULT NULL COMMENT '关注时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_follower_id`(`follower_id` ASC) USING BTREE,
  INDEX `IDX_following_id`(`following_id` ASC) USING BTREE,
  CONSTRAINT `FK_follower_id` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_following_id` FOREIGN KEY (`following_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '关注表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow` VALUES (1, 2, 1, '2024-02-09 16:35:00');
INSERT INTO `follow` VALUES (2, 3, 1, '2024-02-10 00:47:09');
INSERT INTO `follow` VALUES (3, 4, 1, '2024-02-11 06:19:52');
INSERT INTO `follow` VALUES (4, 5, 1, '2024-02-13 04:07:16');
INSERT INTO `follow` VALUES (5, 1, 3, '2024-02-13 04:07:16');

-- ----------------------------
-- Table structure for login_info
-- ----------------------------
DROP TABLE IF EXISTS `login_info`;
CREATE TABLE `login_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `login_time` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `device_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_login_info_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FK_login_info_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登录信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_info
-- ----------------------------
INSERT INTO `login_info` VALUES (1, 1, '2024-02-08 16:35:00', 'ip地址1', '设备信息1');
INSERT INTO `login_info` VALUES (2, 1, '2024-02-09 00:47:09', 'ip地址2', '设备信息2');
INSERT INTO `login_info` VALUES (3, 1, '2024-02-10 06:19:52', 'ip地址3', '设备信息3');
INSERT INTO `login_info` VALUES (4, 2, '2024-02-12 04:07:16', 'ip地址4', '设备信息4');
INSERT INTO `login_info` VALUES (5, 3, '2024-02-13 22:07:19', 'ip地址5', '设备信息5');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `pid` int NULL DEFAULT 0 COMMENT '父级菜单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '首页', '/home/home', 'home', 0);
INSERT INTO `menu` VALUES (2, '系统管理', '/home/systemSet', 'systemSet', 0);
INSERT INTO `menu` VALUES (3, '路由存储管理', '/home/systemSet/routes', 'routes', 2);
INSERT INTO `menu` VALUES (4, '字典存储管理', '/home/systemSet/dictsset', 'dictsset', 2);
INSERT INTO `menu` VALUES (5, '部门管理', '/home/systemSet/department', 'department', 2);
INSERT INTO `menu` VALUES (6, '角色配置', '/home/systemSet/page1', 'page1', 2);
INSERT INTO `menu` VALUES (7, '用户管理', '/home/systemSet/userset', 'userset', 2);
INSERT INTO `menu` VALUES (8, '权限页面分配', '/home/systemSet/pagerootsettip', 'pagerootsettip', 2);
INSERT INTO `menu` VALUES (9, '数据查看', '/home/page1b', 'page1b', 0);
INSERT INTO `menu` VALUES (10, '关于本站弹窗管理', '/home/page1b/subpage1b', 'subpage1b', 9);
INSERT INTO `menu` VALUES (11, '导航分级', '/home/page1b2', 'page1b2', 0);
INSERT INTO `menu` VALUES (12, '导航二级', '/home/page1b2/subpage1b2', 'subpage1b2', 11);
INSERT INTO `menu` VALUES (13, '导航三级', '/home/page1b2/subpage1b2/subpage1b2s', 'subpage1b2s', 12);
INSERT INTO `menu` VALUES (14, '更多图标展示', '/home/icons', 'icons', 0);
INSERT INTO `menu` VALUES (15, '个人资料', '/home/personal', 'personal', 0);
INSERT INTO `menu` VALUES (16, '设置', '/home/setting', 'setting', 0);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `tittle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告名称',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `expiration_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_notice_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FK_notice_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, 2, '公告名称1', '内容1', '2024-02-10 16:35:00', '2024-02-11 16:35:00');
INSERT INTO `notice` VALUES (2, 2, '公告名称2', '内容2', '2024-02-11 00:47:09', '2024-02-12 00:47:09');
INSERT INTO `notice` VALUES (3, 2, '公告名称3', '内容3', '2024-02-12 06:19:52', '2024-02-13 06:19:52');
INSERT INTO `notice` VALUES (4, 2, '公告名称4', '内容4', '2024-02-14 04:07:16', '2024-02-15 04:07:16');
INSERT INTO `notice` VALUES (5, 2, '公告名称5', '内容5', '2024-02-15 22:07:19', '2024-02-16 22:07:19');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `numb` tinyint NULL DEFAULT NULL COMMENT '编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `numb`(`numb` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '学生', '0', NULL, 1);
INSERT INTO `role` VALUES (2, '老师', '0', NULL, 2);
INSERT INTO `role` VALUES (3, '超级管理员', '0', NULL, 0);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_role_menu_role_id`(`role_id` ASC) USING BTREE,
  INDEX `IDX_role_menu_menu_id`(`menu_id` ASC) USING BTREE,
  CONSTRAINT `FK_role_menu_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_role_menu_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限菜单展示表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1, 1);
INSERT INTO `role_menu` VALUES (2, 1, 2);
INSERT INTO `role_menu` VALUES (3, 2, 1);
INSERT INTO `role_menu` VALUES (4, 2, 2);
INSERT INTO `role_menu` VALUES (5, 2, 3);

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `class_id` int NULL DEFAULT NULL COMMENT '班级id',
  `user_id` int NULL DEFAULT NULL COMMENT '教师id',
  `course_id` int NULL DEFAULT NULL COMMENT '课程id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_schedule_class_id`(`class_id` ASC) USING BTREE,
  INDEX `IDX_schedule_user_id`(`user_id` ASC) USING BTREE,
  INDEX `IDX_schedule_course_id`(`course_id` ASC) USING BTREE,
  CONSTRAINT `FK_schedule_class_id` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_schedule_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_schedule_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程安排关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (1, 1, 5, 1);
INSERT INTO `schedule` VALUES (2, 1, 5, 2);
INSERT INTO `schedule` VALUES (3, 1, 5, 3);
INSERT INTO `schedule` VALUES (4, 1, 5, 4);
INSERT INTO `schedule` VALUES (5, 1, 5, 5);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '学生id',
  `class_id` int NULL DEFAULT NULL COMMENT '班级id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `IDU_student_user_id`(`user_id` ASC) USING BTREE,
  INDEX `FK_student_class_id`(`class_id` ASC) USING BTREE,
  CONSTRAINT `FK_student_class_id` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_student_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生班级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 1, 3);
INSERT INTO `student` VALUES (6, 2, 3);
INSERT INTO `student` VALUES (7, 3, 3);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sign` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `organization` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织/机构',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `state` bit(1) NULL DEFAULT b'0' COMMENT '锁定状态 0为非锁定，1为锁定',
  `ture_state` tinyint(1) NULL DEFAULT 0 COMMENT '实名状态 0为非实名，1为已实名，2为审核中ing',
  `role` tinyint(1) NULL DEFAULT 1 COMMENT '用户权限 0为管理员，1为学生，2为老师',
  `true_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `direction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '研究方向',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_user_role`(`role` ASC) USING BTREE,
  CONSTRAINT `FK_user_role` FOREIGN KEY (`role`) REFERENCES `role` (`numb`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'patricia_mfey', '密码1', '昵称1', '头像1', '13209858103', '男', 37, '简介1', 'helen83@linkedin.com', '组织/机构1', '2024-02-13 15:11:58', b'0', 1, 1, '伍云云', '124325198505031532', '研究方向1');
INSERT INTO `user` VALUES (2, 'charles_9fkl', '密码2', '昵称2', '头像2', '13360707300', '女', 55, '简介2', 'sarah19@github.com', '组织/机构2', '2024-02-13 16:35:00', b'0', 1, 1, '郑彬昱', '123016200305210859', '研究方向2');
INSERT INTO `user` VALUES (3, 'paul_km', '密码3', '昵称3', '头像3', '13727353390', '女', 63, '简介3', 'james30@adobe.com', '组织/机构3', '2024-02-14 00:47:09', b'0', 1, 1, '余霞欣', '363685197212040521', '研究方向3');
INSERT INTO `user` VALUES (4, 'david_snh', '密码4', '昵称4', '头像4', '13539400338', '女', 35, '简介4', 'lisa4@w3.org', '组织/机构4', '2024-02-15 06:19:52', b'0', 1, 1, '苏雅莉', '112500197108040217', '研究方向4');
INSERT INTO `user` VALUES (5, 'elizabeth_9jaqmf', '密码5', '昵称5', '头像5', '13918573633', '女', 54, '简介5', 'linda17@blogspot.com', '组织/机构5', '2024-02-17 04:07:16', b'0', 1, 2, '许青', '534922199308020951', '研究方向5');
INSERT INTO `user` VALUES (6, 'admin', 'admin', '管理员', NULL, NULL, NULL, NULL, NULL, 'admin@qq.com', NULL, '2024-04-20 10:57:51', b'0', 0, 1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for view
-- ----------------------------
DROP TABLE IF EXISTS `view`;
CREATE TABLE `view`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_id` int NULL DEFAULT NULL COMMENT '文件id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `time` datetime NULL DEFAULT NULL COMMENT '查看时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_view_file_id`(`file_id` ASC) USING BTREE,
  INDEX `IDX_view_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FK_view_file_id` FOREIGN KEY (`file_id`) REFERENCES `flie` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_view_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '查看记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of view
-- ----------------------------
INSERT INTO `view` VALUES (1, 4, 1, '2024-02-09 16:35:00');
INSERT INTO `view` VALUES (2, 4, 1, '2024-02-10 00:47:09');
INSERT INTO `view` VALUES (3, 4, 2, '2024-02-11 06:19:52');
INSERT INTO `view` VALUES (4, 4, 2, '2024-02-13 04:07:16');
INSERT INTO `view` VALUES (5, 4, 3, '2024-02-14 22:07:19');

SET FOREIGN_KEY_CHECKS = 1;
