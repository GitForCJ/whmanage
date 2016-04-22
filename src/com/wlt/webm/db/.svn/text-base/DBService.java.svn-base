package com.wlt.webm.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.util.Pagination;

/**
 * company 深圳市万恒科技有限公司<br>
 */
public class DBService
{
    /**
     * 查询结果集中最后一列的标志
     */
    private static final int END_COL = -1;

    /**
     * 数据库连接
     */
    private Connection conn;

    /**
     * 数据库分页信息
     */
    private Pagination pagination;

    /**
     * 构造一个自动提交事务的SMP数据库操作封装类。
     * @throws SQLException
     */
    public DBService() throws SQLException
    {
        this(Constants.DBNAME_SCP);
    }

    /**
     * 根据数据库名构造一个自动提交事务的数据库操作封装类。
     * @param dbName 数据库名称（Constants.DBNAME_SMP or Constants.DBNAME_SCP）
     * @throws SQLException
     */
    public DBService(String dbName) throws SQLException
    {
        this.conn = ConnectionFactory.getConnection(dbName);
    }

    /**
     * 根据SQL查询数据库，获得一个String元素的列表。
     * @param sql SQL查询语句
     * @return List(String)
     * @throws SQLException
     */
    public List getStringList(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int columnCount = rs.getMetaData().getColumnCount();
            List list = new ArrayList();

            while (rs.next())
            {
                for (int i = 1; i <= columnCount; i++)
                {
                    list.add(formatNull(rs.getString(i)));
                }
            }
            return list;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 利用预编译SQL语句查询数据库，获得元素为String的列表。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return List(String)元素为String的列表
     * @throws SQLException
     */
    public List getStringList(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);

            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            rs = stmt.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            List list = new ArrayList();

            while (rs.next())
            {
                for (int i = 1; i <= columnCount; i++)
                {
                    list.add(formatNull(rs.getString(i)));
                }
            }
            return list;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据SQL语句查询数据库，并从结果集的起始列至终止列获得元素为String[]的列表。
     * @param sql SQL查询语句
     * @return List(String[])元素为String[]的列表
     * @throws SQLException
     */
    public List getList(String sql) throws SQLException
    {
        return getList(sql, 0, END_COL);
    }

    /**
     * 根据SQL语句查询数据库，并从结果集的指定起始列至终止列获得元素为String[]的列表。
     * @param sql SQL查询语句
     * @param startCol 获取结果集的起始列，从0开始（包含起始列）。
     * @return List(String[])元素为String[]的列表
     * @throws SQLException
     */
    public List getList(String sql, int startCol) throws SQLException
    {
        return getList(sql, startCol, END_COL);
    }

    /**
     * 根据SQL语句查询数据库，并从结果集的指定起始列至指定终止列获得元素为String[]的列表。
     * @param sql SQL查询语句
     * @param startCol 获取结果集的起始列，从0开始（包含起始列）。
     * @param endCol 获取结果集的终止列（不包含终止列），如果为<code>END_COL</code>，则获得从起始列开始往后所有的列。
     * @return List(String[])元素为String[]的列表
     * @throws SQLException
     */
    public List getList(String sql, int startCol, int endCol) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (endCol == END_COL)
            {
                endCol = rs.getMetaData().getColumnCount();
            }

            int len = endCol - startCol;

            List list = new ArrayList();
            while (rs.next())
            {
                String[] info = new String[len];
                for (int i = 0, j = startCol; j < endCol; i++, j++)
                {
                    info[i] = formatNull(rs.getString(j + 1));
                    
                }
                list.add(info);
            }
            return list;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 利用预编译SQL语句查询数据库，并从结果集的起始列至终止列获得元素为String[]的列表。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return List(String[])元素为String数组的列表
     * @throws SQLException
     */
    public List getList(String sql, Object[] params) throws SQLException
    {
        return getList(sql, params, 0, END_COL);
    }

    /**
     * 利用预编译SQL语句查询数据库，并从结果集的指定起始列至终止列获得元素为String[]的列表。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @param startCol 获取结果集的起始列，从0开始（包含起始列）。
     * @return List(String[])元素为String数组的列表
     * @throws SQLException
     */
    public List getList(String sql, Object[] params, int startCol) throws SQLException
    {
        return getList(sql, params, startCol, END_COL);
    }

    /**
     * 利用预编译SQL语句查询数据库，并从结果集的起始列至终止列获得元素为String[]的列表。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @param startCol 获取结果集的起始列，从0开始（包含起始列）。
     * @param endCol 获取结果集的终止列（不包含终止列），如果为<code>END_COL</code>，则获得从起始列开始往后所有的列。
     * @return List(String[])元素为String数组的列表
     * @throws SQLException
     */
    public List getList(String sql, Object[] params, int startCol, int endCol) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);

            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            rs = stmt.executeQuery();

            if (endCol == END_COL)
            {
                endCol = rs.getMetaData().getColumnCount();
            }

            int len = endCol - startCol;

            List list = new ArrayList();
            while (rs.next())
            {
                String[] info = new String[len];
                for (int i = 0, j = startCol; j < endCol; i++, j++)
                {
                    info[i] = formatNull(rs.getString(j + 1));
                }
                list.add(info);
            }
            return list;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据分页信息获得规定的一页数据列表。
     * @param sql SQL语句
     * @param pagination 分页信息
     * @return List(String[])一页数据列表
     * @throws SQLException
     */
    public List getPageList(String sql, Pagination pagination) throws SQLException
    {
    	if(pagination.getPageIndex()==Integer.MAX_VALUE){
    		return getList(sql, 0, END_COL);
    	}else{
    		return getPageList(sql, 0, END_COL, pagination);
    	}
    }

    /**
     * 根据分页信息获得规定的一页数据列表。
     * @param sql SQL语句
     * @param startCol 获取结果集的起始列，从0开始（包含）
     * @param pagination 分页信息
     * @return List(String[])一页数据列表
     * @throws SQLException
     */
    public List getPageList(String sql, int startCol, Pagination pagination) throws SQLException
    {
        return getPageList(sql, startCol, END_COL, pagination);
    }

    /**
     * 根据分页信息对象查询出规定的一页数据列表。
     * @param sql SQL语句
     * @param startCol 获取结果集的起始列，从0开始（包含）
     * @param endCol 获取结果集的终止列（不包）
     * @param pagination 分页信息
     * @return List(String[])一页数据列表
     * @throws SQLException
     */
    public List getPageList(String sql, int startCol, int endCol, Pagination pagination) throws SQLException
    {
        this.pagination = pagination;
        Statement stmt = null;
        ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);

            if (endCol == END_COL)
            {
                endCol = rs.getMetaData().getColumnCount();
            }
            int len = endCol - startCol;

            rs.last();
            pagination.setRowSize(rs.getRow());
            int startRow = pagination.getStartRow();
            if (startRow <= 1)
            {
                rs.beforeFirst();
            }
            else
            {
                rs.absolute(startRow - 1);
            }

            int pageRows = pagination.getPageRows();
            for (int i = 0; i < pageRows && rs.next(); i++)
            {
                String[] row = new String[len];
                for (int j = 0; j < len; j++)
                {
                    row[j] = formatNull(rs.getString(j + 1));
                }
                list.add(row);
            }
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }

        return list;
    }

    /**
     * 根据分页信息和SQL语句查询数据库获得一页数据列表。
     * @param sql 预编译SQL语句
     * @param params SQL参数，可为null。
     * @param pagination 分页信息
     * @return List(String[])一页数据列表
     * @throws SQLException
     */
    public List getPageList(String sql, Object[] params, Pagination pagination) throws SQLException
    {
        return getPageList(sql, params, 0, END_COL, pagination);
    }

    /**
     * 根据分页信息和SQL语句查询数据库，并从结果集的指定起始列至终止列获得一页数据列表。
     * @param sql 预编译SQL语句
     * @param params SQL参数，可为null。
     * @param startCol 获取结果集的起始列，从0开始（包含）。
     * @param pagination 分页信息
     * @return List(String[])一页数据列表
     * @throws SQLException
     */
    public List getPageList(String sql, Object[] params, int startCol, Pagination pagination) throws SQLException
    {
        return getPageList(sql, params, startCol, END_COL, pagination);
    }

    /**
     * 根据分页信息和SQL语句查询数据库，并从结果集的指定起始列至指定终止列获得一页数据列表。
     * @param sql 预编译SQL语句
     * @param params SQL参数，可为null。
     * @param startCol 获取结果集的起始列，从0开始（包含）。
     * @param endCol 获取结果集的终止列（不包）
     * @param pagination 分页信息
     * @return List(String[])一页数据列表
     * @throws SQLException
     */
    public List getPageList(String sql, Object[] params, int startCol, int endCol, Pagination pagination) throws SQLException
    {
        this.pagination = pagination;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery(sql);

            if (endCol == END_COL)
            {
                endCol = rs.getMetaData().getColumnCount();
            }
            int len = endCol - startCol;

            rs.last();
            pagination.setRowSize(rs.getRow());
            int startRow = pagination.getStartRow();
            if (startRow <= 1)
            {
                rs.beforeFirst();
            }
            else
            {
                rs.absolute(startRow - 1);
            }

            int pageRows = pagination.getPageRows();
            for (int i = 0; i < pageRows && rs.next(); i++)
            {
                String[] row = new String[len];
                for (int j = 0; j < len; j++)
                {
                    row[j] = formatNull(rs.getString(j + 1));
                }
                list.add(row);
            }
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }

        return list;
    }

    /**
     * 获得数据库分页信息，只有先调用了<code>getPageList(...)</code>方法之后，
     * 调用此方法才会返回一个分页信息实例，否则返回null。
     * @return 数据库分页信息 or null。
     */
    public Pagination getPagination()
    {
        return pagination;
    }

    /**
     * 执行一条 INSERT SQL 语句获得其中自增字段的值。
     * @param sql INSERT SQL 语句
     * @return 插入的自增字段的值
     * @throws SQLException
     */
    public long getGeneratedKey(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql, new int[]{1});
            rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式执行插入语句并获得自增键的值
     * @param sql 预编译 INSERT SQL 语句
     * @param params SQL参数，可为null。
     * @return 插入的自增字段的值
     * @throws SQLException
     */
    public long getGeneratedKey(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.prepareStatement(sql, new int[]{1});
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }
    
    /**
     * 采用预编译SQL语句形式执行批量插入语句并获得自增键的值数组
     * @param sql 预编译 INSERT SQL 语句
     * @param paramsList SQL参数，可为null。
     * @return 插入的自增字段的值
     * @throws SQLException
     */
    public long[] getGeneratedKeys(String sql,List paramsList)throws SQLException {
    	 PreparedStatement stmt = null;
         ResultSet rs = null;
         long[] ids = null;
         try
         {
             stmt = conn.prepareStatement(sql, new int[]{1});
             if (paramsList != null && paramsList.size()>0)
             {
            	 ids = new long[paramsList.size()];
                 for (int i = 0; i < paramsList.size(); i++)
                 {
                	 String[] params = (String[]) paramsList.get(i);
                	 for(int j=0; j<params.length; j++)
                	 {
                		 stmt.setObject(j + 1, params[j]);	 
                	 }
                	 stmt.executeUpdate();
                     rs = stmt.getGeneratedKeys();
                     rs.next();
                     ids[i] = rs.getLong(1); 
                 }
             }
         }
         finally
         {
             closeResultSet(rs);
             closeStatement(stmt);
         }
         return ids;
    }

    /**
     * 根据SQL语句查询的一条结果集填充Bean的属性
     * @param bean 要设置的JavaBean对象
     * @param fields JavaBean的字段
     * @param sql SQL语句
     * @return true，查询到结果并填充bean成功；否则未查询到结果。
     * @throws SQLException
     */
    public boolean populate(Object bean, String[] fields, String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return populate(bean, fields, rs);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据SQL语句查询的结果集填充每个Bean的属性并存入List中
     * @param cls 要设置的JavaBean的Class
     * @param fields JavaBean的字段
     * @param sql SQL语句
     * @return 存放bean的List
     * @throws SQLException
     */
    public List populate(Class cls, String[] fields, String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return populate(cls, fields, rs);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据预编译SQL语句查询的一条结果集填充Bean的属性
     * @param bean 要设置的JavaBean对象
     * @param fields JavaBean的字段
     * @param sql SQL语句
     * @param params SQL参数，可为null。
     * @return true，查询到结果并填充bean成功；否则未查询到结果。
     * @throws SQLException
     */
    public boolean populate(Object bean, String[] fields, String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            return populate(bean, fields, rs);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据预编译SQL语句查询的结果集填充每个Bean的属性并存入List中
     * @param cls 要设置的JavaBean的Class
     * @param fields JavaBean的字段
     * @param sql SQL语句
     * @param params SQL参数，可为null。
     * @return 存放bean的List
     * @throws SQLException
     */
    public List populate(Class cls, String[] fields, String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            return populate(cls, fields, rs);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据结果集填充bean的属性
     * @param bean JavaBean对像
     * @param fields JavaBean的字段
     * @param rs 结果集
     * @return true，查询到结果并填充bean成功；否则未查询到结果。
     * @throws SQLException
     */
    private boolean populate(Object bean, String[] fields, ResultSet rs) throws SQLException
    {
        if (rs.next())
        {
            Map map = new HashMap();
            for (int i = 0; i < fields.length; i++)
            {
                map.put(fields[i], formatNull(rs.getString(i + 1)));
            }

            try
            {
                BeanUtils.populate(bean, map);
            }
            catch (InvocationTargetException e)
            {
                throw new SQLException(e.getMessage());
            }
            catch (IllegalAccessException e)
            {
                throw new SQLException(e.getMessage());
            }

            return true;
        }
        return false;
    }

    /**
     * 根据结果集填充bean的属性，并存入List中。
     * @param cls 要设置的JavaBean的Class
     * @param fields JavaBean的字段
     * @param rs 结果集
     * @return 存放bean的List
     * @throws SQLException
     */
    private List populate(Class cls, String[] fields, ResultSet rs) throws SQLException
    {
        List list = new ArrayList();

        try
        {
            while (rs.next())
            {
                Map map = new HashMap();
                for (int i = 0; i < fields.length; i++)
                {
                    map.put(fields[i], formatNull(rs.getString(i + 1)));
                }
                Object bean = cls.newInstance();
                BeanUtils.populate(bean, map);
                list.add(bean);
            }
        }
        catch (InstantiationException e)
        {
            throw new SQLException(e.getMessage());
        }
        catch (IllegalAccessException e)
        {
            throw new SQLException(e.getMessage());
        }
        catch (InvocationTargetException e)
        {
            throw new SQLException(e.getMessage());
        }

        return list;
    }

    /**
     * 执行一次给定的 INSERT、UPDATE or DELETE SQL 语句
     * @param sql SQL语句
     * @return 插入、更新或删除的行数
     * @throws SQLException
     */
    public int update(String sql) throws SQLException
    {
        Statement stmt = null;
        try
        {
            stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        }
        finally
        {
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译形式执行一次给定的 INSERT、UPDATE or DELETE SQL 语句
     * @param sql 预编译SQL语句
     * @param params SQL 参数，可为null。
     * @return 插入、更新或删除的行数
     * @throws SQLException
     */
    public int update(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            return stmt.executeUpdate();
        }
        finally
        {
            closeStatement(stmt);
        }
    }
    
    /**
     * 采用预编译形式执行多次给定的 INSERT、UPDATE or DELETE SQL 语句，执行SQL的次数根据参数长度确定。
     * @param sql 预编译SQL语句
     * @param params SQL 参数，可为null。
     * @return int 返回更新的总行数
     * @throws SQLException
     */
    public int updates(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            int rows = 0;
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(1, params[i]);
                    rows += stmt.executeUpdate();
                }
            }
            return rows;
        }
        finally
        {
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译形式执行多次给定的 INSERT、UPDATE or DELETE SQL 语句，
     * 执行SQL的次数根据<code>params.length</code>确定。
     * @param sql 预编译SQL语句
     * @param params 多组 SQL 参数，可为null。
     * @return 返回更新的总行数
     * @throws SQLException
     */
    public int updates(String sql, Object[][] params) throws SQLException
    {
        PreparedStatement stmt = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            int rows = 0;
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    for (int j = 0; j < params[i].length; j++)
                    {
                        stmt.setObject(j + 1, params[i][j]);
                    }
                    rows += stmt.executeUpdate();
                }
            }
            return rows;
        }
        finally
        {
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译形式执行多次给定的 INSERT、UPDATE or DELETE SQL 语句，
     * 执行SQL的次数根据<code>paramList.size()</code>确定。
     * @param sql 预编译SQL语句
     * @param paramList List(Object[]) SQL 参数，可为null。
     * @return 返回更新的总行数
     * @throws SQLException
     */
    public int updates(String sql, List paramList) throws SQLException
    {
        PreparedStatement stmt = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            int rows = 0;
            if (paramList != null)
            {
                for (int i = 0, ii = paramList.size(); i < ii; i++)
                {
                    Object[] objs = (Object[]) paramList.get(i);
                    for (int j = 0; j < objs.length; j++)
                    {
                        stmt.setObject(j + 1, objs[j]);
                    }
                    rows += stmt.executeUpdate();
                }
            }
            return rows;
        }
        finally
        {
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译形式执行一次给定的 INSERT、UPDATE or DELETE SQL 语句组，
     * SQL 语句组对应同一组参数。
     * @param sqls 预编译SQL语句组
     * @param params SQL 参数，可为null。
     * @return 返回SQL语句组更新的行数组
     * @throws SQLException
     */
    public int[] update(String[] sqls, Object[] params) throws SQLException
    {
        int[] updatedRows = new int[sqls.length];
        for (int i = 0; i < sqls.length; i++)
        {
            updatedRows[i] = update(sqls[i], params);
        }
        return updatedRows;
    }

    /**
     * 采用预编译形式执行多次给定的 INSERT、UPDATE or DELETE SQL 语句，
     * 执行SQL语句组的次数根据<code>params.length</code>确定。
     * @param sqls 预编译SQL语句组
     * @param params SQL 参数，可为null。
     * @return 返回SQL语句组更新的行数组
     * @throws SQLException
     */
    public int[] updates(String[] sqls, Object[] params) throws SQLException
    {
        int[] updatedRows = new int[sqls.length];
        for (int i = 0; i < sqls.length; i++)
        {
            updatedRows[i] = updates(sqls[i], params);
        }
        return updatedRows;
    }

    /**
     * 采用预编译形式执行一次给定的 INSERT、UPDATE or DELETE SQL 语句组，
     * 每个SQL对应不同的参数组。
     * @param sqls 预编译SQL语句组
     * @param params SQL参数组
     * @return 返回SQL语句组更新的行数组
     * @throws SQLException
     */
    public int[] update(String[] sqls, Object[][] params) throws SQLException
    {
        int[] updatedRows = new int[sqls.length];
        for (int i = 0; i < sqls.length; i++)
        {
            updatedRows[i] = update(sqls[i], params[i]);
        }
        return updatedRows;
    }

    /**
     * 采用预编译形式执行多次给定的 INSERT、UPDATE or DELETE SQL 语句组，
     * 每个SQL对应不同的参数组，每个SQL执行的次数和其对应的参数组的长度一致。
     * @param sqls 预编译SQL语句组
     * @param params SQL参数组
     * @return 返回SQL语句组更新的行数组
     * @throws SQLException
     */
    public int[] updates(String[] sqls, Object[][] params) throws SQLException
    {
        int[] updatedRows = new int[sqls.length];
        for (int i = 0; i < sqls.length; i++)
        {
            updatedRows[i] = updates(sqls[i], params[i]);
        }
        return updatedRows;
    }

    /**
     * 是否查询到数据
     * @param sql 查询SQL语句
     * @return boolean 查询的结果集中有数据则返回true，否则返回false。
     * @throws SQLException
     */
    public boolean hasData(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs.next();
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得是否查询到数据
     * @param sql 预编译查询SQL语句
     * @param params SQL参数，可为null。
     * @return boolean 查询的结果集中有数据则返回true，否则返回false。
     * @throws SQLException
     */
    public boolean hasData(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            return rs.next();
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得是否查询到数据，该方法将执行<code>params.length</code>次SQL语句。
     * @param sql 预编译SQL语句，该SQL中只允许一个参数
     * @param params SQL参数，可为null。
     * @return 查询到数据则返回true
     * @throws SQLException
     */
    public boolean hasDatas(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(1, params[i]);
                    rs = stmt.executeQuery();
                    if (rs.next())
                    {
                        return true;
                    }
                }
            }
            return false;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得是否查询到数据，该方法将执行<code>paramList.size()</code>次SQL语句。
     * @param sql 预编译SQL语句，该SQL中只允许一个参数
     * @param paramList List(Object[]) SQL参数，列表中每一个数组元素为一组参数，可为null。
     * @return 查询到数据则返回true
     * @throws SQLException
     */
    public boolean hasDatas(String sql, List paramList) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (paramList != null)
            {
                for (int i = 0, ii = paramList.size(); i < ii; i++)
                {
                    Object[] param = (Object[]) paramList.get(i);
                    for (int j = 0; j < param.length; j++)
                    {
                        stmt.setObject(j + 1, param[j]);
                    }
                    rs = stmt.executeQuery();
                    if (rs.next())
                    {
                        return true;
                    }
                }
            }
            return false;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 获得一个整数
     * @param sql 查询SQL语句
     * @return 整数
     * @throws SQLException
     */
    public int getInt(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if(rs.next()){
            	return rs.getInt(1);
            }else {
				return 0;
			}
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }
    /**
     * 采用预编译SQL语句形式获得一个整数
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 整数
     * @throws SQLException
     */
    public int getIntNull(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            if(rs.next())
            	return rs.getInt(1);
            else
            	return -2;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }
    /**
     * 采用预编译SQL语句形式获得一个整数
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 整数
     * @throws SQLException
     */
    public int getInt(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 获得一个长整数
     * @param sql 查询SQL语句
     * @return 长整数
     * @throws SQLException
     */
    public long getLong(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            return rs.getLong(1);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得一个长整数
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 长整数
     * @throws SQLException
     */
    public long getLong(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            rs.next();
            return rs.getLong(1);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 获得一个浮点数
     * @param sql 查询SQL语句
     * @return 浮点数
     * @throws SQLException
     */
    public float getFloat(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next())
            return rs.getFloat(1);
            else return 0;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得一个浮点数
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 浮点数
     * @throws SQLException
     */
    public float getFloat(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            rs.next();
            return rs.getFloat(1);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 获得一个双精度浮点数
     * @param sql 查询SQL语句
     * @return 双精度浮点数
     * @throws SQLException
     */
    public double getDouble(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            return rs.getDouble(1);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得一个双精度浮点数
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 双精度浮点数
     * @throws SQLException
     */
    public double getDouble(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            rs.next();
            return rs.getDouble(1);
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 获得一个字符串
     * @param sql 查询SQL语句
     * @return 返回字符串，若没有查询到数据则返回null。
     * @throws SQLException
     */
    public String getString(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            String str = null;
            if (rs.next())
            {
                str = rs.getString(1);
            }
            return str;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 采用预编译SQL语句形式获得一个字符串
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 返回字符串，若没有查询到数据则返回null。
     * @throws SQLException
     */
    public String getString(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            String str = null;
            if (rs.next())
            {
                str = rs.getString(1);
            }
            return str;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据SQL语句查询数据库并将取得的第一行数据存放到int数组中并返回。
     * @param sql SQL语句
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public int[] getIntArray(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new int[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = rs.getInt(i + 1);
                }
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据预编译SQL语句查询数据库并将取得的第一行数据存放到int数组中并返回。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public int[] getIntArray(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            int[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new int[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = rs.getInt(i + 1);
                }
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据SQL语句查询数据库并将取得的第一行数据存放到long数组中并返回。
     * @param sql SQL语句
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public long[] getLongArray(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            long[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new long[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = rs.getLong(i + 1);
                }
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据预编译SQL语句查询数据库并将取得的第一行数据存放到long数组中并返回。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public long[] getLongArray(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            long[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new long[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = rs.getInt(i + 1);
                }
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据SQL语句查询数据库并将取得的第一行数据存放到float数组中并返回。
     * @param sql SQL语句
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public float[] getFloatArray(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            float[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new float[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = rs.getFloat(i + 1);
                }
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据预编译SQL语句查询数据库并将取得的第一行数据存放到float数组中并返回。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public float[] getFloatArray(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            float[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new float[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = rs.getFloat(i + 1);
                }
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据SQL语句查询数据库并将取得的第一行数据存放到double数组中并返回。
     * @param sql SQL语句
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public double[] getDoubleArray(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            double[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new double[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = rs.getDouble(i + 1);
                }
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据预编译SQL语句查询数据库并将取得的第一行数据存放到double数组中并返回。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public double[] getDoubleArray(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
            double[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new double[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = rs.getDouble(i + 1);
                }
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据SQL语句查询数据库并将取得的第一行数据存放到String数组中并返回。
     * @param sql SQL语句
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public String[] getStringArray(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            String[] array = null;
            if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new String[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] =formatNull(rs.getString(i + 1));
                }
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }

    /**
     * 根据预编译SQL语句查询数据库并将取得的第一行数据存放到String数组中并返回。
     * @param sql 预编译SQL语句
     * @param params 预编译SQL参数，可为null。
     * @return 结果集第一行组成的数组，若没有数据则返回null。
     * @throws SQLException
     */
    public String[] getStringArray(String sql, Object[] params) throws SQLException
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String[] array = null;
        try
        {
            stmt = conn.prepareStatement(sql);
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            rs = stmt.executeQuery();
           if (rs.next())
            {
                ResultSetMetaData rsmt = rs.getMetaData();
                array = new String[rsmt.getColumnCount()];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = formatNull(rs.getString(i + 1));
                }
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }
    
	 /**
	  * 记录数据
	  * @param row     列数
	  * @param params  值
	  * @param tableName
	  * * @param dbservice
	 * @throws SQLException 
	  */
	 public void logData(int row,Object[] params,String tableName) throws SQLException{
		StringBuilder sf =new StringBuilder();
		sf.append("insert into "+tableName+" values(");
		for(int i=0;i<row-1;i++){
			sf.append("?,");
		}
		sf.append("?)");
		update(sf.toString(), params);
	 }

    /**
     * 格式化字符串
     * @param rsString 被格式化的字符串
     * @return 字符串为null则返回""，否则返回原字符串。
     */
    private String formatNull(String rsString)
    {
        return rsString == null ? "" : rsString;
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Integer> getArray(String sql) throws SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            List  array = new ArrayList<Integer>(0);
            int k=0;
            while(rs.next())
            {
                    array.add(rs.getInt(1));
            }
            return array;
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
        }
    }
    
    
    

    /**
     * 关闭结果集（忽略异常）
     * @param rs 结果集
     */
    public void closeResultSet(ResultSet rs)
    {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭SQL语句对象（忽略异常）
     * @param stmt SQL语句对象
     */
    public void closeStatement(Statement stmt)
    {
        if (stmt != null)
        {
            try
            {
                stmt.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置数据库是否自动提交
     * @param autoCommit true，自动提交。
     * @throws SQLException
     */
    public void setAutoCommit(boolean autoCommit) throws SQLException
    {
        conn.setAutoCommit(autoCommit);
    }

    /**
     * 提交数据库更新
     * @throws SQLException
     */
    public void commit() throws SQLException
    {
        conn.commit();
    }

    /**
     * 回滚数据库操作（忽略异常）
     */
    public void rollback()
    {
        if (conn != null)
        {
            try
            {
                conn.rollback();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库连接（忽略异常）
     */
    public void close()
    {
        if (conn != null)
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
	 * @return conn 得到数据库连接 
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn 数据库连接 
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
}
