package com.kursusonline;

import java.sql.Connection;
import java.sql.DriverManager;
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
            System.out.println("2. Lihat Materi");
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
                    tampilMateri();
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
        public static void tampilKursus() {

        try {

            Connection conn = getConnection();

            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM kursus";

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n=== DAFTAR KURSUS ===");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("idKursus") + " | "
                                + rs.getString("namaKursus") + " | "
                                + rs.getString("jadwal"));

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