/*
 Navicat MySQL Data Transfer

 Source Server         : my8.0
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:8306
 Source Schema         : f_permissions_test

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 25/06/2020 23:27:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for f_customer
-- ----------------------------
DROP TABLE IF EXISTS `f_customer`;
CREATE TABLE `f_customer`  (
  `id` bigint(0) NOT NULL,
  `cust_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cust_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cust_level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `org_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_id` bigint(0) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of f_customer
-- ----------------------------
INSERT INTO `f_customer` VALUES (1, '张三', '1', '1', '1', 1, '2020-06-25 22:08:59');
INSERT INTO `f_customer` VALUES (2, '李四', '1', '2', '1', 1, '2020-06-25 22:09:30');
INSERT INTO `f_customer` VALUES (3, '张飞', '2', '1', '2', 2, '2020-06-25 22:09:44');
INSERT INTO `f_customer` VALUES (4, '刘备', '2', '2', '2', 2, '2020-06-25 22:10:04');

SET FOREIGN_KEY_CHECKS = 1;
