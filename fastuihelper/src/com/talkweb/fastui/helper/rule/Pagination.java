package com.talkweb.fastui.helper.rule;

import java.util.List;

/**
 * 
 * 文件名称:    Pagination.java
 * 内容摘要:    后端分页
 * @author:   huangyong 
 * @version:  1.0  
 * @Date:     Apr 21, 2011 10:21:56 PM  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * Apr 21, 2011    yaoxp     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2011
 * 公司:   拓维信息系统股份有限公司
 */
public class Pagination {
	private static final long serialVersionUID = 1L;
	public static final int MAX_PAGE_SIZE = 500;
	public static final int MAX_RECODE_SIZE = 2147483647;
	private boolean needCount = true;
	private boolean onlyCount = false;
	
	//每页显示记录数，对应fastui的pageSize
    private int size;
    //当前页，对应fastui的page
    private int currPage;
  //总记录数，对应fastui的records
    private int count;
  //总页数，对应fastui的total
    private int allPage;
	
	private List list;
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Pagination() {
	}

	public Pagination(boolean batch) {
	}

	public Pagination(boolean batch, int size) {
		setSize(size);
	}

	public Pagination(int currPage, int pageSize) {
		setNeedCount(true);
		setCurrPage(currPage);
		setSize(pageSize);
	}

	public int getMaxPageSize() {
		/*try {
			return Integer.parseInt(this.common.getProperty(
					"globar/navbar/maxpagesize", String.valueOf(500)));
		} catch (Exception e) {
			throw new JdbcException(e);
		}*/
		return 50;
	}

	public int getAllPage() {
		return allPage;
	}

	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isBatch() {
		return true;
	}

	public boolean isRange() {
		return true;
	}

	public boolean isNeedCount() {
		return this.needCount;
	}

	public void setNeedCount(boolean needCount) {
		this.needCount = needCount;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStart() {
		return (currPage - 1) * size;
	}

	public int getSize() {
		return this.size;
	}

	public int getCurrPage() {
		return this.currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public boolean isOnlyCount() {
		return this.onlyCount;
	}

	public void setOnlyCount(boolean onlyCount) {
		this.onlyCount = onlyCount;
	}
}