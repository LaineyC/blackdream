-- ----------------------------
-- Table structure for Employee
-- ----------------------------

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `Employee` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `isDelete` TINYINT(1) NULL COMMENT '是否删除',
  `createDate` DATETIME NULL COMMENT '创建日期',
  `modifyDate` DATETIME NULL COMMENT '修改日期',
  `creator_id` BIGINT(20) NULL COMMENT '创建者',
  `modifier_id` BIGINT(20) NULL COMMENT '修改者',
  `cardNo` VARCHAR(100) NULL UNIQUE COMMENT '工号',
  `loginCount` INT(11) NULL COMMENT '登陆次数',
  `balance` DECIMAL(10, 2) NULL COMMENT '余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;