package com.yuanshanbao.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class PDFUtil {

	public static void generatePDF(File file, String content) throws Exception {
		long start = System.currentTimeMillis();
		// step 1
		Document document = new Document();
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		// step 3
		document.open();
		LoggerUtil.info("[generatePdf] path:" + file.getAbsolutePath() + " open:"
				+ (System.currentTimeMillis() - start));
		// step 4
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(content.getBytes()),
				Charset.forName("UTF-8"));
		LoggerUtil.info("[generatePdf] path:" + file.getAbsolutePath() + " parse:"
				+ (System.currentTimeMillis() - start));
		// step 5
		document.close();
		LoggerUtil.info("[generatePdf] path:" + file.getAbsolutePath() + " close:"
				+ (System.currentTimeMillis() - start));
	}
}
