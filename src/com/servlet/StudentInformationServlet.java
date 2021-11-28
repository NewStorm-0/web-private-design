package com.servlet;

import com.dao.StudentDao;
import com.dao.StudentDaoImp;
import com.dao.UserDao;
import com.dao.UserDaoImp;
import com.entity.Student;
import com.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@WebServlet(urlPatterns = "/StudentInformationServlet")
public class StudentInformationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("Error: 404");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO 学生获取基本信息
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        int identity = Integer.parseInt(req.getParameter("identity"));
        System.out.println("请求类型：Post, account: " + account + ", password: " +
                password + ", identity: " + identity);
        UserDao userDao = new UserDaoImp();
        User user = userDao.login(account, password, identity);
        Gson gson = new GsonBuilder().create();
        String json;
        Student student = (Student) user;
        StudentDao studentDao = new StudentDaoImp();
        Map<String, String> map = new HashMap<>();
        map.put("account", student.getAccount());
        map.put("identity", "0");
        map.put("name", student.getName());
        map.put("schoolId", student.getSchoolId());
        map.put("major", studentDao.getMajor(student.getMajorId()));
        map.put("year", String.valueOf(student.getYear()));
        map.put("class", studentDao.getStudentClass(student.getClassId()));
        map.put("phone", student.getPhone());
        map.put("birthday", student.getBirthday().toString());
        map.put("mail", student.getMail());
        map.put("address", student.getAddress());
        json = gson.toJson(map);
        System.out.println("验证账号为学生成功");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = resp.getWriter()) {
            out.append(json);
            out.flush();
            System.out.println("返回的数据是：" + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
