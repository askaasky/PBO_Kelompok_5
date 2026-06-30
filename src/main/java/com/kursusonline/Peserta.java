package com.kursusonline;

public class Peserta extends User {

    private String nama;
    private String alamat;
    private String noHP;

    public Peserta(
            int idUser,
            String username,
            String password,
            String email,
            String nama,
            String alamat,
            String noHP) {

        super(idUser, username, password, email);

        this.nama = nama;
        this.alamat = alamat;
        this.noHP = noHP;
    }

    @Override
    public void login() {
        System.out.println("Login sebagai peserta");
    }
}