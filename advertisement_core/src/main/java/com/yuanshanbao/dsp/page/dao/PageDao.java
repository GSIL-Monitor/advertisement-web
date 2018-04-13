package com.yuanshanbao.dsp.page.dao;


import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.page.model.Page;

/**
 * 
 * @description
 *
 * @author 
 */
public interface PageDao {

	public int insertPage(Page page);

	public int updatePage(Page page);

	public int deletePage(Page page);

	public List<Page> selectPages(Page page,
			PageBounds pageBounds);

	public List<Page> selectPageByIds(List<Long> pageIds);
}