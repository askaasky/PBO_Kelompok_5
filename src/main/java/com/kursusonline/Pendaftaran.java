package com.kursusonline;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Pendaftaran {

    private int idDaftar;
    private String tglDaftar;
    private String status;

    public Pendaftaran(int idDaftar, String tglDaftar, String status) {
        this.idDaftar = idDaftar;
        this.tglDaftar = tglDaftar;
        this.status = status;
    }

    // 1. Method daftar() - Menyimpan pendaftaran ke database
    // Kita menambahkan parameter idPeserta dan idKursus agar database tahu SIAPA mendaftar kursus APA.
    public void daftar(int idPeserta, int idKursus) {
        String sql = "INSERT INTO pendaftaran (tglDaftar, status, idPeserta, idKursus) VALUES (?, ?, ?, ?)";
        try (Connection conn = Main.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, this.tglDaftar);
            stmt.setString(2, "Aktif"); // Status default saat baru mendaftar
            stmt.setInt(3, idPeserta);
            stmt.setInt(4, idKursus);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Pendaftaran berhasil dicatat di sistem!");
            }
        } catch (Exception e) {
            System.out.println("Gagal melakukan pendaftaran: " + e.getMessage());
        }
    }

    // 2. Method batalkan() - Mengubah status menjadi "Batal"
    public static void batalkan(int idDaftarTarget) {
        String sql = "UPDATE pendaftaran SET status = 'Batal' WHERE idDaftar = ?";
        try (Connection conn = Main.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idDaftarTarget);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Pendaftaran dengan ID " + idDaftarTarget + " berhasil dibatalkan.");
            } else {
                System.out.println("ID Pendaftaran " + idDaftarTarget + " tidak ditemukan.");
            }
        } catch (Exception e) {
            System.out.println("Gagal membatalkan pendaftaran: " + e.getMessage());
        }
    }

    public int getIdDaftar() { return idDaftar; }
    public void setIdDaftar(int idDaftar) { this.idDaftar = idDaftar; }

    public String getTglDaftar() { return tglDaftar; }
    public void setTglDaftar(String tglDaftar) { this.tglDaftar = tglDaftar; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}