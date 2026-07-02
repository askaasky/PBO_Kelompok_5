package com.kursusonline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Materi {

    private int idMateri;
    private String judul;
    private String deskripsi;
    private String fileMateri;

    public Materi(int idMateri, String judul, String deskripsi, String fileMateri) {
        this.idMateri = idMateri;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.fileMateri = fileMateri;
    }

    // 1. Tambah Materi (Create)
    public void tambahMateri(int idKursus) {
        String sql = "INSERT INTO materi (judul, deskripsi, file_materi, id_kursus) VALUES (?, ?, ?, ?)";
        try (Connection conn = Main.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, this.judul);
            stmt.setString(2, this.deskripsi);
            stmt.setString(3, this.fileMateri);
            stmt.setInt(4, idKursus);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Materi \"" + this.judul + "\" berhasil ditambahkan ke database!");
            }
        } catch (Exception e) {
            System.out.println("Gagal menambahkan materi: " + e.getMessage());
        }
    }

    // 2. Ubah Materi (Update)
    public static void ubahMateri(int idTarget, String judulBaru, String deskripsiBaru, String fileBaru) {
        String sql = "UPDATE materi SET judul = ?, deskripsi = ?, file_materi = ? WHERE idMateri = ?";
        try (Connection conn = Main.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, judulBaru);
            stmt.setString(2, deskripsiBaru);
            stmt.setString(3, fileBaru);
            stmt.setInt(4, idTarget);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Materi dengan ID " + idTarget + " berhasil diperbarui!");
            } else {
                System.out.println("Materi dengan ID " + idTarget + " tidak ditemukan.");
            }
        } catch (Exception e) {
            System.out.println("Gagal mengubah materi: " + e.getMessage());
        }
    }

    // 3. Hapus Materi (Delete)
    public static void hapusMateri(int idTarget) {
        String sql = "DELETE FROM materi WHERE idMateri = ?";
        try (Connection conn = Main.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idTarget);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Materi dengan ID " + idTarget + " berhasil dihapus!");
            } else {
                System.out.println("Materi dengan ID " + idTarget + " tidak ditemukan.");
            }
        } catch (Exception e) {
            System.out.println("Gagal menghapus materi: " + e.getMessage());
        }
    }

    public int getIdMateri() { return idMateri; }
    public void setIdMateri(int idMateri) { this.idMateri = idMateri; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getFileMateri() { return fileMateri; }
    public void setFileMateri(String fileMateri) { this.fileMateri = fileMateri; }
}