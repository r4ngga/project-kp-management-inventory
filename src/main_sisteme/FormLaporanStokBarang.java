/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main_sisteme;

import java.awt.Color;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;
import java.awt.print.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.design.JRDesignQuery;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.view.*;

/**
 *
 * @author Rangga
 */
public class FormLaporanStokBarang extends javax.swing.JFrame {

    /**
     * Creates new form FormLaporanStokBarang
     */
    public FormLaporanStokBarang() {
        initComponents();
        tampilbrg();
    }
    
    public void tampilbrg(){
      String nonota, id_brg, nm_brg, kategori, hrg_jual, stok, nmrtostr, hrg_beli;
      int nmr =0;
      try {
            Object [] baris = {"No", "Id Barang","Nama Barang","Kategori",
                "Harga Jual","Harga Beli", "Stok"};
            DefaultTableModel modeltbbrg;
            modeltbbrg = new DefaultTableModel(null, baris);
            tabel_brg.setModel(modeltbbrg);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT tb_barang.id_barang, tb_barang.nama_barang, tb_kategori.nama_kategori"
                    + ", tb_barang.harga_beli, tb_barang.harga_jual, tb_barang.spesifikasi"
                    + ", tb_barang.stok FROM tb_barang join tb_kategori ON "
                    + "tb_kategori.id_kategori = tb_barang.id_kategori";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_brg.getColumnModel().getColumn(0).setPreferredWidth(5);
                id_brg=hasil.getString("id_barang");
                nm_brg=hasil.getString("nama_barang");
                kategori=hasil.getString("nama_kategori");
                hrg_jual=hasil.getString("harga_jual");
                hrg_beli=hasil.getString("harga_beli");
                stok=hasil.getString("stok");
                String data[]={nmrtostr,id_brg,nm_brg,kategori,hrg_jual,hrg_beli,stok};   
                modeltbbrg.addRow(data);
            }  
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
     }
    public void caribrg()
    {
      String cari = jtxt_caridtbrg.getText();
        String nonota, id_brg, nm_brg, kategori, hrg_jual, stok, nmrtostr, hrg_beli;
      int nmr =0;
      try {
            Object [] baris = {"No", "Id Barang","Nama Barang","Kategori",
                "Harga Jual","Harga Beli", "Stok"};
            DefaultTableModel modeltbbrg;
            modeltbbrg = new DefaultTableModel(null, baris); 
            tabel_brg.setModel(modeltbbrg);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT tb_barang.id_barang, tb_barang.nama_barang, tb_kategori.nama_kategori"
                    + ", tb_barang.harga_beli, tb_barang.harga_jual, tb_barang.spesifikasi"
                    + ", tb_barang.stok FROM tb_barang join tb_kategori ON "
                    + "tb_kategori.id_kategori = tb_barang.id_kategori WHERE "
                    + "tb_barang.id_barang like '" + cari + "'"
                    + "or tb_barang.nama_barang like '%" + cari + "%' "
                    + "or tb_kategori.nama_kategori like '" + cari + "' "
                    + "or tb_barang.spesifikasi like '%" + cari + "%' ";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_brg.getColumnModel().getColumn(0).setPreferredWidth(5);
                id_brg=hasil.getString("id_barang");
                nm_brg=hasil.getString("nama_barang");
                kategori=hasil.getString("nama_kategori");
                hrg_jual=hasil.getString("harga_jual");
                hrg_beli=hasil.getString("harga_beli");
                stok=hasil.getString("stok");
                String data[]={nmrtostr,id_brg,nm_brg,kategori,hrg_jual,hrg_beli,stok};   
                modeltbbrg.addRow(data);
            }  
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        } 
    }
    public void printnstok()
    {
       String nonota, id_brg, nm_brg, kategori, hrg_jual, stok, nmrtostr, hrg_beli;
       int nmr =0;
       String pencarian = jtxt_caridtbrg.getText();
       if(!"".equals(pencarian))
       {
        try {
           String exec_sql = "SELECT tb_barang.id_barang, tb_barang.nama_barang, tb_kategori.nama_kategori"
                    + ", tb_barang.harga_beli, tb_barang.harga_jual, tb_barang.spesifikasi"
                    + ", tb_barang.stok FROM tb_barang join tb_kategori ON "
                    + "tb_kategori.id_kategori = tb_barang.id_kategori WHERE "
                    + "tb_barang.id_barang like '" + pencarian + "'"
                    + "or tb_barang.nama_barang like '%" + pencarian + "%' "
                    + "or tb_kategori.nama_kategori like '" + pencarian + "' "
                    + "or tb_barang.spesifikasi like '%" + pencarian + "%' ";
           JRDesignQuery buatquery = new JRDesignQuery();
           buatquery.setText(exec_sql);
           //java.io.File fileditunjuk=new java.io.File("./main_sisteme/laporanstokbarang.jasper");
            InputStream fileditunjuk = getClass().getResourceAsStream("/main_sisteme/laporanstokbarang.jrxml");
           //File fileditunjuk = new File("laporanstokbarang.jrxml");
           JasperDesign jasperDesign = JRXmlLoader.load(fileditunjuk);
           jasperDesign.setQuery(buatquery);
           JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
           JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, koneksi.membukakoneksi());
           JasperViewer.viewReport(jasperPrint, false);
         }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
         } 
       }
       else{
        try {
            InputStream fileditunjuk = getClass().getResourceAsStream("/main_sisteme/laporanstokbarang.jrxml");
           //File fileditunjuk = new File("/laporanstokbarang.jrxml");
           JasperDesign jasperDesign = JRXmlLoader.load(fileditunjuk);
           JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
           JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, koneksi.membukakoneksi());
           JasperViewer.viewReport(jasperPrint, false);
           }catch (JRException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
           }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_brg = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jtxt_caridtbrg = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        jLabel3.setText("Laporan Stok Barang");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 29, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 110));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel_brg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Id Barang", "Nama Barang", "Kategori", "Harga Jual", "Harga Beli", "Stok"
            }
        ));
        jScrollPane1.setViewportView(tabel_brg);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 660, 230));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 680, 260));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-refresh-32.png"))); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-search-32.png"))); // NOI18N
        jButton2.setText("Cari");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, -1, -1));
        jPanel2.add(jtxt_caridtbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 130, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-print-32.png"))); // NOI18N
        jButton3.setText("Cetak");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, -1, -1));

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-back-arrow-24.png"))); // NOI18N
        jButton4.setText("Kembali");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 111, 720, 370));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tampilbrg();
        jtxt_caridtbrg.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //  gantipasswordn();
        caribrg();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        printnstok();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormLaporanStokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLaporanStokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLaporanStokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLaporanStokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLaporanStokBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtxt_caridtbrg;
    private javax.swing.JTable tabel_brg;
    // End of variables declaration//GEN-END:variables
}
