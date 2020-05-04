package com.yc.fresh.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/back/page/**").addResourceLocations("classpath:/static/WEB-INF/manager/");
		registry.addResourceHandler("/front/**").addResourceLocations("classpath:/static/WEB-INF/front/");
	}
}
