package com.chnghx.core.page;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable, Paginable {
	
	public Pagination(Integer page, Integer pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Integer page;
	

	private Integer pageSize;
	
	private Integer totalNum;

	// 是否有下一页
	private Integer hasMore;
	// 总页数
	private Integer totalPage;
	// 分页结果
	private List<T> items;

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPageNo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFirstPage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLastPage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNextPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPrePage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getHasMore() {
		return hasMore;
	}

	public void setHasMore(Integer hasMore) {
		this.hasMore = hasMore;
	}


	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

}
