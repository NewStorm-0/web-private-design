package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestUrl = ((HttpServletRequest) servletRequest).getRequestURI();
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        if (session.getAttribute("id") == null) {
            System.out.println("未登录的访问");
            List<String> logURIs = new ArrayList<>();
            logURIs.add("/webDesign/student_check_score.html");
            logURIs.add("/webDesign/student_choose_course.html");
            logURIs.add("/webDesign/student_drop_course.html");
            logURIs.add("/webDesign/student_information.html");
            logURIs.add("/webDesign/student_main.html");
            logURIs.add("/webDesign/teacher_course.html");
            logURIs.add("/webDesign/teacher_courseDetails.html");
            logURIs.add("/webDesign/teacher_createCourse.html");
            logURIs.add("/webDesign/teacher_main.html");
            for (String url : logURIs) {
                if (url.equals(requestUrl)) {
                    HttpServletResponse resp = (HttpServletResponse) servletResponse;
                    resp.sendRedirect("/webDesign/");
                    break;
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("已登录的访问");
            List<String> toLogURIs = new ArrayList<>();
            toLogURIs.add("/webDesign/");
            toLogURIs.add("/webDesign/index.html");
            for (String url : toLogURIs) {
                if (url.equals(requestUrl)) {
                    int identity = (Integer) session.getAttribute("identity");
                    HttpServletResponse resp = (HttpServletResponse) servletResponse;
                    if (identity == 0) {
                        resp.sendRedirect("student_main.html");
                    } else {
                        resp.sendRedirect("teacher_main.html");
                    }
                    break;
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
