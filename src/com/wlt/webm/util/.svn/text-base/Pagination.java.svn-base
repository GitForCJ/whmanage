package com.wlt.webm.util;

/**
 * 分页信息类<br>
 */
public class Pagination
{
    /**
     * 分页是否完成
     */
    private boolean finished = false;

    /**
     * 默认页面显示的行数
     */
    private final int DEFAULT_PAGE_ROWS = 20;

    /**
     * 总行数
     */
    private int rowSize = 0;

    /**
     * 每页显示的行数
     */
    private int pageRows = DEFAULT_PAGE_ROWS;

    /**
     * 当前页
     */
    private int pageIndex = 1;

    /**
     * 默认构造方法
     */
    public Pagination()
    {
        
    }
    /**
     * 用当前页数初始化类的构造方法
     * @param pageIndex 当前页数
     * @throws PaginationException
     */
    public Pagination(int pageIndex) throws PaginationException
    {
        setPageIndex(pageIndex);
    }
    /**
     * 用总行数和当前页数初始化类的构造方法
     * @param rowSize 总行数
     * @param pageIndex 当前页数
     * @throws PaginationException
     */
    public Pagination(int rowSize, int pageIndex) throws PaginationException
    {
        setRowSize(rowSize);
        setPageIndex(pageIndex);
    }

    /**
     * 用总行数、当前页数和每页显示行数构造分页类
     * @param rowSize 总行数
     * @param pageIndex 当前页数
     * @param pageRows 每页显示行数
     * @throws PaginationException
     */
    public Pagination(int rowSize, int pageIndex, int pageRows) throws PaginationException
    {
        setRowSize(rowSize);
        setPageIndex(pageIndex);
        setPageRows(pageRows);
    }

    /**
     * 得到当前页数
     * @return 当前页数
     */
    public int getPageIndex()
    {
        return pageIndex;
    }

    /**
     * 设置当前页数，如果设置的当前页数小于1，则默认为1。
     * @param pageIndex 设置当前页数
     * @throws PaginationException 
     */
    public void setPageIndex(int pageIndex) throws PaginationException
    {
        this.pageIndex = pageIndex < 1 ? 1 : pageIndex;
    }

    /**
     * 获得每页显示行数
     * @return 每页显示行数
     */
    public int getPageRows()
    {
        return pageRows;
    }

    /**
     * 设置每页显示行数
     * @param pageRows 每页显示行数
     * @throws PaginationException 
     */
    public void setPageRows(int pageRows) throws PaginationException
    {
        if (pageRows < 1)
        {
            throw new PaginationException("设置的每页显示行数不能小于1。");
        }

        this.pageRows = pageRows;
    }

    /**
     * 获得总行数
     * @return 总行数
     */
    public int getRowSize()
    {
        return rowSize;
    }

    /**
     * 设置总行数
     * @param rowSize 总行数
     * @throws PaginationException 
     */
    public void setRowSize(int rowSize) throws PaginationException
    {
        if (rowSize < 0)
        {
            throw new PaginationException("设置的分页总行数不能小于0。");
        }

        this.rowSize = rowSize;
        this.finished = true;
    }

    /**
     * 获得总页数
     * @return 总页数
     */
    public int getPageSize()
    {
        return rowSize / pageRows + (rowSize % pageRows == 0 ? 0 : 1);
    }

    /**
     * 获得当前页面起始行在总行数中的起始位置，如果起始行大于总行数，则为最后一行
     * @return 当前页面起始行在总行数中的起始位置
     */
    public int getStartRow()
    {
        int startRow = (pageIndex - 1) * pageRows + 1;
        return startRow > this.rowSize ? this.rowSize : startRow;
    }

    /**
     * 获得当前页面结束行在总行数中的结束位置，如果结束行大于总行数，则为最后一行
     * @return 当前页面结束行在总行数中的结束位置
     */
    public int getEndRow()
    {
        int endRow = this.getStartRow() + this.pageRows - 1;
        return endRow > this.rowSize ? this.rowSize : endRow;
    }

    /**
     * 获得当前页面在总行数中的实际行数
     * @return 当前页面在总行数中的实际行数
     */
    public int getFactPageRows()
    {
        return this.getEndRow() - this.getStartRow() + 1;
    }

    /**
     * 分页是否完成。
     * @return 当调用了<code>setRowSize(int)</code>方法之后，表明分页完成。
     */
    public boolean finished()
    {
        return finished;
    }
}
