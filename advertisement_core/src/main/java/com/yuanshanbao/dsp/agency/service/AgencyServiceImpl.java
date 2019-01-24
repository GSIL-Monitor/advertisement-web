package com.yuanshanbao.dsp.agency.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.common.util.ExcelUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.dsp.agency.dao.AgencyDao;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.model.vo.AgencyType;
import com.yuanshanbao.dsp.agency.model.vo.AgencyVo;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.dsp.user.model.UserLevel;
import com.yuanshanbao.dsp.user.service.UserService;
import com.yuanshanbao.paginator.domain.PageBounds;

/**
 * Created by Administrator on 2018/10/22.
 */
@Service
public class AgencyServiceImpl implements AgencyService {
	public final static BigDecimal MANAGER_INDIRET_PERCENTAGE = BigDecimal.valueOf(0.1);
	public final static BigDecimal DIRECTOR_INDIRET_PERCENTAGE = BigDecimal.valueOf(0.15);
	public final static BigDecimal CEO_INDIRET_PERCENTAGE = BigDecimal.valueOf(0.25);
	public final static BigDecimal NEW_CEO_PERCENTAGE = BigDecimal.valueOf(0.05);

	public final static String NOW_DATE = "20181210";
	@Autowired
	private AgencyDao agencyDao;

	@Autowired
	private UserService userService;

	@Override
	public List<Agency> selectAgencys(Agency agency, PageBounds pageBounds) {
		return agencyDao.selectAgencys(agency, pageBounds);
	}

	@Override
	public List<Agency> selectAgency(Long userId) {
		if (userId == null) {
			throw new BusinessException(ComRetCode.FAIL);
		}
		return agencyDao.selectAgency(userId);
	}

	@Override
	public int insertAgency(Agency agency) {
		int result = -1;
		result = agencyDao.insertAgency(agency);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
		return result;
	}

	@Override
	public int deleteAgency(String Id) {
		return 0;
	}

	@Override
	public int updateAgency(Agency agency) {
		int result = -1;
		result = agencyDao.updateAgency(agency);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
		return result;

	}

	@Override
	public int updateBankTime(Agency agency) {
		int result = -1;
		result = agencyDao.updateBankTime(agency);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
		return result;
	}

	@Override
	public List<Agency> selectAgencys(List<Long> IdList) {
		return null;
	}

	@Override
	public BigDecimal getAgencyBrokerage(Long inviteUserId) {

		if (inviteUserId == null) {
			throw new BusinessException(ComRetCode.FAIL);
		}

		return agencyDao.getAgencyBrokerage(inviteUserId);
	}

	@Override
	public int selectAgencyByInviteId(Long inviteUserId) {
		int result = -1;
		result = agencyDao.selectAgencyByInviteId(inviteUserId);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
		return result;
	}

	@Override
	public int selectAgencyByInviteIdCount(Long inviteUserId) {
		int result = -1;
		result = agencyDao.selectAgencyByInviteIdCount(inviteUserId);
		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
		return result;
	}

	@Override
	public List<AgencyVo> getAgencyInfos(User user, Agency agency, PageBounds pageBounds) {

		List<AgencyVo> agencyVoList = new LinkedList<>();
		try {
			agency.setInviteUserId(user.getUserId());
			List<Agency> agencyList = selectAgencys(agency, pageBounds);

			for (Iterator<Agency> iterator = agencyList.iterator(); iterator.hasNext();) {
				Agency agen = (Agency) iterator.next();
				if (agen.getProductId() != null) {
					iterator.remove();
				}
			}
			if (agencyList.size() != 0) {
				for (Agency agen : agencyList) {
					User agencyUser = userService.selectUserById(agen.getUserId());
					User inviteUser = userService.selectUserById(agen.getInviteUserId());

					AgencyVo agencyVo = new AgencyVo();
					agencyVo.setUserId(agen.getUserId());
					agencyVo.setInviteTime(agen.getInviteTimeValue());
					if (agencyUser != null) {
						agencyVo.setAgencyName(agencyUser.getNickName());
						agencyVo.setUserLevel(agencyUser.getLevelValue());
					}
					if (inviteUser != null) {
						agencyVo.setInviteUserLevel(inviteUser.getLevelValue());
					} else {
						agencyVo.setInviteUserLevel(UserLevel.MANAGER_DESCRIPTION);
					}
					agencyVoList.add(agencyVo);
				}
			} else {
				return agencyVoList;

			}
		} catch (Exception e) {
			LoggerUtil.error("[getAgencyInfos error:] ", e);
		}

		return agencyVoList;
	}

	@Override
	public List<AgencyVo> getAgencyListVo(List<Agency> twoAgencyList, User user) {

		List<AgencyVo> agencyVoList = new ArrayList<>();

		if (twoAgencyList.size() > 0) {
			Collections.sort(twoAgencyList, new Comparator<Agency>() {
				@Override
				public int compare(Agency b, Agency a) {
					return a.getUpdateTime().compareTo(b.getUpdateTime());
				}
			});
		}

		for (Agency agen : twoAgencyList) {
			User inivteUser = userService.selectUserById(agen.getInviteUserId());
			AgencyVo agencyVo = new AgencyVo();
			if (inivteUser != null) {
				agencyVo.setName(inivteUser.getNickName());
			}
			agencyVo.setProductName(agen.getProductName());
			agencyVo.setUpdateTime(agen.getUpdateTimeValue());
			agencyVo.setStatus(agen.getStatusValue());
			String createTime = DateUtils.format(agen.getCreateTime(), DateUtils.DATE_FORMAT_YYYYMMDD);

			// if (DateUtils.compareTwoDates(DateUtils.format(new Date(),
			// DateUtils.DATE_FORMAT_YYYYMMDD), "20181211")) {
			// agencyVo.setBrokerage((agen.getBrokerage().multiply(CEO_INDIRET_PERCENTAGE)).setScale(2,
			// RoundingMode.HALF_UP));
			// }
			if (DateUtils.compareTwoDates(createTime, NOW_DATE)) {
				agencyVo.setBrokerage((agen.getBrokerage().multiply(NEW_CEO_PERCENTAGE)).setScale(2,
						RoundingMode.HALF_UP));
			} else {
				if (user.getLevel() != null && user.getLevel() == UserLevel.MANAGER) {
					agencyVo.setBrokerage((agen.getBrokerage().multiply(MANAGER_INDIRET_PERCENTAGE)).setScale(2,
							RoundingMode.HALF_UP));
				} else if (user.getLevel() != null && user.getLevel() == UserLevel.MAJORDOMO) {
					agencyVo.setBrokerage((agen.getBrokerage().multiply(DIRECTOR_INDIRET_PERCENTAGE)).setScale(2,
							RoundingMode.HALF_UP));
				} else if (user.getLevel() != null && user.getLevel() == UserLevel.BAILLIFF) {
					agencyVo.setBrokerage((agen.getBrokerage().multiply(CEO_INDIRET_PERCENTAGE)).setScale(2,
							RoundingMode.HALF_UP));
				} else {
					agencyVo.setBrokerage((agen.getBrokerage().multiply(MANAGER_INDIRET_PERCENTAGE)).setScale(2,
							RoundingMode.HALF_UP));
				}

			}
			agencyVoList.add(agencyVo);
		}

		return agencyVoList;
	}

	@Override
	public BigDecimal getBrokerages(Agency agency, User user, PageBounds pageBounds) {
		return getSumBrokerage(agency, pageBounds, user);
	}

	public BigDecimal getSumBrokerage(Agency agency, PageBounds pageBounds, User user) {
		List<Agency> oneAgencyList = selectAgencys(agency, pageBounds);

		List<Long> oneInviteUserIds = new ArrayList<>();
		List<Long> twoInviteUserIds = new ArrayList<>();
		BigDecimal oneAgencyBrokerage = BigDecimal.valueOf(0);
		BigDecimal twoAgencyBrokerage = BigDecimal.valueOf(0);
		BigDecimal brokerage = BigDecimal.valueOf(0);
		BigDecimal compareTimeBrokerage = BigDecimal.valueOf(0);
		BigDecimal timeBrokerageBigDecimal = BigDecimal.valueOf(0);

		for (Iterator<Agency> iterator = oneAgencyList.iterator(); iterator.hasNext();) {
			Agency agen = (Agency) iterator.next();
			if (agen.getUserId() == null) {
				iterator.remove();
			}
		}
		for (Agency agencyIds : oneAgencyList) {
			if (oneAgencyList.size() == 0) {
				break;
			}
			oneInviteUserIds.add(Long.valueOf(agencyIds.getUserId()));
		}
		if (oneInviteUserIds.size() != 0) {
			oneAgencyBrokerage = agencyDao.getSumBrokerage(oneInviteUserIds);
		}
		List<Agency> twoAgencyList = new ArrayList<>();
		for (Agency agen : oneAgencyList) {
			if (oneAgencyList.size() == 0) {
				break;
			}
			agency.setInviteUserId(agen.getUserId());
			twoAgencyList.addAll(selectAgencys(agency, new PageBounds()));
		}

		for (Iterator<Agency> iterator = twoAgencyList.iterator(); iterator.hasNext();) {
			Agency agen = (Agency) iterator.next();
			if (agen.getUserId() == null) {
				iterator.remove();
			}
		}
		for (Agency agencyIds : twoAgencyList) {
			twoInviteUserIds.add(Long.valueOf(agencyIds.getUserId()));
		}
		if (twoInviteUserIds.size() != 0) {
			twoAgencyBrokerage = agencyDao.settledTwoAgencyBrokerages(twoInviteUserIds);
			if (twoAgencyBrokerage == null) {
				twoAgencyBrokerage = BigDecimal.ZERO;
			}

			if (user != null && user.getLevel() == UserLevel.MANAGER) {
				brokerage = twoAgencyBrokerage.multiply(MANAGER_INDIRET_PERCENTAGE);
			} else if (user != null && user.getLevel() == UserLevel.MAJORDOMO) {
				brokerage = twoAgencyBrokerage.multiply(DIRECTOR_INDIRET_PERCENTAGE);
			} else if (user != null && user.getLevel() == UserLevel.BAILLIFF) {
				brokerage = twoAgencyBrokerage.multiply(CEO_INDIRET_PERCENTAGE);
			} else {
				brokerage = twoAgencyBrokerage.multiply(MANAGER_INDIRET_PERCENTAGE);
			}
			if (DateUtils.compareTwoDates(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YYYYMMDD), NOW_DATE)) {
				compareTimeBrokerage = agencyDao.settledBrokerageAndTimeCompare(twoInviteUserIds);
				if (compareTimeBrokerage == null) {
					compareTimeBrokerage = BigDecimal.ZERO;
				}
				timeBrokerageBigDecimal = compareTimeBrokerage.multiply(NEW_CEO_PERCENTAGE);
			}

		}
		if (oneAgencyBrokerage == null) {
			oneAgencyBrokerage = BigDecimal.ZERO;
		}
		if (brokerage == null) {
			brokerage = BigDecimal.ZERO;
		}
		BigDecimal sumAgencyBrokerage = oneAgencyBrokerage.add(brokerage).add(timeBrokerageBigDecimal);
		return sumAgencyBrokerage;
	}

	@Override
	public List<AgencyVo> getVIPAgentInfos(User user, Agency agency, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		List<AgencyVo> agencyVos = new ArrayList<AgencyVo>();
		agency.setInviteUserId(user.getUserId());
		agency.setType(AgencyType.AGENT_CREDIT_CARD);
		List<Agency> agents = selectAgencys(agency, pageBounds);
		if (agents.size() != 0) {
			for (Agency agent : agents) {
				AgencyVo agencyVo = new AgencyVo();
				agencyVo.setAgencyName(agent.getName());
				agencyVo.setMobile(agent.getMobile());
				agencyVo.setInviteTime(agent.getUpdateTimeValue());
				agencyVos.add(agencyVo);
			}
		}
		return agencyVos;
	}

	@Override
	public BigDecimal queryVIPAgenctSumBrokerage(Long inviteUserId) {
		// TODO Auto-generated method stub
		if (inviteUserId == null) {
			throw new BusinessException(ComRetCode.FAIL);
		}
		return agencyDao.queryVIPAgenctSumBrokerage(inviteUserId);
	}

	public String downAgency(List<AgencyVo> list) {
		String path = null;

		if (list.size() != 0 && list != null) {
			Map<String, List<List<String>>> sheetMap = new HashMap<String, List<List<String>>>();
			List<List<String>> rowList = new ArrayList<List<String>>();
			List<String> columnList = new ArrayList<String>();
			columnList.add("间接邀请人ID");
			columnList.add("直接邀请人ID");
			columnList.add("办卡人ID");
			columnList.add("办卡人姓名");
			columnList.add("办卡人手机号");
			columnList.add("产品名称");
			columnList.add("佣金");
			columnList.add("状态");
			columnList.add("更新时间");
			rowList.add(columnList);

			for (AgencyVo temp : list) {
				columnList = new ArrayList<String>();
				if (temp.getInviteUserId() != null) {
					User user = userService.selectUserById(temp.getInviteUserId());
					if (user != null && user.getInviteUserId() != null) {
						columnList.add(user.getInviteUserId().toString());
					}
				}
				columnList.add(temp.getInviteUserId().toString());
				if (temp.getUserId() != null) {
					columnList.add(temp.getUserId().toString());
				} else {
					columnList.add("");
				}
				columnList.add(temp.getName());
				columnList.add(temp.getMobile());
				columnList.add(temp.getProductName());
				columnList.add(temp.getBrokerageValue());
				columnList.add(temp.getStatusValue());
				columnList.add(temp.getUpdateTimeValue());
				rowList.add(columnList);
			}
			sheetMap.put("sheet1", rowList);
			try {
				path = ExcelUtil.createSuffixXlsExcelByMoreSheets(sheetMap, "数据统计表");
			} catch (Exception e) {
				LoggerUtil.error("[excel creat error]", e);
			}
		}
		return path;
	}

}
