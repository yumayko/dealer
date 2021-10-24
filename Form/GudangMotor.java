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
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author LENOVO
 */
public class GudangMotor extends javax.swing.JFrame {
    
    DefaultTableModel tablem;
    /**
     * Creates new form GudangMotor
     */
    
    //Method untuk mendapatkan data dengan parameter semua ArrayList yang dipassing
    public void getData(ArrayList<Sparepart> spareparts, ArrayList<Motor> motors, ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors, ArrayList<dataKaryawan> datak){
        loadDataMotor(motors);
    }
    
    public GudangMotor(ArrayList<Sparepart> spareparts, ArrayList<Motor> motors, ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors, ArrayList<dataKaryawan> datak) {
        //Memanggil Method untuk memuat semua komponen JFrame
        initComponents();
        //Memanggil method untuk memuat semua data pada ArrayList yang diterima
        getData(spareparts,motors,tspareparts,tmotors,datak);
        
        //Menambahkan action listener kedalam button tambah
        tambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //untuk mengecek apakah text field sudah diisi atau belum
                //jika belum maka akan keluar dialog dan inputan tidak diproses
                if(mkode.getText().isEmpty() || mnama.getText().isEmpty() || mmodel.getText().isEmpty() || mwarna.getText().isEmpty() 
                    || mtahun.getText().isEmpty() || mharga.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Mohon isi data dengan lengkap!!");
                }
                //jika field sudah terisi semua, maka data tiap text field akan dimasukan ke variabel
                else{
                    String kode = mkode.getText().trim();
                    String nama = mnama.getText().trim();
                    String model = mmodel.getText().trim();
                    String warna = mwarna.getText().trim();
                    String tahun = mtahun.getText().trim();
                    String harga = mharga.getText().trim();
                    int stok = (int) mstok.getValue();
                    String desk = mdeskripsi.getText().trim();
                    Boolean same = false;
                    //untuk mengecek apakah ada kode motor yang sama
                    //jika iya maka same adalah true
                    if(!motors.isEmpty()){
                        for(int i=0;i<motors.size();i++){
                            if(kode.equals(motors.get(i).getKodeMotor())){
                                same = true;
                            }
                        }
                    }
                    //jika kode motor berbeda maka akan dimasukkan
                    //ke arraylist motor dan database
                    if(same.equals(false)|| motors.isEmpty()){
                        Motor objek = new Motor(kode,nama,model,warna,desk,Integer.parseInt(tahun),Integer.parseInt(harga),stok);
                        motors.add(objek);
                        try {
                            Connection conn = Koneksi.getConnection();
                            String sql = "INSERT INTO tb_motor (kode_motor,nama_motor,model_motor,warna_motor,deskripsi_motor,tahun_motor,harga_motor,stok_motor) VALUES ('"+kode+"','"+nama+"','"+model+"','"+warna+"','"+desk+"','"+Integer.parseInt(tahun)+"','"+Integer.parseInt(harga)+"','"+stok+"')";
                            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                            pst.execute();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    //jika kode motor ada yang sama maka akan keluar dialog gagal
                    else{
                        JOptionPane.showMessageDialog(null, "Maaf kode yang dimasukkan sudah ada!\nMasukkan kode yang berbeda");
                    }
                    //memanggil method untuk menghapus text field
                    clear();
                    //memanggil method untuk merefresh isi arraylist yang ditampilkan pada jtable
                    loadDataMotor(motors);
                }
            }
        });
        
        //Menambahkan action listener kedalam button update
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //jika arraylist kosong maka akan keluar dialog tabel kosong
                if(motors.isEmpty()){
                JOptionPane.showMessageDialog(null, "Tabel masih kosong!");
            }
            else
            {
                //untuk mengecek apakah text field sudah diisi atau belum
                //jika belum maka akan keluar dialog dan inputan tidak diproses
                if(mkode.getText().isEmpty() || mnama.getText().isEmpty() || mmodel.getText().isEmpty() || mwarna.getText().isEmpty() 
                    || mtahun.getText().isEmpty() || mharga.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Mohon isi data dengan lengkap!!");
                }
                else{
                    //jika field sudah terisi semua, maka data tiap text field akan dimasukan ke variabel
                    int selectedRowIndex = (int) tabelmotor.getValueAt(tabelmotor.getSelectedRow(), 0);
                    String newkode = mkode.getText().trim();
                    String newnama = mnama.getText().trim();
                    String newmodel = mmodel.getText().trim();
                    String newwarna = mwarna.getText().trim();
                    String newdesk = mdeskripsi.getText().trim();
                    String newtahun = mtahun.getText().trim();
                    String newharga = mharga.getText().trim();
                    int newstok = (int)mstok.getValue();
                    //membuat objek baru untuk update motor
                    Motor updatemotor = new Motor(newkode,newnama,newmodel,newwarna,newdesk,Integer.parseInt(newtahun),Integer.parseInt(newharga),newstok);
                    //melakukan update motor pada database
                    try {
                        Connection conn = Koneksi.getConnection();
                        String sql = "UPDATE tb_motor SET kode_motor='"+newkode+"',nama_motor='"+newnama+"',model_motor='"+newmodel+"',warna_motor='"+newwarna+"',deskripsi_motor='"+newdesk+"',tahun_motor='"+newtahun+"',harga_motor='"+newharga+"',stok_motor='"+newstok+"' WHERE kode_motor='"+newkode+"'";
                        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    //melakukan update pada arraylist motors dengan parameter index dan objek updatemotor
                    motors.set((selectedRowIndex-1), updatemotor);
                }
            }
                //memanggil method untuk menghapus text field
                clear();
                //memanggil method untuk merefresh isi arraylist yang ditampilkan pada jtable
                loadDataMotor(motors);
            }
        });
        
        //Menambahkan action listener kedalam button hapus
        hapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            //jika arraylist kosong maka akan keluar dialog tabel kosong
            if(motors.isEmpty()){
                JOptionPane.showMessageDialog(null, "Tabel masih kosong!");
            }
            else{
                //proses untuk menghapus data dari arraylist dan database
                //berdasarkan index pada arraylist dan kode motor pada database
                int selectedRowIndex = (int) tabelmotor.getValueAt(tabelmotor.getSelectedRow(),0);
                String kode = mkode.getText().trim();
                motors.remove(selectedRowIndex-1);
                try {
                    Connection conn = Koneksi.getConnection();
                    String sql = "DELETE FROM tb_motor WHERE kode_motor='"+kode+"'";
                    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                    pst.execute();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            //memanggil method untuk menghapus text field
            clear();
            //memanggil method untuk merefresh isi arraylist yang ditampilkan pada jtable
            loadDataMotor(motors);
            }
        });
        
        //Menambahkan action listener kedalam button refresh
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                clear();
                if(motors.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dimuat!!");
                }
                else{
                    //melakukan set table model
                    tablem = (DefaultTableModel) tabelmotor.getModel();
                    TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel> (tablem);
                    tabelmotor.setRowSorter(filter);

                    filter.setRowFilter(null);
                }
                loadDataMotor(motors);
            }
        });
        //Menambahkan action listener kedalam button back
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //method untuk menghilangkan form dan kembali pada menu gudang
                //dengan passing arraylist
                dispose();
                new MenuGudang(spareparts,motors,tspareparts,tmotors,datak).setVisible(true);
            }
        });
    }
    
    //Method untuk memuat semua data ArrayList datak kedalam Tabel Daftar karyawan
    private void loadDataMotor(ArrayList<Motor> motors){
        //Instansiasi model tabel dan deklarasi variabel yang digunakan
        tablem = new DefaultTableModel();
        //Tambahkan semua kolom yang digunakan kedalam model tabel yang telah diinstansiasi
        tablem.addColumn("No");
        tablem.addColumn("Kode Motor");
        tablem.addColumn("Nama");
        tablem.addColumn("Model");
        tablem.addColumn("Warna");
        tablem.addColumn("Tahun");
        tablem.addColumn("Harga");
        tablem.addColumn("Stok");
        tablem.addColumn("Deskripsi");
        
        //Perulangan loop for untuk memasukkan semua data ArrayList datak kedalam model tabel
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
        //Set model JTable menjadi model tabel yang telah dibuat
        tabelmotor.setModel(tablem);
    }
    //method untuk menghapus isi pada text field
    private void clear(){
        mkode.setText("mtr");
        mkode.setEditable(true);
        mnama.setText("");
        mmodel.setText("");
        mwarna.setText("");
        mtahun.setText("");
        mstok.setValue(0);
        mharga.setText("");
        mdeskripsi.setText("");
        carikey.setText("");
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
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        mkode = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        mnama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        mmodel = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mwarna = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        mtahun = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        mdeskripsi = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        mharga = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        mstok = new javax.swing.JSpinner();
        tambah = new javax.swing.JButton();
        update = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelmotor = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        carikey = new javax.swing.JTextField();
        hapus = new javax.swing.JButton();
        cari = new javax.swing.JButton();
        back = new javax.swing.JButton();
        refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Gudang Motor");

        jLabel2.setText("Kode Motor");

        mkode.setText("mtr");

        jLabel6.setText("Nama");

        jLabel3.setText("Model");

        jLabel4.setText("Warna");

        jLabel7.setText("Tahun");

        jLabel8.setText("Deskripsi");

        jLabel9.setText("Harga");

        jLabel10.setText("Stok");

        tambah.setText("Tambah Motor");

        update.setText("Update Motor");

        tabelmotor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode Motor", "Nama", "Model", "Warna", "Tahun", "Harga", "Stok", "Deskripsi"
            }
        ));
        tabelmotor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelmotorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelmotor);

        jLabel11.setText("Masukkan Keyword :");

        hapus.setText("Hapus Motor");

        cari.setText("Cari");
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dealermotor/Icon/arrow_back_icon_151627 (1).png"))); // NOI18N

        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dealermotor/Icon/twocirclingarrows1_120592.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(107, 107, 107))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(mmodel, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(mnama)
                                        .addComponent(mkode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(tambah))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel8)
                                            .addComponent(mtahun)
                                            .addComponent(mdeskripsi)
                                            .addComponent(jLabel7)
                                            .addComponent(mwarna, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(54, 54, 54)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel9)
                                            .addComponent(mharga)
                                            .addComponent(jLabel10)
                                            .addComponent(mstok, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                        .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(162, 162, 162)
                                .addComponent(back))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(carikey, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(cari)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(refresh))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(back))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mwarna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mkode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7))
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mtahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mstok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mmodel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mdeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(carikey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tambah)
                        .addComponent(update)
                        .addComponent(hapus)
                        .addComponent(cari))
                    .addComponent(refresh))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
            tablem = (DefaultTableModel) tabelmotor.getModel();
            TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel> (tablem);
            tabelmotor.setRowSorter(filter);
            
            String key = carikey.getText().trim();
            filter.setRowFilter(RowFilter.regexFilter(key));
    }//GEN-LAST:event_cariActionPerformed

    private void tabelmotorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelmotorMouseClicked
        
        int baris = (int) tabelmotor.getValueAt(tabelmotor.getSelectedRow(), 0);
        baris = baris-1;
        mkode.setText(tablem.getValueAt(baris, 1).toString());
        mnama.setText(tablem.getValueAt(baris, 2).toString());
        mmodel.setText(tablem.getValueAt(baris, 3).toString());
        mwarna.setText(tablem.getValueAt(baris, 4).toString());
        mtahun.setText(tablem.getValueAt(baris, 5).toString());
        mharga.setText(tablem.getValueAt(baris, 6).toString());
        mstok.setValue(tablem.getValueAt(baris, 7));
        mdeskripsi.setText(tablem.getValueAt(baris, 8).toString());
        mkode.setEditable(false);
    }//GEN-LAST:event_tabelmotorMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton cari;
    private javax.swing.JTextField carikey;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mdeskripsi;
    private javax.swing.JTextField mharga;
    private javax.swing.JTextField mkode;
    private javax.swing.JTextField mmodel;
    private javax.swing.JTextField mnama;
    private javax.swing.JSpinner mstok;
    private javax.swing.JTextField mtahun;
    private javax.swing.JTextField mwarna;
    private javax.swing.JButton refresh;
    private javax.swing.JTable tabelmotor;
    private javax.swing.JButton tambah;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
