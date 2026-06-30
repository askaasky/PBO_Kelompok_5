package com.kursusonline;

public class Kursus {

    private int idKursus;
    private String namaKursus;
    private String deskripsi;
    private String jadwal;

    public Kursus(int idKursus, String namaKursus, String deskripsi, String jadwal) {
        this.idKursus = idKursus;
        this.namaKursus = namaKursus;
        this.deskripsi = deskripsi;
        this.jadwal = jadwal;
    }

    public int getIdKursus() {
        return idKursus;
    }

    public String getNamaKursus() {
        return namaKursus;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void tampilkanInfo() {

        System.out.println(
            idKursus + " | "
            + namaKursus + " | "
            + jadwal
        );

    }
}