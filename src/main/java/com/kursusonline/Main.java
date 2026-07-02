package com.kursusonline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class Main {

    static final String URL = "jdbc:mysql://localhost:3307/db_kursus";
    static final String USER = "root";
    static final String PASSWORD = "";

    static Scanner input = new Scanner(System.in);

    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

    public static void main(String[] args) {

        int pilihan;

        do {

            System.out.println("\n=== SISTEM PENGELOLAAN KURSUS ONLINE ===");
            System.out.println("1. Login");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu : ");

            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {

                case 1:
                    login();
                    break;

                case 0:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Menu tidak tersedia.");

            }

        } while (pilihan != 0);

        input.close();

    }

    public static void login() {

        try {

            System.out.print("Username : ");
            String username = input.nextLine();

            System.out.print("Password : ");
            String password = input.nextLine();

            Connection conn = getConnection();

            Statement stmt = conn.createStatement();

            String sql =
                    "SELECT * FROM users WHERE username='"
                            + username
                            + "' AND password='"
                            + password
                            + "'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {

                int idUser = rs.getInt("idUser");

                System.out.println("\nLogin berhasil!");

                if (isInstruktur(idUser)) {

                    String nama = getNamaInstruktur(idUser);

                    menuInstruktur(idUser, nama);

                } else {

                    String nama = getNamaPeserta(idUser);

                    menuPeserta(idUser, nama);

                }

            } else {

                System.out.println("Username atau password salah.");

            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static boolean isInstruktur(int idUser) {

        try {

            Connection conn = getConnection();

            Statement stmt = conn.createStatement();

            String sql =
                    "SELECT * FROM instruktur WHERE idUser = " + idUser;

            ResultSet rs = stmt.executeQuery(sql);

            boolean hasil = rs.next();

            conn.close();

            return hasil;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    public static String getNamaPeserta(int idUser) {

        try {

            Connection conn = getConnection();

            Statement stmt = conn.createStatement();

            String sql =
                    "SELECT nama FROM peserta WHERE idUser = " + idUser;

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {

                String nama = rs.getString("nama");

                conn.close();

                return nama;

            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "Peserta";

    }

    public static String getNamaInstruktur(int idUser) {

        try {

            Connection conn = getConnection();

            Statement stmt = conn.createStatement();

            String sql =
                    "SELECT nama FROM instruktur WHERE idUser = " + idUser;

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {

                String nama = rs.getString("nama");

                conn.close();

                return nama;

            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return "Instruktur";

    }

    public static void menuPeserta(int idUser, String nama) {

        int pilih;

        do {

            System.out.println("\n=== MENU PESERTA ===");
            System.out.println("Selamat datang, " + nama);
            System.out.println("1. Lihat Semua Kursus");
            System.out.println("2. Kursus Saya");
            System.out.println("3. Cetak Laporan");
            System.out.println("0. Logout");
            System.out.print("Pilih menu : ");

            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {

                case 1:
                    tampilKursus();
                    break;

                case 2:
                    kursusSaya(idUser);
                    break;

                case 3:
                    cetakPdf();
                    break;

                case 0:
                    System.out.println("Logout berhasil.");
                    break;

                default:
                    System.out.println("Menu tidak tersedia.");

            }

        } while (pilih != 0);

    }

    public static void menuInstruktur(int idUser, String nama) {

        int pilih;

        do {

            System.out.println("\n=== MENU INSTRUKTUR ===");
            System.out.println("Selamat datang, " + nama);
            System.out.println("1. Lihat Semua Kursus");
            System.out.println("2. Tambah Kursus");
            System.out.println("3. Edit Kursus");
            System.out.println("4. Hapus Kursus");
            System.out.println("5. Lihat Materi");
            System.out.println("6. Tambah Materi");
            System.out.println("7. Edit Materi");
            System.out.println("8. Hapus Materi");
            System.out.println("9. Cetak Laporan");
            System.out.println("0. Logout");
            System.out.print("Pilih menu : ");

            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {

                case 1:
                    tampilKursus();
                    break;

                case 2:
                    tambahKursus();
                    break;

                case 3:
                    editKursus();
                    break;

                case 4:
                    hapusKursus();
                    break;

                case 5:
                    tampilMateri();
                    break;

                case 6:
                    tambahMateri();
                    break;

                case 7:
                    editMateri();
                    break;

                case 8:
                    hapusMateri();
                    break;

                case 9:
                    cetakPdf();
                    break;

                case 0:
                    System.out.println("Logout berhasil.");
                    break;

                default:
                    System.out.println("Menu tidak tersedia.");

            }

        } while (pilih != 0);

    }

    public static void tambahKursus() {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO kursus (namaKursus, deskripsi, jadwal) VALUES (?, ?, ?)") ) {

            System.out.print("Nama Kursus : ");
            String nama = input.nextLine();
            System.out.print("Deskripsi : ");
            String deskripsi = input.nextLine();
            System.out.print("Jadwal : ");
            String jadwal = input.nextLine();

            pstmt.setString(1, nama);
            pstmt.setString(2, deskripsi);
            pstmt.setString(3, jadwal);

            int hasil = pstmt.executeUpdate();

            if (hasil > 0) {
                System.out.println("Kursus berhasil ditambahkan.");
            } else {
                System.out.println("Gagal menambahkan kursus.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editKursus() {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE kursus SET namaKursus = ?, deskripsi = ?, jadwal = ? WHERE idKursus = ?") ) {

            System.out.print("ID Kursus yang akan diubah : ");
            int idKursus = input.nextInt();
            input.nextLine();
            System.out.print("Nama Kursus baru : ");
            String nama = input.nextLine();
            System.out.print("Deskripsi baru : ");
            String deskripsi = input.nextLine();
            System.out.print("Jadwal baru : ");
            String jadwal = input.nextLine();

            pstmt.setString(1, nama);
            pstmt.setString(2, deskripsi);
            pstmt.setString(3, jadwal);
            pstmt.setInt(4, idKursus);

            int hasil = pstmt.executeUpdate();

            if (hasil > 0) {
                System.out.println("Kursus berhasil diubah.");
            } else {
                System.out.println("ID Kursus tidak ditemukan.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hapusKursus() {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "DELETE FROM kursus WHERE idKursus = ?") ) {

            System.out.print("ID Kursus yang akan dihapus : ");
            int idKursus = input.nextInt();
            input.nextLine();

            pstmt.setInt(1, idKursus);

            int hasil = pstmt.executeUpdate();

            if (hasil > 0) {
                System.out.println("Kursus berhasil dihapus.");
            } else {
                System.out.println("ID Kursus tidak ditemukan.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tambahMateri() {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO materi (judul, deskripsi, fileMateri, idKursus) VALUES (?, ?, ?, ?)") ) {

            tampilKursus();
            System.out.print("ID Kursus untuk materi : ");
            int idKursus = input.nextInt();
            input.nextLine();
            System.out.print("Judul Materi : ");
            String judul = input.nextLine();
            System.out.print("Deskripsi Materi : ");
            String deskripsi = input.nextLine();
            System.out.print("File Materi : ");
            String fileMateri = input.nextLine();

            pstmt.setString(1, judul);
            pstmt.setString(2, deskripsi);
            pstmt.setString(3, fileMateri);
            pstmt.setInt(4, idKursus);

            int hasil = pstmt.executeUpdate();

            if (hasil > 0) {
                System.out.println("Materi berhasil ditambahkan.");
            } else {
                System.out.println("Gagal menambahkan materi.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editMateri() {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE materi SET judul = ?, deskripsi = ?, fileMateri = ?, idKursus = ? WHERE idMateri = ?") ) {

            tampilMateri();
            System.out.print("ID Materi yang akan diubah : ");
            int idMateri = input.nextInt();
            input.nextLine();
            System.out.print("Judul Materi baru : ");
            String judul = input.nextLine();
            System.out.print("Deskripsi Materi baru : ");
            String deskripsi = input.nextLine();
            System.out.print("File Materi baru : ");
            String fileMateri = input.nextLine();
            System.out.print("ID Kursus baru untuk materi : ");
            int idKursus = input.nextInt();
            input.nextLine();

            pstmt.setString(1, judul);
            pstmt.setString(2, deskripsi);
            pstmt.setString(3, fileMateri);
            pstmt.setInt(4, idKursus);
            pstmt.setInt(5, idMateri);

            int hasil = pstmt.executeUpdate();

            if (hasil > 0) {
                System.out.println("Materi berhasil diubah.");
            } else {
                System.out.println("ID Materi tidak ditemukan.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hapusMateri() {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "DELETE FROM materi WHERE idMateri = ?") ) {

            tampilMateri();
            System.out.print("ID Materi yang akan dihapus : ");
            int idMateri = input.nextInt();
            input.nextLine();

            pstmt.setInt(1, idMateri);

            int hasil = pstmt.executeUpdate();

            if (hasil > 0) {
                System.out.println("Materi berhasil dihapus.");
            } else {
                System.out.println("ID Materi tidak ditemukan.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tampilKursus() {

        try {

            Connection conn = getConnection();

            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM kursus";

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n=== DAFTAR KURSUS ===");

            boolean adaData = false;

            while (rs.next()) {
                adaData = true;
                System.out.println(
                        rs.getInt("idKursus") + " | "
                                + rs.getString("namaKursus") + " | "
                                + rs.getString("jadwal"));
            }

            if (!adaData) {
                System.out.println("Tidak ada data kursus.");
            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static void kursusSaya(int idUser) {

        try {

            Connection conn = getConnection();

            Statement stmt = conn.createStatement();

            String sql =
                    "SELECT k.namaKursus, k.jadwal " +
                    "FROM peserta ps " +
                    "JOIN pendaftaran p ON ps.idPeserta = p.idPeserta " +
                    "JOIN kursus k ON p.idKursus = k.idKursus " +
                    "WHERE ps.idUser = " + idUser;

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n=== KURSUS SAYA ===");

            boolean adaData = false;

            while (rs.next()) {

                adaData = true;

                System.out.println(
                        rs.getString("namaKursus")
                                + " | "
                                + rs.getString("jadwal"));

            }

            if (!adaData) {

                System.out.println("Belum mengambil kursus.");

            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static void tampilMateri() {

        try {

            Connection conn = getConnection();

            Statement stmt = conn.createStatement();

            String sql =
                    "SELECT m.idMateri, m.judul, k.namaKursus " +
                    "FROM materi m " +
                    "JOIN kursus k ON m.idKursus = k.idKursus";

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n=== DAFTAR MATERI ===");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("idMateri") + " | "
                                + rs.getString("judul") + " | "
                                + rs.getString("namaKursus"));

            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static void cetakPdf() {

        try {

            Connection conn = getConnection();

            JasperReport report =
                    JasperCompileManager.compileReport(
                            Main.class.getClassLoader()
                                    .getResourceAsStream("laporan_kursus.jrxml"));

            JasperPrint print =
                    JasperFillManager.fillReport(
                            report,
                            null,
                            conn);

            JasperExportManager.exportReportToPdfFile(
                    print,
                    "laporan_kursus.pdf");

            System.out.println("\n=== LAPORAN ===");
            System.out.println("PDF berhasil dibuat.");
            System.out.println("File : laporan_kursus.pdf");

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}