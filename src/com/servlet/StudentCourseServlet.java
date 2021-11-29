package com.servlet;

import com.dao.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@WebServlet(urlPatterns = "/StudentCourseServlet")
public class StudentCourseServlet extends HttpServlet {
    private static final String GET_OPTIONAL_COURSES = "getOptionalCourses";
    private static final String CHOOSE_COURSES = "chooseCourses";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("Error: 404");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String service = req.getParameter("service");
        if (GET_OPTIONAL_COURSES.equals(service)) {
            responseOptionalCourses(req, resp);
        } else if (CHOOSE_COURSES.equals(service)) {
            chooseCourses(req, resp);
        }

    }

    private void responseOptionalCourses(HttpServletRequest req, HttpServletResponse resp) {
        CourseDao courseDao = new CourseDaoImp();
        CourseSelectionDao courseSelectionDao = new CourseSelectionDaoImp();
        TeacherDao teacherDao = new TeacherDaoImp();
        HttpSession session = req.getSession();
        int studentId = (Integer) session.getAttribute("id");
        List<Course> courses = courseDao.getOptionalCourses();
        List<Map<String, String>> list = new ArrayList<>();
        for (Course course : courses) {
            Map<String, String> map = new HashMap<>(courses.size());
            boolean isSelected = courseSelectionDao.isSelected(studentId, course.getId());
            map.put("selected", Boolean.toString(isSelected));
            map.put("teacherName", teacherDao.getTeacherName(course.getTeacherId()));
            map.put("courseName", course.getCourseName());
            map.put("description", course.getDescription());
            map.put("day", course.getDay());
            map.put("time", course.getTime());
            map.put("courseId", String.valueOf(course.getId()));
            list.add(map);
        }
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(list);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        System.out.println("学生请求可选课程");
        try (PrintWriter out = resp.getWriter()) {
            out.append(json);
            out.flush();
            System.out.println("返回的数据是：" + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseCourses(HttpServletRequest req, HttpServletResponse resp) {
        //TODO 学生选课
    }
}
