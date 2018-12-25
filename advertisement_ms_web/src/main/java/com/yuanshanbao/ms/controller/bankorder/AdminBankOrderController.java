package com.yuanshanbao.ms.controller.bankorder;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.StringUtil;
import com.yuanshanbao.dsp.activity.model.Activity;
import com.yuanshanbao.dsp.agency.model.Agency;
import com.yuanshanbao.dsp.agency.service.AgencyService;
import com.yuanshanbao.dsp.bankcard.model.BankCard;
import com.yuanshanbao.dsp.bankcard.model.vo.BankCardStatus;
import com.yuanshanbao.dsp.bankcard.service.BankCardService;
import com.yuanshanbao.dsp.config.ConfigManager;
import com.yuanshanbao.dsp.core.InterfaceRetCode;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.model.ProductStatus;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.ms.controller.base.PaginationController;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

/**
 * Created by Administrator on 2018/11/13.
 */
@Controller
@RequestMapping("/admin/bankOrder")
public class AdminBankOrderController extends PaginationController {
	private static final String WANGZHUAN = "wangzhuan";

	private static final String PAGE_LIST = "advertisement/bankorder/listBankOrder";

	private static final String PAGE_INSERT = "advertisement/bankorder/insertBankOrder";

	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private ProductService productService;

	@Autowired
	private AgencyService agencyService;

	@RequestMapping("/list.do")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		return PAGE_LIST;
	}

	@ResponseBody
	@RequestMapping("/query.do")
	public Object query(String range, HttpServletRequest request, HttpServletResponse response, BankCard bankCard,
			Agency agency) {

		List<BankCard> bankCardList = bankCardService.selectBankCards(bankCard, getPageBounds(range, request));
		// 核对名单
		// List<Agency> agencyList = agencyService.selectAgencys(agency, new
		// PageBounds());

		PageList<BankCard> pageList = (PageList) bankCardList;

		return setPageInfo(request, response, pageList);
	}

	@RequestMapping("/insertBankOrderWindow.do")
	public String uploadFileWindow(String range, HttpServletRequest request, HttpServletResponse reponse) {

		Activity activity = ConfigManager.getActivityByKey(WANGZHUAN);
		Product product = new Product();
		product.setActivityId(activity.getActivityId());
		product.setStatus(ProductStatus.ONLINE);
		List<Product> productList = productService.selectProducts(product, new PageBounds());
		request.setAttribute("productList", productList);
		return PAGE_INSERT;
	}

	@RequestMapping("/insertBankOrder.do")
	@ResponseBody
	public Object uploadFile(HttpServletRequest request, HttpServletResponse reponse,
			@RequestParam("file") MultipartFile file, @RequestParam("productId") String productId) {
		Map<String, Object> result = new HashMap<>();
		try {
			String name = file.getName();
			List<BankCard> bankCardList = exportBankInfo(file);
			// 入账
			Agency agency = new Agency();
			List<Agency> agencies = agencyService.selectAgencys(agency, new PageBounds());
			bankCardService.transferUserAccount(bankCardList, productId);

			InterfaceRetCode.setAppCodeDesc(result, ComRetCode.SUCCESS);

		} catch (BusinessException e) {
			InterfaceRetCode.setAppCodeDesc(result, e.getReturnCode(), e.getMessage());
		} catch (Exception e2) {
			LoggerUtil.error("bankOrder insert function - upload image error", e2);
		}

		return result;

	}

	private List<BankCard> exportBankInfo(MultipartFile file) {
		List<BankCard> list = new ArrayList<BankCard>();
		try {
			if (file == null) {
				throw new BusinessException(ComRetCode.WRONG_PARAMETER);
			}

			InputStream inputStream = file.getInputStream();
			XSSFWorkbook wb = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row = sheet.getRow(0);
			XSSFCell cell = null;
			for (int i = 0; i < row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				System.out.print(cell.getStringCellValue() + ",");
			}
			// 根据sheet获取其它所有的HSSFRow对象,这些对象封装了数据行中所有的数据
			// sheet.getLastRowNum():最后一行的编号
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				BankCard bankCard = new BankCard();
				// 根据row获取所有的HSSFCell对象,这些对象封装每一列的数据
				for (int j = 0; j < row.getLastCellNum(); j++) {
					cell = row.getCell(j);

					if (cell != null) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
					}
					String cellValueForStr = getCellValueForStr(cell);
					switch (cell.getColumnIndex()) {
					case 0:
						if (cellValueForStr == null) {
							bankCard.setName("");
						} else {
							if (!"姓名".equals(cellValueForStr)) {
								bankCard.setName(cellValueForStr);
							}

						}
						break;
					case 1:
						if (cellValueForStr == null) {
							bankCard.setMobile("");
						} else {
							if (!"手机号".equals(cellValueForStr)) {
								bankCard.setMobile(cellValueForStr);
							}
						}
						break;
					case 2:
						if (cellValueForStr == null) {
							bankCard.setStatus(BankCardStatus.NULL);
						} else {
							if (!"状态".equals(cellValueForStr)) {
								if (BankCardStatus.APPROVED_DESCRIPTION.equals(cellValueForStr)) {
									bankCard.setStatus(BankCardStatus.APPROVED);
								} else if (BankCardStatus.REFESEND_TO_DESCRIPTION.equals(cellValueForStr)) {
									bankCard.setStatus(BankCardStatus.REFESEND_TO);
								} else if (BankCardStatus.CANCEL_DESCRIPTION.equals(cellValueForStr)) {
									bankCard.setStatus(BankCardStatus.CANCEL);
								} else if (BankCardStatus.APPROVING_DESCRIPTION.equals(cellValueForStr)) {
									bankCard.setStatus(BankCardStatus.APPROVING);
								}

							}

						}
						break;
					}
					LoggerUtil.info("Excel info ==" + getCellValueForStr(cell) + ",");
				}
				System.out.println();
				if (!(StringUtil.isEmpty(bankCard.getBankName()) && StringUtil.isEmpty(bankCard.getMobile()))) {
					list.add(bankCard);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public String getCellValueForStr(XSSFCell cell) {
		String str = null;

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			str = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			str = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date d = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				str = sdf.format(d);
			} else {
				str = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			str = cell.getCellFormula();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			str = "";
			break;
		default:
			str = "";
		}
		return str;
	}

}
