package com.entity;

/**
 * @author Administrator
 */
public class User {
    protected int id;
    protected String account;
    protected String password;
    private int identity;

    public User() {
    }

    public User(int id, String account, String password, int identity) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.identity = identity;
    }

    @Override
    public String toString() {
        return ("User: id=" + id + ", account=" + account +
                ", password=" + password + ", identity=" + identity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }
}
