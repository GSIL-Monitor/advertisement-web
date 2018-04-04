package com.yuanshanbao.paginator.domain;

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

	public PageList<E> getPageList(PageList<E> resultList, PageBounds pageBounds) {
		PageList<E> pageList = new PageList<E>();
		formatPageBounds(pageBounds);
		int size = resultList.size();
		if (size > pageBounds.getLimit()) {
			int start = pageBounds.getLimit() * (pageBounds.getPage() - 1);
			int end = start + pageBounds.getLimit();
			pageList.setPaginator(new Paginator(start, pageBounds.getLimit(), size));
			for (int i = start; i < (end > size ? size : end); i++) {
				pageList.add(resultList.get(i));
			}
		} else {
			for (E param : resultList) {
				pageList.add(param);
			}
		}
		return pageList;
	}
	
	protected PageBounds formatPageBounds(PageBounds pageBounds) {
		if (pageBounds == null) {
			pageBounds = new PageBounds();
		}
		if (pageBounds.getLimit() == PageBounds.NO_ROW_LIMIT) {
			pageBounds.setLimit(PageBounds.DEFAULT_PAGE_LIMIT);
		}
		return pageBounds;
	}

}
