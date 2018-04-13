package  com.yuanshanbao.dsp.page.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.page.model.PageStep;

/**
 * @description 
 * @author 
 */
public interface PageStepService{
	
	public void insertPageStep(PageStep pageStep);

	public void updatePageStep(PageStep pageStep);

	public void deletePageStep(PageStep pageStep);

	public List<PageStep> selectPageSteps(PageStep pageStep, PageBounds pageBounds);

	public Map<Long, PageStep> selectPageStepByIds(List<Long> pageStepIds);
	
	public PageStep selectPageStep(Long pageStepId);
	
	public void insertOrUpdatePageStep(PageStep pageStep);
}