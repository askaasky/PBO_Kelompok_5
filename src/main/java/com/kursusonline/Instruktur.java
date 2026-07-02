package com.kursusonline;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
        System.out.println("Login berhasil sebagai instruktur: " + this.nama);
    }

    // Fungsi bagi instruktur untuk memberikan nilai kepada peserta
    public void beriPenilaian(int idDaftar, double nilai) {
        String sql = "UPDATE pendaftaran SET nilai = ? WHERE idDaftar = ?";
        try (Connection conn = Main.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, nilai);
            stmt.setInt(2, idDaftar);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Penilaian berhasil disimpan untuk ID Pendaftaran: " + idDaftar);
            } else {
                System.out.println("Gagal memberi nilai, ID Pendaftaran tidak ditemukan.");
            }
        } catch (Exception e) {
            System.out.println("Error saat memberi penilaian: " + e.getMessage());
        }
    }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getKeahlian() { return keahlian; }
    public void setKeahlian(String keahlian) { this.keahlian = keahlian; }

    public String getNoHP() { return noHP; }
    public void setNoHP(String noHP) { this.noHP = noHP; }
}