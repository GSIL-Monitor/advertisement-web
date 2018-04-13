package com.yuanshanbao.dsp.paginator.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * 包含“分页”信息的List
 * 
 * <p>
 * 要得到总页数请使用 toPaginator().getTotalPages();
 * </p>
 * 
 * @author badqiu
 * @author miemiedev
 */
public class PageList<E> extends ArrayList<E> implements Serializable {

	private static final long serialVersionUID = 1412759446332294208L;

	private Paginator paginator;

	public PageList() {
		paginator = new Paginator(0, 10, 0);
	}

	public PageList(Collection<? extends E> c) {
		super(c);
	}

	public PageList(Collection<? extends E> c, Paginator p) {
		super(c);
		this.paginator = p;
	}

	public PageList(Paginator p) {
		this.paginator = p;
	}

	/**
	 * 得到分页器，通过Paginator可以得到总页数等值
	 * 
	 * @return
	 */
	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public int getPage() {
		return paginator.getPage();
	}

	public int getLimit() {
		return paginator.getLimit();
	}

	public int getTotalCount() {
		return paginator.getTotalCount();
	}

	public int getTotalPage() {
		return paginator.getTotalPages();
	}
	
	public boolean isHasNextPage() {
		return paginator.isHasNextPage();
	}

	public void setList(Collection<E> collection) {
		this.clear();
		this.addAll(collection);
	}

}
