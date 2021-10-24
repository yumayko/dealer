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

//inheritance dari sparepart
public class TransaksiSparepart extends Sparepart{
    String kodetran;
    public TransaksiSparepart(String kodetran, String kode, String nama, String merk, String deskripsi, int harga, int stok) {
        super(kode, nama, merk, deskripsi, harga, stok);
        this.kodetran = kodetran;
    }    

    public String getKodetran() {
        return kodetran;
    }

    public void setKodetran(String kodetran) {
        this.kodetran = kodetran;
    }
    
}
