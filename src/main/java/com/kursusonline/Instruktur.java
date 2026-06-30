package com.kursusonline;

public class Instruktur extends User {

    private String nama;
    private String keahlian;
    private String noHP;

    public Instruktur(
            int idUser,
            String username,
            String password,
            String email,
            String nama,
            String keahlian,
            String noHP) {

        super(idUser, username, password, email);

        this.nama = nama;
        this.keahlian = keahlian;
        this.noHP = noHP;
    }

    @Override
    public void login() {
        System.out.println("Login sebagai instruktur");
    }
}