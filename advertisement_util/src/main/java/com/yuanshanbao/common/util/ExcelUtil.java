package com.yuanshanbao.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * 读取，写入xls文件
 * 
 * @author lbc
 * @version 1.0
 * @since 16-1-18
 */
public class ExcelUtil {

	public static final String COMMON_EXCEL_PASSWORD = "yuanShan123";

	// /**
	// *
	// * @param title
	// * @param toList
	// * @throws WriteException
	// * @throws IOException
	// */
	// public static String createExcel(List<String> title, List<List<String>>
	// toList, String name) throws Exception {
	// File file = FileUtil.getFileByDirAndFile("/form",
	// UUID.randomUUID().toString() + ".xls");
	// String path = "form/" + name + "_" + new
	// SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".xls";
	// OutputStream os = new FileOutputStream(file);
	// long start = System.currentTimeMillis();
	// WritableWorkbook wwb;
	// WritableCellFormat wc = new WritableCellFormat();
	// wc.setBackground(jxl.format.Colour.ROSE);
	// wc.setAlignment(Alignment.CENTRE);
	// wwb = Workbook.createWorkbook(os);
	// WritableSheet sheet = wwb.createSheet("导出列表", 0);
	// Label label;
	// for (int i = 0; i < title.size(); i++) {
	// label = new Label(i, 0, title.get(i), wc);
	// sheet.addCell(label);
	// }
	// for (int i = 0; i < toList.size(); i++) {
	// Label[] labels = new Label[title.size()];
	// for (int j = 0; j < title.size(); j++) {
	// try {
	// labels[j] = new Label(j, i + 1, toList.get(i).get(j));
	// sheet.addCell(labels[j]);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// wwb.write();
	// wwb.close();
	// os.close();
	// LoggerUtil.info("Excel本地文件保存完成");
	// InputStream is = new FileInputStream(file);
	// UploadUtils.uploadFiles(is, file.length(), path);
	// is.close();
	// long end = System.currentTimeMillis();
	// System.out.println("----use the time is:" + (end - start) + "ms");
	// LoggerUtil.info("上传到OSS完成");
	// return path;
	// }

	// /**
	// * 读取上传文件的标题，以及列
	// *
	// * @param file
	// * @return
	// * @throws Exception
	// */
	// public static List<List<String>> read(MultipartFile file) throws
	// Exception {
	// List<String> title = new ArrayList<String>();
	// List<List<String>> colsList = new ArrayList<List<String>>();
	// if (file.getSize() <= 0) {
	// return null;
	// }
	// InputStream in = file.getInputStream();
	// Workbook wb = Workbook.getWorkbook(in);
	// Sheet sheet = wb.getSheet(0);
	// int rows = sheet.getRows();
	// int cols = sheet.getColumns();
	// for (int j = 0; j < cols; j++) {
	// title.add(sheet.getCell(j, 0).getContents().trim());
	// }
	// colsList.add(title);
	// for (int i = 0; i < rows - 1; i++) {
	// List<String> perCol = new ArrayList<String>();
	// for (int j = 0; j < cols; j++) {
	// if (i + 1 < rows) {
	// perCol.add(sheet.getCell(j, i + 1).getContents().trim());
	// }
	// }
	// colsList.add(perCol);
	// }
	//
	// return colsList;
	// }

	// public static List<List<String>> read(InputStream in) throws Exception {
	// List<String> title = new ArrayList<String>();
	// List<List<String>> colsList = new ArrayList<List<String>>();
	//
	// Workbook wb = Workbook.getWorkbook(in);
	// Sheet sheet = wb.getSheet(0);
	// int rows = sheet.getRows();
	// int cols = sheet.getColumns();
	// for (int j = 0; j < cols; j++) {
	// title.add(sheet.getCell(j, 0).getContents().trim());
	// }
	// colsList.add(title);
	// for (int i = 0; i < rows - 1; i++) {
	// List<String> perCol = new ArrayList<String>();
	// for (int j = 0; j < cols; j++) {
	// if (i + 1 < rows) {
	// perCol.add(sheet.getCell(j, i + 1).getContents().trim());
	// }
	// }
	// colsList.add(perCol);
	// }
	// wb.close();
	// return colsList;
	// }

	private static org.apache.poi.ss.usermodel.Workbook getWorkBook(MultipartFile multipartFile, String password)
			throws IOException {
		String filePath = multipartFile.getOriginalFilename();
		org.apache.poi.ss.usermodel.Workbook workBook = null;
		if (filePath.endsWith("xls")) {
			workBook = new HSSFWorkbook(multipartFile.getInputStream());
		} else if (filePath.endsWith("xlsx")) {
			InputStream is;
			try {
				is = decryptExcel(multipartFile.getInputStream(), password);
			} catch (Exception e) {
				is = multipartFile.getInputStream();
			}
			workBook = new XSSFWorkbook(is);
		}
		return workBook;
	}

	private static InputStream decryptExcel(InputStream is, String password) throws Exception {
		if (StringUtils.isBlank(password)) {
			return is;
		}
		LoggerUtil.info("[begin decrypt] password:" + password);
		long start = System.currentTimeMillis();
		POIFSFileSystem pfs = new POIFSFileSystem(is);
		EncryptionInfo encInfo = new EncryptionInfo(pfs);
		Decryptor decryptor = Decryptor.getInstance(encInfo);
		decryptor.verifyPassword(password);
		long end = System.currentTimeMillis();
		is.close();
		LoggerUtil.info("[decrypt success] use time:{}ms", (end - start));
		return decryptor.getDataStream(pfs);
	}

	/**
	 * 读取上传文件 支持版本 ： 03-13（xls,xlsx） xlsx版本，密码可为空
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> readExcel03And13(MultipartFile multipartFile, String password) throws IOException {
		org.apache.poi.ss.usermodel.Workbook workBook = getWorkBook(multipartFile, password);
		return getRowsAndColums(workBook);
	}

	/**
	 * 读取网络地址的excel文件 (必须 xls,xlsx结尾)
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static List<List<String>> readExcel03And16(String path, boolean isXlsSuffix, String password)
			throws Exception {
		if (StringUtils.isBlank(path)) {
			return new ArrayList<List<String>>();
		}
		LoggerUtil.info("[read excel] path:{}, isXls:{}", path, isXlsSuffix);
		InputStream is = getInputStreamByUrl(path, isXlsSuffix);
		org.apache.poi.ss.usermodel.Workbook workBook = null;
		if (isXlsSuffix) {
			workBook = new HSSFWorkbook(is);
		} else {
			try {
				is = decryptExcel(is, password);
			} catch (Exception e) {
				// 解密失败，没有密码，重新下载获取输入流
				LoggerUtil.info("[decrypt fail get inputstream again]");
				is = getInputStreamByUrl(path, isXlsSuffix);
			}
			workBook = new XSSFWorkbook(is);
		}
		is.close();
		return getRowsAndColums(workBook);
	}

	/**
	 * 
	 * @param path
	 * @param isXlsSuffix
	 * @return
	 * @throws Exception
	 */
	private static InputStream getInputStreamByUrl(String path, boolean isXlsSuffix) throws Exception {
		URL url = null;
		try {
			url = new URL(path);
			URLConnection conn = url.openConnection();
			return conn.getInputStream();
		} catch (Exception e) {
			LoggerUtil.error("[excelUtil error" + ", path :" + path + ", isXls:" + isXlsSuffix + "]", e);
			throw e;
		}
	}

	private static List<List<String>> getRowsAndColums(org.apache.poi.ss.usermodel.Workbook workBook) {
		List<List<String>> excelList = new ArrayList<List<String>>();
		List<String> titles = new ArrayList<String>();
		try {
			// for (int numSheet = 0; numSheet < workBook.getNumberOfSheets();
			// numSheet++) {
			org.apache.poi.ss.usermodel.Sheet sheet = workBook.getSheetAt(0);
			if (sheet == null) {
				return null;
				// continue; //读取全部sheet使用
			}

			int lastRowIndex = sheet.getLastRowNum();
			// 读取首行 即,表头
			Row firstRow = sheet.getRow(0);
			for (int i = firstRow.getFirstCellNum(); i <= firstRow.getLastCellNum(); i++) {
				Cell cell = firstRow.getCell(i);
				String cellValue = getCellValue(cell, true);
				titles.add(cellValue);
			}
			excelList.add(titles);

			// 读取数据行
			List<String> columnList = null;
			for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
				columnList = new ArrayList<String>();
				Row currentRow = sheet.getRow(rowIndex);// 当前行
				if (currentRow == null) {// 该行不读取
					continue;
				}
				if (isRowEmpty(currentRow)) {
					continue;
				}
				int lastColumnIndex = currentRow.getLastCellNum();// 最后一列
				for (int columnIndex = 0; columnIndex <= lastColumnIndex; columnIndex++) {
					Cell currentCell = currentRow.getCell(columnIndex);// 当前单元格
					String currentCellValue = getCellValue(currentCell, true);// 当前单元格的值
					columnList.add(currentCellValue);
				}
				excelList.add(columnList);
			}
			return excelList;
			// }
		} catch (Exception e) {
			LoggerUtil.error("[get row and clumns error]", e);
			throw e;
		}
	}

	private static boolean isRowEmpty(Row row) {
		for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
			Cell currentCell = row.getCell(columnIndex);// 当前单元格
			String currentCellValue = getCellValue(currentCell, true);// 当前单元格的值
			if (StringUtils.isNotBlank(currentCellValue)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 读取本地文件
	 * 
	 * @param filePath
	 * @param password
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static List<List<String>> readExcel03And13(String filePath, String password) throws IOException {
		InputStream is = new FileInputStream(filePath);
		org.apache.poi.ss.usermodel.Workbook workBook = null;
		if (filePath.endsWith("xls")) {
			workBook = new HSSFWorkbook(is);
		} else if (filePath.endsWith("xlsx")) {
			try {
				is = decryptExcel(is, password);
			} catch (Exception e) {
				is = new FileInputStream(filePath);
			}
			workBook = new XSSFWorkbook(is);
		}
		is.close();
		return getRowsAndColums(workBook);
	}

	/**
	 * 取单元格的值
	 * 
	 * @param cell
	 *            单元格对象
	 * @param treatAsStr
	 *            为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
	 * @return
	 */
	private static String getCellValue(Cell cell, boolean treatAsStr) {
		if (cell == null) {
			return "";
		}

		if (treatAsStr) {
			// 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
			// 加上下面这句，临时把它当做文本来读取
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}

		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}

	/**
	 * sheetMap key: sheet名字， value: 一个sheet内的数据 List<List<String>> 外层 每一行
	 * 内层：当前行的每一列 name: 文件名称
	 * 
	 * @param sheetMap
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String createSuffixXlsExcelByMoreSheets(Map<String, List<List<String>>> sheetMap, String name)
			throws Exception {
		if (sheetMap == null || sheetMap.size() == 0) {
			return null;
		}
		File file = FileUtil.getFileByDirAndFile("/form", UUID.randomUUID().toString() + ".xls");
		String path = "form/" + name + "_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".xls";
		OutputStream os = new FileOutputStream(file);
		long start = System.currentTimeMillis();
		HSSFWorkbook workBook = new HSSFWorkbook();
		for (Entry<String, List<List<String>>> entry : sheetMap.entrySet()) {
			if (entry == null) {
				continue;
			}
			HSSFSheet sheet = workBook.createSheet(entry.getKey());
			for (int j = 0; j < entry.getValue().size(); j++) {
				HSSFRow row = sheet.createRow(j);
				for (int k = 0; k < entry.getValue().get(j).size(); k++) {
					HSSFCell cell = row.createCell(k);
					cell.setCellValue(entry.getValue().get(j).get(k));
				}
			}
		}
		workBook.write(os);
		workBook.close();
		os.close();
		LoggerUtil.info("excel本地文件保存完成，路径:" + path);
		InputStream is = new FileInputStream(file);
		UploadUtils.uploadFiles(is, file.length(), path);
		is.close();
		long end = System.currentTimeMillis();
		LoggerUtil.info("上传到OSS完成，使用时长:{}ms", (end - start));
		return UploadUtils.OSS_HOST_FILES + "/" +path;
	}

	public static String createSuffixXlsxExcelByMoreSheets(Map<String, List<List<String>>> sheetMap, String name,
			String password) throws Exception {
		if (sheetMap == null || sheetMap.size() == 0) {
			return null;
		}
		File file = FileUtil.getFileByDirAndFile("/form", UUID.randomUUID().toString() + ".xlsx");
		String path = "form/" + name + "_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".xlsx";
		OutputStream os = new FileOutputStream(file);
		long start = System.currentTimeMillis();
		XSSFWorkbook workBook = new XSSFWorkbook();
		for (Entry<String, List<List<String>>> entry : sheetMap.entrySet()) {
			if (entry == null) {
				continue;
			}
			XSSFSheet sheet = workBook.createSheet(entry.getKey());
			for (int j = 0; j < entry.getValue().size(); j++) {
				XSSFRow row = sheet.createRow(j);
				for (int k = 0; k < entry.getValue().get(j).size(); k++) {
					XSSFCell cell = row.createCell(k);
					cell.setCellValue(entry.getValue().get(j).get(k));
				}
			}
		}
		workBook.write(os);
		workBook.close();
		os.close();
		LoggerUtil.info("excel本地文件保存完成，路径:" + path);
		encryptExcel(file, password);
		InputStream is = new FileInputStream(file);
		UploadUtils.uploadFiles(is, file.length(), path);
		is.close();
		long end = System.currentTimeMillis();
		LoggerUtil.info("上传到OSS完成，使用时长:{}ms", (end - start));
		return path;
	}

	/**
	 * excelFilePath : excel文件路径 excelPassword : 打开文件密码
	 * 
	 * @param file
	 * @param excelPassword
	 * @throws Exception
	 */
	private static void encryptExcel(File file, String excelPassword) throws Exception {
		if (StringUtils.isBlank(excelPassword)) {
			return;
		}
		// add password protection and encrypt the file
		long start = System.currentTimeMillis();
		LoggerUtil.info("[begin encrypt] password:" + excelPassword);
		POIFSFileSystem fs = new POIFSFileSystem();
		EncryptionInfo info = new EncryptionInfo(fs, EncryptionMode.agile);
		Encryptor encryptor = info.getEncryptor();

		// set the password
		encryptor.confirmPassword(excelPassword);

		// encrypt the file
		OPCPackage opc = OPCPackage.open(file, PackageAccess.READ_WRITE);
		OutputStream os = encryptor.getDataStream(fs);
		opc.save(os);
		opc.close();

		// save the file back to the filesystem
		FileOutputStream fos = new FileOutputStream(file);
		fs.writeFilesystem(fos);
		fos.close();
		long end = System.currentTimeMillis();
		LoggerUtil.info("[encrypt success] use time:", (end - start));
	}

	public static void main(String[] args) throws Exception {

		// List<List<String>> data13 =
		// ExcelUtil.readExcel03And13("C:/Users/Administrator/Desktop/赠险订单.xlsx");
		// for (List<String> list :
		// ExcelUtil.readExcel03And16("http://yuanshanbao.oss-cn-beijing.aliyuncs.com/test_file/ms/1494244985997_9656.xls",
		// true)) {
		// for (String str : list) {
		// System.out.print(str + ",");
		// }
		// System.out.println(" ");
		// }
		// Map<String, List<List<String>>> map = new HashMap<String,
		// List<List<String>>>();
		// map.put("sheet1", data);
		// map.put("sheet2", data13);
		// createExcelByMoreSheets(map, "test", "234");
	}

}
