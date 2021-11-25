package com.servlet;

import com.dao.UserDao;
import com.dao.UserDaoImp;
import com.entity.Student;
import com.entity.Teacher;
import com.entity.User;
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

/**
 * @author Administrator
 */
@WebServlet(urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("Error: 404");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getInformation(req);
        if (user != null) {
            setSession(req, user);
            responseOutWithJson(resp, user);
        } else {
            // 设置响应类型:
            resp.setContentType("text/plain");
            // 获取输出流:
            PrintWriter pw = resp.getWriter();
            // 写入响应:
            pw.println(-1);
            // 最后不要忘记flush强制输出:
            pw.flush();
            pw.close();
            System.out.println("登录失败");
        }

    }

    private User getInformation(HttpServletRequest req) {
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        int identity = Integer.parseInt(req.getParameter("identity"));
        System.out.println("请求类型：Post, account: " + account + ", password: " +
                password + ", identity: " + identity);
        UserDao userDao = new UserDaoImp();
        return userDao.login(account, password, identity);
    }

    private void responseOutWithJson(HttpServletResponse response, User user) {
        //将实体对象转换为JSON Object转换
        Gson gson = new GsonBuilder().create();
        String json = "";
        if (user instanceof Teacher teacher) {
            json = gson.toJson(teacher);
            System.out.println("验证账号为老师成功");
        } else {
            Student student = (Student) user;
            json = gson.toJson(student);
            System.out.println("验证账号为老师成功");
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
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

    private void setSession(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute("id", user.getId());
        session.setAttribute("password", user.getPassword());
    }
}