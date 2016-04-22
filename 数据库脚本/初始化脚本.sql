--账户数据
INSERT INTO wht_faccttype VALUES('01','资金账户');
INSERT INTO wht_faccttype VALUES('02','佣金账户');
INSERT INTO wht_faccttype VALUES('03','冻结账户');

--交易类型
INSERT INTO wht_acctradetype VALUES(0,'广东移动');
INSERT INTO wht_acctradetype VALUES(1,'广东电信');
INSERT INTO wht_acctradetype VALUES(2,'广东联通');
INSERT INTO wht_acctradetype VALUES(3,'全国电信');
INSERT INTO wht_acctradetype VALUES(4,'全国移动');
INSERT INTO wht_acctradetype VALUES(5,'全国联通');
INSERT INTO wht_acctradetype VALUES(6,'Q币');
INSERT INTO wht_acctradetype VALUES(7,'游戏点卡');
INSERT INTO wht_acctradetype VALUES(8,'交通罚款');
INSERT INTO wht_acctradetype VALUES(9,'管理员提现');
INSERT INTO wht_acctradetype VALUES(10,'管理员充值');
INSERT INTO wht_acctradetype VALUES(11,'额度转移');
INSERT INTO wht_acctradetype VALUES(12,'佣金转押金');
INSERT INTO wht_acctradetype VALUES(13,'欧飞供货');
INSERT INTO wht_acctradetype VALUES(14,'航空票务');
INSERT INTO wht_acctradetype VALUES(15,'下级交易提成');
INSERT INTO wht_acctradetype VALUES(16,'财付通转账');
INSERT INTO wht_acctradetype VALUES(17,'慧付款');

--超级管理员
INSERT INTO sys_user VALUES(NULL,'0000000001','admin','21232f297a57a5a743894a0e4a801fc3',
'21232f297a57a5a743894a0e4a801fc3','1','老虎',0,0,NULL,NULL,'20131115000000',0,'18682000000',
'竹子林','wh@126.com',1,1,NULL,NULL,NULL);


INSERT INTO wht_BankCard_Type(BandCardTypeNumber,BandCardTypeName) VALUES('1','借记卡');
INSERT INTO wht_BankCard_Type(BandCardTypeNumber,BandCardTypeName) VALUES('2','信用卡');
INSERT INTO wht_BankCard_Type(BandCardTypeNumber,BandCardTypeName) VALUES('4','存折');
INSERT INTO wht_BankCard_Type(BandCardTypeNumber,BandCardTypeName) VALUES('8','公司账户');

INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('00','身份证');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('01','护照');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('02','军人证');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('03','户口本');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('06','港澳通行证');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('08','学生证');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('09','工作证');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('10','工商执照');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('11','警官证');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('12','事业单位编码');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('13','房产证');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('51','组织机构代码');
INSERT INTO wht_Papers_Type(Papers_TypeNumber,Papers_TypeName) VALUES('99','其他证件');

INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('110000','北京');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('120000','天津');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('130000','河北');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('140000','山西');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('150000','内蒙');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('210000','辽宁');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('220000','吉林');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('230000','黑龙');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('310000','上海');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('320000','江苏');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('330000','浙江');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('340000','安徽');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('350000','福建');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('360000','江西');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('370000','山东');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('410000','河南');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('420000','湖北');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('430000','湖南');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('440000','广东');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('450000','广西');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('460000','海南');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('500000','重庆');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('510000','四川');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('520000','贵州');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('530000','云南');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('540000','西藏');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('610000','陕西');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('620000','甘肃');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('630000','青海');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('640000','宁夏');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('650000','新疆');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('710000','台湾');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('810000','香港');
INSERT INTO wht_RegionalCode(RegionalCodeNumber,RegionalCodeName) VALUES('820000','澳门');

INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('860000','中国人民银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('866000','中国邮政储蓄银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('866100','中国银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('866200','中国工商银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('866300','中国农业银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('866400','交通银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('866500','中国建设银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('866600','中国民生银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('866700','广州农商');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('866800','广发银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('866900','招商银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('867000','广州银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('867100','上海浦发银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('867200','光大银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('867300','广东省农信');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('867400','中信银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('867600','兴业银行');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('867800','东莞农商');
INSERT INTO wht_BankCode(BankCodeNumber,BankCodeName) VALUES('867900','东莞银行');


INSERT INTO sys_menu VALUE('0125','0101','代收验证','','/AccountInfo/wltAccountVerify.jsp',NULL,NULL,228,3);
INSERT INTO sys_sm_power VALUES(1,'0125',1);

INSERT INTO sys_menu VALUE('0412','0401','代扣绑定','','/AccountInfo/showAccountInfo.do?method=showAccountInfo',NULL,NULL,229,3);
INSERT INTO sys_sm_power VALUES(1,'0412',1);

INSERT INTO sys_menu VALUE('0413','0401','银行代扣','','/AccountInfo/TransferAccounts.do?method=showTransferAccounts',NULL,NULL,230,3);
INSERT INTO sys_sm_power VALUES(1,'0413',1);



------------------20141110
INSERT INTO sys_menu VALUE('0425','0101','附属油站','','/rights/oilstationmain.jsp',NULL,NULL,331,3);
INSERT INTO sys_sm_power VALUES(1,'0425',1);

INSERT INTO sys_menu VALUE('0523','0501','附属油站充值统计','','/business/oilstatisticsOrder.jsp',NULL,NULL,332,3);
INSERT INTO sys_sm_power VALUES(1,'0523',1);

INSERT INTO sys_menu VALUE('0426','0101','交罚配置','','/rights/reversal.do?method=jtfklist&flag=0',NULL,NULL,333,3);
INSERT INTO sys_sm_power VALUES(1,'0426',1);


========================20150206
==会付款转款信息表
CREATE TABLE sys_hfk_record
(
 id INT(11) NOT NULL AUTO_INCREMENT,
 phone     VARCHAR(20),
 userno    VARCHAR(10),
 facct     VARCHAR(15),
 hfknum    VARCHAR(30) NOT NULL,
 ournum    VARCHAR(30) NOT NULL,
 tradefee  BIGINT(20) NOT NULL,
 fee       BIGINT(20) NOT NULL,
 tradefirst VARCHAR(14) ,
 tradetime VARCHAR(14) NOT NULL,
 feetime VARCHAR(14),
 cardtype CHAR(1) ,
 state CHAR(1) NOT NULL,
 descript  VARCHAR(10) ,
 exp1  VARCHAR(30) ,
 exp2  VARCHAR(30) ,
 exp3  VARCHAR(30) ,
 PRIMARY KEY (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8

----------------20150303
CREATE TABLE `sys_gddxinterfacemaping` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` INT(11) NOT NULL,
  `TYPE` INT(11) NOT NULL,
  `interid` INT(11) NOT NULL,
  `cmid` VARCHAR(20) DEFAULT NULL,
  `reserve` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8



SELECT * FROM sys_tpemploy WHERE groups=15 AND cm_id IN('wh1020','wh2030')

SELECT id,userno,interName FROM sys_agentsign WHERE userno='0001471350'

--20150325 

ALTER TABLE sys_oufei ADD oufeitype INT  增加欧飞Q币供货  取消

--20150421
#补齐代理商之前所开代办点的默认讯源君宝的佣金比
INSERT INTO wht_agentAnduser  
SELECT user_no,user_pt,NULL,25,88.0,1 FROM sys_user WHERE user_pt 
IN (SELECT user_id FROM sys_user WHERE user_role IN (SELECT sr_id FROM sys_role WHERE sr_type=2))

SELECT * FROM sys_user WHERE user_pt=203

根据综合业务id区分默认百分比
--升级内容
1、接口商佣金调整为组的形式
2、增加默认区号追加，修改功能
3、代理商可以配置广东电信单独业务的佣金比例
4、君宝接口账单查询区分综合、单一不同类型
5、交通罚款佣金组


--app用户信息表   20150513
CREATE TABLE `wht_aap_user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_login` VARCHAR(50) NOT NULL,
  `user_pass` VARCHAR(20) NOT NULL,
  `user_createdate` VARCHAR(14) NOT NULL,
  `user_state` INT DEFAULT 0 NOT NULL,
  `url_id` INT NOT NULL NOT NULL,
  `name` VARCHAR(30),
  `phone` VARCHAR(30),
  `qq` VARCHAR(30),
  `telphone` VARCHAR(30),  
  `msn` VARCHAR(30),  
  `email` VARCHAR(30),
  `company` VARCHAR(30),
  `position` VARCHAR(30),
  `address` VARCHAR(100),
  `fax` VARCHAR(30),
  `website` VARCHAR(50),
  `note` VARCHAR(50),
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
  `exp4` VARCHAR(20),
  `exp5` VARCHAR(20),
  `exp6` VARCHAR(20),
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8


--智能名片二维码表
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

-----------------------
INSERT INTO sys_menu VALUE('0307','0201','联通流量充值','','/business/unicomflowcharge.jsp',NULL,NULL,340,3);
INSERT INTO sys_sm_power VALUES(1,'0307',1);


INSERT INTO sys_menu VALUE('0431','0101','流量产品价格','','/rights/reversal.do?method=flowlist',NULL,NULL,342,3);
INSERT INTO sys_sm_power VALUES(1,'0431',1);


--------------------2015-07-03
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('21','50',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('21','200',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('23','20',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('23','50',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('23','100',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('23','200',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('23','500',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('25','10',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('25','30',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('25','70',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('25','150',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('25','500',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('25','1024',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('25','2048',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('25','4096',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('25','6144',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('25','11264',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('26','50',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('26','200',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('27','10',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('27','30',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('27','100',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('27','200',NULL,NULL,NULL);
insert into `sys_interface2face` (`interfaceID`, `flowface`, `exp1`, `exp2`, `exp3`) values('27','500',NULL,NULL,NULL);


SELECT DISTINCT flow_interId FROM sys_Flow 

SELECT * FROM sys_interface  WHERE flag=2                21   27  35  33

 29  1  31 0  32  1 36 2 37  0  38 1  39  2
 
 SELECT * FROM sys_Flow WHERE flow_interId=29
 
 insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('3','2','21','20',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('4','2','21','50',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('5','2','21','100',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('6','2','21','200',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('7','2','21','500',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('14','0','27','5',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('15','0','27','10',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('16','0','27','30',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('17','0','27','100',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('18','0','27','200',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('19','0','27','500',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('20','0','27','1000',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('21','1','35','10',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('22','1','35','30',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('23','1','35','70',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('24','1','35','150',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('25','1','35','500',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('26','1','35','1024',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('27','1','35','2048',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('28','2','33','20',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('29','2','33','50',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('30','2','33','100',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('31','2','33','200',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('32','2','33','500',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('33','1','29','20',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('34','1','29','50',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('35','1','29','100',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('36','1','29','200',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('37','1','29','500',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('38','0','31','20',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('39','1','32','20',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('40','2','36','20',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('41','0','37','20',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('42','1','38','20',NULL,NULL);
insert into `sys_Flow` (`id`, `operator_type`, `flow_interId`, `exp1`, `exp2`, `exp3`) values('43','2','39','20',NULL,NULL);
 
