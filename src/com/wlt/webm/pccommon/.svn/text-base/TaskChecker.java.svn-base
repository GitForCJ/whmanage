package com.wlt.webm.pccommon;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.message.MemcacheConfig;


/**
 * 任务日志检测类<br>
 */
public class TaskChecker
{
    /** 任务类型：数据库同步 */
    public static final TaskType SYNC = new TaskType("1", "数据库同步任务");
    /** 任务类型：结算 */
    public static final TaskType SETT = new TaskType("2", "结算任务");
    /** 任务类型：汇总 */
    public static final TaskType STAT = new TaskType("3", "汇总任务");
    /** 任务类型：对帐 */
    public static final TaskType CHECK = new TaskType("4", "对帐任务");
    /** 任务类型：计费汇总推送 */
    public static final TaskType PUSH = new TaskType("5", "计费汇总推送任务");
    /** 任务类型：计费资金账户对帐 */
    public static final TaskType FACCTCHECK = new TaskType("6", "计费资金账户对帐任务");
    /** 任务类型：建表 */
    public static final TaskType CREATETABLE = new TaskType("7", "建表任务");
    /** 用户充值是否需要交易密码*/
    public static HashMap<String, String> USEREXT3=new HashMap<String, String>();
    /**
     * 任务类型<br>
     * company 深圳市万恒科技有限公司<br>
     * copyright Copyright (c) 2008<br>
 * version 3.0.0.0
     * @author 鹿振
     */
    private static class TaskType
    {
        /**
         * 任务类型编号
         */
        public final String id;

        /**
         * 任务类型名称
         */
        public final String name;

        /**
         * 构造方法
         * @param id 任务类型编号
         * @param name 任务类型名称
         */
        public TaskType(String id, String name)
        {
            this.id = id;
            this.name = name;
        }
    }

    /**
     * 检测每月任务，并返回未完成任务的提示信息
     * @param taskType 任务类型
     * @param taskName 任务名称，即表名
     * @param date 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd","yyyy-MM","yyyyMM"
     * @return 任务未完成提示信息，若没有未完成的任务则返回null
     * @throws SQLException
     */
    public static String checkMonthTask(TaskType taskType, String taskName, String date) throws SQLException
    {
        return checkMonthTask(taskType, taskName, date, date);
    }

    /**
     * 检测每月任务，并返回未完成任务的提示信息
     * @param taskType 任务类型
     * @param taskName 任务名称数组，即表名
     * @param date 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd","yyyy-MM","yyyyMM"
     * @return 任务未完成提示信息，若没有未完成的任务则返回null
     * @throws SQLException
     */
    public static String checkMonthTask(TaskType taskType, String[] taskName, String date) throws SQLException
    {
        List allMonthList = getAllMonthList(date, date);
        for (int i = 0; i < taskName.length; i++)
        {
            List successMonthList = getSuccessMonthList(taskType.id, taskName[i], date, date);
            List failMonthList = getFailDateList(allMonthList, successMonthList);
            String msg = getMessage(taskType, taskName[i], failMonthList);
            if (msg != null)
            {
                return msg;
            }
        }
        return null;
    }

    /**
     * 检测每月任务，并返回未完成任务的提示信息
     * @param taskType 任务类型
     * @param taskName 任务名称，即表名
     * @param startDate 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd","yyyy-MM","yyyyMM"
     * @param endDate 结束日期，支持的格式同上
     * @return 任务未完成提示信息，若没有未完成的任务则返回null
     * @throws SQLException
     */
    public static String checkMonthTask(TaskType taskType, String taskName, String startDate, String endDate) throws SQLException
    {
        List allMonthList = getAllMonthList(startDate, endDate);
        List successMonthList = getSuccessMonthList(taskType.id, taskName, startDate, endDate);
        List failMonthList = getFailDateList(allMonthList, successMonthList);
        return getMessage(taskType, taskName, failMonthList);
    }

    /**
     * 检测每月任务，并返回未完成任务的提示信息
     * @param taskType 任务类型
     * @param taskName 任务名称数组，即表名
     * @param startDate 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd","yyyy-MM","yyyyMM"
     * @param endDate 结束日期，支持的格式同上
     * @return 任务未完成提示信息，若没有未完成的任务则返回null
     * @throws SQLException
     */
    public static String checkMonthTask(TaskType taskType, String[] taskName, String startDate, String endDate) throws SQLException
    {
        List allMonthList = getAllMonthList(startDate, endDate);
        for (int i = 0; i < taskName.length; i++)
        {
            List successMonthList = getSuccessMonthList(taskType.id, taskName[i], startDate, endDate);
            List failMonthList = getFailDateList(allMonthList, successMonthList);
            String msg = getMessage(taskType, taskName[i], failMonthList);
            if (msg != null)
            {
                return msg;
            }
        }
        return null;
    }

    /**
     * 检测每日任务，并返回未完成任务的提示信息
     * @param taskType 任务类型
     * @param taskName 任务名称，即表名
     * @param date 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd"
     * @return 任务未完成提示信息，若没有未完成的任务则返回null
     * @throws SQLException
     */
    public static String checkDayTask(TaskType taskType, String taskName, String date) throws SQLException
    {
        return checkDayTask(taskType, taskName, date, date);
    }

    /**
     * 检测每日任务，并返回未完成任务的提示信息
     * @param taskType 任务类型
     * @param taskName 任务名称数组，即表名
     * @param date 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd"
     * @return 任务未完成提示信息，若没有未完成的任务则返回null
     * @throws SQLException
     */
    public static String checkDayTask(TaskType taskType, String[] taskName, String date) throws SQLException
    {
        List allDayList = getAllDayList(date, date);
        for (int i = 0; i < taskName.length; i++)
        {
            List successDayList = getSuccessDayList(taskType.id, taskName[i], date, date);
            List failDayList = getFailDateList(allDayList, successDayList);
            String msg = getMessage(taskType, taskName[i], failDayList);
            if (msg != null)
            {
                return msg;
            }
        }
        return null;
    }

    /**
     * 检测每日任务，并返回未完成任务的提示信息
     * @param taskType 任务类型
     * @param taskName 任务名称，即表名
     * @param startDate 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd"
     * @param endDate 结束日期，支持的格式同上
     * @return 任务未完成提示信息，若没有未完成的任务则返回null
     * @throws SQLException
     */
    public static String checkDayTask(TaskType taskType, String taskName, String startDate, String endDate) throws SQLException
    {
        List allDayList = getAllDayList(startDate, endDate);
        List successDayList = getSuccessDayList(taskType.id, taskName, startDate, endDate);
        List failDayList = getFailDateList(allDayList, successDayList);
        return getMessage(taskType, taskName, failDayList);
    }

    /**
     * 检测每日任务，并返回未完成任务的提示信息
     * @param taskType 任务类型
     * @param taskName 任务名称数组，即表名
     * @param startDate 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd"
     * @param endDate 结束日期，支持的格式同上
     * @return 任务未完成提示信息，若没有未完成的任务则返回null
     * @throws SQLException
     */
    public static String checkDayTask(TaskType taskType, String[] taskName, String startDate, String endDate) throws SQLException
    {
        List allDayList = getAllDayList(startDate, endDate);
        for (int i = 0; i < taskName.length; i++)
        {
            List successDayList = getSuccessDayList(taskType.id, taskName[i], startDate, endDate);
            List failDayList = getFailDateList(allDayList, successDayList);
            String msg = getMessage(taskType, taskName[i], failDayList);
            if (msg != null)
            {
                return msg;
            }
        }
        return null;
    }

    /**
     * 根据表名和未完成建表的日期列表获得提示信息
     * @param taskType 任务类型
     * @param taskName 表名
     * @param failDateList 未完成建表的日期列表
     * @return 提示信息，若没有未完成的建表任务则返回null
     */
    private static String getMessage(TaskType taskType, String taskName, List failDateList)
    {
        //TODO 暂时不做任务检测
//        int failDateSize = failDateList.size();
//        if (failDateSize > 0)
//        {
//            StringBuffer msg = new StringBuffer(200);
//            msg.append("任务名为").append(taskName).append("的").append(taskType.name).append("在");
//
//            for (int i = 0; i < failDateSize; i++)
//            {
//                String date = (String) failDateList.get(i);
//                msg.append(date).append("、");
//            }
//
//            msg.deleteCharAt(msg.length() - 1);
//            msg.append("这些日期尚未完成。");
//
//            return msg.toString();
//        }
        return null;
    }

    /**
     * 根据表名和日期间隔获得已统计的实际日期列表，日期格式"yyyyMM"
     * @param taskType 任务类型
     * @param taskName 表名
     * @param startDate 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd","yyyy-MM","yyyyMM"
     * @param endDate 结束日期，支持的格式同上
     * @return List(String) 日期格式"yyyyMMdd"
     * @throws SQLException
     */
    private static List getSuccessMonthList(String taskType, String taskName, String startDate, String endDate) throws SQLException
    {
        List dayList = getSuccessDayList(taskType, taskName, startDate, endDate);
        List monthList = new ArrayList();
        for (int i = 0, ii = dayList.size(); i < ii; i++)
        {
            monthList.add(((String) dayList.get(i)).substring(0, 6)); 
        }
        return monthList;
    }

    /**
     * 根据任务类型、表名和日期间隔获得已完成任务的实际日期列表，日期格式"yyyyMMdd"
     * @param taskType 任务类型
     * @param taskName 表名
     * @param startDate 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd"
     * @param endDate 结束日期，支持的格式同上
     * @return List(String) 日期格式"yyyyMMdd"
     * @throws SQLException
     */
    private static List getSuccessDayList(String taskType, String taskName, String startDate, String endDate) throws SQLException
    {
        StringBuffer sql = new StringBuffer(300);
        sql.append("select shoulddate from ec_task a,ec_tasklog b")
                .append(" where a.name=b.taskname and a.type=").append(taskType)
                .append(" and a.name='").append(taskName).append("' and b.shoulddate>='")
                .append(toYYYYMMDD(startDate)).append("' and b.shoulddate<='")
                .append(toYYYYMMDD(endDate)).append("'");

        return DBToolSCP.getStringList(sql.toString());
    }

    /**
     * 根据统计日志的所有日期和已统计日期获得未统计的日期列表
     * @param allDateList 所有日期
     * @param successDateList 已统计成功日期
     * @return List(String)未统计的日期列表
     */
    private static List getFailDateList(List allDateList, List successDateList)
    {
        List failMonthList = new ArrayList(allDateList);
        failMonthList.removeAll(successDateList);
        return failMonthList;
    }

    /**
     * 获取两个日期之间所有的日期列表，日期格式"yyyyMM"
     * @param startDate 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd","yyyy-MM","yyyyMM"
     * @param endDate 结束日期，支持的格式同上
     * @return List(String) 日期格式"yyyyMM"
     */
    private static List getAllMonthList(String startDate, String endDate)
    {
        startDate = toYYYYMM(startDate);
        int startYear = Integer.parseInt(startDate.substring(0, 4));
        int startMonth = Integer.parseInt(startDate.substring(4, 6));

        endDate = toYYYYMM(endDate);
        int endYear = Integer.parseInt(endDate.substring(0, 4));
        int endMonth = Integer.parseInt(endDate.substring(4, 6));

        Calendar startCal = Calendar.getInstance();
        startCal.set(startYear, startMonth - 1, 1);

        Calendar endCal = Calendar.getInstance();
        endCal.set(endYear, endMonth - 1, 1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        ArrayList allDate = new ArrayList();
        while (!endCal.before(startCal))
        {
            allDate.add(sdf.format(startCal.getTime()));
            startCal.add(Calendar.MONTH, 1);// 起始日期＋1月
        }

        return allDate;
    }

    /**
     * 获取两个日期之间所有的日期列表，日期格式"yyyyMMdd"
     * @param startDate 起始日期，支持的格式有"yyyy-MM-dd","yyyyMMdd"
     * @param endDate 结束日期，支持的格式同上
     * @return List(String) 日期格式"yyyyMMdd"
     */
    private static List getAllDayList(String startDate, String endDate)
    {
        startDate = toYYYYMMDD(startDate);
        int startYear = Integer.parseInt(startDate.substring(0, 4));
        int startMonth = Integer.parseInt(startDate.substring(4, 6));
        int startDay  = Integer.parseInt(startDate.substring(6, 8));

        endDate = toYYYYMMDD(endDate);
        int endYear = Integer.parseInt(endDate.substring(0, 4));
        int endMonth = Integer.parseInt(endDate.substring(4, 6));
        int endDay = Integer.parseInt(endDate.substring(6, 8));

        Calendar startCal = Calendar.getInstance();
        startCal.set(startYear, startMonth - 1, startDay);

        Calendar endCal = Calendar.getInstance();
        endCal.set(endYear, endMonth - 1, endDay);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        ArrayList allDate = new ArrayList();
        while (!endCal.before(startCal))
        {
            allDate.add(sdf.format(startCal.getTime()));
            startCal.add(Calendar.MONTH, 1);// 起始日期＋1月
        }

        return allDate;
    }

    /**
     * <p>将传入的日期格转换成"yyyyMMdd"格式的日期格式。</p>
     * 转换规则示例：<br>
     * "2009-04-14" - "20090414"<br>
     * "20090414" - "20090414"<br>
     * "2009-04" - "20090401"<br>
     * "200904" - "20090401"<br>
     * @param date 支持的日期参数格式："yyyy-MM-dd","yyyyMMdd","yyyy-MM","yyyyMM"
     * @return 格式化后的日期
     */
    private static String toYYYYMMDD(String date)
    {
        if (date.matches("^\\d{4}-\\d{2}-\\d{2}$"))
        {
            return date.replaceAll("-", "");
        }
        else if (date.matches("^\\d{8}$"))
        {
            return date;
        }
        else if (date.matches("^\\d{4}-\\d{2}$"))
        {
            return date.replaceAll("-", "") + "01";
        }
        else if (date.matches("^\\d{6}$"))
        {
            return date + "01";
        }
        
        throw new IllegalArgumentException("不正确的日期格式：\"" + date + "\"");
    }

    /**
     * 根据传入的日期参数获得"yyMM"格式的日期
     * @param date 支持的日期参数有："yyyy-MM-dd","yyyyMMdd","yyyy-MM","yyyyMM"
     * @return "yyMM"格式的日期
     */
    private static String toYYYYMM(String date)
    {
        if (date.matches("^\\d{4}-\\d{2}-\\d{2}$") || date.matches("^\\d{4}-\\d{2}$"))
        {
            return date.substring(0, 4) + date.substring(5, 7);
        }
        else if (date.matches("^\\d{8}$") || date.matches("^\\d{6}$"))
        {
            return date.substring(0, 6);
        }

        throw new IllegalArgumentException("不正确的日期格式：\"" + date + "\"");
    }

    /**
     * 测试
     * @param args
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException
    {
        
    }

    
    /**
     * 加载 接口商信息
     */
    @SuppressWarnings("unchecked")
	public static void initAgentsign()
    {
    	DBService db = null;
		@SuppressWarnings("unused")
		ArrayList<String[]> result = null;
		try {
			db = new DBService();
			String sql = "SELECT s.userno,s.keyvalue,s.keyvalue1,s.ip,s.state,u.user_login,u.user_id," +
					" u.user_pass,u.trade_pass,u.user_site,u.user_city,u.user_status,a.sa_zone,f.fundacct,s.id,s.qbChannel,u.user_pt,s.qbCommission,s.caidan,s.groups,s.jfgroups,s.flowgroups,s.returnurl  " +
					" FROM sys_agentsign s,sys_user u,sys_area a,wht_facct f WHERE s.state=0 AND s.userno=u.user_no AND a.sa_id=u.user_city AND f.user_no=u.user_no";
			result = (ArrayList<String[]>) db.getList(sql);
			for(int i=0;i<result.size();i++)
			{
				String[] strobj=result.get(i);
				TPForm f=new TPForm();
				f.setUser_no(strobj[0]);
				f.setKeyvalue(strobj[1]);
				f.setKeyvalue1(strobj[2]);
				f.setIp(strobj[3]);
				f.setState(Integer.parseInt(strobj[4]));
				f.setUser_login(strobj[5]);
				f.setUser_id(Integer.parseInt(strobj[6]));
				f.setUser_pass(strobj[7]);
				f.setTrade_pass(strobj[8]);
				f.setUser_site(Integer.parseInt(strobj[9]));
				f.setUser_city(Integer.parseInt(strobj[10]));
				f.setUser_status(Integer.parseInt(strobj[11]));
				f.setSa_zone(strobj[12]);
				f.setFundacct(strobj[13]);
				f.setEmployID(strobj[14]);
				f.setQbFlag(strobj[15]);
				f.setUserPt(strobj[16]);
				f.setQbCommission(strobj[17]);
				f.setCaidan(strobj[18]);
				f.setGroups(strobj[19]);
				f.setJfgroups(strobj[20]);
				f.setFlowgroups(strobj[21]);
				f.setReturnurl(strobj[22]);
				MemcacheConfig.getInstance().delete(strobj[0]);
				MemcacheConfig.getInstance().addTP(strobj[0],f);
				Log.info("加载接口商信息,,,,,,key="+strobj[0]);
			}
			//加载拉卡拉密钥信息
			HashMap<String, String> keys=new HashMap<String, String>();
			ArrayList<String[]> a= (ArrayList<String[]>) db.getList("select * from wht_lakal_keys");
			for(String[] b:a){
				keys.put(b[0], b[1]);
			}
			MemcacheConfig.getInstance().delete("mposuuid");
			MemcacheConfig.getInstance().addMposUUID("mposuuid", keys);
			//加载用户是否开启充值输入密码功能
			String sql1 ="SELECT a.userno,a.ext3 FROM mac a LEFT JOIN sys_user b ON a.userno=b.user_no WHERE b.user_status=0";
			List lists=db.getList(sql1);
			if(null!=lists&&lists.size()>0){
				List<String[]> lis=(List<String[]>)lists;
				for(String[] str:lis){
					USEREXT3.put(str[0], str[1]);
				}
			}
			
			//加载广东省号段信息
			String sql2="SELECT phone_num,phone_type FROM sys_phone_area WHERE province_code=35";
			List listphone=db.getList(sql2);
			if(null!=listphone&&listphone.size()>0){
				HashMap<String, String> phones=new HashMap<String, String>();
				List<String[]> lis=(List<String[]>)listphone;
				for(String[] str:lis){
					phones.put(str[0],str[1]);
				}
				MemcacheConfig.getInstance().delete("phone_area");
				MemcacheConfig.getInstance().addMposUUID("phone_area", phones);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("加载接口商信息异常,,,,,,:" + e.toString());
		} finally {
			if (null != db) {
				db.close();
			}
		}
    }
}
