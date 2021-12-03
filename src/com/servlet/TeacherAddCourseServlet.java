package com.servlet;

import com.dao.CourseDao;
import com.dao.CourseDaoImp;
import com.entity.Course;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = "/TeacherAddCourseServlet")
public class TeacherAddCourseServlet extends HttpServlet {
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
        String courseName = req.getParameter("courseName");
        String description = req.getParameter("courseDescription");
        String deadline = req.getParameter("deadline");
        String day = req.getParameter("day");
        String time = req.getParameter("time");
        java.sql.Date sqlDate = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = dateFormat.parse(deadline);
            sqlDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int credit = Integer.parseInt(req.getParameter("credit"));
        Course course = new Course();
        course.setCourseName(courseName);
        course.setDescription(description);
        course.setTeacherId(teacherId);
        course.setSelectionDeadline(sqlDate);
        course.setCredit(credit);
        course.setDay(day);
        course.setTime(time);
        CourseDao courseDao = new CourseDaoImp();
        boolean flag = courseDao.addCourse(course);
        try (PrintWriter out = resp.getWriter()) {
            if (flag) {
                out.append('1');
            } else {
                out.append('0');
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
