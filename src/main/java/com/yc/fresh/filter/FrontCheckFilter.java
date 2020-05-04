package com.yc.fresh.filter;

import java.io.IOException;
import java.io.PrintWriter;

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
@WebFilter("/front/*")
public class FrontCheckFilter implements Filter{
	public void init(FilterConfig filterConfig) throws ServletException {

	};

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (request.getSession().getAttribute("loginMember") == null) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('请先登录...');location.href='../login.html';</script>");
			out.flush();
			return;
		}
		
		arg2.doFilter(request, response); // 如果登录，则放行
		
	}

	@Override
	public void destroy() {

	}
}
