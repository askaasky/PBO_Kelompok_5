package com.kursusonline;

public abstract class User {

    protected int idUser;
    protected String username;
    protected String password;
    protected String email;

    public User(int idUser, String username, String password, String email) {

        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.email = email;

    }

    public int getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public abstract void login();

    public void logout() {
        System.out.println(username + " logout dari sistem.");
    }
}