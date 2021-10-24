/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

/**
 *
 * @author wahyu
 */

//objek data karyawan
public class dataKaryawan {
    String username;
    String password;
    String nama;
    String posisi;
    
    //konstruktor data karyawan
    public dataKaryawan(String username, String password, String nama, String posisi){
        this.username= username;
        this.password= password;
        this.nama= nama;
        this.posisi= posisi;
    }
    
    //method untuk mengecek kesamaan pada saat login menggunakan objek data karyawan
    public boolean equals(dataKaryawan datakaryawan){
        return (this.username.equals(datakaryawan.username) && this.password.equals(datakaryawan.password) && 
                this.nama.equals(datakaryawan.nama) && this.posisi == datakaryawan.posisi);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNama() {
        return nama;
    }
    
    public String getposisi(){
        return posisi;
    }
    
}
