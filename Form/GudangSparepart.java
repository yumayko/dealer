/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import ConnectionDatabase.Koneksi;
import Class.Motor;
import Class.Sparepart;
import Class.TransaksiMotor;
import Class.TransaksiSparepart;
import Class.dataKaryawan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
/**
 *
 * @author LENOVO
 */
public class GudangSparepart extends javax.swing.JFrame {
    
    DefaultTableModel tablesp;
    
    
    /**
     * Creates new form GudangSparepart
     */
    
    //Method untuk mendapatkan data dengan parameter semua ArrayList yang dipassing
    public void getData(ArrayList<Sparepart> spareparts, ArrayList<Motor> motors, ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors, ArrayList<dataKaryawan> datak){
        loadDataSparepart(spareparts);
    }
    
    public GudangSparepart(ArrayList<Sparepart> spareparts, ArrayList<Motor> motors, ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors, ArrayList<dataKaryawan> datak) {
        //Memanggil Method untuk memuat semua komponen JFrame
        initComponents();
        //Memanggil method untuk memuat semua data pada ArrayList yang diterima
        getData(spareparts,motors,tspareparts,tmotors,datak);
        
        //Menambahkan action listener kedalam button refresh
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                clear();
                if(spareparts.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Data Berhasil dimuat!!");
                    loadDataSparepart(spareparts);
                }
                else{
                    //melakukan set table model
                    DefaultTableModel tablesp = (DefaultTableModel) tabelsparepart.getModel();
                    TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel>(tablesp);
                    tabelsparepart.setRowSorter(filter);

                    filter.setRowFilter(null);
                }
                loadDataSparepart(spareparts);
            }
        });
        
        //Menambahkan action listener kedalam button tambah
        tambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //untuk mengecek apakah text field sudah diisi atau belum
                //jika belum maka akan keluar dialog dan inputan tidak diproses
                if(spkode.getText().isEmpty() || spname.getText().isEmpty() || spmerk.getText().isEmpty() || spharga.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Mohon diisi dengan lengkap");
                }
                //jika field sudah terisi semua, maka data tiap text field akan dimasukan ke variabel
                else{
                    String kode = spkode.getText().trim();
                    String nama = spname.getText().trim();
                    String merk = spmerk.getText().trim();
                    String harga = spharga.getText().trim();
                    String stok = spstok.getValue().toString();
                    String desk = spdeskripsi.getText().trim();
                    Boolean same = false;
                    //untuk mengecek apakah ada kode sparepart yang sama
                    //jika iya maka same adalah true
                    if(!spareparts.isEmpty()){
                        for(int i=0;i<spareparts.size();i++){
                            if(kode.equals(spareparts.get(i).getKode())){
                                same = true;
                            }
                        }
                    }
                    //jika kode sparepart berbeda maka akan dimasukkan
                    //ke arraylist motor dan database
                    if(same.equals(false)|| spareparts.isEmpty()){
                        Sparepart sparepart = new Sparepart(kode,nama,merk,desk,(Integer.parseInt(harga)),(Integer.parseInt(stok)));
                        spareparts.add(sparepart);
                        try {
                            Connection conn = Koneksi.getConnection();
                            String sql = "INSERT INTO tb_sparepart (kode_sparepart,nama_sparepart,merk_sparepart,harga_sparepart,stok_sparepart,deskripsi_sparepart) VALUES ('"+kode+"','"+nama+"','"+merk+"','"+harga+"','"+stok+"','"+desk+"')";
                            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                            pst.execute();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    else{
                        //jika kode motor ada yang sama maka akan keluar dialog gagal
                        JOptionPane.showMessageDialog(null, "Maaf kode yang dimasukkan sudah ada! \nMasukkan kode yang berbeda");
                    }
                    //memanggil method untuk menghapus text field
                    clear();
                    //memanggil method untuk merefresh isi arraylist yang ditampilkan pada jtable
                    loadDataSparepart(spareparts);
                }
            }
        });
        
        //Menambahkan action listener kedalam button update
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //jika arraylist kosong maka akan keluar dialog tabel kosong
                if(spareparts.isEmpty()){
                JOptionPane.showMessageDialog(null, "Tabel masih kosong!");
                }
                else
                {
                    //untuk mengecek apakah text field sudah diisi atau belum
                    //jika belum maka akan keluar dialog dan inputan tidak diproses
                    if(spkode.getText().isEmpty() || spname.getText().isEmpty() || spmerk.getText().isEmpty() || spharga.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Mohon diisi dengan lengkap");
                    }
                    else{
                        //jika field sudah terisi semua, maka data tiap text field akan dimasukan ke variabel
                        int selectedRowIndex = (int) tabelsparepart.getValueAt(tabelsparepart.getSelectedRow(), 0);
                        String NewCode = spkode.getText().trim();
                        String NewName = spname.getText().trim();
                        String NewBrand = spmerk.getText().trim();
                        String NewPrice = spharga.getText().trim();
                        String NewStock = spstok.getValue().toString();
                        String NewDesc = spdeskripsi.getText().trim();
                    //membuat objek baru untuk updatesparepart
                    Sparepart updatesparepart = new Sparepart(NewCode,NewName,NewBrand,NewDesc,(Integer.parseInt(NewPrice)),(Integer.parseInt(NewStock)));
                    //melakukan update sparepart pada database
                     try {
                        Connection conn = Koneksi.getConnection();
                        String sql = "UPDATE tb_sparepart SET kode_sparepart='"+NewCode+"',nama_sparepart='"+NewName+"',merk_sparepart='"+NewBrand+"',harga_sparepart='"+NewPrice+"',stok_sparepart='"+NewStock+"',deskripsi_sparepart='"+NewDesc+"' WHERE kode_sparepart='"+NewCode+"'";
                        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                     //melakukan update pada arraylist spareparts dengan parameter index dan objek updatesparepart
                    spareparts.set((selectedRowIndex-1), updatesparepart);

                    }
                }

            //memanggil method untuk menghapus text field
            clear();
            //memanggil method untuk merefresh isi arraylist yang ditampilkan pada jtable
            loadDataSparepart(spareparts);
            }
        });
        
        //Menambahkan action listener kedalam button hapus
        hapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            //jika arraylist kosong maka akan keluar dialog tabel kosong
            if(spareparts.isEmpty()){
                JOptionPane.showMessageDialog(null, "Tabel masih kosong!");
            }
            else{
                //proses untuk menghapus data dari arraylist dan database
                //berdasarkan index pada arraylist dan kode motor pada database
                int selectedRowIndex = (int) tabelsparepart.getValueAt(tabelsparepart.getSelectedRow(),0);
                String kode = spkode.getText().trim();
                spareparts.remove(selectedRowIndex-1);
                try {
                    Connection conn = Koneksi.getConnection();
                    String sql = "DELETE FROM tb_sparepart WHERE kode_sparepart='"+kode+"'";
                    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                    pst.execute();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            //memanggil method untuk menghapus text field
            clear();
            //memanggil method untuk merefresh isi arraylist yang ditampilkan pada jtable
            loadDataSparepart(spareparts);
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
    
    private void clear(){
        spname.setText("");
        spmerk.setText("");
        spkode.setText("spr");
        spkode.setEditable(true);
        spharga.setText("");
        spdeskripsi.setText("");
        spstok.setValue(0);
        carikode.setText("");
    }   
    
    private void loadDataSparepart(ArrayList<Sparepart> spareparts){
        tablesp = new DefaultTableModel();
        
        tablesp.addColumn("No");
        tablesp.addColumn("Kode");
        tablesp.addColumn("Nama");
        tablesp.addColumn("Merk");
        tablesp.addColumn("Harga");
        tablesp.addColumn("Stok");
        tablesp.addColumn("Deskripsi");
        
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
        tabelsparepart.setModel(tablesp);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelsparepart = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        spname = new javax.swing.JTextField();
        spmerk = new javax.swing.JTextField();
        spharga = new javax.swing.JTextField();
        spstok = new javax.swing.JSpinner();
        spdeskripsi = new javax.swing.JTextField();
        update = new javax.swing.JButton();
        tambah = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        spkode = new javax.swing.JTextField();
        hapus = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        carikode = new javax.swing.JTextField();
        cari = new javax.swing.JButton();
        back = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Gudang Sparepart");

        tabelsparepart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode", "Nama", "Merk", "Harga", "Stok", "Deskripsi"
            }
        ));
        tabelsparepart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsparepartMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelsparepart);

        jLabel2.setText("Nama Sparepart :");

        jLabel3.setText("Merk :");

        jLabel4.setText("Harga :");

        jLabel5.setText("Stok :");

        jLabel6.setText("Deskripsi :");

        spdeskripsi.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        spdeskripsi.setToolTipText("optional");

        update.setText("Update Sparepart");

        tambah.setText("Tambah Sparepart");

        jLabel7.setText("Kode Sparepart :");

        spkode.setText("spr");

        hapus.setText("Hapus Sparepart");

        jLabel8.setText(" Masukkan Keyword :");

        cari.setText("Cari");
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dealermotor/Icon/arrow_back_icon_151627 (1).png"))); // NOI18N

        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dealermotor/Icon/twocirclingarrows1_120592.png"))); // NOI18N
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(257, 257, 257)
                        .addComponent(back)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(spdeskripsi)
                                    .addComponent(spname)
                                    .addComponent(spmerk)
                                    .addComponent(spharga)
                                    .addComponent(spstok, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                    .addComponent(spkode))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tambah))
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(carikode, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cari))
                                    .addComponent(hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 4, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(back)
                    .addComponent(jLabel1))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(spkode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(spname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(spmerk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(spharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(spstok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(spdeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(carikode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cari)
                            .addComponent(tambah))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(update)
                            .addComponent(hapus))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refresh))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        
        DefaultTableModel tablesp = (DefaultTableModel) tabelsparepart.getModel();
        TableRowSorter<DefaultTableModel> filter = new TableRowSorter<DefaultTableModel>(tablesp);
        tabelsparepart.setRowSorter(filter);

        String key = carikode.getText().trim();
        filter.setRowFilter(RowFilter.regexFilter(key));
    }//GEN-LAST:event_cariActionPerformed

    private void tabelsparepartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsparepartMouseClicked
        
        int baris = (int) tabelsparepart.getValueAt(tabelsparepart.getSelectedRow(), 0);
        baris = baris-1;
        spkode.setText(tablesp.getValueAt(baris, 1).toString());
        spkode.setEditable(false);
        spname.setText(tablesp.getValueAt(baris, 2).toString());
        spmerk.setText(tablesp.getValueAt(baris, 3).toString());
        spharga.setText(tablesp.getValueAt(baris, 4).toString());
        spstok.setValue(tablesp.getValueAt(baris, 5));
        spdeskripsi.setText(tablesp.getValueAt(baris, 6).toString());
    }//GEN-LAST:event_tabelsparepartMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton cari;
    private javax.swing.JTextField carikode;
    private javax.swing.JButton hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton refresh;
    private javax.swing.JTextField spdeskripsi;
    private javax.swing.JTextField spharga;
    private javax.swing.JTextField spkode;
    private javax.swing.JTextField spmerk;
    private javax.swing.JTextField spname;
    private javax.swing.JSpinner spstok;
    private javax.swing.JTable tabelsparepart;
    private javax.swing.JButton tambah;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}