--�û���
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


--�ʽ��˻�
CREATE TABLE `wht_facct` (
  `fundacct` varchar(15) NOT NULL,
  `accountleft` int(11) NOT NULL,
  `awardleft` bigint NOT NULL,
  `state` int NOT NULL,
  `user_no` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`fundacct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--�ʽ����˻�
CREATE TABLE `wht_childfacct` (
  `childfacct` varchar(17) NOT NULL,
  `fundacct` varchar(15) NOT NULL,
  `type`      varchar(2) NOT NULL,
  `accountleft` int(11) NOT NULL,
  `awardleft` int(11) NOT NULL,
  PRIMARY KEY (`childfacct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--�˻�����
CREATE TABLE `wht_faccttype` (
  `code` VARCHAR(2) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--�������� 
CREATE TABLE `sys_valueRange` (
  `cm_id` VARCHAR(12) NOT NULL,
  `min` int NOT NULL,
  `max` int NOT NULL,
  PRIMARY KEY (`cm_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--��ʡӶ���
CREATE TABLE `sys_employ0` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` int  NOT NULL,
  `TYPE` int NOT NULL,
  `cm_id` VARCHAR(12) NOT NULL,
  `VALUE` float(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--��ʡӶ���
CREATE TABLE `sys_employ1` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` int  NOT NULL,
  `TYPE` int NOT NULL,
  `cm_id` VARCHAR(12) NOT NULL,
  `VALUE` float(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--�ӿ���Ӷ���
CREATE TABLE `sys_tpemploy` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` int  NOT NULL,
  `TYPE` int NOT NULL,
  `cm_id` VARCHAR(12) NOT NULL,
  `VALUE` float(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8



--ֱӪӶ���
CREATE TABLE `sys_employ2` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` int  NOT NULL,
  `TYPE` int NOT NULL,
  `cm_id` VARCHAR(12) NOT NULL,
  `VALUE` float(5,2)  NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--��ֵҵ��Ӷ���
CREATE TABLE `sys_employ3` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(4) NOT NULL,
  `VALUE` float(5,2) NOT NULL,
  `flag` int  NOT NULL,  0��ֱӪ 1ֱӪ
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--�Ŷα�
CREATE TABLE `sys_phone_area` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `phone_num` VARCHAR(20) DEFAULT NULL,
  `province_code` INT(10) DEFAULT NULL,
  `cart_type` VARCHAR(50) DEFAULT NULL,
  `phone_type` INT(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=188181 DEFAULT CHARSET=utf8


--�˻���־��
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

--������
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


--��ֵҵ��Ӷ���
CREATE TABLE `sys_employ3` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `CODE` VARCHAR(4) NOT NULL,
  `VALUE` float NOT NULL,
  `flag` int  NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--ҵ�����ͱ�
CREATE TABLE `wht_service` (
  `code` VARCHAR(4) NOT NULL,
  `flag` int  NOT NULL,
  `name` VARCHAR(30) NOT NULL,
) ENGINE=INNODB DEFAULT CHARSET=utf8


--�ӿ���Ϣ��
CREATE TABLE `sys_interface` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(30) NOT NULL,
    `flag` INT NOT NULL,
  PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--�ӿ� ʡ�� ��Ӫ�̹�����
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

--�����̴����Ӷ����������
CREATE TABLE `wht_agentAnduser` (
  `userno` VARCHAR(20) NOT NULL,
  `parentid` INT(11) NOT NULL,
  `employ0id` INT  NULL, --��ֵҵ��Ӷ��id
  `employ3id` INT  NULL,--��ֵҵ��Ӷ��id
  `value` VARCHAR(12) NOT NULL,  --�������������
  `flag` int NOT NULL  --0��ֵҵ�� 1��ֵҵ�� 
) ENGINE=INNODB DEFAULT CHARSET=utf8

--�������ͱ�
CREATE TABLE `wht_acctradetype`(
  `code` int NOT NULL,
  `name` VARCHAR(30) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8

--һ������ƷƷ��ͬ����Ϣ��
CREATE TABLE `wht_brandTable` (
  `brandId` VARCHAR(20) NOT NULL,
  `brandName` VARCHAR(100) NOT NULL,
  `remark` VARCHAR(100) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8

--�Ƹ�ͨת�˱�
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

--Ź���û�����Ϣ
CREATE TABLE `sys_oufei` (
  `userno` varchar(50) NOT NULL, --�����ϵͳ���
  `fundacct` varchar(50) NOT NULL,--������ʽ��˺�
  `oufeiID` varchar(60) NOT NULL,--
  `oufeimoban` varchar(60) NOT NULL,
  `oufeisign` varchar(60) NOT NULL,
  `TIME` varchar(14) NOT NULL,
    `state` int NOT NULL,        -- 0���� --1������
      `isstart` int NOT NULL,   --0���� 1�ر�
  `userpid` int NOT NULL, --�û�ʡ�ݱ��
    `areacode` varchar(10) NOT NULL,
      `login` varchar(20) NOT NULL,
      `parentid` varchar(10) NOT NULL,--���ڵ�
   PRIMARY KEY (`userno`)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8

--С���Ӷ����
CREATE TABLE `sys_employ5` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` int  NOT NULL,
  `type` int NOT NULL,
  `cm_id` VARCHAR(20) NOT NULL,
  `value` int  NOT NULL,  --���׽��
  `value1` int  NOT NULL, --��Ӷ���
  `flag` int NOT NULL,   
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--Ź�ɿ���
CREATE TABLE `sys_oufeiswitch` (
  `userno` varchar(50) NOT NULL,
  `fundacct` varchar(50) NOT NULL,
  `oufeitype` VARCHAR(20)  -- 0 ���ѳ�ֵ  1 Q�ҳ�ֵ
  PRIMARY KEY (`userno`)
) ENGINE=INNODB DEFAULT CHARSET=utf8


--ϵͳ�ۿ�����ͳ�Ʊ�
CREATE TABLE `wht_acctout` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(20) NOT NULL,--ҵ������
  `buyid` int NOT NULL,--�ӿ�id
  `state` int NOT NULL,--״̬
  `total` int  NOT NULL, --����
  `totalmoney` int  NOT NULL,  --��Ԥ�ƽ��׽�� 
  `facctmoney` int NOT NULL,  --ʵ�ʽ��׽��
   `date` VARCHAR(8) NOT NULL,--����
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--ϵͳ�ӿ���������
CREATE TABLE `wht_acctin` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `code`  int NOT NULL,--ҵ������
  `total` int  NOT NULL, --����
  `totalmoney` int  NOT NULL,  --���׽�� 
   `date` VARCHAR(8) NOT NULL,--����
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--�����̿������Ʊ�
CREATE TABLE `wht_agentNum` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `agentnum`  int NOT NULL,--ҵ������
   `date` VARCHAR(8) NOT NULL,--����
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8
--�û���������¼��
CREATE TABLE `sys_userloginLimit` (
   `login` VARCHAR(20) NOT NULL, --��½�˺�
  `userno` VARCHAR(20) NOT NULL, --�û�ϵͳ���
  `time` VARCHAR(14) ,  --��һ�δ���ʱ��
  `errornum` INT , --�������Ĭ��Ϊ1 
  `total` INT , --����Ϊ7
  PRIMARY KEY (`login`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--�¶Ƚ�����ϸ�� concat(c.fundacct,'02')
CREATE TABLE `wht_monthawards` (
   `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(20) NOT NULL,-- ��½�˺�
  `userid` INT NOT NULL,-- �û�id
  `usertype` INT NOT NULL,-- �û�����
  `money` INT NOT NULL,-- �����ܶ�
  `state` INT NOT NULL,  -- ״̬ 0 �ѷ� 1δ��
  `statisticdate` VARCHAR(6) NOT NULL,-- ͳ�Ƶ��·� 
  `logdate` VARCHAR(14) NOT NULL,-- ��¼����ʱ��
  `operate` VARCHAR(14) ,-- ����ʱ�� 
  `operatewho` VARCHAR(20) ,-- ������ 
  `childfacct` VARCHAR(20) ,-- Ӷ���˺�
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--�¶Ƚ�����
CREATE TABLE `wht_awardsrule` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `usertype` int NOT NULL, -- �û�����
  `minmoney` int NOT NULL,--���ڵ��� ��Сֵ 
  `maxmoney` int NOT NULL, --С�� ���ֵ 
  `commissionrate` float(5,2)  NOT NULL,--��������
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--�ӿ���Ӷ���
CREATE TABLE `sys_tpemploy` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `pid` INT(11) NOT NULL,
  `TYPE` INT(11) NOT NULL,
  `cm_id` VARCHAR(12) NOT NULL,
  `value` FLOAT(5,2) DEFAULT NULL,
  `groups` int ,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8

--�ӿ�����Ϣ���ñ�

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

 --��������Ϣ��
CREATE TABLE `wht_netpay` (
  `userno` VARCHAR(20) NOT NULL,--�û�ϵͳ���
  `username`  VARCHAR(20) NOT NULL,--�ֿ�������
  `credentialType` VARCHAR(4),--֤������ 
  `credentialNum` VARCHAR(30), --֤������
  `bankID` VARCHAR(4) NOT NULL,--���б��
  `bankflag` int NOT NULL,--�����ͱ�ʾ 0��ǿ�  1����  2���ÿ�
  `banknum` VARCHAR(30) NOT NULL, --���п���
  `isbanding` int NOT NULL, --�Ƿ��Ѿ���   0δ��   1�Ѱ�
  `submit` VARCHAR(8) NOT NULL, --�ύ����
  `finish` VARCHAR(8), --������
  `ext1` VARCHAR(20),  --��չ
  `ext2` VARCHAR(20), --��չ
  primary key (userno)
) ENGINE=INNODB DEFAULT CHARSET=utf8

 --�����б�
CREATE TABLE `wht_uniontype` (
  `unionum` VARCHAR(20) NOT NULL,--���б��
  `username`  VARCHAR(20) NOT NULL--˵��
) ENGINE=INNODB DEFAULT CHARSET=utf8

 --�������б�  0��ǿ� 1���� 2���ÿ�
CREATE TABLE `wht_cardtype` (
  `cardnum` int NOT NULL,--�����ͱ�ʾ
  `cardname`  VARCHAR(20) NOT NULL --˵��
) ENGINE=INNODB DEFAULT CHARSET=utf8

 --������ͱ�
CREATE TABLE `wht_identitytype` (
  `cardnum` int NOT NULL,--�������
  `cardname`  VARCHAR(20) NOT NULL --˵��
) ENGINE=INNODB DEFAULT CHARSET=utf8

--һ��֧�����۰���Ϣ��
CREATE TABLE `wht_transactionrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userLogin` varchar(20) DEFAULT NULL,
  `userNumber` varchar(20) DEFAULT NULL,
  `orderNumber` varchar(20) DEFAULT NULL,
  `recordMoney` varchar(20) DEFAULT NULL,
  `bankacct` varchar(64) DEFAULT NULL,
  `payType` int(11) DEFAULT NULL,
  `recordStatus` int(11) DEFAULT NULL,--0 ��ʾ ������ 1 ��ʾ�ɹ� 2��ʾʧ��
  `recordTime` varchar(14) DEFAULT NULL,
  `finishTime` varchar(14) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8

/*���д�����*/
CREATE TABLE wht_BankCode
(
 BankCodeId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
 BankCodeNumber VARCHAR(20),
 BankCodeName VARCHAR(100)
)ENGINE=INNODB DEFAULT CHARSET=utf8
/* ������� */
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

/* ֤����*/
CREATE TABLE wht_Papers_Type
(
Papers_TypeId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
Papers_TypeNumber VARCHAR(10),
Papers_TypeName VARCHAR(50)
)ENGINE=INNODB DEFAULT CHARSET=utf8

==������
CREATE TABLE `mac` (
  `id` INT(11) NOT NULL AUTO_INCREMENT, --����
  `userno` VARCHAR(20) DEFAULT NULL,  --�û�ϵͳ���
  `macaddress` VARCHAR(30) DEFAULT NULL, --mac
  `googlesign` VARCHAR(30) DEFAULT NULL, --�����֤��Կ
  `twodimensionalcode` TEXT  DEFAULT NULL, --�����֤��ά��
  `ext1` VARCHAR(30) DEFAULT NULL, --��չ
  `ext2` VARCHAR(30) DEFAULT NULL, --��չ
  `ext3` VARCHAR(30) DEFAULT NULL,--��չ
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
ALTER TABLE mac MODIFY ext1 VARCHAR(300) 

--��������
CREATE TABLE `wht_datelimit` (
  `date` VARCHAR(8)  UNICODE  NOT NULL, 
  `ext1` VARCHAR(30) DEFAULT NULL,  
  `ext2` VARCHAR(30) DEFAULT NULL, 
  PRIMARY KEY (`date`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

INSERT INTO sys_menu VALUE('0132','0101','��������','','/business/whtdatelimitlist.jsp',NULL,NULL,316,3);
INSERT INTO sys_sm_power VALUES(1,'0132',1);


--������ת����Ϣ��¼��
CREATE TABLE `wht_lakal_record` (
  `id` INT(12) NOT NULL AUTO_INCREMENT,
  `txncod` VARCHAR(20) NOT NULL,--������
  `requestId` VARCHAR(20) NOT NULL,--����
  `mercId` VARCHAR(20) NOT NULL,--�̻���
  `termId` VARCHAR(20) NOT NULL,--�ն˱��
  `refNumber` varchar(18) not  NULL,--ϵͳ�ο���
  `orgRefNum` VARCHAR(18) default NULL,--ԭϵͳ�ο��� 
  `orderID` VARCHAR(18) NOT NULL,--������ 
  `amount` bigint NOT NULL, --��� 
  `ransTime` VARCHAR(18) NOt NULL,--���״���ʱ��
  `orderSta` varchar(2) NULL,--����������״̬
  `payType`  varchar(2) NOT NULL,--֧������
  `extData` varchar(50) DEFAULT NULL,
  `isbinding` int  NOT NULL, --�ն˱�������ͨƽ̨�Ƿ�� 0δ�� 1�Ѿ���
  `tradetime` VARCHAR(14) NOT NULL,--���ͨƽ̨����ʱ��
  `userno` VARCHAR(10) DEFAULT NULL,--���ͨϵͳ���
  `fundacct` VARCHAR(16) DEFAULT NULL,--���ͨ�ʽ��˺�
  `userlogin` varchar(11) DEFAULT NULL,--���ͨƽ̨��¼�˺�
  `fee` bigint NOT NULL, --ʵ�ʽ��׽�� 
  `state` INT(12) NOT NULL, --0 δ�ӿ�  1 �Ѽӿ�  2������  3��ʱ 4 �ѳ���
  `typeT` varchar(4) ,-- T0,T1
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
   PRIMARY KEY (id),
   INDEX three(termId,refNumber)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

--��������
CREATE TABLE `sys_lkal` (

  `termid` VARCHAR(20) NOT NULL,--�ն˱��
  `userno` VARCHAR(10) NOT NULL,--���ͨϵͳ���
  `fundacct` VARCHAR(16) NOT NULL,--���ͨ�ʽ��˺�
  `userlogin` VARCHAR(11) NOT NULL,--���ͨƽ̨��¼�˺�
  `time` VARCHAR(14) NOT NULL,--�󶨵�ʱ��
  `type` int NOT NULL,--������ 0 ������
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
  unique index one(termid),
  index two(userno,time)
) ENGINE=INNODB DEFAULT CHARSET=utf8

select userno,fundacct,userlogin from sys_lkal where type=0 and termid=


#==8.30 �޸ı��ֶ�Ϊbigint
ALTER TABLE wht_acctbill_1408 MODIFY  accountleft BIGINT  infacctfee accountleft
---------------------------------------------20140906
--������
CREATE TABLE `sys_setupgroups` (
  `groupsID` int not null AUTO_INCREMENT,--��id
  `typenum`  int  NOT NULL,--�����ͱ�� 0 һ�������̽���
  `groupname` VARCHAR(50) NOT NULL,--������
  `isdefaut` int  NOT NULL,--�Ƿ�Ĭ��  0 ��Ĭ��  1 Ĭ��
  `time` VARCHAR(14) NOT NULL,--���ʱ��
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
  PRIMARY KEY (`groupsID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8

--��������
CREATE TABLE `sys_oneagent` (
  `setupID` int NOT NULL AUTO_INCREMENT,--���ݱ�ʾ
  `groupsID` int NOT NULL,--����
  `tradetype` VARCHAR(4) NOT NULL,--��������
  `monbegin` int NOT NULL,--�����ʼֵ
  `monend` int  NOT NULL,--������ֵ
  `percent` VARCHAR(20) NOT NULL,--Ӷ����� %
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  PRIMARY KEY (`setupID`),
  INDEX agentsetup (groupsID),
  FOREIGN KEY (groupsID) REFERENCES sys_setupgroups(groupsID) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8


----һ�������̽���ӳ���
CREATE TABLE `sys_oneagentmaps` (
  `groupsID` int NOT NULL  ,--�������ʾ
  `userno` VARCHAR(20) NOT NULL,--���ͨϵͳ���
   FOREIGN KEY (groupsID) REFERENCES sys_setupgroups(groupsID) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO sys_menu VALUE('0705','0701','��������','','/rights/autouser.jsp',NULL,NULL,328,3);
INSERT INTO sys_sm_power VALUES(1,'0705',1);
-------------------
ALTER TABLE wht_facct ADD CONSTRAINT con1 
FOREIGN KEY (user_no) REFERENCES sys_user(user_no) ON DELETE CASCADE

ALTER TABLE wht_facct ADD INDEX index11(user_no)����� ����������
-------------
ALTER TABLE wht_childfacct ADD CONSTRAINT con2 
FOREIGN KEY (fundacct) REFERENCES wht_facct(fundacct) ON DELETE CASCADE
--��������ֵ��
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
tradetype     0 ��������  1 Q��ÿ���޶Ԫ��  2֧����ÿ���޶Ԫ�� 3 ���ת�ƣ�0 ��ʾ��Ȩ�� 1��ʾ��Ȩ�ޣ� 4 �Ƿ����ö�ͨ��(1 ����  0���� 2������ʱ����)
============10.11


===============10.17
--��ͨ�������
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

--��ͨ����Υ����Ϣ��
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

--���۽����û�
#�˱��е��û���ʾ ���ս���
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

--�㶫���ŵ����˵��ӿ�����
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
   `tradeserial` VARCHAR(50) NOT NULL,--��ˮ��
   `tradeobject` VARCHAR(30) NOT NULL,--���׺���
   `buyid` VARCHAR(30) DEFAULT NULL,--�ӿڱ��
   `service` VARCHAR(20) DEFAULT NULL,--�������ͣ�0005��
   `fee` INT(11) NOT NULL,--��ֵ ��
   `tradefee` INT(11) NOT NULL,--���׽�� ��
   `tradetime` VARCHAR(14) NOT NULL, --����ʱ��
   `chgtime` VARCHAR(50) NOT NULL,--״̬���ʱ��
   `state` INT(11) NOT NULL,--״̬  ͬ������
   `writecheck` VARCHAR(100) DEFAULT NULL,--������������ ��ʱ���� ��ˮ��+��ֵ
   `userno` VARCHAR(20) DEFAULT NULL,--�û�ϵͳ���
   `oldorderid` VARCHAR(50) DEFAULT NULL,--�����Ŷ�Ӧ������
   `bak1` VARCHAR(20) DEFAULT NULL,--����
   `bak2` VARCHAR(20) DEFAULT NULL,--����
   `bak3` VARCHAR(20) DEFAULT NULL,--����
   `bak4` VARCHAR(20) DEFAULT NULL,--����
  PRIMARY KEY (`id`),
  KEY `order_index` (`oldorderid`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

-------------
CREATE TABLE `sys_oufei_qb` (
  `userno` VARCHAR(50) PRIMARY KEY NOT NULL, --�����ϵͳ���
  `fundacct` VARCHAR(50) NOT NULL,--������ʽ��˺�
  `oufeiID` VARCHAR(60) NOT NULL,--
  `oufeimoban` VARCHAR(60) NOT NULL,
  `oufeisign` VARCHAR(60) NOT NULL,
  `TIME` VARCHAR(14) NOT NULL,
    `state` INT NOT NULL,        -- 0���� --1������
      `isstart` INT NOT NULL,   --0���� 1�ر�
  `userpid` INT NOT NULL, --�û�ʡ�ݱ��
    `areacode` VARCHAR(10) NOT NULL,
      `login` VARCHAR(20) NOT NULL,
      `parentid` VARCHAR(10) NOT NULL,--���ڵ�
      `oufeitype` INT(11) DEFAULT NULL, -- 1 Q�ҹ���
   exp1 VARCHAR(30),
   exp2 VARCHAR(30)
) ENGINE=INNODB DEFAULT CHARSET=utf8

 
ALTER TABLE sys_oufeiswitch ADD COLUMN oufeitype VARCHAR(20)  -- 0 ���ѳ�ֵ  1 Q�ҳ�ֵ
ALTER TABLE sys_agentsign ADD COLUMN qbCommission FLOAT(5,2)
ALTER TABLE sys_agentsign ADD COLUMN caidan VARCHAR(20) -- 0��  1����

INSERT INTO sys_menu VALUE('0524','0501','Q�Ҳ�','','/business/qbOrderAffiliated.jsp',NULL,NULL,336,3);
INSERT INTO sys_sm_power VALUES(1,'0524',1);

--=============================0513
--app�û���Ϣ��
CREATE TABLE `wht_app_user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,--id
  `user_login` VARCHAR(50) NOT NULL,--��½��
  `user_pass` VARCHAR(20) NOT NULL,--����
  `user_createdate` VARCHAR(14) NOT NULL,--ע������
  `user_state` INT DEFAULT 0 NOT NULL,--״̬ 0 ����  1 ��ͣ  2 ע��
  `url_id` INT NOT NULL NOT NULL,--url id
  `name` VARCHAR(30),--����
  `phone` VARCHAR(30),--�ֻ���
  `qq` VARCHAR(30),--qq��
  `telphone` VARCHAR(30),--����  
  `msn` VARCHAR(30),  --msn
  `email` VARCHAR(30),--����
  `company` VARCHAR(30),--��˾����
  `position` VARCHAR(30),--ְλ
  `address` VARCHAR(100),--��˾��ַ
  `fax` VARCHAR(30),--����
  `website` VARCHAR(50),--��˾����
  `note` VARCHAR(50),--��ע
  `exp1` VARCHAR(20),
  `exp2` VARCHAR(20),
  `exp3` VARCHAR(20),
  `exp4` VARCHAR(20),
  `exp5` VARCHAR(20),
  `exp6` VARCHAR(20),
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
 
 --app ��ά���
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
--������Ʒ��
 CREATE TABLE `wht_flowproduct` (
  `product_id` varchar(30) NOT NULL,--��Ʒ���
  `PRODUCT_NAME` VARCHAR(20) NOT NULL,--��Ʒ����
  `PRICE_NUM` INT NOT NULL,--��Ʒ�ʷѣ�Ԫ��
  `PRO_NUM`INT NOT NULL,--�ɵ��Ӵ���
  `exp1` VARCHAR(20),--��չ
  `exp2` VARCHAR(20),--��չ
  `exp3` VARCHAR(20),--��չ
  `exp4` VARCHAR(20),--��չ
  PRIMARY KEY (`product_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

 CREATE TABLE `wht_flowprice` (
  `id` varchar(30) NOT NULL AUTO_INCREMENT,--��Ʒ���
  `name` VARCHAR(10) NOT NULL,--��Ʒ����
  `price` decimal(5,2) NOT NULL,--��Ʒ�۸�Ԫ��
  `type` INT NOT NULL,--��Ʒ���� 0 ���� 1�ƶ� 2��ͨ
  `exp1` VARCHAR(20),--��չ
  `exp2` VARCHAR(20),--��չ
  `exp3` VARCHAR(20),--��չ
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
--�����ӿ�������Ӧ��
CREATE TABLE `sys_interface2face` (
  `interfaceID` INT(4) NOT NULL,--�����ӿ�
  `flowface` INT(4)  NOT NULL,--�������  ���� 20 30 50 100 200 
  `exp1` VARCHAR(20) DEFAULT NULL,
  `exp2` VARCHAR(20) DEFAULT NULL,
  `exp3` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY(interfaceID,flowface)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

10 30 70 150 500 1024 2048 3072 4096 6144 11264

50 200

10 30 100 200 500

--�������˲Ƹ�ͨת���
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

--���ӻص���ַ
ALTER TABLE sys_agentsign ADD COLUMN returnurl VARCHAR(100) 

 --�ص�id��¼��
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
  `pid` INT  NOT NULL,--ʡ��id
  `TYPE` INT NOT NULL,--��Ӫ������
  `interid` INT NOT NULL,--�ӿ�id
   exp1 VARCHAR(20) ,
   exp2 VARCHAR(20) ,
  INDEX towindex(pid)
) ENGINE=INNODB DEFAULT CHARSET=utf8

�����ӿ����ò˵� /rights/flowconfig.jsp ��Ϊ  /business/prod.do?method=flowinterList&type=0
�������ӿ� flag��Ϊ2 
ALTER TABLE wht_orderform_1511 ADD INDEX buyidindex(buyid)
--����ͳ�Ƽ�¼��
CREATE TABLE sys_flowcount(
`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
 riqi VARCHAR(8) NOT NULL, --����
 counttime VARCHAR(14) NOT NULL,--���ʱ��
 state INT NOT NULL, --״̬
    exp1 VARCHAR(20),
    exp2 VARCHAR(20),
    exp3 VARCHAR(20),
 PRIMARY KEY (riqi)
)ENGINE = INNODB DEFAULT CHARSET = utf8

-- ����ͳ����ϸ
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

  
  
INSERT INTO sys_menu VALUE('0530','0501','��������ͳ��','','/rights/flowproportioncount.jsp',NULL,NULL,350,3);
INSERT INTO sys_sm_power VALUES(1,'0530',1);


--------------20151201
--ͳ����������ʱ���
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

sys_flowcount  ����
��������ͳ��--> /rights/flowproportioncount.jsp ���� /rights/flowcount.jsp

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
ֱ��ӿڣ�http://www.wanhuipay.com/PPOrderPay.do
״̬��ѯ�ӿڣ�http://www.wanhuipay.com/PPOrdercheck.do

--------------------------------20160222
INSERT INTO sys_menu VALUE('0435','0101','�ƶ�ʡ������Ӷ��','','/employ/FlowAddYdong.jsp',NULL,NULL,353,3);
INSERT INTO sys_sm_power VALUES(1,'0435',1);

--------------20160329
INSERT INTO sys_menu VALUE('0588','0201','Q��ֱ��','','/wlttencent/tenxunqbpay.jsp',NULL,NULL,353,3);
INSERT INTO sys_sm_power VALUES(1,'0588',1);


