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

 Date: 11/11/2026 10:45:00

 修订说明（相对上一版）：
 1. 统一字符集 utf8mb4 / utf8mb4_general_ci。
 2. back_notice.text -> content（与实体 BackNotice.content 对齐，去掉手工 resultMap）。
 3. class.name int -> varchar(32)（班级号可含文本）。
 4. notice.tittle -> title；notice.content varchar(255) -> text。
 5. user.password varchar(16) -> varchar(60)（适配 BCrypt）。
 6. role.state varchar(255) -> tinyint(1)（1启用 0禁用）。
 7. file_star 增加 UNIQUE(user_id, file_id)。
 8. follow 增加 UNIQUE(follower_id, following_id)。
 9. file_comment.pid 外键改 ON DELETE CASCADE。
10. menu 补齐前端已存在页面的菜单记录。
11. 表名 flie/floder 保留原拼写（历史遗留，改动牵连面过大，暂不改名）。
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for back_notice
-- ----------------------------
DROP TABLE IF EXISTS `back_notice`;
CREATE TABLE `back_notice`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '班级号',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '班级表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, 2020, '1班', '软件工程专业');
INSERT INTO `class` VALUES (2, 2020, '2班', '软件工程专业');
INSERT INTO `class` VALUES (3, 2020, '3班', '计算机科学与技术专业');
INSERT INTO `class` VALUES (4, 2021, '1班', '软件工程专业');
INSERT INTO `class` VALUES (5, 2021, '2班', '计算机科学与技术专业');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程名',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'Java程序设计', '计算机专业核心课程，讲授 Java 基础与面向对象编程');
INSERT INTO `course` VALUES (2, '数据库原理', '讲授关系型数据库设计、SQL 与事务');
INSERT INTO `course` VALUES (3, '数据结构', '讲授线性表、树、图等数据结构与算法');
INSERT INTO `course` VALUES (4, '操作系统', '讲授进程管理、内存管理与文件系统');
INSERT INTO `course` VALUES (5, '计算机网络', '讲授 TCP/IP 协议栈与网络编程');
INSERT INTO `course` VALUES (6, '软件工程导论', '讲授软件生命周期、需求分析与项目管理');

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '下载记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of download
-- ----------------------------
INSERT INTO `download` VALUES (1, 2, 3, '2026-10-21 16:35:00');
INSERT INTO `download` VALUES (2, 3, 2, '2026-09-06 00:47:09');
INSERT INTO `download` VALUES (3, 1, 20, '2026-09-16 06:19:52');

-- ----------------------------
-- Table structure for file_comment
-- ----------------------------
DROP TABLE IF EXISTS `file_comment`;
CREATE TABLE `file_comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_id` int NULL DEFAULT NULL COMMENT '文件id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论',
  `pid` int NULL DEFAULT NULL COMMENT '父级评论',
  `time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  `likes` int NULL DEFAULT 0 COMMENT '点赞数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_file_comment_file_id`(`file_id` ASC) USING BTREE,
  INDEX `IDX_file_comment_user_id`(`user_id` ASC) USING BTREE,
  INDEX `IDX_file_comment_pid`(`pid` ASC) USING BTREE,
  CONSTRAINT `FK_file_comment_file_id` FOREIGN KEY (`file_id`) REFERENCES `flie` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_file_comment_pid` FOREIGN KEY (`pid`) REFERENCES `file_comment` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `FK_file_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件评论' ROW_FORMAT = DYNAMIC;

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
  UNIQUE INDEX `IDU_file_star_user_file`(`user_id` ASC, `file_id` ASC) USING BTREE,
  INDEX `IDX_file_star_file_id`(`file_id` ASC) USING BTREE,
  CONSTRAINT `FK_file_star_file_id` FOREIGN KEY (`file_id`) REFERENCES `flie` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_file_star_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评级信息' ROW_FORMAT = DYNAMIC;

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
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `privacy_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '隐私程度',
  `star` double NULL DEFAULT NULL COMMENT '星级',
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本',
  `upload_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `state` bit(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_flie_user_id`(`user_id` ASC) USING BTREE,
  INDEX `IDX_flie_floder_id`(`floder_id` ASC) USING BTREE,
  CONSTRAINT `FK_flie_floder_id` FOREIGN KEY (`floder_id`) REFERENCES `floder` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_flie_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of flie
-- ----------------------------
INSERT INTO `flie` VALUES (1, 1, 3, '毕业设计开题报告.docx', 'docx', '开题报告', '公开', NULL, 'v1', '2026-09-15 10:30:00', b'0');
INSERT INTO `flie` VALUES (2, 1, 4, '毕业设计论文初稿.docx', 'docx', '论文', '私有', NULL, 'v1', '2026-10-20 15:00:00', b'0');
INSERT INTO `flie` VALUES (3, 2, 2, '数据库原理课件.zip', 'zip', '课件', '公开', NULL, 'v1', '2026-09-05 09:00:00', b'0');
INSERT INTO `flie` VALUES (4, 1, 1, '毕业设计要求说明.pdf', 'pdf', '要求', '公开', NULL, 'v1', '2026-09-01 11:00:00', b'0');

-- ----------------------------
-- Table structure for floder
-- ----------------------------
DROP TABLE IF EXISTS `floder`;
CREATE TABLE `floder`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '目录id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录名',
  `pid` int NULL DEFAULT NULL COMMENT '父目录id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_floder_pid`(`pid` ASC) USING BTREE,
  INDEX `IDX_floder_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FK_floder_pid` FOREIGN KEY (`pid`) REFERENCES `floder` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_floder_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '目录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of floder
-- ----------------------------
INSERT INTO `floder` VALUES (1, '毕业设计资料', NULL, 1, '2026-09-01 10:00:00');
INSERT INTO `floder` VALUES (2, '课程资料', NULL, 2, '2026-09-01 10:00:00');
INSERT INTO `floder` VALUES (3, '开题报告', 1, 1, '2026-09-15 10:00:00');
INSERT INTO `floder` VALUES (4, '论文初稿', 1, 1, '2026-10-20 10:00:00');

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
  UNIQUE INDEX `IDU_follow_pair`(`follower_id` ASC, `following_id` ASC) USING BTREE,
  INDEX `IDX_following_id`(`following_id` ASC) USING BTREE,
  CONSTRAINT `FK_follower_id` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_following_id` FOREIGN KEY (`following_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关注表' ROW_FORMAT = DYNAMIC;

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
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `device_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_login_info_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FK_login_info_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录信息' ROW_FORMAT = DYNAMIC;

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
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `pid` int NULL DEFAULT 0 COMMENT '父级菜单',
  `is_hide` tinyint(1) NULL DEFAULT 0,
  `is_leaf` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '首页', '/home', 'home', 0, 0, 0);
INSERT INTO `menu` VALUES (2, '系统管理', '/systemset', 'systemset', 0, 0, 0);
INSERT INTO `menu` VALUES (3, '路由存储管理', '/routesset', 'routes', 2, 0, 1);
INSERT INTO `menu` VALUES (4, '字典存储管理', '/dictsset', 'dictsset', 2, 0, 1);
INSERT INTO `menu` VALUES (5, '部门管理', '/department', 'department', 2, 0, 1);
INSERT INTO `menu` VALUES (6, '角色配置', '/rootsset', 'role', 2, 0, 1);
INSERT INTO `menu` VALUES (7, '用户管理', '/userset', 'userset', 2, 0, 1);
INSERT INTO `menu` VALUES (8, '权限页面分配', '/pagerootset', 'root', 2, 0, 1);
INSERT INTO `menu` VALUES (9, '数据查看', '/dataview', 'collection', 0, 0, 0);
INSERT INTO `menu` VALUES (10, '关于本站弹窗管理', '/sitepopup', 'note', 9, 0, 1);
INSERT INTO `menu` VALUES (11, '测试', '/test', 'page', 0, 0, 1);
INSERT INTO `menu` VALUES (14, '更多图标展示', '/icons', 'icons', 0, 1, 1);
INSERT INTO `menu` VALUES (15, '个人资料', '/personal', 'personal', 0, 1, 1);
INSERT INTO `menu` VALUES (16, '设置', '/setting', 'setting', 0, 1, 1);
-- 补充业务模块菜单（对应 views/children/systemset/ 下新建页面）
INSERT INTO `menu` VALUES (40, '班级管理', '/class', 'class', 2, 0, 1);
INSERT INTO `menu` VALUES (41, '课程管理', '/course', 'course', 2, 0, 1);
INSERT INTO `menu` VALUES (42, '学生管理', '/student', 'student', 2, 0, 1);
INSERT INTO `menu` VALUES (43, '课程安排', '/schedule', 'schedule', 2, 0, 1);
INSERT INTO `menu` VALUES (44, '公告管理', '/notice', 'notice', 2, 0, 1);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公告名称',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `expiration_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_notice_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FK_notice_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公告' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, 1, '关于2026届毕业设计选题的通知', '各位同学：2026届毕业设计选题工作已启动，请于本月内登录系统完成选题，逾期系统将自动分配。选题方向包括：Java Web 开发、数据库系统设计、移动应用开发、人工智能应用等。如有疑问请联系指导教师。', '2026-11-01 09:00:00', '2026-12-31 23:59:59');
INSERT INTO `notice` VALUES (2, 1, '毕业设计中期检查安排', '定于12月中旬进行毕业设计中期检查，请各位同学准备好阶段成果（需求文档、设计文档、原型系统）按时参加。中期检查未通过者将影响最终答辩资格。', '2026-11-08 10:00:00', '2026-12-20 23:59:59');
INSERT INTO `notice` VALUES (3, 2, '数据库课程设计提交提醒', '数据库课程设计报告提交截止时间为12月10日，请未提交的同学抓紧时间。报告需包含 ER 图、关系模式设计、SQL 脚本及测试结果。', '2026-11-10 08:30:00', '2026-12-10 23:59:59');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `state` tinyint(1) NULL DEFAULT 1 COMMENT '状态 1启用 0禁用',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', 1, NULL);
INSERT INTO `role` VALUES (2, '普通用户', 1, NULL);
INSERT INTO `role` VALUES (3, '付费会员', 1, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限菜单展示表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1, 1);
INSERT INTO `role_menu` VALUES (2, 1, 2);
INSERT INTO `role_menu` VALUES (3, 2, 1);
INSERT INTO `role_menu` VALUES (4, 2, 2);
INSERT INTO `role_menu` VALUES (5, 2, 3);
INSERT INTO `role_menu` VALUES (6, 3, 8);
INSERT INTO `role_menu` VALUES (7, 3, 2);
-- 超级管理员（role_id=1）补充新业务菜单权限
INSERT INTO `role_menu` VALUES (8, 1, 40);
INSERT INTO `role_menu` VALUES (9, 1, 41);
INSERT INTO `role_menu` VALUES (10, 1, 42);
INSERT INTO `role_menu` VALUES (11, 1, 43);
INSERT INTO `role_menu` VALUES (12, 1, 44);

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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程安排关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (1, 1, 1, 1);
INSERT INTO `schedule` VALUES (2, 1, 2, 2);
INSERT INTO `schedule` VALUES (3, 1, 5, 3);
INSERT INTO `schedule` VALUES (4, 2, 1, 1);
INSERT INTO `schedule` VALUES (5, 2, 3, 4);
INSERT INTO `schedule` VALUES (6, 3, 3, 5);
INSERT INTO `schedule` VALUES (7, 3, 2, 2);
INSERT INTO `schedule` VALUES (8, 4, 4, 6);
INSERT INTO `schedule` VALUES (9, 4, 1, 1);
INSERT INTO `schedule` VALUES (10, 5, 3, 5);

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学生班级表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 20, 1);
INSERT INTO `student` VALUES (2, 21, 1);
INSERT INTO `student` VALUES (3, 22, 2);
INSERT INTO `student` VALUES (4, 23, 3);
INSERT INTO `student` VALUES (5, 24, 4);
INSERT INTO `student` VALUES (6, 25, 5);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码(BCrypt)',
  `nick_name` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `organization` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组织/机构',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `state` tinyint(1) NULL DEFAULT 0 COMMENT '锁定状态 0为非锁定，1为锁定',
  `true_state` tinyint(1) NULL DEFAULT 0 COMMENT '实名状态 0为非实名，1为已实名，2为审核中ing',
  `role_id` int NULL DEFAULT 1 COMMENT '用户权限 role_id',
  `true_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `id_number` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `direction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '研究方向',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_user_role`(`role_id` ASC) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- 注意：以下密码为占位明文，接入 BCrypt 后需重新生成密文（如 admin -> $2a$10$...）
-- 教师用户 id=1~5（role_id=2 普通用户），学生用户 id=20~25
-- ----------------------------
INSERT INTO `user` VALUES (1, 'teacher_wang', '123456', '王老师', NULL, '13800000001', '男', 42, '教授 Java 与数据结构', 'wang@bishe.edu.cn', '计算机学院', '2024-02-13 15:11:58', 0, 1, 2, '王建国', NULL, 'Java 开发');
INSERT INTO `user` VALUES (2, 'teacher_li', '123456', '李老师', NULL, '13800000002', '女', 38, '教授数据库原理', 'li@bishe.edu.cn', '计算机学院', '2024-02-13 16:35:00', 0, 1, 2, '李丽', NULL, '数据库');
INSERT INTO `user` VALUES (3, 'teacher_zhang', '123456', '张老师', NULL, '13800000003', '男', 45, '教授操作系统与计算机网络', 'zhang@bishe.edu.cn', '计算机学院', '2024-02-14 00:47:09', 0, 1, 2, '张伟', NULL, '操作系统');
INSERT INTO `user` VALUES (4, 'teacher_chen', '123456', '陈老师', NULL, '13800000004', '女', 35, '教授软件工程导论', 'chen@bishe.edu.cn', '软件学院', '2024-02-15 06:19:52', 0, 1, 2, '陈静', NULL, '软件工程');
INSERT INTO `user` VALUES (5, 'teacher_liu', '123456', '刘老师', NULL, '13800000005', '男', 40, '教授数据结构', 'liu@bishe.edu.cn', '计算机学院', '2024-02-17 04:07:16', 0, 1, 2, '刘强', NULL, '数据结构');
INSERT INTO `user` VALUES (6, 'admin', 'admin', '管理员', NULL, NULL, NULL, NULL, NULL, 'admin@qq.com', NULL, '2024-04-20 10:57:51', 0, 0, 1, NULL, NULL, NULL);
INSERT INTO `user` VALUES (10, 'test', '001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2026-03-16 21:33:58', 0, 0, 1, NULL, NULL, NULL);
INSERT INTO `user` VALUES (11, 'test1', '001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2026-03-16 22:34:50', 0, 0, 1, NULL, NULL, NULL);
INSERT INTO `user` VALUES (20, 'stu_zhaolei', '123456', '赵磊', NULL, '13900000001', '男', 21, '2020级软件工程1班学生', 'zhaolei@stu.bishe.edu.cn', '软件学院', '2026-09-01 10:00:00', 0, 1, 2, '赵磊', NULL, 'Java 开发');
INSERT INTO `user` VALUES (21, 'stu_sunli', '123456', '孙丽', NULL, '13900000002', '女', 21, '2020级软件工程1班学生', 'sunli@stu.bishe.edu.cn', '软件学院', '2026-09-01 10:00:00', 0, 1, 2, '孙丽', NULL, '数据库');
INSERT INTO `user` VALUES (22, 'stu_zhoujie', '123456', '周杰', NULL, '13900000003', '男', 22, '2020级软件工程2班学生', 'zhoujie@stu.bishe.edu.cn', '软件学院', '2026-09-01 10:00:00', 0, 1, 2, '周杰', NULL, '人工智能');
INSERT INTO `user` VALUES (23, 'stu_wuhao', '123456', '吴昊', NULL, '13900000004', '男', 21, '2020级计科3班学生', 'wuhao@stu.bishe.edu.cn', '计算机学院', '2026-09-01 10:00:00', 0, 1, 2, '吴昊', NULL, '操作系统');
INSERT INTO `user` VALUES (24, 'stu_zhengmin', '123456', '郑敏', NULL, '13900000005', '女', 20, '2021级软件工程1班学生', 'zhengmin@stu.bishe.edu.cn', '软件学院', '2026-09-01 10:00:00', 0, 1, 2, '郑敏', NULL, '软件工程');
INSERT INTO `user` VALUES (25, 'stu_fengyu', '123456', '冯宇', NULL, '13900000006', '男', 20, '2021级计科2班学生', 'fengyu@stu.bishe.edu.cn', '计算机学院', '2026-09-01 10:00:00', 0, 1, 2, '冯宇', NULL, '计算机网络');

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '查看记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of view
-- ----------------------------
INSERT INTO `view` VALUES (1, 4, 1, '2024-02-09 16:35:00');
INSERT INTO `view` VALUES (2, 4, 1, '2024-02-10 00:47:09');
INSERT INTO `view` VALUES (3, 4, 2, '2024-02-11 06:19:52');
INSERT INTO `view` VALUES (4, 4, 2, '2024-02-13 04:07:16');
INSERT INTO `view` VALUES (5, 4, 3, '2024-02-14 22:07:19');

SET FOREIGN_KEY_CHECKS = 1;
