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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author LENOVO
 */
public class MenuUtamaDealer extends javax.swing.JFrame {
    //arraylist untuk login
    ArrayList<String> userpass = new ArrayList<String>();
    String user,pass;
    int login = 0;
    /**
     * Creates new form MenuUtamaDealer
     */
    public MenuUtamaDealer(ArrayList<Sparepart> spareparts, ArrayList<Motor> motors, ArrayList<TransaksiSparepart> tspareparts, ArrayList<TransaksiMotor> tmotors, ArrayList<dataKaryawan> datak) {
        //Memanggil Method untuk memuat semua komponen JFrame
        initComponents();
        
        //listener button admin
        //untuk masuk ke menu admin
        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //login ke menu admin
                popUpLogin();
                String username = userpass.get(0);
                String password = userpass.get(1);
                //jika text field belum diisi maka akan keluar warning
                if(userpass.get(0).isEmpty() || userpass.get(1).isEmpty() ){
                    JOptionPane.showMessageDialog(null, "Harap masukkan semua field!");
                }
                else{
                    //untuk mengecek jika user dan pass benar
                    //maka bisa masuk ke menu admin
                    //jika salah maka akses ditolak
                    if("admin".equals(username) && "admin".equals(password)){
                        JOptionPane.showMessageDialog(null, "Login Berhasil!");
                        //membuka menu Admin dengan passing semua arraylist
                        new Admin(spareparts,motors,tspareparts,tmotors,datak).setVisible(true);
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Akses ditolak!");
                    }
                }
                userpass.removeAll(userpass);
            }
        });
        
        //listener button kasir
        //untuk masuk ke menu kasir
        kasir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //login ke menu kasir
                popUpLogin();
                String username = userpass.get(0);
                String password = userpass.get(1);
                
                //jika text field belum diisi maka akan keluar warning
                if(userpass.get(0).isEmpty() || userpass.get(1).isEmpty() ){
                    JOptionPane.showMessageDialog(null, "Harap masukkan semua field!");
                }
                else{
                    //untuk mengecek jika user,pass dan jabatan benar
                    //maka bisa maka login = 1
                    //jika salah maka login = 0
                    for(int i=0; i<datak.size();i++){
                        user = datak.get(i).getUsername();
                        pass = datak.get(i).getPassword();
                        if(user.equals(username) && pass.equals(password) && "Kasir".equals(datak.get(i).getposisi()) || "Sales".equals(datak.get(i).getposisi()) || "Teknisi".equals(datak.get(i).getposisi())){
                            login = 1;
                            break;
                        }
                        else{
                            login = 0;
                        }
                    }
                    //login berhasil jika login = 1
                    if(login==1){
                        JOptionPane.showMessageDialog(null, "Login Berhasil!");
                        //membuka menu kasir dengan passing semua arraylist
                        new Kasir(spareparts,motors,tspareparts,tmotors,datak).setVisible(true);
                        dispose();
                    }
                    //login gagal jika login = 0
                    else{
                        JOptionPane.showMessageDialog(null, "Akses ditolak!");
                    }
                }
                userpass.removeAll(userpass);
            }
        });
        
        //listener button gudang
        //untuk masuk ke menu gudang
        gudang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //login ke menu gudang
                popUpLogin();
                String username = userpass.get(0);
                String password = userpass.get(1);
                
                //jika text field belum diisi maka akan keluar warning
                if(userpass.get(0).isEmpty() || userpass.get(1).isEmpty() ){
                    JOptionPane.showMessageDialog(null, "Harap masukkan semua field!");
                }
                else{
                    //untuk mengecek jika user,pass dan jabatan benar
                    //maka bisa maka login = 1
                    //jika salah maka login = 0
                    for(int i=0; i<datak.size();i++){
                        user = datak.get(i).getUsername();
                        pass = datak.get(i).getPassword();
                        if(user.equals(username) && pass.equals(password) && "Staff Gudang".equals(datak.get(i).getposisi())){
                            login = 1;
                            //System.out.println(datak.get(i).getposisi());
                            break;
                        }
                        else{
                            login = 0;
                        }
                    }
                    //login berhasil jika login = 1
                    if(login==1){
                        JOptionPane.showMessageDialog(null, "Login Berhasil!");
                        //membuka MenuGudang dengan passing semua arraylist
                        new MenuGudang(spareparts,motors,tspareparts,tmotors,datak).setVisible(true);
                        dispose();
                    }
                    //login gagal jika login = 0
                    else{
                        JOptionPane.showMessageDialog(null, "Akses ditolak!");
                    }
                }
                userpass.removeAll(userpass);
            }
        });
    }
    //method untuk menampilkan popup login
    public void popUpLogin(){
        Object[] options = { "Login", "Cancel" };
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("Masukkan Username : "));
        JTextField usernamefield = new JTextField(10);
        panel.add(usernamefield);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Masukkan Password : "));
        JPasswordField passwordfield = new JPasswordField(10);
        panel.add(passwordfield);
        
        int result = JOptionPane.showOptionDialog(null, panel, "Login Akun", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        if (result == JOptionPane.YES_OPTION){
            userpass.add(usernamefield.getText());
            userpass.add(passwordfield.getText());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        admin = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        kasir = new javax.swing.JButton();
        gudang = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Dealer");

        admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dealermotor/Icon/admin_man_person_user_icon_127232.png"))); // NOI18N
        admin.setText("Admin");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setText("Menu Admin");

        kasir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dealermotor/Icon/cashier2_118071.png"))); // NOI18N
        kasir.setText("Kasir");

        gudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dealermotor/Icon/iconfinder-warehouse-3992927_112594.png"))); // NOI18N
        gudang.setText("Gudang");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel3.setText("Menu Kasir");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel4.setText("Menu Gudang");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jLabel2)
                .addGap(171, 171, 171)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(124, 124, 124))
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(admin, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kasir, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gudang, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(gudang, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                    .addComponent(kasir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(admin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton admin;
    private javax.swing.JButton gudang;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton kasir;
    // End of variables declaration//GEN-END:variables
}
