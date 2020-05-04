package com.yc.fresh.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台页面跳转拦截
 * 源辰信息
 * @author navy
 * @date 2019年10月1日
 */
@WebFilter("/back/loginout")
public class BackLoginOutFilter implements Filter{
	public void init(FilterConfig filterConfig) throws ServletException {

	};

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		request.getSession().removeAttribute("loginMember");
		response.sendRedirect("index.html");
		return;
	}

	@Override
	public void destroy() {

	}
}
