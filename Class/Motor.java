/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

/**
 *
 * @author LENOVO
 */

//object untuk motor
public class Motor {
    public String kodeMotor,nama,model,warna,deskripsi;
    public int tahun,harga,stok;

    //constructor object motor
    public Motor(String kodeMotor, String nama, String model, String warna, String deskripsi, int tahun, int harga, int stok) {
        this.kodeMotor = kodeMotor;
        this.nama = nama;
        this.model = model;
        this.warna = warna;
        this.deskripsi = deskripsi;
        this.tahun = tahun;
        this.harga = harga;
        this.stok = stok;
    }

    public String getKodeMotor() {
        return kodeMotor;
    }

    public String getNama() {
        return nama;
    }

    public String getModel() {
        return model;
    }

    public String getWarna() {
        return warna;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getTahun() {
        return tahun;
    }

    public int getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }
    
    
}
