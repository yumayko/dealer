/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionDatabase;

import Class.Motor;
import Class.Sparepart;
import Class.TransaksiMotor;
import Class.TransaksiSparepart;
import Class.dataKaryawan;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author vorse
 */
public class Koneksi {
    static Connection conn;
    static Statement stmt;
    static ResultSet res;
    
    //method untuk melakukan koneksi ke database db_bengkel pada mysql
     public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/db_bengkel";
        String user = "root";
        String password = "";
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return conn;
    }
     
     //method untuk mengambil data pada tabel dan menyimpannya pada arraylist yang sudah dibuat sebelumnya
     public static void databaseToArrayList(ArrayList<Sparepart> spareparts, ArrayList<Motor> motors, ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors, ArrayList<dataKaryawan> datak){
         try {
            String url = "jdbc:mysql://localhost/db_bengkel";
            String user = "root";
            String pass = "";

            conn = DriverManager.getConnection(url, user, pass);
            stmt = conn.createStatement();
            //Import tabel sparepart
            String SQL = "SELECT * FROM tb_sparepart";
            res = stmt.executeQuery(SQL);
            while (res.next()) {
                Sparepart objeksparepart = new Sparepart(res.getString("kode_sparepart"),
                                                    res.getString("nama_sparepart"),
                                                    res.getString("merk_sparepart"),
                                                    res.getString("deskripsi_sparepart"),
                                                    res.getInt("harga_sparepart"),
                                                    res.getInt("stok_sparepart"));
                spareparts.add(objeksparepart);
            }
            //Import tabel motor
            SQL = "SELECT * FROM tb_motor";
            res = stmt.executeQuery(SQL);
            while (res.next()) {
                Motor objek = new Motor(res.getString("kode_motor"),
                                        res.getString("nama_motor"),
                                        res.getString("model_motor"),
                                        res.getString("warna_motor"),
                                        res.getString("deskripsi_motor"),
                                        res.getInt("tahun_motor"),
                                        res.getInt("harga_motor"),
                                        res.getInt("stok_motor"));
                motors.add(objek);
            }
            //Import tabel transaksi sparepart
            SQL = "SELECT * FROM tb_transaksi_sparepart";
            res = stmt.executeQuery(SQL);
            while (res.next()) {
                TransaksiSparepart transp = new TransaksiSparepart(res.getString("kode_transaksi"),
                                        res.getString("kode_sparepart"),
                                        res.getString("nama_sparepart"),
                                        res.getString("merk_sparepart"),
                                        res.getString("deskripsi"),
                                        res.getInt("subtotal"),
                                        res.getInt("jumlah"));
                                        
                tspareparts.add(transp);
            }
            //Import tabel transaksi motor
            SQL = "SELECT * FROM tb_transaksi_motor";
            res = stmt.executeQuery(SQL);
            while (res.next()) {
                TransaksiMotor transm = new TransaksiMotor(res.getString("kode_transaksi"),
                                        res.getString("kode_motor"),
                                        res.getString("nama_motor"),
                                        res.getString("model_motor"),
                                        res.getString("warna_motor"),
                                        res.getString("deskripsi"),
                                        res.getInt("tahun_motor"),
                                        res.getInt("subtotal"),
                                        res.getInt("jumlah"));
                tmotors.add(transm);
            }
            //Import tabel data karyawan
            SQL = "SELECT * FROM tb_karyawan";
            res = stmt.executeQuery(SQL);
            while (res.next()) {
                dataKaryawan karyawan = new dataKaryawan(res.getString("username"),
                                        res.getString("password"),
                                        res.getString("nama_karyawan"),
                                        res.getString("posisi"));
                datak.add(karyawan);
            }
            stmt.close();
            conn.close();
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
     }
     public static void main(String[] args) {
        try {
            Connection c = Koneksi.getConnection();
            System.out.println(String.format("koneksi ke database %s " + "sukses!", c.getCatalog()));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
