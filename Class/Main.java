 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import Form.GudangSparepart;
import ConnectionDatabase.Koneksi;
import Form.GudangMotor;
import Form.MenuGudang;
import Form.MenuUtamaDealer;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class Main {

        static ArrayList<Sparepart> spareparts;
        static ArrayList<Motor> motors;
        static ArrayList<TransaksiSparepart> tspareparts;
        static ArrayList<TransaksiMotor> tmotors;
        static ArrayList<dataKaryawan> datak;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // membuat objek arraylist baru dari class sparepart,motor,transaksi sparepart,transaksi motor,data karyawan
        spareparts = new ArrayList<>();
        motors = new ArrayList<>();
        tspareparts = new ArrayList<>();
        tmotors = new ArrayList<>();
        datak = new ArrayList<>();
        
        //mengambil method dari koneksi untuk mengambil data dari database lalu disimpan di arraylist
        Koneksi.databaseToArrayList(spareparts, motors, tspareparts, tmotors, datak);
        
        //membuat objek baru untuk membuka form MenuUtamaDealer dengan parameter arraylist yang sudah dibuat sebelumnya
        MenuUtamaDealer newMenu = new MenuUtamaDealer(spareparts,motors,tspareparts,tmotors,datak);
        newMenu.setVisible(true);
    }
    
}
