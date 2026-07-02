package com.kursusonline;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

    // 1. Tambah Kursus (Create)
    public void tambahKursus() {
        String sql = "INSERT INTO kursus (namaKursus, deskripsi, jadwal) VALUES (?, ?, ?)";
        try (Connection conn = Main.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, this.namaKursus);
            stmt.setString(2, this.deskripsi);
            stmt.setString(3, this.jadwal);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Kursus \"" + this.namaKursus + "\" berhasil ditambahkan ke database!");
            }
        } catch (Exception e) {
            System.out.println("Gagal menambahkan kursus: " + e.getMessage());
        }
    }

    // 2. Ubah Kursus (Update)
    public static void ubahKursus(int idTarget, String namaBaru, String deskripsiBaru, String jadwalBaru) {
        String sql = "UPDATE kursus SET namaKursus = ?, deskripsi = ?, jadwal = ? WHERE idKursus = ?";
        try (Connection conn = Main.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, namaBaru);
            stmt.setString(2, deskripsiBaru);
            stmt.setString(3, jadwalBaru);
            stmt.setInt(4, idTarget);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Kursus dengan ID " + idTarget + " berhasil diperbarui!");
            } else {
                System.out.println("Kursus dengan ID " + idTarget + " tidak ditemukan.");
            }
        } catch (Exception e) {
            System.out.println("Gagal mengubah kursus: " + e.getMessage());
        }
    }

    // 3. Hapus Kursus (Delete)
    public static void hapusKursus(int idTarget) {
        String sql = "DELETE FROM kursus WHERE idKursus = ?";
        try (Connection conn = Main.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idTarget);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Kursus dengan ID " + idTarget + " berhasil dihapus!");
            } else {
                System.out.println("Kursus dengan ID " + idTarget + " tidak ditemukan.");
            }
        } catch (Exception e) {
            System.out.println("Gagal menghapus kursus: " + e.getMessage());
        }
    }

    public int getIdKursus() { return idKursus; }
    public void setIdKursus(int idKursus) { this.idKursus = idKursus; }

    public String getNamaKursus() { return namaKursus; }
    public void setNamaKursus(String namaKursus) { this.namaKursus = namaKursus; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getJadwal() { return jadwal; }
    public void setJadwal(String jadwal) { this.jadwal = jadwal; }

    // Metode bawaan sebelumnya
    public void tampilkanInfo() {
        System.out.println(
            idKursus + " | "
            + namaKursus + " | "
            + jadwal
        );
    }
}