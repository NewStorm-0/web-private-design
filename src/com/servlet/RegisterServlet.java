package com.servlet;

import com.dao.*;
import com.entity.Major;
import com.entity.Student;
import com.entity.Teacher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Administrator
 */
@WebServlet(urlPatterns = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final String GET_REGISTER_STUDENT_INFORMATION = "getRegisterInformation";
    private static final String REGISTER_ACCOUNT = "register";
    private static final int STUDENT = 0;
    private static final int TEACHER = 1;

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
        if (GET_REGISTER_STUDENT_INFORMATION.equals(service)) {
            responseRegisterInformation(resp);
        } else if (REGISTER_ACCOUNT.equals(service)) {
            registerAccount(req, resp);
        }
    }

    private void responseRegisterInformation(HttpServletResponse resp) {
        ClassDao classDao = new ClassDaoIml();
        MajorDao majorDao = new MajorDaoImp();
        List<com.entity.Class> classList = classDao.getClassesList();
        List<Major> majorList = majorDao.getMajorsList();
        Map<String, List<Object>> mapData = new HashMap<>(2);
        mapData.put("classes", Collections.singletonList(classList));
        mapData.put("majors", Collections.singletonList(majorList));
        System.out.println(mapData);
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(mapData);
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

    private void registerAccount(HttpServletRequest req, HttpServletResponse resp) {
        int identity = Integer.parseInt(req.getParameter("identity"));
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        UserDao userDao = new UserDaoImp();
        boolean flag;
        if (identity == TEACHER) {
            Teacher teacher = new Teacher();
            teacher.setIdentity(1);
            teacher.setName(name);
            teacher.setAccount(account);
            teacher.setPassword(password);
            flag = userDao.createUser(teacher);
        } else if (identity == STUDENT) {
            int year = Integer.parseInt(req.getParameter("year"));
            int classId = Integer.parseInt(req.getParameter("classId"));
            int majorId = Integer.parseInt(req.getParameter("majorId"));
            String birthdayString = req.getParameter("birthday");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d = null;
            try {
                d =sdf.parse(birthdayString);

            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            assert d != null;
            java.sql.Date birthday = new java.sql.Date(d.getTime());
            Student student = new Student();
            student.setIdentity(0);
            student.setAccount(account);
            student.setPassword(password);
            student.setName(name);
            student.setYear(year);
            student.setClassId(classId);
            student.setMajorId(majorId);
            student.setBirthday(birthday);
            flag = userDao.createUser(student);
        } else {
            flag = false;
            System.out.println("未能识别的identity");
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
}
