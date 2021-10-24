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

//object untuk sparepart
public class Sparepart {
    public String kode,nama,merk,deskripsi;
    public int harga,stok;
    
    //constructor object sparepart
    public Sparepart(String kode, String nama, String merk, String deskripsi, int harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.merk = merk;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.stok = stok;
        
    }
    
    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getMerk() {
        return merk;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }
    public int minusstok(int stok, int jumlah){
        return stok-jumlah;
    }
}