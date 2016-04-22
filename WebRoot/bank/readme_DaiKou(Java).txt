=======================================================
ChinaPay 代扣接口演示程序 (Java版) 使用说明
=======================================================
/**
 * @author 黄旭婷
 * 2012-04-18
 */
 
ChinaPay接口调用实例: 单笔代扣接口调用实例（Java版jdk1.6）使用说明
1、本实例主要实现单笔代扣、查询和白名单添加 3个接口的调用。
2、运行本实例前，请修改/src/res/cp_config.properties文件中相应的配置信息。
   需要修改的配置如下：
#MerchantID
chinapay.merid = 808080080988041

#MerchantKey FilePath
chinapay.merkey.filepath = F\:\\Workspace\\\u4EE3\u6263-key\\808080080988041-key\\MerPrk.key

#PublicKey FilePath
chinapay.pubkey.filepath = F\:\\Workspace\\\u4EE3\u6263-key\\808080080988041-key\\PgPubk.key

3、启动工程后，可访问诸如：http://localhost:8080//chinabank/index.jsp页面使用本实例。		
