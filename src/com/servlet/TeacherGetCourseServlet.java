package com.servlet;

import com.dao.CourseDao;
import com.dao.CourseDaoImp;
import com.entity.Course;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/TeacherGetCourseServlet")
public class TeacherGetCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("Error: 404");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int teacherId = (Integer) session.getAttribute("id");
        CourseDao courseDao = new CourseDaoImp();
        List<Course> list = courseDao.getAllCourses(teacherId);
        System.out.println("查询老师的课程，老师 id=" + teacherId);
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(list);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            out.append(json);
            out.flush();
            System.out.println("返回的数据是：" + json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
