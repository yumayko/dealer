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

//inheritance dari objek motor
public class TransaksiMotor extends Motor{
    String kodetransaksi;
    public TransaksiMotor(String kodetran, String kodeMotor, String nama, String model, String warna, String deskripsi, int tahun, int harga, int stok) {
        super(kodeMotor, nama, model, warna, deskripsi, tahun, harga, stok);
        this.kodetransaksi = kodetran;
    }

    public String getKodetransaksi() {
        return kodetransaksi;
    }

    public void setKodetransaksi(String kodetransaksi) {
        this.kodetransaksi = kodetransaksi;
    }
    
}
