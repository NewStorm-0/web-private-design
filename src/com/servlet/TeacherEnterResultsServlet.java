package com.servlet;

import com.dao.CourseSelectionDao;
import com.dao.CourseSelectionDaoImp;
import com.dao.StudentDao;
import com.dao.StudentDaoImp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Administrator
 */
@WebServlet(urlPatterns = "/TeacherEnterResultsServlet")
public class TeacherEnterResultsServlet extends HttpServlet {
    private static final String GET_STUDENTS_INFORMATION = "getInformation";
    private static final String SUBMIT_STUDENTS_GRADES = "submitGrades";

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
        System.out.println("service类型为：" + service);
        if (GET_STUDENTS_INFORMATION.equals(service)) {
            responseStudentsInformation(req, resp);
        } else if (SUBMIT_STUDENTS_GRADES.equals(service)) {
            submitGrades(req, resp);
        } else {
            System.out.println("未知的service: " + service);
        }
    }

    private void responseStudentsInformation(HttpServletRequest req, HttpServletResponse resp) {
        StudentDao studentDao = new StudentDaoImp();
        CourseSelectionDao courseSelectionDao = new CourseSelectionDaoImp();
        int courseId = Integer.parseInt(req.getParameter("courseId"));
        System.out.println("courseId: " + courseId);
        List<Integer> list = courseSelectionDao.getStudentsId(courseId);
        System.out.println("list size: " + list.size());
        List<Map<String, String>> responseList = new ArrayList<>();
        for (int i = 0; i < list.size(); i += 2) {
            int studentId = list.get(i);
            Map<String, String> map = new HashMap<>(6);
            map.put("studentId", String.valueOf(studentId));
            map.put("schoolId", studentDao.getSchoolId(studentId));
            map.put("name", studentDao.getName(studentId));
            map.put("className", studentDao.getClassFromStudent(studentId));
            map.put("majorName", studentDao.getMajorFromStudent(studentId));
            map.put("studentGrade", list.get(i + 1).toString());
            responseList.add(map);
        }
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(responseList);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        System.out.println("教师请求课程学生名单");
        try (PrintWriter out = resp.getWriter()) {
            out.append(json);
            out.flush();
            System.out.println("返回的数据是：" + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void submitGrades(HttpServletRequest req, HttpServletResponse resp) {
        String jsonData = req.getParameter("data");
        int courseId = Integer.parseInt(req.getParameter("courseId"));
        CourseSelectionDao courseSelectionDao = new CourseSelectionDaoImp();
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<InitialData>>(){}.getType();
        List<InitialData> gradeList = gson.fromJson(jsonData, listType);
        boolean flag = true;
        for (InitialData initialData : gradeList) {
            int studentId = Integer.parseInt(initialData.studentId);
            int grade = Integer.parseInt(initialData.grade);
            if (!courseSelectionDao.serGrade(studentId, courseId, grade)) {
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

    static class InitialData {
        @SerializedName("studentId")
        private String studentId;
        @SerializedName("grade")
        private String grade;

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "InitialData{" +
                    "studentId=" + studentId +
                    ", grade=" + grade +
                    '}';
        }
    }
}