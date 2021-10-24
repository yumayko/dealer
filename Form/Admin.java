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
import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author wahyu
 */
public class Admin extends javax.swing.JFrame {
    
    DefaultTableModel tablekaryawan;
    
    /**
     * 
     * Creates new form Admin
     * @param spareparts
     * @param motors
     * @param tspareparts
     * @param tmotors
     * @param datak
     */
    
    //Method untuk mendapatkan data dengan parameter semua ArrayList yang dipassing
    public void getData(ArrayList<Sparepart> spareparts, ArrayList<Motor> motors, ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors, ArrayList<dataKaryawan> datak){
        loadDataKaryawan(datak);
    }
    
    public Admin(ArrayList<Sparepart> spareparts, ArrayList<Motor> motors, ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors, ArrayList<dataKaryawan> datak) {
        //Memanggil Method untuk memuat semua komponen JFrame
        initComponents();
        //Memanggil method untuk memuat semua data pada ArrayList yang diterima
        getData(spareparts, motors, tspareparts, tmotors, datak);
        
        //Menambahkan action listener kedalam button tambah
        Tambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //untuk mengecek apakah text field sudah diisi atau belum
                //jika belum maka akan keluar dialog dan inputan tidak diproses
               if(Uname.getText().isEmpty() || pass.getText().isEmpty() || name.getText().isEmpty() || pos.getSelectedItem().toString().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Mohon diisi dengan lengkap");
               }
               //jika field sudah terisi semua, maka data tiap text field akan dimasukan ke variabel
               else{
                    String username = Uname.getText().trim();
                    String password = pass.getText().trim();
                    String nama = name.getText().trim();
                    String posisi = pos.getSelectedItem().toString();
                    //membuat objek karyawan baru dengan parameter variabel yang berisi inputan pada text field
                    dataKaryawan karyawan = new dataKaryawan(username,password,nama,posisi);
                    //menambahkan objek karyawan ke arraylist datak
                    datak.add(karyawan);
                    //memasukkan variabel inputan tadi ke database
                    try {
                        Connection conn = Koneksi.getConnection();
                        String sql = "INSERT INTO tb_karyawan (username,password,nama_karyawan,posisi) VALUES ('"+username+"','"+password+"','"+nama+"','"+posisi+"')";
                        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
               }
               //memanggil method untuk menghapus text field
               clear();
               //memanggil method untuk merefresh isi arraylist yang ditampilkan pada jtable
               loadDataKaryawan(datak);
            }
        });
        //Menambahkan action listener kedalam button update
        Update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //jika arraylist kosong maka akan keluar dialog tabel kosong
                if(datak.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Tabel masih kosong!");
                }
                else
                {
                    //untuk mengecek apakah text field sudah diisi atau belum
                    //jika belum maka akan keluar dialog dan inputan tidak diproses
                    if(Uname.getText().isEmpty() || pass.getText().isEmpty() || name.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Mohon isi data dengan lengkap!!");
                    }
                    else{
                        //jika field sudah terisi semua, maka data tiap text field akan dimasukan ke variabel
                        int selectedRowIndex = (int) tabelkaryawan.getValueAt(tabelkaryawan.getSelectedRow(), 0);
                        String newuname = Uname.getText().trim();
                        String newpass = pass.getText().trim();
                        String newname = name.getText().trim();
                        String newposisi = pos.getSelectedItem().toString();
                        //membuat objek baru untuk update karyawan
                        dataKaryawan updatekaryawan = new dataKaryawan(newuname,newpass,newname,newposisi);
                        //melakukan update pada database
                        try {
                            Connection conn = Koneksi.getConnection();
                            String sql = "UPDATE tb_karyawan SET username='"+newuname+"',password='"+newpass+"',nama_karyawan='"+newname+"',posisi='"+newposisi+"' WHERE username='"+newuname+"'";
                            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                            pst.execute();
                        } 
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                        //melakukan update pada arraylist datak dengan parameter index dan objek updatekaryawan
                        datak.set((selectedRowIndex-1), updatekaryawan);
                    }
                }
                //memanggil method untuk menghapus text field
                clear();
                //memanggil method untuk merefresh isi arraylist yang ditampilkan pada jtable
                loadDataKaryawan(datak);
            }
        });
        //Menambahkan action listener kedalam button hapus
        Hapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //jika arraylist kosong maka akan keluar dialog tabel kosong
                if(datak.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Tabel masih kosong!");
                }
                else{
                    //proses untuk menghapus data dari arraylist dan database berdasarkan index pada arraylist
                    //dan username pada database
                    int selectedRowIndex = (int) tabelkaryawan.getValueAt(tabelkaryawan.getSelectedRow(),0);
                    String username = Uname.getText().trim();
                    datak.remove(selectedRowIndex-1);
                    try {
                        Connection conn = Koneksi.getConnection();
                        String sql = "DELETE FROM tb_karyawan WHERE username='"+username+"'";
                        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
                //memanggil method untuk menghapus text field
                clear();
                //memanggil method untuk merefresh isi arraylist yang ditampilkan pada jtable
                loadDataKaryawan(datak);
            }
        });
        //Menambahkan action listener kedalam button refresh
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                clear();        
                if(datak.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dimuat!!");
                }
                //melakukan set table model
                else{
                    tablekaryawan = (DefaultTableModel) tabelkaryawan.getModel();
                    TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel> (tablekaryawan);
                    tabelkaryawan.setRowSorter(filter);

                    filter.setRowFilter(null);
                }
                loadDataKaryawan(datak);
            }
        });
        //Menambahkan action listener kedalam button back
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //method untuk menghilangkan form dan kembali pada menu utama dealer
                //dengan passing arraylist
                dispose();
                new MenuUtamaDealer(spareparts, motors, tspareparts, tmotors, datak).setVisible(true);
            }
        });
    }
    
    //method untuk menghapus isi pada text field
    private void clear(){
        Uname.setText("");
        pass.setText("");
        name.setText("");
        pos.setSelectedItem("");
    }
    
    //Method untuk memuat semua data ArrayList datak kedalam Tabel Daftar karyawan
    private void loadDataKaryawan(ArrayList<dataKaryawan> datak){
        //Instansiasi model tabel dan deklarasi variabel yang digunakan
        tablekaryawan = new DefaultTableModel();
        
        //Tambahkan semua kolom yang digunakan kedalam model tabel yang telah diinstansiasi
        tablekaryawan.addColumn("No");
        tablekaryawan.addColumn("Username");
        tablekaryawan.addColumn("Password");
        tablekaryawan.addColumn("Nama");
        tablekaryawan.addColumn("Posisi");
        
        //Perulangan loop for untuk memasukkan semua data ArrayList datak kedalam model tabel
        for(int i = 0; i<datak.size(); i++){
            tablekaryawan.addRow(new Object[]{
                (i+1),
                datak.get(i).getUsername(),
                datak.get(i).getPassword(),
                datak.get(i).getNama(),
                datak.get(i).getposisi(),
            });
        }
        //Set model JTable menjadi model tabel yang telah dibuat
        tabelkaryawan.setModel(tablekaryawan);
        
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
        name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pass = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Uname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelkaryawan = new javax.swing.JTable();
        pos = new javax.swing.JComboBox<>();
        Tambah = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        Update = new javax.swing.JButton();
        Hapus = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        kolomcari = new javax.swing.JTextField();
        refresh = new javax.swing.JButton();
        back = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nama");

        jLabel3.setText("Password");

        jLabel4.setText("Username");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("MANAJEMEN KARYAWAN");

        tabelkaryawan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Username", "Password", "Nama", "Posisi"
            }
        ));
        tabelkaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelkaryawanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelkaryawan);

        pos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kasir", "Sales", "Teknisi", "Staff Gudang" }));

        Tambah.setText("Tambah");
        Tambah.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel6.setText("Posisi");

        Update.setText("Update");
        Update.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        Hapus.setText("Hapus");
        Hapus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jButton1.setText("Cari");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dealermotor/Icon/twocirclingarrows1_120592.png"))); // NOI18N

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dealermotor/Icon/arrow_back_icon_151627 (1).png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Uname, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addComponent(Tambah)
                        .addGap(36, 36, 36)
                        .addComponent(Update)
                        .addGap(38, 38, 38)
                        .addComponent(Hapus)
                        .addGap(24, 24, 24)
                        .addComponent(kolomcari, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(255, 255, 255)
                        .addComponent(back)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(back))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(Uname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Update, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(kolomcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refresh))
                .addGap(39, 39, 39)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tablekaryawan = (DefaultTableModel) tabelkaryawan.getModel();
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel>(tablekaryawan);
        tabelkaryawan.setRowSorter(filter);
        
        String key = kolomcari.getText().trim();
        filter.setRowFilter(RowFilter.regexFilter(key));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabelkaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelkaryawanMouseClicked
        int baris = (int) tabelkaryawan.getValueAt(tabelkaryawan.getSelectedRow(), 0);
        baris = baris-1;
        Uname.setText(tablekaryawan.getValueAt(baris, 1).toString());
        pass.setText(tablekaryawan.getValueAt(baris, 2).toString());
        name.setText(tablekaryawan.getValueAt(baris, 3).toString());
    }//GEN-LAST:event_tabelkaryawanMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Hapus;
    private javax.swing.JButton Tambah;
    private javax.swing.JTextField Uname;
    private javax.swing.JButton Update;
    private javax.swing.JButton back;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField kolomcari;
    private javax.swing.JTextField name;
    private javax.swing.JTextField pass;
    private javax.swing.JComboBox<String> pos;
    private javax.swing.JButton refresh;
    private javax.swing.JTable tabelkaryawan;
    // End of variables declaration//GEN-END:variables

}
