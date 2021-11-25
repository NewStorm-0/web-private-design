package com.dao;

/**
 * @author Administrator
 */
public interface StudentDao {
    boolean resetMail(String mail, int id);
    boolean resetPhone(String phone, int id);
    boolean resetAddress(String address, int id);
}
