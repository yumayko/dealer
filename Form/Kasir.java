/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Class.Motor;
import Class.Sparepart;
import Class.TransaksiMotor;
import Class.TransaksiSparepart;
import Class.dataKaryawan;
import ConnectionDatabase.Koneksi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wahyu
 */
public class Kasir extends javax.swing.JFrame {

    DefaultTableModel tablesp,tablem,listkeranjangs,listkeranjangm,listtransaksi;
    ArrayList<TransaksiSparepart> tempkeranjangs = new ArrayList<>();
    ArrayList<TransaksiMotor> tempkeranjangm = new ArrayList<>();
    
    /**
     * Creates new form NewJFrame
     */
    
    //Method untuk mendapatkan data dengan parameter semua ArrayList yang dipassing 
    public void getData(ArrayList<Sparepart> spareparts, ArrayList<Motor> motors, ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors, ArrayList<dataKaryawan> datak){
        loadDataSparepart(spareparts);
        loadDataMotor(motors);
        loadDataTransactionS(tempkeranjangs);
        loadDataTransactionM(tempkeranjangm);
        loadDataTabelTransaksi(tspareparts, tmotors);
    }
    
    //Method untuk memuat data sparepart kedalam tabel dengan parameter ArrayList spareparts
    private void loadDataSparepart(ArrayList<Sparepart> spareparts){
        //Instansiasi model tabel untuk digunakan pada JTable
        tablesp = new DefaultTableModel();
        
        //Menambahkan kolom kepada Model tabel yang telah diinstasiasi
        tablesp.addColumn("No");
        tablesp.addColumn("Kode");
        tablesp.addColumn("Nama");
        tablesp.addColumn("Merk");
        tablesp.addColumn("Harga");
        tablesp.addColumn("Stok");
        tablesp.addColumn("Deskripsi");
        
        //Menggunakan loop for untuk memasukkan semua data pada Arraylist spareparts kedalam row pada Model tabel yang telah diinstansiasi
        for(int i = 0; i<spareparts.size(); i++){
            tablesp.addRow(new Object[] {
            (i+1),
            spareparts.get(i).getKode(),
            spareparts.get(i).getNama(),
            spareparts.get(i).getMerk(),
            spareparts.get(i).getHarga(),
            spareparts.get(i).getStok(),
            spareparts.get(i).getDeskripsi()
            });
        }
        //Set model tabel pada JTable menjadi model tabel yang telah dibuat
        listsp.setModel(tablesp);
    }
    
    //Method untuk memuat data Motor kedalam tabel dengan parameter ArrayList motors
    private void loadDataMotor(ArrayList<Motor> motors){
        //Instansiasi model tabel
        tablem = new DefaultTableModel();
        
        //Menambahkan semua kolom yang akan digunakan kedalam model tabel
        tablem.addColumn("No");
        tablem.addColumn("Kode Motor");
        tablem.addColumn("Nama");
        tablem.addColumn("Model");
        tablem.addColumn("Warna");
        tablem.addColumn("Tahun");
        tablem.addColumn("Harga");
        tablem.addColumn("Stok");
        tablem.addColumn("Deskripsi");
        
        //Menggunakan loop for untuk menambahkan semua data pada ArrayList motors kedalam model tabel
        for(int i=0; i<motors.size(); i++){
            tablem.addRow(new Object[] {
                (i+1),
                motors.get(i).getKodeMotor(),
                motors.get(i).getNama(),
                motors.get(i).getModel(),
                motors.get(i).getWarna(),
                motors.get(i).getTahun(),
                motors.get(i).getHarga(),
                motors.get(i).getStok(),
                motors.get(i).getDeskripsi()
            });
        }
        //Set model tabel JTable menjadi tabel yang telah dibuat
        listm.setModel(tablem);
    }
    
    //Method untuk memuat data transaksi sparepart pada ArrayList tempkeranjangs kedalam tabel keranjang sparepart
    private void loadDataTransactionS(ArrayList<TransaksiSparepart> tempkeranjangs) {
        //Instansiasi model tabel dan deklarasi variabel yang digunakan
        listkeranjangs = new DefaultTableModel();
        int totalharga = 0;
        
        //Percabangan if ketika ArrayList tidak kosong maka value JTextField kodetrs akan diset menjadi value kode transaksi pada ArrayList tempkeranjangs
        if(!tempkeranjangs.isEmpty()){
            kodetrs.setText(tempkeranjangs.get(0).getKodetran());
        }
        
        //Menambahkan semua kolom yang dibutuhkan kedalam model tabel yang telah diinstansiasi
        listkeranjangs.addColumn("No");
        listkeranjangs.addColumn("Kode Transaksi");
        listkeranjangs.addColumn("Kode Sparepart");
        listkeranjangs.addColumn("Nama");
        listkeranjangs.addColumn("Merk");
        listkeranjangs.addColumn("Subtotal");
        listkeranjangs.addColumn("Jumlah");
        listkeranjangs.addColumn("Deskripsi");
        
        //Perulangan loop for untuk menambahkan semua data pada ArrayList tempkeranjangd kedalam model tabel
        for(int i = 0; i<tempkeranjangs.size(); i++){
            listkeranjangs.addRow(new Object[] {
                (i+1),
                tempkeranjangs.get(i).getKodetran(),
                tempkeranjangs.get(i).getKode(),
                tempkeranjangs.get(i).getNama(),
                tempkeranjangs.get(i).getMerk(),
                tempkeranjangs.get(i).getHarga(),
                tempkeranjangs.get(i).getStok(),
                tempkeranjangs.get(i).getDeskripsi(),
            });
        }
        //Set model tabel JTable menjadi model yang telah dibuat
        keranjangsp.setModel(listkeranjangs);
        
        //Menggunakan loop for untuk menghitung semua nilai harga pada ArrayList tempkeranjangs
        for(int i=0;i<tempkeranjangs.size();i++){
                    totalharga = totalharga + tempkeranjangs.get(i).getHarga();
                }
                //Set isi dari JTextfield totalhargas dengan total harga yang telah dihitung
                totalhargas.setText("Rp."+totalharga);
    }
    
    //Method untuk memuat data transaksi motor pada ArrayList tempkeranjangm kedalam tabel keranjang motor
    private void loadDataTransactionM(ArrayList<TransaksiMotor> tempkeranjangm){
        //Instansiasi model tabel dan deklarasi variabel
        listkeranjangm = new DefaultTableModel();
        int totalharga = 0;
        
        //Percabangan if ketika ArrayList tidak kosong maka value JTextField kodetrm akan diset menjadi value kode transaksi pada ArrayList tempkeranjangm
        if(!tempkeranjangm.isEmpty()){
            kodetrs.setText(tempkeranjangm.get(0).getKodetransaksi());
        }
        
        //Menambahkan semua kolom yang digunakan kedalam model tabel yang telah diinstansiasi
        listkeranjangm.addColumn("No");
        listkeranjangm.addColumn("Kode Transaksi");
        listkeranjangm.addColumn("Kode Motor");
        listkeranjangm.addColumn("Nama Motor");
        listkeranjangm.addColumn("Model");
        listkeranjangm.addColumn("Warna");
        listkeranjangm.addColumn("Tahun");
        listkeranjangm.addColumn("Subtotal");
        listkeranjangm.addColumn("Jumlah");
        listkeranjangm.addColumn("Deskripsi");
        
        //Perulangan untuk menambahkan semua data pada ArrayList tempkeranjangm kedalam model tabel
        for(int i = 0; i<tempkeranjangm.size();i++){
            listkeranjangm.addRow(new Object[] {
                (i+1),
                tempkeranjangm.get(i).getKodetransaksi(),
                tempkeranjangm.get(i).getKodeMotor(),
                tempkeranjangm.get(i).getNama(),
                tempkeranjangm.get(i).getModel(),
                tempkeranjangm.get(i).getWarna(),
                tempkeranjangm.get(i).getTahun(),
                tempkeranjangm.get(i).getHarga(),
                tempkeranjangm.get(i).getStok(),
                tempkeranjangm.get(i).getDeskripsi()
                
            });
        }
        //Set model JTable menjadi model tabel yang telah dibuat
        keranjangm.setModel(listkeranjangm);

        //Menggunakan loop for untuk menghitung semua nilai harga yang ada pada ArrayList tempkeranjangm
        for(int i=0;i<tempkeranjangm.size();i++){
                    totalharga = totalharga + tempkeranjangm.get(i).getHarga();
                }
                //Set isi dari JTextField totalhargam dengan total harga yang telah dihitung
                totalhargam.setText("Rp."+totalharga);
    }
    
    //Method untuk memuat semua data ArrayList tspareparts dan tmotors kedalam Tabel Daftar Transaksi
    private void loadDataTabelTransaksi(ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors){
        //Instansiasi model tabel dan deklarasi variabel yang digunakan
        int i=1;
        listtransaksi = new DefaultTableModel();
        
        //Tambahkan semua kolom yang digunakan kedalam model tabel yang telah diinstansiasi
        listtransaksi.addColumn("No");
        listtransaksi.addColumn("Kode Transaksi");
        listtransaksi.addColumn("Kode Barang");
        listtransaksi.addColumn("Nama Barang");
        listtransaksi.addColumn("Subtotal");
        listtransaksi.addColumn("Jumlah");
        
        //Perulangan loop for untuk memasukkan semua data ArrayList tspareparts kedalam model tabel
        for(int j = 0; j<tspareparts.size();j++){
            listtransaksi.addRow(new Object[]{
                i,
                tspareparts.get(j).getKodetran(),
                tspareparts.get(j).getKode(),
                tspareparts.get(j).getNama(),
                tspareparts.get(j).getHarga(),
                tspareparts.get(j).getStok()
            });
            i++;
        }
        //Perulangan loop for untuk memasukkan semua data ArrayList tmotors kedalam model tabel
        for(int j=0; j<tmotors.size();j++){
            listtransaksi.addRow(new Object[]{
                i,
                tmotors.get(j).getKodetransaksi(),
                tmotors.get(j).getKodeMotor(),
                tmotors.get(j).getNama(),
                tmotors.get(j).getHarga(),
                tmotors.get(j).getStok()
            });
            i++;
        }
        //Set model JTable menjadi model tabel yang telah dibuat
        tabeltransaksi.setModel(listtransaksi);
    }
    
    //Frame Kasir menerima semua ArrayList yang dipassing dari MenuUtamaDealer
    public Kasir(ArrayList<Sparepart> spareparts, ArrayList<Motor> motors, ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors, ArrayList<dataKaryawan> datak) {
        //Memanggil Method untuk memuat semua komponen JFrame
        initComponents();
        //Memanggil method untuk memuat semua data pada ArrayList yang diterima
        getData(spareparts, motors, tspareparts, tmotors, datak);
        
        //Menambahkan action listener kedalam button tambahsparepart
        tambahsparepart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Deklarasikan variabel untuk menyimpan indeks row yang dipilih pada tabel listsp
                int baris = (int) listsp.getValueAt(listsp.getSelectedRow(), 0);
                baris=baris-1;
                int currentstock = 0;
                //Simpan semua data pada tabel kedalam variabel sementara untuk nantinya dimasukkan kedalam ArrayList tempkeranjangs
                String kodetran = kodetrs.getText().trim();
                String tkode = listsp.getValueAt(baris, 1).toString();
                String tnama = listsp.getValueAt(baris, 2).toString();
                String tmerk = listsp.getValueAt(baris, 3).toString();
                int tharga = (int) listsp.getValueAt(baris, 4);
                int tstok = Integer.parseInt(jumlahsp.getText().trim());
                String tdesk = listsp.getValueAt(baris, 6).toString();
                //Hitung subtotal ketika customer membeli barang dalam stok banyak
                int subtotal = tharga*tstok;
                //Percabangan if ketika customer memasukkan nilai jumlah barang yang melebihi stok barang
                if(spareparts.get(baris).getStok()<tstok){
                    //Tampilkan pesan
                    JOptionPane.showMessageDialog(jumlahsp, "Jumlah tidak boleh melebihi stok!");
                }
                else{
                    //Instansiasi objek untuk nantinya dimasukkan kedalam ArrayList tempkeranjangs
                    TransaksiSparepart keranjang = new TransaksiSparepart(kodetran, tkode, tnama, tmerk, tdesk, subtotal, tstok);
                    //Tambahkan data kedalam ArrayList tempkeranjangs
                    tempkeranjangs.add(keranjang);
                    //Hitung sisa stok dengan mengurangkan stok saat ini dengan jumlah yang dibeli
                    currentstock = spareparts.get(baris).getStok() - tstok;
                    //Instansiasi objek untuk nantinya dimasukkan kedalam ArrayList spareparts untuk mengupdate jumlah stok
                    Sparepart tmp = new Sparepart(tkode, tnama, tmerk, tdesk, tharga, currentstock);
                    //Update nilai stok yang berubah kedalam database sesuai dengan barang yang bersangkutan
                    try {
                        Connection conn = Koneksi.getConnection();
                        String sql = "UPDATE tb_sparepart SET stok_sparepart='"+currentstock+"' WHERE kode_sparepart='"+tkode+"'";
                        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    //Update ArrayList spareparts dengan indeks sesuai dengan indeks tabel yang dipilih
                    spareparts.set(baris, tmp);
                }
                //Set JTextField jumlahsp menjadi kosong
                jumlahsp.setText("");
                //Memanggil method untuk memuat data ArrayList yang baru
                loadDataSparepart(spareparts);
                loadDataTransactionS(tempkeranjangs);
            }
        });
        //Menambahkan ActionListener kedalam button tambahmotor
        tambahmotor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Deklarasikan variabel untuk menyimpan indeks row yang dipilih pada tabel listm
                int baris = (int) listm.getValueAt(listm.getSelectedRow(), 0);
                baris=baris-1;
                int currentstock = 0;
                //Simpan semua data pada tabel kedalam variabel sementara untuk nantinya dimasukkan kedalam ArrayList tempkeranjangm
                String kodetransaksi = kodetrm.getText().trim();
                String tkode = listm.getValueAt(baris, 1).toString();
                String tnama = listm.getValueAt(baris, 2).toString();
                String tmodel = listm.getValueAt(baris, 3).toString();
                String twarna = listm.getValueAt(baris, 4).toString();
                int ttahun = (int) listm.getValueAt(baris, 5);
                int tharga = (int) listm.getValueAt(baris, 6);
                int tstok = Integer.parseInt(jumlahm.getText().trim());
                String tdesk = listm.getValueAt(baris, 7).toString();
                //Hitung subtotal ketika customer membeli barang dalam stok banyak
                int subtotal = tharga*tstok;
                //Percabangan if ketika customer memasukkan nilai jumlah barang yang melebihi stok barang
                if(motors.get(baris).getStok()<tstok){
                    JOptionPane.showMessageDialog(jumlahsp, "Jumlah tidak boleh melebihi stok!");
                }
                else{
                    //Instansiasi objek untuk nantinya dimasukkan kedalam ArrayList tempkeranjangm
                    TransaksiMotor keranjang = new TransaksiMotor(kodetransaksi, tkode, tnama, tmodel, twarna, tdesk, ttahun, subtotal, tstok);
                    //Tambahkan data kedalam ArrayList tempkeranjangm
                    tempkeranjangm.add(keranjang);
                    //Hitung sisa stok dengan mengurangkan stok saat ini dengan jumlah yang dibeli
                    currentstock = motors.get(baris).getStok() - tstok;
                    //Instansiasi objek untuk nantinya dimasukkan kedalam ArrayList motors untuk mengupdate jumlah stok
                    Motor tmp = new Motor(tkode, tnama, tmodel, twarna, tdesk, ttahun, tharga, currentstock);
                    //Update nilai stok yang berubah kedalam database sesuai dengan barang yang bersangkutan
                    try {
                        Connection conn = Koneksi.getConnection();
                        String sql = "UPDATE tb_motor SET stok_motor='"+currentstock+"' WHERE kode_motor='"+tkode+"'";
                        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    //Update ArrayList motors dengan indeks sesuai indeks tabel yang dipilih
                    motors.set(baris, tmp);
                }
                //Set JTextField jumlahm menjadi kosong
                jumlahm.setText("");
                //Memanggil method untuk memuat data ArrayList yang baru
                loadDataMotor(motors);
                loadDataTransactionM(tempkeranjangm);
            }
        });
        
        //Tambahkan ActionListener kedalam button SubmitDatabaseTSP
        SubmitDatabaseTSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Perulangan loop for untuk memasukkan semua data ArrayList tempkeranjangs kedalam ArrayList tspareparts dan database
                for(int i=0; i<tempkeranjangs.size(); i++){
                    String datakodetran = tempkeranjangs.get(i).getKodetran();
                    String datakode = tempkeranjangs.get(i).getKode();
                    String datanama = tempkeranjangs.get(i).getNama();
                    String datamerk = tempkeranjangs.get(i).getMerk();
                    String datadesk = tempkeranjangs.get(i).getDeskripsi();
                    int datasubtotal = tempkeranjangs.get(i).getHarga();
                    int datajumlah = tempkeranjangs.get(i).getStok();
                    //Try catch untuk menambahkan data ArrayList tempkeranjangs kedalam database
                    try {
                        Connection conn = Koneksi.getConnection();
                        String sql = "INSERT INTO tb_transaksi_sparepart (kode_transaksi,kode_sparepart,nama_sparepart,merk_sparepart,subtotal,jumlah,deskripsi) VALUES ('"+datakodetran+"','"+datakode+"','"+datanama+"','"+datamerk+"','"+datasubtotal+"','"+datajumlah+"','"+datadesk+"')";
                        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    //Tambahkan data-data pada ArrayList tempkeranjangs kedalam ArrayList tspareparts
                    TransaksiSparepart tsp = new TransaksiSparepart(datakodetran, datakode, datanama, datamerk, datadesk, datasubtotal, datajumlah);
                    tspareparts.add(tsp);
                }
                //Mengosongkan isi ArrayList tempkeranjangs
                tempkeranjangs.removeAll(tempkeranjangs);
                //Memanggil method untuk memuat data ArrayList yang baru
                loadDataTabelTransaksi(tspareparts, tmotors);
                loadDataTransactionS(tempkeranjangs);
                //Set value JTextField menjadi default
                kodetrs.setText("trsp");
            }
        });
        
        //Tambahkan ActionListener pada button SubmitDatabaseTM
        SubmitDatabaseTM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Perulangan loop for untuk memasukkan semua data ArrayList tempkeranjangm kedalam ArrayList tmotors dan database
                for(int i=0;i<tempkeranjangm.size();i++){
                    String datakodetran = tempkeranjangm.get(i).getKodetransaksi();
                    String datakode = tempkeranjangm.get(i).getKodeMotor();
                    String datanama = tempkeranjangm.get(i).getNama();
                    String datamodel = tempkeranjangm.get(i).getModel();
                    String datawarna = tempkeranjangm.get(i).getWarna();
                    String datadesk = tempkeranjangm.get(i).getDeskripsi();
                    int datatahun = tempkeranjangm.get(i).getTahun();
                    int datasubtotal = tempkeranjangm.get(i).getHarga();
                    int datajumlah = tempkeranjangm.get(i).getStok();
                    //Try catch untuk menambahkan data ArrayList tempkeranjangm kedalam database
                    try {
                        Connection conn = Koneksi.getConnection();
                        String sql = "INSERT INTO tb_transaksi_motor (kode_transaksi,kode_motor,nama_motor,model_motor,warna_motor,tahun_motor,subtotal,jumlah,deskripsi) VALUES ('"+datakodetran+"','"+datakode+"','"+datanama+"','"+datamodel+"','"+datawarna+"','"+datatahun+"','"+datasubtotal+"','"+datajumlah+"','"+datadesk+"')";
                        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    //Tambahkan data-data pada ArrayList tempkeranjangm kedalam ArrayList tmotors
                    TransaksiMotor tsm = new TransaksiMotor(datakodetran, datakode, datanama, datamodel, datawarna, datadesk, datatahun, datasubtotal, datajumlah);
                    tmotors.add(tsm);
                }
                //Mengosongkan isi ArrayList tempkeranjangs
                tempkeranjangm.removeAll(tempkeranjangm);
                //Memanggil method untuk memuat data ArrayList yang baru
                loadDataTabelTransaksi(tspareparts, tmotors);
                loadDataTransactionM(tempkeranjangm);
                //Set value JTextField menjadi default
                kodetrm.setText("trm");
                
            }
        });
        //Tambahkan ActionListener kedalam button hapustsp
        hapustsp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Deklarasikan variabel untuk menyimpan indeks row yang dipilih pada tabel keranjangsp
                int baris = (int) keranjangsp.getValueAt(keranjangsp.getSelectedRow(), 0);;
                baris = baris-1;
                int j = 0;
                //Perulangan loop for untuk mencari indeks sparepart pada ArrayList spareparts yang sesuai dengan sparepart yang dipilih pada tabel keranjangsp
                for(int i=0;i<spareparts.size();i++){
                    if(!tempkeranjangs.get(baris).getKode().equals(spareparts.get(j).getKode())){
                        j++;
                    }
                    else{
                        break;
                    }
                }
                //Hitung stok terbaru dengan menambahkan stok barang saat ini dengan jumlah barang yang dihapus pada tabel yang dipilih
                int ustok = tempkeranjangs.get(baris).getStok() + spareparts.get(j).getStok();
                //Instansiasi objek untuk update stok barang yang sesuai
                Sparepart newsp = new Sparepart(spareparts.get(j).getKode(), spareparts.get(j).getNama(), spareparts.get(j).getMerk(), spareparts.get(j).getDeskripsi(), spareparts.get(j).getHarga(), ustok);
                //Update stok yang terbaru pada database
                try {
                        Connection conn = Koneksi.getConnection();
                        String sql = "UPDATE tb_sparepart SET stok_sparepart='"+ustok+"' WHERE kode_sparepart='"+spareparts.get(j).getKode()+"'";
                        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                //Set nilai pada ArrayList spareparts sesuai dengan indeks barang yang berubah
                spareparts.set(j, newsp);
                //Hapus nilai pada ArrayList tempkeranjangs dengan indeks sesuai dengan indeks row pada tabel yang dipilih
                tempkeranjangs.remove(baris);
                
                //Memanggil method untuk memuat data yang terbaru
                loadDataSparepart(spareparts);
                loadDataTransactionS(tempkeranjangs);
            }
        });
        //Tambahkan ActionListener kedalam button hapustm
        hapustm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Deklarasikan variabel untuk menyimpan indeks row yang dipilih pada tabel keranjangm
                int baris = (int) keranjangm.getValueAt(keranjangm.getSelectedRow(), 0);;
                baris = baris-1;
                int j = 0;
                //Perulangan loop for untuk mencari indeks motor pada ArrayList motors yang sesuai dengan motor yang dipilih pada tabel keranjangm
                for(int i=0;i<motors.size();i++){
                    if(!tempkeranjangm.get(baris).getKodeMotor().equals(motors.get(j).getKodeMotor())){
                        j++;
                    }
                    else{
                        break;
                    }
                }
                //Hitung stok terbaru dengan menambahkan stok barang saat ini dengan jumlah barang yang dihapus pada tabel yang dipilih
                int ustok = tempkeranjangm.get(baris).getStok() + motors.get(j).getStok();
                //Instansiasi objek untuk update stok barang yang sesuai
                Motor newm = new Motor(motors.get(j).getKodeMotor(), motors.get(j).getNama(), motors.get(j).getModel(), motors.get(j).getWarna(), motors.get(j).getDeskripsi(), motors.get(j).getTahun(), motors.get(j).getHarga(), ustok);
                //Update stok yang terbaru pada database
                try {
                        Connection conn = Koneksi.getConnection();
                        String sql = "UPDATE tb_motor SET stok_motor='"+ustok+"' WHERE kode_motor='"+motors.get(j).getKodeMotor()+"'";
                        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                //Set nilai pada ArrayList motors sesuai dengan indeks barang yang berubah
                motors.set(j, newm);
                //Hapus nilai pada ArrayList tempkeranjangs dengan indeks sesuai dengan indeks row pada tabel yang dipilih
                tempkeranjangm.remove(baris);
                //Memanggil method untuk memuat data yang terbaru
                loadDataMotor(motors);
                loadDataTransactionM(tempkeranjangm);
            }
        });
        //Tambahkan ActionListener pada button logout
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Membuka MenuUtamaDealer dengan mempassing semua ArrayList yang dibutuhkan
                new MenuUtamaDealer(spareparts, motors, tspareparts, tmotors, datak).setVisible(true);
                //Menghilangkan tampilan menu kasir
                dispose();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        sparepart = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listsp = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        keranjangsp = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tambahsparepart = new javax.swing.JButton();
        jumlahsp = new javax.swing.JTextField();
        kodetrs = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        totalhargas = new javax.swing.JTextField();
        SubmitDatabaseTSP = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        hapustsp = new javax.swing.JButton();
        motor = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listm = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        keranjangm = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        kodetrm = new javax.swing.JTextField();
        jumlahm = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tambahmotor = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        totalhargam = new javax.swing.JTextField();
        SubmitDatabaseTM = new javax.swing.JButton();
        hapustm = new javax.swing.JButton();
        transaksi = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabeltransaksi = new javax.swing.JTable();
        logout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("KASIR");

        listsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(listsp);

        keranjangsp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(keranjangsp);

        jLabel2.setText("List Sparepart :");

        jLabel3.setText("Daftar Keranjang :");

        tambahsparepart.setText("Tambah");

        kodetrs.setText("trsp");

        jLabel4.setText("Kode :");

        jLabel5.setText("Total Harga :");

        totalhargas.setText("Rp.-");

        SubmitDatabaseTSP.setText("Submit");

        jLabel10.setText("Jumlah :");

        hapustsp.setText("Hapus dari Keranjang");

        javax.swing.GroupLayout sparepartLayout = new javax.swing.GroupLayout(sparepart);
        sparepart.setLayout(sparepartLayout);
        sparepartLayout.setHorizontalGroup(
            sparepartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sparepartLayout.createSequentialGroup()
                .addGroup(sparepartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sparepartLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel2))
                    .addGroup(sparepartLayout.createSequentialGroup()
                        .addGroup(sparepartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, sparepartLayout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(hapustsp))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, sparepartLayout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addGroup(sparepartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(34, 34, 34)
                        .addGroup(sparepartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(totalhargas, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SubmitDatabaseTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tambahsparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addGroup(sparepartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jumlahsp, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(kodetrs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        sparepartLayout.setVerticalGroup(
            sparepartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sparepartLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sparepartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sparepartLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kodetrs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jumlahsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tambahsparepart)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(sparepartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sparepartLayout.createSequentialGroup()
                        .addGroup(sparepartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(hapustsp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sparepartLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalhargas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SubmitDatabaseTSP)
                        .addGap(93, 93, 93))))
        );

        jTabbedPane2.addTab("Sparepart", sparepart);

        listm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(listm);

        keranjangm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(keranjangm);

        jLabel6.setText("List Motor :");

        jLabel7.setText("Daftar Keranjang :");

        jLabel8.setText("Kode :");

        kodetrm.setText("trm");

        jLabel9.setText("Jumlah");

        tambahmotor.setText("Tambah");

        jLabel11.setText("Total Harga :");

        totalhargam.setText("Rp.-");

        SubmitDatabaseTM.setText("Submit");

        hapustm.setText("Hapus dari Keranjang");

        javax.swing.GroupLayout motorLayout = new javax.swing.GroupLayout(motor);
        motor.setLayout(motorLayout);
        motorLayout.setHorizontalGroup(
            motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(motorLayout.createSequentialGroup()
                .addGroup(motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(motorLayout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel6))
                    .addGroup(motorLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(motorLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(hapustm))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                            .addComponent(jScrollPane4))
                        .addGroup(motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(motorLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(tambahmotor, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                    .addComponent(jumlahm)
                                    .addComponent(kodetrm)))
                            .addGroup(motorLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11)
                                    .addComponent(SubmitDatabaseTM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(totalhargam, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        motorLayout.setVerticalGroup(
            motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(motorLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addGroup(motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(motorLayout.createSequentialGroup()
                        .addGroup(motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(motorLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(motorLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kodetrm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addGap(5, 5, 5)
                                .addComponent(jumlahm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tambahmotor)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(4, 4, 4))
                    .addGroup(motorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hapustm)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(motorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(motorLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalhargam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SubmitDatabaseTM)))
                .addGap(23, 23, 23))
        );

        jTabbedPane2.addTab("Motor", motor);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setText("DAFTAR TRANSAKSI");

        tabeltransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tabeltransaksi);

        javax.swing.GroupLayout transaksiLayout = new javax.swing.GroupLayout(transaksi);
        transaksi.setLayout(transaksiLayout);
        transaksiLayout.setHorizontalGroup(
            transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiLayout.createSequentialGroup()
                .addGap(377, 377, 377)
                .addComponent(jLabel12)
                .addContainerGap(382, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        transaksiLayout.setVerticalGroup(
            transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Daftar Transaksi", transaksi);

        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dealermotor/Icon/4115235-exit-logout-sign-out_114030.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(453, 453, 453)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logout))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(logout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SubmitDatabaseTM;
    private javax.swing.JButton SubmitDatabaseTSP;
    private javax.swing.JButton hapustm;
    private javax.swing.JButton hapustsp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jumlahm;
    private javax.swing.JTextField jumlahsp;
    private javax.swing.JTable keranjangm;
    private javax.swing.JTable keranjangsp;
    private javax.swing.JTextField kodetrm;
    private javax.swing.JTextField kodetrs;
    private javax.swing.JTable listm;
    private javax.swing.JTable listsp;
    private javax.swing.JButton logout;
    private javax.swing.JPanel motor;
    private javax.swing.JPanel sparepart;
    private javax.swing.JTable tabeltransaksi;
    private javax.swing.JButton tambahmotor;
    private javax.swing.JButton tambahsparepart;
    private javax.swing.JTextField totalhargam;
    private javax.swing.JTextField totalhargas;
    private javax.swing.JPanel transaksi;
    // End of variables declaration//GEN-END:variables
}
