package com.kursusonline;

public class Pendaftaran {

    private int idDaftar;
    private String tglDaftar;
    private String status;

    public Pendaftaran(
            int idDaftar,
            String tglDaftar,
            String status) {

        this.idDaftar = idDaftar;
        this.tglDaftar = tglDaftar;
        this.status = status;
    }
}