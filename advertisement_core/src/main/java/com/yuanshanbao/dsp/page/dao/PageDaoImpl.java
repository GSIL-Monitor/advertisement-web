package com.yuanshanbao.dsp.page.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.page.dao.PageDao;
import com.yuanshanbao.dsp.page.model.Page;

/**
 * 
 * @description 
 *
 * @author 
 */
@Repository
public class PageDaoImpl extends BaseDaoImpl implements PageDao {
	
	
	@Override
	public int insertPage(Page page) {
		return getSqlSession()
				.insert("page.insertPage", page);
	}

	@Override
	public int updatePage(Page page) {
		return getSqlSession()
				.update("page.updatePage", page);
	}

	@Override
	public int deletePage(Page page) {
		return getSqlSession()
				.delete("page.deletePage", page);
	}

	@Override
	public List<Page> selectPages(Page page,
			PageBounds pageBounds) {
		return getSqlSession().selectList("page.selectPages",
				page, pageBounds);
	}

	@Override
	public List<Page> selectPageByIds(List<Long> pageIds) {
		if (pageIds == null || pageIds.size() == 0) {
			return new ArrayList<Page>();
		}
		return getSqlSession().selectList(
				"page.selectPageByIds", pageIds);
	}
}