package com.yuanshanbao.ms.controller.advertisement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.UploadUtils;
import com.yuanshanbao.dsp.advertisement.model.Advertisement;
import com.yuanshanbao.dsp.advertisement.model.AdvertiserStatus;
import com.yuanshanbao.dsp.advertisement.service.AdvertisementService;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.bill.model.Bill;
import com.yuanshanbao.dsp.bill.service.BillService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.partner.agent.AbstractDspHandler;
import com.yuanshanbao.dsp.partner.agent.DspAgentType;
import com.yuanshanbao.dsp.partner.agent.feifan.model.FeiFanMedia;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.ms.controller.common.AdminServerController;
import com.yuanshanbao.ms.model.admin.User;
import com.yuanshanbao.ms.service.admin.GroupService;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Controller
@RequestMapping("/admin/advertiser")
public class AdminAdvertiserController extends PaginationController {

	private static final String PAGE_LIST = "advertisement/advertiser/listAdvertiser";

	private static final String PAGE_INSERT = "advertisement/advertiser/insertAdvertiser";

	private static final String PAGE_UPDATE = "advertisement/advertiser/updateAdvertiser";

	private static final String PAGE_VIEW = "advertisement/advertiser/viewAdvertiser";

	private static final String PAGE_LIST_VIEW = "advertisement/advertiser/listAdvertisementOfAdvertiser";

	private static final String PAGE_RECHARGE_WINDOW = "advertisement/advertiser/dsp/rechargeAdvertiser";

	private static final String PAGE_DSP_ADVERTISER_LIST = "advertisement/advertiser/dsp/listDspAdvertiser";

	@Autowired
	private AdvertiserService advertiserService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private BillService billService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, Advertiser advertiser, HttpServletRequest request, HttpServletResponse response) {
		Advertiser user = getBindAdvertiserByUser();
		if (user != null) {
			advertiser.setAdvertiserId(user.getAdvertiserId());
		}
		advertiser.setProjectId(getProjectId(request));
		Object object = advertiserService.selectAdvertiser(advertiser, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertWindow.do")
	public String insertWindow(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("statusList", CommonStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("partnerList", DspAgentType.getCodeDescriptionMap().entrySet());
		request.setAttribute("feifanMediaList", FeiFanMedia.getCodeDescriptionMap().entrySet());
		return PAGE_INSERT;
	}

	@ResponseBody
	@RequestMapping("/insert.do")
	public Object insert(Advertiser advertiser, User user, MultipartFile image, String partner,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if (image != null && !image.isEmpty()) {
				advertiser.setBusinessPicture(UploadUtils.uploadFile(image, "file/game"));
			}
			validateParameters(advertiser);
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			advertiser.setBindUserName(user.getUsername());
			advertiser.setProjectId(getProjectId(request));
			advertiser.setBalance(new BigDecimal(0));
			user.setPassword(encoder.encodePassword(user.getPassword(), null));
			user.setProjectId(getProjectId(request));
			user.setPwd_keep_time(1000);
			if (userService.isUserExits(user.getUsername())) {
				result.put(RET_CODE_PARAM, RET_INTERROR);
				result.put(ComRetCode.RET_DESC, "该用户已经存在，请重新输入用户名！");
				return result;
			}
			// 调用合作方
			if (!StringUtils.isEmpty(partner)) {
				AbstractDspHandler dspHandler = (AbstractDspHandler) Class.forName(partner).newInstance();
				Advertiser resultAdvertiser = dspHandler.creatAdvertiser(advertiser);
				if (resultAdvertiser == null) {
					throw new BusinessException(ComRetCode.PARTNER_INTERFACE_ERROR);
				}
			}
			userService.insertUser(user);
			advertiserService.insertAdvertiser(advertiser);
			groupService.insertUserGroups(user.getUsername(), new String[] { "002", "002007" });
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertiser insert function - upload image error", e2);
		}

		return result;
	}

	@RequestMapping("/updateWindow.do")
	public String updateWindow(HttpServletRequest request, HttpServletResponse response, Advertiser advertiser) {
		List<Advertiser> list = advertiserService.selectAdvertiser(advertiser, new PageBounds());
		if (list != null && list.size() >= 0) {
			advertiser = list.get(0);
		}
		request.setAttribute("statusList", AdvertiserStatus.getCodeDescriptionMap().entrySet());
		request.setAttribute("itemEdit", advertiser);
		return PAGE_UPDATE;
	}

	@ResponseBody
	@RequestMapping("/update.do")
	public Object update(Advertiser advertiser, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			validateParameters(advertiser);
			advertiserService.updateAdvertiser(advertiser);
			AdminServerController.refreshConfirm();
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("advertiser update function - upload image error", e2);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete.do")
	public Object delete(Long advertiserId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			if (advertiserId == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			Advertiser advertiser = new Advertiser();
			advertiser.setStatus(CommonStatus.OFFLINE);
			advertiser.setAdvertiserId(advertiserId);
			advertiserService.updateAdvertiser(advertiser);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		}

		return result;
	}

	@RequestMapping("/view.do")
	public String view(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		Advertiser advertiser = getBindAdvertiserByUser();
		request.setAttribute("itemEdit", advertiser);
		return PAGE_VIEW;
	}

	@RequestMapping("/viewAdvertisement.do")
	public String viewAdvertisement(String range, Advertiser advertiser, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("advertiserId", advertiser.getAdvertiserId());
		return PAGE_LIST_VIEW;
	}

	@ResponseBody
	@RequestMapping("/viewAd.do")
	public Object viewAd(String range, Advertiser advertiser, String title, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		Advertisement advertisement = new Advertisement();
		advertisement.setAdvertiserId(advertiser.getAdvertiserId());
		advertisement.setTitle(title);
		Object resultList = advertisementService.selectAdvertisement(advertisement, getPageBounds(range, request));
		PageList pageList = (PageList) resultList;
		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("dsp/rechargeWindow.do")
	public String rechargeWindow(HttpServletRequest request, Long advertiserId, HttpServletResponse response) {
		Advertiser advertiser = advertiserService.selectAdvertiser(advertiserId);
		request.setAttribute("advertiser", advertiser);
		return PAGE_RECHARGE_WINDOW;
	}

	@ResponseBody
	@RequestMapping("/recharge.do")
	public Object recharge(Bill bill, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			User user = getCurrentUser();
			if (user == null) {
				throw new Exception();
			}
			if (bill.getAdvertiserId() == null || bill.getAmount() == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}
			billService.recharge(bill);
			LoggerUtil.info("recharge success operator=" + user.getUsername());
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);
		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.FAIL);
			LoggerUtil.error("recharge", e2);
		}
		return result;
	}

	@RequestMapping("dsp/list.do")
	public String dspList(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_DSP_ADVERTISER_LIST;
	}

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("dsp/query.do")
	public Object dspQuery(String range, HttpServletRequest request, HttpServletResponse response) {
		Advertiser advertiser = new Advertiser();
		advertiser.setProjectId(getProjectId(request));
		Object object = advertiserService.selectAdvertiser(advertiser, getPageBounds(range, request));
		PageList pageList = (PageList) object;
		return setPageInfo(request, response, pageList);
	}
}
