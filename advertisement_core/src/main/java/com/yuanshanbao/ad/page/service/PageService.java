package  com.yuanshanbao.ad.page.service;

import java.util.Map;
import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Service;
import com.yuanshanbao.ad.page.model.Page;

/**
 * @description 
 * @author 
 */
@Service
public interface PageService{
	
	public void insertPage(Page page);

	public void updatePage(Page page);

	public void deletePage(Page page);

	public List<Page> selectPages(Page page, PageBounds pageBounds);

	public Map<Long, Page> selectPageByIds(List<Long> pageIds);
	
	public Page selectPage(Long pageId);
	
	public void insertOrUpdatePage(Page page);
}