package com.kursusonline;

public class Materi {

    private int idMateri;
    private String judul;
    private String deskripsi;
    private String fileMateri;

    public Materi(int idMateri, String judul,
                  String deskripsi,
                  String fileMateri) {

        this.idMateri = idMateri;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.fileMateri = fileMateri;
    }
}