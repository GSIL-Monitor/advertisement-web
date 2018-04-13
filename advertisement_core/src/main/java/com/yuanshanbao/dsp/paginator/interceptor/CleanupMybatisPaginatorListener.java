package com.yuanshanbao.dsp.paginator.interceptor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 */
public class CleanupMybatisPaginatorListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        PaginationInterceptor.Pool.shutdownNow();
    }
}
