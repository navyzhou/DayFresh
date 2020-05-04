
package com.yc.fresh.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener{
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg) {
		String path = "pics";
		
		String temp = arg.getServletContext().getInitParameter("uploadPath");
		if (temp != null) {
			path = temp;
		}
		
		String basePath = arg.getServletContext().getRealPath("/");
		File fl = new File(basePath, "../" + path);
		if (!fl.exists()) {
			fl.mkdirs();
		}
	}
}
