/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_sisteme;

/**
 *
 * @author Rangga
 */

import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;

public class koneksi {

    private static Connection koneksinya;
    public static Connection membukakoneksi(){
      if(koneksinya==null)
      {
       try{
            String db = "jdbc:mysql://localhost/db_inventori";
            String user = "root";
            String pasword = "";
            koneksinya = DriverManager.getConnection(db,user,pasword);
        }catch(SQLException a){
            JOptionPane.showMessageDialog(null, "Belum terkoneksi database.");
            return null;
        }
      }
        return koneksinya;
    }
    
    public static Connection menutupkoneksi() {
        if(koneksinya != null)
        {
            try{
            if(koneksinya != null){
                System.out.print("Tutup Koneksi\n");
              // JOptionPane.showMessageDialog(null, "Menutup Koneksi");
            }
            }catch(Exception gagal){
            }
        }
        return koneksinya;
    }
    
}
