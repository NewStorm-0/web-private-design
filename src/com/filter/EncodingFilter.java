package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * @author Administrator
 */
@WebFilter(urlPatterns = "/*")
public class EncodingFilter implements Filter {
    public EncodingFilter() {
        System.out.println("过滤器构造");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //将编码改为utf-8
        servletRequest.setCharacterEncoding("utf-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("过滤器初始化");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("过滤器销毁");
    }
}
