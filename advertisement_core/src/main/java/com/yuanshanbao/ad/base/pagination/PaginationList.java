package com.yuanshanbao.ad.base.pagination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class PaginationList<T> implements Serializable, Iterable<T> {
	private static final long serialVersionUID = -6059628280162549106L;

	private PaginationInfo paginationInfo = null;
	private List<T> list = new ArrayList<>();

	public T get(int index) {
		return this.list.get(index);
	}

	public boolean addAll(Collection<? extends T> list) {
		return this.list.addAll(list);
	}

	public int size() {
		return this.list.size();
	}

	public boolean add(T e) {
		return this.list.add(e);
	}

	@Override
	public Iterator<T> iterator() {
		return this.list.iterator();
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		this.list.forEach(action);
	}

	@Override
	public Spliterator<T> spliterator() {
		return this.list.spliterator();
	}

	public PaginationList() {
	}

	public PaginationList(PaginationInfo paginationInfo, List<T> list) {
		this.paginationInfo = paginationInfo;
		this.list = list;
	}

	public PaginationInfo getPaginationInfo() {
		return paginationInfo;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
