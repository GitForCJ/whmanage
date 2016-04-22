--用户表
CREATE TABLE `sys_user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_no` VARCHAR(20) NOT NULL,
  `user_login` VARCHAR(20) NOT NULL,
  `user_pass` VARCHAR(20) NOT NULL,
  `trade_pass` VARCHAR(20) NOT NULL,
  `user_role` INT(11) not  NULL,
  `user_ename` VARCHAR(20) NOT NULL, 
  `user_pt` INT(11) NOT NULL, 
  `user_status` INT NOT NULL,
  `user_site` INT DEFAULT NULL,
  `user_city` INT DEFAULT NULL,
  `user_createdate` VARCHAR(14) NOT NULL,
  `user_activate` INT DEFAULT 1, 
  `user_tel` VARCHAR(20) NOT NULL,
  `user_adress` VARCHAR(100) DEFAULT NULL,
  `user_mail` VARCHAR(30) DEFAULT NULL,
  `user_shortflag` int DEFAULT 1,
  `user_feeshortflag` int DEFAULT 1,
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8


--资金账户
CREATE TABLE `wht_facct` (
  `fundacct` varchar(15) NOT NULL,
  `accountleft` int(11) NOT NULL,
  `awardleft` bigint NOT NULL,
  `state` int NOT NULL,
  `user_no` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`fundacct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--资金子账户
CREATE TABLE `wht_childfacct` (
  `childfacct` varchar(17) NOT NULL,
  `fundacct` varchar(15) NOT NULL,
  `type`      varchar(2) NOT NULL,
  `accountleft` int(11) NOT NULL,
  `awardleft` int(11) NOT NULL,
  PRIMARY KEY (`childfacct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--账户类型
CREATE TABLE `wht_faccttype` (
  `code` VARCHAR(2) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--面额区间表 
CREATE TABLE `sys_valueRange` (
  `cm_id` VARCHAR(12) NOT NULL,
  `min` int NOT NULL,
  `max` int NOT NULL,
  PRIMARY KEY (`cm_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--本省佣金表
CREATE TABLE `sys_employ0` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` int  NOT NULL,
  `TYPE` int NOT NULL,
  `cm_id` VARCHAR(12) NOT NULL,
  `VALUE` float(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--外省佣金表
CREATE TABLE `sys_employ1` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` int  NOT NULL,
  `TYPE` int NOT NULL,
  `cm_id` VARCHAR(12) NOT NULL,
  `VALUE` float(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--接口商佣金表
CREATE TABLE `sys_tpemploy` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` int  NOT NULL,
  `TYPE` int NOT NULL,
  `cm_id` VARCHAR(12) NOT NULL,
  `VALUE` float(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8



--直营佣金表
CREATE TABLE `sys_employ2` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` int  NOT NULL,
  `TYPE` int NOT NULL,
  `cm_id` VARCHAR(12) NOT NULL,
  `VALUE` float(5,2)  NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--增值业务佣金表
CREATE TABLE `sys_employ3` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(4) NOT NULL,
  `VALUE` float(5,2) NOT NULL,
  `flag` int  NOT NULL,  0非直营 1直营
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--号段表
CREATE TABLE `sys_phone_area` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `phone_num` VARCHAR(20) DEFAULT NULL,
  `province_code` INT(10) DEFAULT NULL,
  `cart_type` VARCHAR(50) DEFAULT NULL,
  `phone_type` INT(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=188181 DEFAULT CHARSET=utf8


--账户日志表
CREATE TABLE `wht_acctbill_1401` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `childfacct` varchar(17) NOT NULL,
  `dealserial` varchar(50) NOT NULL,
  `tradeaccount` varchar(30) NOT NULL,
  `tradetime` varchar(14) NOT NULL,
  `tradefee` int(11) NOT NULL,
  `infacctfee` int(11) NOT NULL,
  `tradetype` int(11) NOT NULL,
  `expl` varchar(11) NOT NULL,
  `state` int(11) NOT NULL,
  `distime` varchar(14) NOT NULL,
  `accountleft` int(11) NOT NULL,
  `tradeserial` varchar(50) NOT NULL,
  `other_childfacct` varchar(40) DEFAULT NULL,
  `pay_type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8

--订单表
CREATE TABLE `wht_orderform_1405` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `areacode` VARCHAR(11) NOT NULL,
  `tradeserial` VARCHAR(50) NOT NULL,
  `tradeobject` VARCHAR(30) NOT NULL,
  `buyid` VARCHAR(30) DEFAULT NULL,
  `service` VARCHAR(6) NOT NULL,
  `fee` INT(11) NOT NULL,
  `tradefee` INT(11) NOT NULL,
  `fundacct` VARCHAR(20) NOT NULL,
  `tradetime` VARCHAR(14) NOT NULL,
  `chgtime` VARCHAR(50) NOT NULL,
  `state` INT(11) NOT NULL,
  `writeoff` VARCHAR(100) DEFAULT NULL,
  `writecheck` VARCHAR(100) DEFAULT NULL,
  `term_type` VARCHAR(2) DEFAULT NULL,
  `userno` VARCHAR(20) DEFAULT NULL,
  `user_name` VARCHAR(20) DEFAULT NULL,
  `phone_type` INT(11) NOT NULL,
  `phone_pid` INT(11) NOT NULL,
  `phoneleft` INT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8


--增值业务佣金表
CREATE TABLE `sys_employ3` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(4) NOT NULL,
  `VALUE` float NOT NULL,
  `flag` int  NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--业务类型表
CREATE TABLE `wht_service` (
  `code` VARCHAR(4) NOT NULL,
  `flag` int  NOT NULL,
  `name` VARCHAR(30) NOT NULL,
) ENGINE=INNODB DEFAULT CHARSET=utf8


--接口信息表
CREATE TABLE `sys_interface` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(30) NOT NULL,
    `flag` INT NOT NULL,
  PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--接口 省份 运营商关联表
CREATE TABLE `sys_interfacemaping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `TYPE` int(11) NOT NULL,
  `interid` int(11) NOT NULL,
  `cmid` varchar(20) DEFAULT NULL,
  `reserve` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2544 DEFAULT CHARSET=utf8

 ALTER TABLE sys_interfaceMaping ADD cmid VARCHAR(20)
 ALTER TABLE sys_interfaceMaping ADD reserve VARCHAR(20)

--代理商代理点佣金比例分配表
CREATE TABLE `wht_agentAnduser` (
  `userno` VARCHAR(20) NOT NULL,
  `parentid` INT(11) NOT NULL,
  `employ0id` INT  NULL, --充值业务佣金id
  `employ3id` INT  NULL,--增值业务佣金id
  `value` VARCHAR(12) NOT NULL,  --分配给代理点比例
  `flag` int NOT NULL  --0充值业务 1增值业务 
) ENGINE=INNODB DEFAULT CHARSET=utf8

--交易类型表
CREATE TABLE `wht_acctradetype`(
  `code` int NOT NULL,
  `name` VARCHAR(30) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8

--一卡惠商品品牌同步信息表
CREATE TABLE `wht_brandTable` (
  `brandId` VARCHAR(20) NOT NULL,
  `brandName` VARCHAR(100) NOT NULL,
  `remark` VARCHAR(100) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8

--财付通转账表
CREATE TABLE `wht_tenpaytransferlog` (
  `id` int(12) unsigned NOT NULL AUTO_INCREMENT,
  `roletype` char(10) NOT NULL,
  `rolenum` varchar(30) NOT NULL,
  `childfacct` varchar(30) NOT NULL,
  `desc` varchar(32) NOT NULL,
  `purchaser_id` varchar(50) NOT NULL,
  `bargainor_id` varchar(20) NOT NULL,
  `transaction_id` varchar(28) NOT NULL,
  `sp_billno` varchar(28) NOT NULL,
  `clientIP` varchar(15) NOT NULL,
  `pay_fee` int(12) NOT NULL,
  `poundage` int(12) NOT NULL,
  `date` varchar(14) NOT NULL,
  `txstatechg` varchar(14) NOT NULL,
  `neistatechg` varchar(14) NOT NULL,
  `tradetime` varchar(14) NOT NULL,
  `neiflag` varchar(14) NOT NULL,
  `tencentflag` varchar(14) NOT NULL,
  `accoutstate` varchar(14) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

--殴飞用户绑定信息
CREATE TABLE `sys_oufei` (
  `userno` varchar(50) NOT NULL, --代理点系统编号
  `fundacct` varchar(50) NOT NULL,--代理点资金账号
  `oufeiID` varchar(60) NOT NULL,--
  `oufeimoban` varchar(60) NOT NULL,
  `oufeisign` varchar(60) NOT NULL,
  `TIME` varchar(14) NOT NULL,
    `state` int NOT NULL,        -- 0可用 --1不可用
      `isstart` int NOT NULL,   --0开启 1关闭
  `userpid` int NOT NULL, --用户省份编号
    `areacode` varchar(10) NOT NULL,
      `login` varchar(20) NOT NULL,
      `parentid` varchar(10) NOT NULL,--父节点
   PRIMARY KEY (`userno`)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--小面额佣金组
CREATE TABLE `sys_employ5` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` int  NOT NULL,
  `type` int NOT NULL,
  `cm_id` VARCHAR(20) NOT NULL,
  `value` int  NOT NULL,  --交易金额
  `value1` int  NOT NULL, --返佣金额
  `flag` int NOT NULL,   
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--殴飞开关
CREATE TABLE `sys_oufeiswitch` (
  `userno` varchar(50) NOT NULL,
  `fundacct` varchar(50) NOT NULL,
  `oufeitype` VARCHAR(20)  -- 0 话费充值  1 Q币充值
  PRIMARY KEY (`userno`)
) ENGINE=INNODB DEFAULT CHARSET=utf8


--系统扣款账务统计表
CREATE TABLE `wht_acctout` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(20) NOT NULL,--业务类型
  `buyid` int NOT NULL,--接口id
  `state` int NOT NULL,--状态
  `total` int  NOT NULL, --数量
  `totalmoney` int  NOT NULL,  --总预计交易金额 
  `facctmoney` int NOT NULL,  --实际交易金额
   `date` VARCHAR(8) NOT NULL,--日期
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--系统加款账务表组成
CREATE TABLE `wht_acctin` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `code`  int NOT NULL,--业务类型
  `total` int  NOT NULL, --数量
  `totalmoney` int  NOT NULL,  --交易金额 
   `date` VARCHAR(8) NOT NULL,--日期
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--代理商开户限制表
CREATE TABLE `wht_agentNum` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `agentnum`  int NOT NULL,--业务类型
   `date` VARCHAR(8) NOT NULL,--日期
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8
--用户密码错误记录表
CREATE TABLE `sys_userloginLimit` (
   `login` VARCHAR(20) NOT NULL, --登陆账号
  `userno` VARCHAR(20) NOT NULL, --用户系统编号
  `time` VARCHAR(14) ,  --第一次错误时间
  `errornum` INT , --错误次数默认为1 
  `total` INT , --总数为7
  PRIMARY KEY (`login`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--月度奖励明细表 concat(c.fundacct,'02')
CREATE TABLE `wht_monthawards` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(20) NOT NULL,-- 登陆账号
  `userid` INT NOT NULL,-- 用户id
  `usertype` INT NOT NULL,-- 用户类型
  `money` INT NOT NULL,-- 交易总额
  `state` INT NOT NULL,  -- 状态 0 已返 1未返
  `statisticdate` VARCHAR(6) NOT NULL,-- 统计的月份 
  `logdate` VARCHAR(14) NOT NULL,-- 记录生成时间
  `operate` VARCHAR(14) ,-- 奖励时间 
  `operatewho` VARCHAR(20) ,-- 操作人 
  `childfacct` VARCHAR(20) ,-- 佣金账号
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--月度奖励表
CREATE TABLE `wht_awardsrule` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `usertype` int NOT NULL, -- 用户类型
  `minmoney` int NOT NULL,--大于等于 最小值 
  `maxmoney` int NOT NULL, --小于 最大值 
  `commissionrate` float(5,2)  NOT NULL,--奖励比例
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--接口商佣金表
CREATE TABLE `sys_tpemploy` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` INT(11) NOT NULL,
  `TYPE` INT(11) NOT NULL,
  `cm_id` VARCHAR(12) NOT NULL,
  `value` FLOAT(5,2) DEFAULT NULL,
  `groups` int ,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8

--接口上信息配置表

CREATE TABLE `sys_agentsign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userno` varchar(20) DEFAULT NULL,
  `keyvalue` varchar(12) DEFAULT NULL,
  `keyvalue1` varchar(8) DEFAULT NULL,
  `ip` text,
  `state` int(1) DEFAULT NULL,
  `interName` varchar(50) DEFAULT NULL,
  `qbCommission` FLOAT(5,2),
  `caidan` VARCHAR(20)
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8

 --银联绑定信息表
CREATE TABLE `wht_netpay` (
  `userno` VARCHAR(20) NOT NULL,--用户系统编号
  `username`  VARCHAR(20) NOT NULL,--持卡人姓名
  `credentialType` VARCHAR(4),--证件类型 
  `credentialNum` VARCHAR(30), --证件号码
  `bankID` VARCHAR(4) NOT NULL,--银行编号
  `bankflag` int NOT NULL,--卡类型标示 0借记卡  1存折  2信用卡
  `banknum` VARCHAR(30) NOT NULL, --银行卡号
  `isbanding` int NOT NULL, --是否已经绑定   0未绑定   1已绑定
  `submit` VARCHAR(8) NOT NULL, --提交日期
  `finish` VARCHAR(8), --绑定日期
  `ext1` VARCHAR(20),  --扩展
  `ext2` VARCHAR(20), --扩展
  primary key (userno)
) ENGINE=INNODB DEFAULT CHARSET=utf8

 --银行列表
CREATE TABLE `wht_uniontype` (
  `unionum` VARCHAR(20) NOT NULL,--银行编号
  `username`  VARCHAR(20) NOT NULL--说明
) ENGINE=INNODB DEFAULT CHARSET=utf8

 --卡类型列表  0借记卡 1存折 2信用卡
CREATE TABLE `wht_cardtype` (
  `cardnum` int NOT NULL,--卡类型标示
  `cardname`  VARCHAR(20) NOT NULL --说明
) ENGINE=INNODB DEFAULT CHARSET=utf8

 --身份类型表
CREATE TABLE `wht_identitytype` (
  `cardnum` int NOT NULL,--身份类型
  `cardname`  VARCHAR(20) NOT NULL --说明
) ENGINE=INNODB DEFAULT CHARSET=utf8

--一翼支付代扣绑定信息表
CREATE TABLE `wht_transactionrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userLogin` varchar(20) DEFAULT NULL,
  `userNumber` varchar(20) DEFAULT NULL,
  `orderNumber` varchar(20) DEFAULT NULL,
  `recordMoney` varchar(20) DEFAULT NULL,
  `bankacct` varchar(64) DEFAULT NULL,
  `payType` int(11) DEFAULT NULL,
  `recordStatus` int(11) DEFAULT NULL,--0 表示 处理中 1 标示成功 2标示失败
  `recordTime` varchar(14) DEFAULT NULL,
  `finishTime` varchar(14) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8

/*银行代表码*/
CREATE TABLE wht_BankCode
(
 BankCodeId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
 BankCodeNumber VARCHAR(20),
 BankCodeName VARCHAR(100)
)ENGINE=INNODB DEFAULT CHARSET=utf8
/* 区域编码 */
CREATE TABLE wht_RegionalCode
(
 RegionalCodeId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
 RegionalCodeNumber VARCHAR(20),
 RegionalCodeName VARCHAR(20)
)ENGINE=INNODB DEFAULT CHARSET=utf8

CREATE TABLE wht_BankCard_Type
(
 BandCardTypeId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
 BandCardTypeNumber VARCHAR(10),
 BandCardTypeName VARCHAR(20)
)ENGINE=INNODB DEFAULT CHARSET=utf8

/* 证件表*/
CREATE TABLE wht_Papers_Type
(
Papers_TypeId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
Papers_TypeNumber VARCHAR(10),
Papers_TypeName VARCHAR(50)
)ENGINE=INNODB DEFAULT CHARSET=utf8

==新增表
CREATE TABLE `mac` (
  `id` INT(11) NOT NULL AUTO_INCREMENT, --自增
  `userno` VARCHAR(20) DEFAULT NULL,  --用户系统编号
  `macaddress` VARCHAR(30) DEFAULT NULL, --mac
  `googlesign` VARCHAR(30) DEFAULT NULL, --身份验证秘钥
  `twodimensionalcode` TEXT  DEFAULT NULL, --身份验证二维码
  `ext1` VARCHAR(30) DEFAULT NULL, --扩展
  `ext2` VARCHAR(30) DEFAULT NULL, --扩展
  `ext3` VARCHAR(30) DEFAULT NULL,--扩展
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
ALTER TABLE mac MODIFY ext1 VARCHAR(300) 

--限制日期
CREATE TABLE `wht_datelimit` (
  `date` VARCHAR(8)  UNICODE  NOT NULL, 
  `ext1` VARCHAR(30) DEFAULT NULL,  
  `ext2` VARCHAR(30) DEFAULT NULL, 
  PRIMARY KEY (`date`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

INSERT INTO sys_menu VALUE('0132','0101','限制日期','','/business/whtdatelimitlist.jsp',NULL,NULL,316,3);
INSERT INTO sys_sm_power VALUES(1,'0132',1);


--拉卡拉转账信息记录表
CREATE TABLE `wht_lakal_record` (
  `id` INT(12) NOT NULL AUTO_INCREMENT,
  `txncod` VARCHAR(20) NOT NULL,--交易码
  `requestId` VARCHAR(20) NOT NULL,--请求方
  `mercId` VARCHAR(20) NOT NULL,--商户号
  `termId` VARCHAR(20) NOT NULL,--终端编号
  `refNumber` varchar(18) not  NULL,--系统参考号
  `orgRefNum` VARCHAR(18) default NULL,--原系统参考号 
  `orderID` VARCHAR(18) NOT NULL,--订单号 
  `amount` bigint NOT NULL, --金额 
  `ransTime` VARCHAR(18) NOt NULL,--交易传输时间
  `orderSta` varchar(2) NULL,--拉卡拉交易状态
  `payType`  varchar(2) NOT NULL,--支付类型
  `extData` varchar(50) DEFAULT NULL,
  `isbinding` int  NOT NULL, --终端编号在万汇通平台是否绑定 0未绑定 1已经绑定
  `tradetime` VARCHAR(14) NOT NULL,--万汇通平台接收时间
  `userno` VARCHAR(10) DEFAULT NULL,--万汇通系统编号
  `fundacct` VARCHAR(16) DEFAULT NULL,--万汇通资金账号
  `userlogin` varchar(11) DEFAULT NULL,--万汇通平台登录账号
  `fee` bigint NOT NULL, --实际交易金额 
  `state` INT(12) NOT NULL, --0 未加款  1 已加款  2处理中  3超时 4 已冲正
  `typeT` varchar(4) ,-- T0,T1
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
   PRIMARY KEY (id),
   INDEX three(termId,refNumber)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

--拉卡拉绑定
CREATE TABLE `sys_lkal` (

  `termid` VARCHAR(20) NOT NULL,--终端编号
  `userno` VARCHAR(10) NOT NULL,--万汇通系统编号
  `fundacct` VARCHAR(16) NOT NULL,--万汇通资金账号
  `userlogin` VARCHAR(11) NOT NULL,--万汇通平台登录账号
  `time` VARCHAR(14) NOT NULL,--绑定的时间
  `type` int NOT NULL,--绑定类型 0 拉卡拉
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
  unique index one(termid),
  index two(userno,time)
) ENGINE=INNODB DEFAULT CHARSET=utf8

select userno,fundacct,userlogin from sys_lkal where type=0 and termid=


#==8.30 修改表字段为bigint
ALTER TABLE wht_acctbill_1408 MODIFY  accountleft BIGINT  infacctfee accountleft
---------------------------------------------20140906
--阶梯组
CREATE TABLE `sys_setupgroups` (
  `groupsID` int not null AUTO_INCREMENT,--组id
  `typenum`  int  NOT NULL,--组类型编号 0 一级代理商阶梯
  `groupname` VARCHAR(50) NOT NULL,--组名称
  `isdefaut` int  NOT NULL,--是否默认  0 非默认  1 默认
  `time` VARCHAR(14) NOT NULL,--添加时间
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
  PRIMARY KEY (`groupsID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--阶梯配置
CREATE TABLE `sys_oneagent` (
  `setupID` int NOT NULL AUTO_INCREMENT,--阶梯标示
  `groupsID` int NOT NULL,--组编号
  `tradetype` VARCHAR(4) NOT NULL,--交易类型
  `monbegin` int NOT NULL,--面额起始值
  `monend` int  NOT NULL,--面额结束值
  `percent` VARCHAR(20) NOT NULL,--佣金比例 %
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  PRIMARY KEY (`setupID`),
  INDEX agentsetup (groupsID),
  FOREIGN KEY (groupsID) REFERENCES sys_setupgroups(groupsID) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8


----一级代理商阶梯映射表
CREATE TABLE `sys_oneagentmaps` (
  `groupsID` int NOT NULL  ,--阶梯组表示
  `userno` VARCHAR(20) NOT NULL,--万汇通系统编号
   FOREIGN KEY (groupsID) REFERENCES sys_setupgroups(groupsID) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO sys_menu VALUE('0705','0701','批量开户','','/rights/autouser.jsp',NULL,NULL,328,3);
INSERT INTO sys_sm_power VALUES(1,'0705',1);
-------------------
ALTER TABLE wht_facct ADD CONSTRAINT con1 
FOREIGN KEY (user_no) REFERENCES sys_user(user_no) ON DELETE CASCADE

ALTER TABLE wht_facct ADD INDEX index11(user_no)主外键 必须有索引
-------------
ALTER TABLE wht_childfacct ADD CONSTRAINT con2 
FOREIGN KEY (fundacct) REFERENCES wht_facct(fundacct) ON DELETE CASCADE
--拉卡拉盐值对
   CREATE TABLE `wht_lakal_keys` (
  `index` INT(12) NOT NULL ,
  `keyss` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`index`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8


  ALTER TABLE wht_lakal_record MODIFY termid VARCHAR(30)
 
  ALTER TABLE wht_lakal_record MODIFY paytype VARCHAR(10)
  
  ALTER TABLE wht_lakal_record MODIFY refNumber VARCHAR(50)
  
  =================9.28
  
  CREATE TABLE `sys_reversalcount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_login` varchar(20) DEFAULT NULL,
  `user_no` varchar(20) DEFAULT NULL,
  `reversalcount` int(11) DEFAULT NULL,
  `tradetype` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `reversal_index` (`user_no`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8
  
ALTER TABLE sys_reversalcount ADD tradetype INT NOT NULL      
tradetype     0 冲正次数  1 Q币每天限额（元）  2支付宝每天限额（元） 3 额度转移（0 标示无权限 1标示有权限） 4 是否启用多通道(1 启用  0禁用 2程序暂时禁用)
============10.11


===============10.17
--交通罚款工单表
CREATE TABLE `wht_bruleorder` (
  `id` BIGINT(20) NOT NULL,
  `woNum` VARCHAR(50) NOT NULL,
  `userno` VARCHAR(12) NOT NULL,
  `contactNum` VARCHAR(20) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `autoCarID` BIGINT(20) NOT NULL,
  `carnum` VARCHAR(20) NOT NULL,
  `dealserial` VARCHAR(30) NOT NULL,
  `payMethod` CHAR(1) NOT NULL,
  `isVisit` CHAR(1) NOT NULL,
  `totalCharge` INT(11) NOT NULL,
  `fromCanal` CHAR(1) NOT NULL,
  `createDate` VARCHAR(30) NOT NULL,
  `workerNo` VARCHAR(30) NOT NULL,
  `woState` CHAR(4) DEFAULT NULL,
  `spID` BIGINT(20) NOT NULL,
  `resultCode` VARCHAR(10) NOT NULL,
  `carFrameNum` VARCHAR(10) NOT NULL,
  `engineNumber` VARCHAR(4) NOT NULL,
  `Exp1` VARCHAR(30) DEFAULT NULL,
  `Exp2` VARCHAR(30) DEFAULT NULL,
  `Exp3` VARCHAR(30) DEFAULT NULL,
  `Exp4` VARCHAR(30) DEFAULT NULL,
  `Exp5` VARCHAR(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `indexwoNum` (`woNum`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--交通罚款违章信息表
CREATE TABLE `wht_breakrules` (
  `id` bigint(20) NOT NULL,
  `gdid` bigint(20) NOT NULL,
  `pecNum` varchar(100) NOT NULL,
  `pecCode` varchar(30) NOT NULL,
  `pecDesc` varchar(200) NOT NULL,
  `pecAddr` varchar(200) NOT NULL,
  `pecDate` varchar(30) NOT NULL,
  `pecScore` int(11) NOT NULL,
  `corpus` int(11) NOT NULL,
  `lateFee` int(11) NOT NULL,
  `replaceMoney` int(11) NOT NULL,
  `ownMoney` int(11) NOT NULL,
  `agent` char(1) NOT NULL,
  `pecState` char(1) NOT NULL,
  `pecChanl` char(1) NOT NULL,
  `createDate` varchar(30) NOT NULL,
  `updateDate` varchar(30) NOT NULL,
  `updateWorkerNo` varchar(30) NOT NULL,
  `woState` char(4) DEFAULT NULL,
  `areaCode` varchar(10) NOT NULL,
  `illegalType` varchar(10) NOT NULL,
  `Exp1` varchar(30) DEFAULT NULL,
  `Exp2` varchar(30) DEFAULT NULL,
  `Exp3` varchar(30) DEFAULT NULL,
  `Exp4` varchar(30) DEFAULT NULL,
  `Exp5` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `indexgdid` (`gdid`),
  CONSTRAINT `wht_breakrules_ibfk_1` FOREIGN KEY (`gdid`) REFERENCES `wht_bruleorder` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--代扣禁用用户
#此表中的用户表示 代收禁用
CREATE TABLE wht_AccountInfo_hidden
(
 id INT(11) NOT NULL AUTO_INCREMENT,
 userno VARCHAR(20),
 PRIMARY KEY (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8


---------------
CREATE TABLE `sys_hfk_record` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `phone` VARCHAR(20) DEFAULT NULL,
  `userno` VARCHAR(10) DEFAULT NULL,
  `facct` VARCHAR(15) DEFAULT NULL,
  `hfknum` VARCHAR(30) NOT NULL,
  `ournum` VARCHAR(30) NOT NULL,
  `tradefee` BIGINT(20) NOT NULL,
  `fee` BIGINT(20) NOT NULL,
  `tradefirst` VARCHAR(14) DEFAULT NULL,
  `tradetime` VARCHAR(14) NOT NULL,
  `feetime` VARCHAR(14) DEFAULT NULL,
  `cardtype` CHAR(1) DEFAULT NULL,
  `state` CHAR(1) NOT NULL,
  `descript` VARCHAR(10) DEFAULT NULL,
  `exp1` VARCHAR(30) DEFAULT NULL,
  `exp2` VARCHAR(30) DEFAULT NULL,
  `exp3` VARCHAR(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=314 DEFAULT CHARSET=utf8

--广东电信单独菜单接口配置
CREATE TABLE `sys_gddxinterfacemaping` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` INT(11) NOT NULL,
  `TYPE` INT(11) NOT NULL,
  `interid` INT(11) NOT NULL,
  `cmid` VARCHAR(20) DEFAULT NULL,
  `reserve` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

-------------------------20150327
CREATE TABLE `wht_qbbreakrecord_1503` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
   `tradeserial` VARCHAR(50) NOT NULL,--流水号
   `tradeobject` VARCHAR(30) NOT NULL,--交易号码
   `buyid` VARCHAR(30) DEFAULT NULL,--接口编号
   `service` VARCHAR(20) DEFAULT NULL,--交易类型（0005）
   `fee` INT(11) NOT NULL,--面值 厘
   `tradefee` INT(11) NOT NULL,--交易金额 厘
   `tradetime` VARCHAR(14) NOT NULL, --交易时间
   `chgtime` VARCHAR(50) NOT NULL,--状态变更时间
   `state` INT(11) NOT NULL,--状态  同订单表
   `writecheck` VARCHAR(100) DEFAULT NULL,--对账所需数据 暂时填入 流水号+面值
   `userno` VARCHAR(20) DEFAULT NULL,--用户系统编号
   `oldorderid` VARCHAR(50) DEFAULT NULL,--订单号对应订单表
   `bak1` VARCHAR(20) DEFAULT NULL,--备用
   `bak2` VARCHAR(20) DEFAULT NULL,--备用
   `bak3` VARCHAR(20) DEFAULT NULL,--备用
   `bak4` VARCHAR(20) DEFAULT NULL,--备用
  PRIMARY KEY (`id`),
  KEY `order_index` (`oldorderid`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

-------------
CREATE TABLE `sys_oufei_qb` (
  `userno` VARCHAR(50) PRIMARY KEY NOT NULL, --代理点系统编号
  `fundacct` VARCHAR(50) NOT NULL,--代理点资金账号
  `oufeiID` VARCHAR(60) NOT NULL,--
  `oufeimoban` VARCHAR(60) NOT NULL,
  `oufeisign` VARCHAR(60) NOT NULL,
  `TIME` VARCHAR(14) NOT NULL,
    `state` INT NOT NULL,        -- 0可用 --1不可用
      `isstart` INT NOT NULL,   --0开启 1关闭
  `userpid` INT NOT NULL, --用户省份编号
    `areacode` VARCHAR(10) NOT NULL,
      `login` VARCHAR(20) NOT NULL,
      `parentid` VARCHAR(10) NOT NULL,--父节点
      `oufeitype` INT(11) DEFAULT NULL, -- 1 Q币供货
   exp1 VARCHAR(30),
   exp2 VARCHAR(30)
) ENGINE=INNODB DEFAULT CHARSET=utf8

 
ALTER TABLE sys_oufeiswitch ADD COLUMN oufeitype VARCHAR(20)  -- 0 话费充值  1 Q币充值
ALTER TABLE sys_agentsign ADD COLUMN qbCommission FLOAT(5,2)
ALTER TABLE sys_agentsign ADD COLUMN caidan VARCHAR(20) -- 0拆单  1不拆单

INSERT INTO sys_menu VALUE('0524','0501','Q币拆单','','/business/qbOrderAffiliated.jsp',NULL,NULL,336,3);
INSERT INTO sys_sm_power VALUES(1,'0524',1);

--=============================0513
--app用户信息表
CREATE TABLE `wht_app_user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,--id
  `user_login` VARCHAR(50) NOT NULL,--登陆名
  `user_pass` VARCHAR(20) NOT NULL,--密码
  `user_createdate` VARCHAR(14) NOT NULL,--注册日期
  `user_state` INT DEFAULT 0 NOT NULL,--状态 0 可用  1 暂停  2 注销
  `url_id` INT NOT NULL NOT NULL,--url id
  `name` VARCHAR(30),--姓名
  `phone` VARCHAR(30),--手机号
  `qq` VARCHAR(30),--qq号
  `telphone` VARCHAR(30),--座机  
  `msn` VARCHAR(30),  --msn
  `email` VARCHAR(30),--邮箱
  `company` VARCHAR(30),--公司名字
  `position` VARCHAR(30),--职位
  `address` VARCHAR(100),--公司地址
  `fax` VARCHAR(30),--传真
  `website` VARCHAR(50),--公司官网
  `note` VARCHAR(50),--备注
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
  `exp4` VARCHAR(20),
  `exp5` VARCHAR(20),
  `exp6` VARCHAR(20),
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
 
 --app 二维码表
 CREATE TABLE `wht_app_url` (
  `url_id` INT(11) NOT NULL,
  `url` VARCHAR(100) NOT NULL,
  `imgedir` VARCHAR(30) NOT NULL,
  `imgename` VARCHAR(20) NOT NULL,
  `imgedirname` VARCHAR(50) NOT NULL,
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
  `exp4` VARCHAR(20),
  `exp5` VARCHAR(20),
  `exp6` VARCHAR(20),
  PRIMARY KEY (`url_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

insert into wht_app_url(url_id,url,imgedir,imgename,imgedirname) values(?,?,?,?,?) 


---------------0521
--流量产品表
 CREATE TABLE `wht_flowproduct` (
  `product_id` varchar(30) NOT NULL,--产品编号
  `PRODUCT_NAME` VARCHAR(20) NOT NULL,--产品名称
  `PRICE_NUM` INT NOT NULL,--产品资费（元）
  `PRO_NUM`INT NOT NULL,--可叠加次数
  `exp1` VARCHAR(20),--扩展
  `exp2` VARCHAR(20),--扩展
  `exp3` VARCHAR(20),--扩展
  `exp4` VARCHAR(20),--扩展
  PRIMARY KEY (`product_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

 CREATE TABLE `wht_flowprice` (
  `id` varchar(30) NOT NULL AUTO_INCREMENT,--产品编号
  `name` VARCHAR(10) NOT NULL,--产品名称
  `price` decimal(5,2) NOT NULL,--产品价格（元）
  `type` INT NOT NULL,--产品类型 0 电信 1移动 2联通
  `exp1` VARCHAR(20),--扩展
  `exp2` VARCHAR(20),--扩展
  `exp3` VARCHAR(20),--扩展
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

select price from wht_flowprice where type=2 and name='
--
CREATE TABLE `sys_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operator_type` int(11) DEFAULT NULL,
  `flow_interId` int(11) DEFAULT NULL,
  `exp1` varchar(20) DEFAULT NULL,
  `exp2` varchar(20) DEFAULT NULL,
  `exp3` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8
-----------------------2015-7-03
--流量接口与面额对应表
CREATE TABLE `sys_interface2face` (
  `interfaceID` INT(4) NOT NULL,--流量接口
  `flowface` INT(4)  NOT NULL,--流量面额  比如 20 30 50 100 200 
  `exp1` VARCHAR(20) DEFAULT NULL,
  `exp2` VARCHAR(20) DEFAULT NULL,
  `exp3` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY(interfaceID,flowface)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

10 30 70 150 500 1024 2048 3072 4096 6144 11264

50 200

10 30 100 200 500

--交罚个人财付通转款表
CREATE TABLE `wht_jftenpay` (
  `id` INT(12) UNSIGNED NOT NULL AUTO_INCREMENT,
  `desc` VARCHAR(32) NOT NULL,
  `purchaser_id` VARCHAR(50) NOT NULL,
  `bargainor_id` VARCHAR(20) NOT NULL,
  `transaction_id` VARCHAR(28) NOT NULL,
  `sp_billno` VARCHAR(28) NOT NULL,
  `clientIP` VARCHAR(15) NOT NULL,
  `pay_fee` FLOAT NOT NULL,
  `poundage` FLOAT NOT NULL,
  `date` VARCHAR(14) NOT NULL,
  `txstatechg` VARCHAR(14) NOT NULL,
  `neistatechg` VARCHAR(14) NOT NULL,
  `tradetime` VARCHAR(14) NOT NULL,
  `neiflag` VARCHAR(14) NOT NULL,
  `tencentflag` VARCHAR(14) NOT NULL,
  `accoutstate` VARCHAR(14) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

--增加回调地址
ALTER TABLE sys_agentsign ADD COLUMN returnurl VARCHAR(100) 

 --回调id记录表
CREATE TABLE sys_returnrecord(
orderid INT NOT NULL,
logtime VARCHAR(14) NOT NULL,
exp1 VARCHAR(20),
exp2 VARCHAR(20),
exp3 VARCHAR(20),
INDEX timeindex (logtime)
)ENGINE=INNODB  DEFAULT CHARSET=utf8

------------------2015-11-05
CREATE TABLE `sys_flowinterfaceMaping` (
  `pid` INT  NOT NULL,--省份id
  `TYPE` INT NOT NULL,--运营商类型
  `interid` INT NOT NULL,--接口id
   exp1 VARCHAR(20) ,
   exp2 VARCHAR(20) ,
  INDEX towindex(pid)
) ENGINE=INNODB DEFAULT CHARSET=utf8

流量接口配置菜单 /rights/flowconfig.jsp 改为  /business/prod.do?method=flowinterList&type=0
将流量接口 flag改为2 
ALTER TABLE wht_orderform_1511 ADD INDEX buyidindex(buyid)
--流量统计记录表
CREATE TABLE sys_flowcount(
`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
 riqi VARCHAR(8) NOT NULL, --日期
 counttime VARCHAR(14) NOT NULL,--完成时间
 state INT NOT NULL, --状态
    exp1 VARCHAR(20),
    exp2 VARCHAR(20),
    exp3 VARCHAR(20),
 PRIMARY KEY (riqi)
)ENGINE = INNODB DEFAULT CHARSET = utf8

-- 流量统计明细
CREATE TABLE `sys_flowcountrecord` (
  `riqi` varchar(8) NOT NULL,
  `bishu` int(11) NOT NULL,
  `state` int(11) DEFAULT NULL,
  `buyid` varchar(30) NOT NULL,
  `buyname` varchar(50) NOT NULL,
  `sa_id` int(11) DEFAULT NULL,
  `sa_name` varchar(30) DEFAULT NULL,
  `exp1` varchar(20) DEFAULT NULL,
  `exp2` varchar(20) DEFAULT NULL,
  `exp3` varchar(20) DEFAULT NULL,
  KEY `buyidindex` (`buyid`),
  KEY `riqiindex` (`riqi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

  
  
INSERT INTO sys_menu VALUE('0530','0501','流量交易统计','','/rights/flowproportioncount.jsp',NULL,NULL,350,3);
INSERT INTO sys_sm_power VALUES(1,'0530',1);


--------------20151201
--统计流量交易时间表
  CREATE TABLE `sys_flowtimerecord` (
  `riqi` VARCHAR(20) NOT NULL ,
  `userno` VARCHAR(20) NOT NULL ,
  `timerange` VARCHAR(20) NOT NULL,
  `num` INT(14) NOT NULL,
  `exp1` VARCHAR(20) DEFAULT NULL,
  `exp2` VARCHAR(20) DEFAULT NULL,
  `exp3` VARCHAR(20) DEFAULT NULL,
   INDEX index1 (riqi,userno)
) ENGINE=INNODB DEFAULT CHARSET=utf8

sys_flowcount  保留
流量交易统计--> /rights/flowproportioncount.jsp 换成 /rights/flowcount.jsp

CREATE TABLE `sys_flowcountrecord` (
  `bishu` INT(11) NOT NULL,
  `state` INT(11) DEFAULT NULL,
  `buyid` VARCHAR(30) NOT NULL,
  `buyname` VARCHAR(50) NOT NULL,
  `sa_id` INT(11) DEFAULT NULL,
  `sa_name` VARCHAR(30) DEFAULT NULL,
  `exp1` VARCHAR(20) DEFAULT NULL,
  `exp2` VARCHAR(20) DEFAULT NULL,
  `exp3` VARCHAR(20) DEFAULT NULL,
  KEY `buyidindex` (`buyid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

------------20160104
直充接口：http://www.wanhuipay.com/PPOrderPay.do
状态查询接口：http://www.wanhuipay.com/PPOrdercheck.do

--------------------------------20160222
INSERT INTO sys_menu VALUE('0435','0101','移动省份流量佣金','','/employ/FlowAddYdong.jsp',NULL,NULL,353,3);
INSERT INTO sys_sm_power VALUES(1,'0435',1);

--------------20160329
INSERT INTO sys_menu VALUE('0588','0201','Q币直充','','/wlttencent/tenxunqbpay.jsp',NULL,NULL,353,3);
INSERT INTO sys_sm_power VALUES(1,'0588',1);


