package com.yuanshanbao.dsp.controller.base;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanshanbao.common.constant.SessionConstants;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.MD5Util;
import com.yuanshanbao.common.util.RequestUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.common.validator.util.ValidatorModel;
import com.yuanshanbao.common.validator.util.ValidatorUtils;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementPosition;
import com.yuanshanbao.dsp.advertisement.model.AdvertisementShowType;
import com.yuanshanbao.dsp.advertisement.model.vo.AdvertisementVo;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.app.model.AppType;
import com.yuanshanbao.dsp.app.service.AppService;
import com.yuanshanbao.dsp.apply.service.ApplyService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.model.SmsToken;
import com.yuanshanbao.dsp.common.model.SmsTokenList;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.config.ConfigConstants;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.model.vo.ProductVo;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.project.service.ProjectService;
import com.yuanshanbao.dsp.sms.model.SmsConstant;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserStatus;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

public class BaseController {

	public static final String TOP1 = "http://yuanshanbao.oss-cn-beijing.aliyuncs.com/file/ms/1503908710080_825.png";

	public static final String TOP2 = "http://yuanshanbao.oss-cn-beijing.aliyuncs.com/file/ms/1503908724530_5582.png";

	public static final String TOP3 = "http://yuanshanbao.oss-cn-beijing.aliyuncs.com/file/ms/1503908735923_678.png";

	private static final String POST_UNIQUE_TOKEN = "postUniqueToken";

	private static String TEMPLATE_VARIABLE_NAME = "${name}";
	private static String TEMPLATE_VARIABLE_AMOUNT = "<advertisementAmount>";
	private static String TEMPLATE_VARIABLE_PROVINCE = "<province>";

	public static final String MOBILE_ENCRYPT_KEY = "aadecfe68c1c06a7";

	@Autowired
	protected RedisService redisCacheService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected AdvertisementService advertisementService;

	@Autowired
	protected AppService appService;

	@Autowired
	protected ProjectService projectService;

	@Autowired
	protected ApplyService applyService;

	@Autowired
	private ProductService productService;

	public Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 * @param response
	 */
	public void setNoCache(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
	}

	public long[] getTimeRange(String startTimeStr, String endTimeStr) {
		long[] range = new long[2];
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			range[0] = fmt.parse(startTimeStr).getTime();
			range[1] = fmt.parse(endTimeStr).getTime();
		} catch (java.text.ParseException e) {
			fmt = new SimpleDateFormat("yyyy-MM-dd");
			try {
				range[0] = fmt.parse(startTimeStr).getTime();
				range[1] = fmt.parse(endTimeStr).getTime();
			} catch (java.text.ParseException e2) {
				LoggerUtil.error("getTimeRange-error", e2);
				return null;
			}
		}
		return range;
	}

	protected User getSessionUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(SessionConstants.SESSION_ACCOUNT);
		if (user != null && user.getStatus() != null && user.getStatus() == UserStatus.LOCK) {
			throw new BusinessException(ComRetCode.USER_LOCKED);
		}
		return user;
	}

	protected boolean checkRepeatPostRequest(HttpServletRequest request) {
		String token = request.getParameter(POST_UNIQUE_TOKEN);
		if (StringUtils.isBlank(token)) {
			token = request.getHeader(POST_UNIQUE_TOKEN);
		}
		if (StringUtils.isNotBlank(token)) {
			Long count = redisCacheService.incr(RedisConstant.getUniqueTokenKey(token));
			redisCacheService.expire(RedisConstant.getUniqueTokenKey(token), 60 * 10);
			if (count != null && count > 1) {
				return false;
			}
		}
		return true;
	}

	protected void validateParameters(Object object) {
		ValidatorModel model = null;
		try {
			model = ValidatorUtils.validate(object);
		} catch (Exception e) {
			LoggerUtil.error("[Error validate parameters: ]", e);
		}
		if (model != null) {
			throw new BusinessException(model.getField().getName(), model.getMessageCode());
		}
	}

	protected String forward(String url) {
		if (!url.contains(".html")) {
			url = url + ".html";
		}
		return "forward:" + url;
	}

	protected String redirect(String url) {
		if (!url.contains(".")) {
			url = url + ".html";
		}
		return "redirect:" + url;
	}

	protected String redirect(HttpServletRequest request, String url) {
		if (!url.contains(".")) {
			url = url + ".html";
		}
		return "redirect:" + getMobileLink(request, url);
	}

	protected String redirect(HttpServletResponse response, String url) throws IOException {
		if (!url.contains(".")) {
			url = url + ".html";
		}
		response.sendRedirect(url);
		return null;
	}

	protected boolean isMobile(HttpServletRequest request) {
		if (RequestUtil.isFromWeixin(request)) {
			request.setAttribute("isFromWeixin", "true");
		}
		if (CommonUtil.isMobile(request)) {
			request.setAttribute("isMobilePage", "true");
			return true;
		}
		return false;
	}

	protected boolean isApp(HttpServletRequest request) {
		if (CommonUtil.isApp(request)) {
			request.setAttribute("isApp", "true");
			return true;
		}
		return false;
	}

	protected String getFtlPath(HttpServletRequest request, String ftl) {
		setHost(request);
		if (isMobile(request)) {
			return "/wap" + ftl;
		}
		return "/web" + ftl;
	}

	protected String getFtlPath(HttpServletRequest request, String ftl, boolean checkAgent) {
		setHost(request);
		if (RequestUtil.isFromWeixin(request)) {
			request.setAttribute("isFromWeixin", "true");
		}
		if (checkAgent) {
			if (CommonUtil.isFromMobile(request)) {
				request.setAttribute("isMobilePage", "true");
				return "/wap" + ftl;
			} else {
				return "/web" + ftl;
			}
		}
		if (isMobile(request)) {
			return "/wap" + ftl;
		}
		return "/web" + ftl;
	}

	private void setHost(HttpServletRequest request) {
		try {
			String host = request.getHeader("Host");
			if (StringUtils.isNotBlank(host) && host.contains("yuanshanbao")) {
				request.setAttribute("isYuanshanbao", "true");
			} else if (StringUtils.isNotBlank(host) && host.contains("dachuanbao")) {
				request.setAttribute("isDachuanbao", "true");
			} else if (StringUtils.isNotBlank(host) && host.contains("yuanshanbx.com")) {
				request.setAttribute("isYuanshanbx", "true");
			} else if (StringUtils.isNotBlank(host) && host.contains("huhabao.com")) {
				request.setAttribute("isHuhabao", "true");
			}
			String isHttps = request.getHeader("IsHttps");
			if ("false".equals(isHttps)) {
				request.setAttribute("isHttps", "false");
			} else {
				request.setAttribute("isHttps", "true");
			}
		} catch (Exception e) {
			LoggerUtil.error("[setHost]", e);
		}
	}

	protected String getMobileLink(HttpServletRequest request, String link) {
		if (isMobile(request)) {
			link = "/m" + link;
		}
		return link;
	}

	protected void verifySmsToken(HttpServletRequest request) {
		String sessionToken = (String) request.getSession().getAttribute(SessionConstants.SMS_TOKEN);
		Long time = (Long) request.getSession().getAttribute(SessionConstants.TOKEN_TIME);
		String smsToken = request.getParameter("smsToken");
		if (!(StringUtils.isNotBlank(sessionToken) && sessionToken.equals(smsToken))) {
			LoggerUtil.info("[VerifySmsToken] mobile={}, sessionToken={}, smsToken={}", request.getParameter("mobile"),
					sessionToken, smsToken);
		}
		if (time == null || (System.currentTimeMillis() - time) < 4000) {
			LoggerUtil.info("[VerifySmsToken] mobile={}, time={}", request.getParameter("mobile"), time);
		}
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

	protected String getAppKey(HttpServletRequest request) {
		String appKey = request.getParameter("pt");
		if (StringUtils.isNotBlank(appKey)) {
			if (AppType.RUIDAI_SHORT.equals(appKey)) {
				return AppType.RUIDAI;
			} else if (AppType.XINGDAI_SHORT.equals(appKey)) {
				return AppType.XINGDAI;
			}
		} else {
			String appId = request.getParameter("appId");
			appKey = appService.getAppKey(appId);
			if (StringUtils.isNotBlank(appKey) && (appKey.equals(AppType.XINGDAI) || appKey.equals(AppType.RUIDAI))) {
				return appKey;
			}
		}
		return "xingdai";
	}

	protected void setAdvertisement(Integer client, Map<String, Object> resultMap, String channel, String appKey,
			Long activityId, String position) {
		List<AdvertisementVo> bannerList = setAdvertisementLink(position, AdvertisementPosition.BANNER, channel,
				appKey, activityId, client);
		List<AdvertisementVo> connerList = setAdvertisementLink(position, AdvertisementPosition.CONNER, channel,
				appKey, activityId, client);
		List<AdvertisementVo> popupList = setAdvertisementLink(position, AdvertisementPosition.POPUP, channel, appKey,
				activityId, client);
		resultMap.put("bannerList", bannerList);
		if (popupList != null && popupList.size() > 0) {
			AdvertisementVo popupAd = popupList.get(0);
			resultMap.put("popupAd", popupAd);
		}
		if (connerList != null && connerList.size() > 0) {
			AdvertisementVo connerAd = connerList.get(0);
			resultMap.put("connerAd", connerAd);
		}
	}

	protected List<AdvertisementVo> setAdvertisementLink(String config, String position, String channel, String appKey,
			Long activityId, Integer client) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ConfigManager.setConfigMap(resultMap, activityId, channel, appKey);
		String constant = config + AdvertisementPosition.getConfig(position);
		List<Advertisement> list = ConfigManager.getAdvertisementList((String) resultMap.get(constant), activityId,
				channel);
		List<AdvertisementVo> resultList = new ArrayList<AdvertisementVo>();
		for (Advertisement advertisement : list) {
			String link = "";
			if (client == null || Advertisement.IS_IOS == client || Advertisement.IS_ANDROID == client) {
				link = advertisement.getAppLink(position, channel);
			} else {
				link = advertisement.getJumperLink(position, channel);
			}
			advertisement.setLink(link);
			advertisement.setCount(advertisementService.getAdvertisementCount(advertisement.getAdvertisementId()));
			if (advertisement.getShowType() != null) {
				advertisement.setPosition(constant);
				String cycleTime = (String) resultMap.get(ConfigConstants.ADVERTISEMENT_CYCLE_TIME_CONFIG);
				if (ValidateUtil.isNumber(cycleTime)
						&& advertisement.getShowType().equals(AdvertisementShowType.PERIOD)) {
					advertisement.setCycleTime(Long.parseLong(cycleTime));
				}
			}
			resultList.add(new AdvertisementVo(advertisement));
		}
		return resultList;
	}

	public String format(String template, String name, String advertisementAmount) {
		return format(template, name, advertisementAmount, null);
	}

	public String format(String template, String name, String advertisementAmount, String province) {
		if (name != null) {
			template = template.replace(TEMPLATE_VARIABLE_NAME, name);
		}
		if (advertisementAmount != null) {
			template = template.replace(TEMPLATE_VARIABLE_AMOUNT, advertisementAmount);
		}
		if (province != null) {
			template = template.replace(TEMPLATE_VARIABLE_PROVINCE, province);
		}
		return template;
	}

	protected Long getProjectId(HttpServletRequest request) {
		return ConstantsManager.getProjectId(projectService, request);
	}

	protected void setSmsToken(HttpServletRequest request, Map<String, Object> map) {
		String prefix = "";
		List<SmsTokenList> list = generateRandomSmsTokenList();
		for (SmsTokenList tokenList : list) {
			int result = 0;
			for (SmsToken token : tokenList.getList()) {
				if (token.isEnable()) {
					if (token.getOperation().equals("+")) {
						result += token.getValue();
					} else {
						result -= token.getValue();
					}
				}
			}
			if (tokenList.isEnable()) {
				prefix += result + "";
			}
		}
		map.put("smsId", MD5Util.get(CommonUtil.getRandomID()));

		String randomID = MD5Util.get(CommonUtil.getRandomID());
		map.put("smsToken", randomID);
		map.put("smsTokenList", list);
		request.getSession().setAttribute(SessionConstants.SMS_TOKEN, randomID + prefix);
		request.getSession().setAttribute(SessionConstants.TOKEN_TIME, System.currentTimeMillis());
	}

	private List<SmsTokenList> generateRandomSmsTokenList() {
		// TODO Auto-generated method stub
		return null;
	}

	protected PageList<ProductVo> convertVo(HttpServletRequest request, List<Product> productList, String token,
			PageBounds pageBounds) {
		PageList<ProductVo> voList = new PageList<ProductVo>();
		User user = null;
		if (StringUtils.isNotEmpty(token)) {
			user = userService.selectUserByToken(token);
		}
		int index = 0;
		for (Product param : productList) {
			// if (isApprovalEdition(request, param)) {
			// param.setApplyInterface(null);
			// }
			param.setApplyCount(applyService.getProductApplyCount(param.getProductId()));
			ProductVo vo = new ProductVo(param);
			index++;
			if (pageBounds.getPage() == 1) {
				switch (index) {
				case 1:
					setRecommentTagsVo(vo, "top1", TOP1);
					break;
				case 2:
					setRecommentTagsVo(vo, "top2", TOP2);
					break;
				case 3:
					setRecommentTagsVo(vo, "top3", TOP3);
					break;
				default:
					break;
				}
			}
			if (user != null) {
				applyService.checkExist(user, vo);
			}
			voList.add(vo);
		}
		return voList;
	}

	private void setRecommentTagsVo(ProductVo vo, String name, String imageUrl) {
		// List<TagsVo> tagsList = vo.getRecommendTagsList();
		// TagsVo tags = new TagsVo();
		// tags.setName(name);
		// tags.setImage(imageUrl);
		// if (tagsList == null || tagsList.size() == 0) {
		// tagsList = new ArrayList<TagsVo>();
		// tagsList.add(tags);
		// } else {
		// tagsList.add(0, tags);
		// }
		// vo.setRecommendTagsList(tagsList);
	}
	
	/**
	 * 短信接口添加标识，区分远山，青蓝
	 * 
	 * @param request
	 * @return
	 */
	protected String getSmsSignName(HttpServletRequest request) {
		String center = SmsConstant.TEMPLATE_SMS_JUBAOKEJI_SIGN_NAME;
		try {

//			String signName = getConfigSignName(request);
//			if (StringUtils.isNotBlank(signName)) {
//				return signName;
//			}
			String host = request.getHeader("Host");
			if (StringUtils.isNotBlank(host) && host.contains("huhabao.com")) {
				return SmsConstant.TEMPLATE_SMS_QINGLAN_SIGN_NAME;
			}
			if (StringUtils.isNotBlank(host) && host.contains("dachuanbao.cn")) {
				return SmsConstant.TEMPLATE_SMS_HANQIAN_SIGN_NAME;
			}
			if (StringUtils.isNotBlank(host) && host.contains("ruidaizhijia.com")) {
				return SmsConstant.TEMPLATE_SMS_JUBAOKEJI_SIGN_NAME;
			}
			if (StringUtils.isNotBlank(host) && host.contains("ruidaiwang.cn")) {
				return SmsConstant.TEMPLATE_SMS_JUBAOKEJI_SIGN_NAME;
			}
		} catch (Exception e) {
			LoggerUtil.error("sms center error", e);
		}
		return center;
	}
}
