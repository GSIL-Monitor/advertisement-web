package com.yuanshanbao.ms.listener.init;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yuanshanbao.common.constant.SystemConstants;

public class SysInitListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger("init");

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String rootPath = sce.getServletContext().getRealPath("/");

		SystemConstants.SYS_ROOT = rootPath;

		System.setProperty("LOGROOT", rootPath + File.separator + "log");

		SystemConstants.REPORT_FOLDER = rootPath + File.separator + "report" + File.separator;

		SystemConstants.ICON_FOLDER = rootPath + File.separator + "resources" + File.separator + "css" + File.separator
				+ "images" + File.separator + "icon" + File.separator;

		File reportFolder = new File(SystemConstants.REPORT_FOLDER);

		if (!reportFolder.exists()) {
			reportFolder.mkdir();
		}

		logger.info("ctx path:" + sce.getServletContext().getContextPath());

		logger.info("Report folder path:" + SystemConstants.REPORT_FOLDER);

		logger.info("Beginning refresh report job...");

		ApplicationContext act = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());

		logger.info("Refresh report job finished.");

		VelocityEngine velocityEngine = act.getBean(VelocityEngine.class);

		Map<String, Object> model = new HashMap<String, Object>();
		// model.put("ctxpath", sce.getServletContext().getContextPath());

		SystemConstants.HTML_SUCCESS = "#msg#";

		SystemConstants.HTML_FAILURE = "#msg#";
	}
}
