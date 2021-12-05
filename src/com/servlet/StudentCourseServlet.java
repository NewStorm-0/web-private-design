package com.servlet;

import com.dao.*;
import com.entity.Course;
import com.entity.CourseSelection;
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
import java.sql.Date;
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
    private static final String GET_DROP_COURSES = "getDropCourses";
    private static final String DROP_COURSES = "dropCourses";
    private static final String GET_STUDENT_GRADES = "getGrade";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("Error: 404");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String service = req.getParameter("service");
        System.out.println("service类型为: " + service);
        if (GET_OPTIONAL_COURSES.equals(service)) {
            responseOptionalCourses(req, resp);
        } else if (CHOOSE_COURSES.equals(service)) {
            chooseCourses(req, resp);
        } else if (GET_DROP_COURSES.equals(service)) {
            responseDropCourses(req, resp);
        } else if (DROP_COURSES.equals(service)) {
            dropCourses(req, resp);
        } else if (GET_STUDENT_GRADES.equals(service)) {
            responseGrades(req, resp);
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
            Map<String, String> map = new HashMap<>(7);
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
        String[] coursesId = req.getParameterValues("coursesId[]");
        HttpSession session = req.getSession();
        int studentId = (Integer) session.getAttribute("id");
        CourseSelectionDao courseSelection = new CourseSelectionDaoImp();
        boolean flag = true;
        for (String j : coursesId) {
            if (!courseSelection.selectCourse(studentId, Integer.parseInt(j))) {
                flag = false;
            }
        }
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

    private void responseDropCourses(HttpServletRequest req, HttpServletResponse resp) {
        CourseDao courseDao = new CourseDaoImp();
        CourseSelectionDao courseSelectionDao = new CourseSelectionDaoImp();
        TeacherDao teacherDao = new TeacherDaoImp();
        HttpSession session = req.getSession();
        int studentId = (Integer) session.getAttribute("id");
        List<CourseSelection> selectionList = courseSelectionDao.getAllCourses(studentId);
        List<Course> courses = new ArrayList<>();
        for (CourseSelection cs : selectionList) {
            Course c = courseDao.getSingleCourse(cs.getCourseId());
            java.sql.Date deadline = c.getSelectionDeadline();
            java.sql.Date now = new Date(System.currentTimeMillis());
            if (now.before(deadline)) {
                courses.add(c);
            }
        }
        List<Map<String, String>> dataList = new ArrayList<>();
        for (Course c : courses) {
            Map<String, String> map = new HashMap<>(courses.size());
            map.put("teacherName", teacherDao.getTeacherName(c.getTeacherId()));
            map.put("courseName", c.getCourseName());
            map.put("description", c.getDescription());
            map.put("day", c.getDay());
            map.put("time", c.getTime());
            map.put("courseId", String.valueOf(c.getId()));
            dataList.add(map);
        }
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(dataList);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        System.out.println("学生请求可退课程");
        try (PrintWriter out = resp.getWriter()) {
            out.append(json);
            out.flush();
            System.out.println("返回的数据是：" + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dropCourses(HttpServletRequest req, HttpServletResponse resp) {
        String[] coursesId = req.getParameterValues("coursesId[]");
        HttpSession session = req.getSession();
        int studentId = (Integer) session.getAttribute("id");
        CourseSelectionDao courseSelection = new CourseSelectionDaoImp();
        boolean flag = true;
        for (String j : coursesId) {
            if (!courseSelection.dropCourse(studentId, Integer.parseInt(j))) {
                flag = false;
            }
        }
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

    private void responseGrades(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        int studentId = (Integer) session.getAttribute("id");
        CourseSelectionDao courseSelectionDao = new CourseSelectionDaoImp();
        CourseDao courseDao = new CourseDaoImp();
        TeacherDao teacherDao = new TeacherDaoImp();
        List<CourseSelection> selectionList = courseSelectionDao.getAllCourses(studentId);
        List<Map<String, String>> returnedList = new ArrayList<>();
        for (CourseSelection selectionTemp : selectionList) {
            Course course = courseDao.getSingleCourse(selectionTemp.getCourseId());
            java.sql.Date deadline = course.getSelectionDeadline();
            java.sql.Date now = new Date(System.currentTimeMillis());
            if (now.after(deadline)) {
                Map<String, String> mapTemp = new HashMap<>(5);
                mapTemp.put("courseName", course.getCourseName());
                mapTemp.put("teacherName", teacherDao.getTeacherName(course.getTeacherId()));
                mapTemp.put("studentGrade", String.valueOf(selectionTemp.getStudentGrade()));
                mapTemp.put("classRank", String.valueOf(getClassRank(course.getId(), studentId)));
                mapTemp.put("majorRank", String.valueOf(getMajorRank(course.getId(), studentId)));
                returnedList.add(mapTemp);
            }
        }
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(returnedList);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        System.out.println("学生请求课程成绩及排名");
        try (PrintWriter out = resp.getWriter()) {
            out.append(json);
            out.flush();
            System.out.println("返回的数据是：" + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getClassRank(int courseId, int studentId) {
        StudentDao studentDao = new StudentDaoImp();
        ClassDao classDao = new ClassDaoIml();
        CourseSelectionDao courseSelectionDao = new CourseSelectionDaoImp();
        List<Integer> studentsIdList = classDao.getStudentsId(studentDao.getClassId(studentId));
        int grade = courseSelectionDao.getGrade(studentId, courseId);
        int rank = 1;
        for (Integer i : studentsIdList) {
            if (courseSelectionDao.getGrade(i, courseId) > grade) {
                rank++;
            }
        }
        return rank;
    }

    private int getMajorRank(int courseId, int studentId) {
        StudentDao studentDao = new StudentDaoImp();
        MajorDao majorDao = new MajorDaoImp();
        CourseSelectionDao courseSelectionDao = new CourseSelectionDaoImp();
        List<Integer> studentsIdList = majorDao.getStudentsId(studentDao.getMajorId(studentId));
        int grade = courseSelectionDao.getGrade(studentId, courseId);
        int rank = 1;
        for (Integer i : studentsIdList) {
            if (courseSelectionDao.getGrade(i, courseId) > grade) {
                rank++;
            }
        }
        return rank;
    }
}
