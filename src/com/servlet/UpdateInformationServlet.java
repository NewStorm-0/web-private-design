package com.servlet;

import com.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/UpdateInformationServlet")
public class UpdateInformationServlet extends HttpServlet {
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
        String type = req.getParameter("type");
        if (Type.Information.name().equals(type)) {
            updateInformation(req, resp);
        } else if (Type.Password.name().equals(type)) {
            updatePassword(req, resp);
        } else {
            System.out.println("修改信息类型出错");
        }
    }

    void updateInformation(HttpServletRequest req, HttpServletResponse resp) {
        boolean flag = false;
        HttpSession session = req.getSession();
        int id = (Integer) session.getAttribute("id");
        int identity = Integer.parseInt(req.getParameter("identity"));
        String account = req.getParameter("account");
        //String name = req.getParameter("name");
        UserDao userDao = new UserDaoImp();
        flag = userDao.resetAccount(account, id);
        if (identity == TEACHER) {
            //TeacherDao teacherDao = new TeacherDaoImp();
            //flag = flag && teacherDao.resetName(name, id);
        } else {
            String address = req.getParameter("address");
            String mail = req.getParameter("mail");
            String phone = req.getParameter("phone");
            StudentDao studentDao = new StudentDaoImp();
            flag = flag && studentDao.resetAddress(address, id) &&
                    studentDao.resetMail(mail, id) && studentDao.resetPhone(phone, id);
        }
        try (PrintWriter pw = resp.getWriter()) {
            if (flag) {
                pw.append("1");
            } else {
                pw.append('0');
            }
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void updatePassword(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        String password = req.getParameter("password");
        UserDao userDao = new UserDaoImp();
        if (userDao.resetPassword(password, id)) {
            HttpSession session = req.getSession();
            session.setAttribute("password", password);
            try (PrintWriter out = resp.getWriter()) {
                out.append("1");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (PrintWriter out = resp.getWriter()) {
                out.append("0");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

enum Type {
    //Information表示修改的是基本信息，Password表示修改的是密码
    Information, Password;
}


