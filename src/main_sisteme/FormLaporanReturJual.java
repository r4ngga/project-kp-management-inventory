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
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.*;

/**
 *
 * @author Rangga
 */
public class FormLaporanReturJual extends javax.swing.JFrame {

    /**
     * Creates new form FormLaporanReturJual
     */
    public FormLaporanReturJual() {
        initComponents();
        tampilbrgretur();
    }
    
    public void tampilbrgretur(){
      String nonota, id_brg, nm_brg, kd_retur, hrg_jual, keterangan, nmrtostr, tgl_retur,jmlh;
      int nmr =0;
      try {
            Object [] baris = {"No","Kode Retur", "No Nota","Id Barang","Nama Barang",
                "Harga Jual","Jumlah Kembali","Tanggal Retur", "Keterangan"};
            DefaultTableModel modeltbbrgreturjual;
            modeltbbrgreturjual = new DefaultTableModel(null, baris);
            tabel_returjual.setModel(modeltbbrgreturjual);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT tb_detail_returjual.no_urut_kembali,tb_detail_returjual.kd_retur"
                    + ", tb_retur_jual.no_nota as nomernota, tb_barang.id_barang as idbarang"
                    + ", tb_barang.nama_barang as namabarang, tb_detail_returjual.harga_jual"
                    + ", tb_detail_returjual.jumlah_brgkembali, tb_retur_jual.tgl_retur as tglkembali"
                    + ", tb_retur_jual.keterangan as ket"
                    + " FROM tb_detail_returjual JOIN tb_barang ON "
                    + "tb_barang.id_barang = tb_detail_returjual.id_barang JOIN tb_retur_jual ON"
                    + " tb_retur_jual.kd_retur = tb_detail_returjual.kd_retur";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_returjual.getColumnModel().getColumn(0).setPreferredWidth(5);
                kd_retur=hasil.getString("kd_retur");
                nonota=hasil.getString("nomernota");
                id_brg=hasil.getString("idbarang");
                nm_brg=hasil.getString("namabarang");
                hrg_jual=hasil.getString("harga_jual");
                jmlh=hasil.getString("jumlah_brgkembali");
                tgl_retur=hasil.getString("tglkembali");
                keterangan=hasil.getString("ket");
                String data[]={nmrtostr,kd_retur,nonota,id_brg,nm_brg,hrg_jual,jmlh
                ,tgl_retur,keterangan};   
                modeltbbrgreturjual.addRow(data);
            }  
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
     }
    
    public void pencariantanggal(){
     String nonota, id_brg, nm_brg, kd_retur, hrg_jual, keterangan, nmrtostr, tgl_retur,jmlh;
     int nmr =0;
     String setdatenya = "yyyy-MM-dd";
     SimpleDateFormat formatdatenya1 = new SimpleDateFormat(setdatenya);
     SimpleDateFormat formatdatenya2 = new SimpleDateFormat(setdatenya);
     String pencariandari = String.valueOf(formatdatenya1.format(jdt_daritgl.getDate()));
     String pencariansampai = String.valueOf(formatdatenya2.format(jdt_sampaitgl.getDate()));
      try {
            Object [] baris = {"No","Kode Retur", "No Nota","Id Barang","Nama Barang",
                "Harga Jual","Jumlah Kembali","Tanggal Retur", "Keterangan"};
            DefaultTableModel modeltbbrgreturjual;
            modeltbbrgreturjual = new DefaultTableModel(null, baris);
            tabel_returjual.setModel(modeltbbrgreturjual);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT tb_detail_returjual.no_urut_kembali,tb_detail_returjual.kd_retur"
                    + ", tb_retur_jual.no_nota as nomernota, tb_barang.id_barang as idbarang"
                    + ", tb_barang.nama_barang as namabarang, tb_detail_returjual.harga_jual"
                    + ", tb_detail_returjual.jumlah_brgkembali, tb_retur_jual.tgl_retur as tglkembali"
                    + ", tb_retur_jual.keterangan as ket"
                    + " FROM tb_detail_returjual JOIN tb_barang ON "
                    + "tb_barang.id_barang = tb_detail_returjual.id_barang JOIN tb_retur_jual ON"
                    + " tb_retur_jual.kd_retur = tb_detail_returjual.kd_retur WHERE "
                    + " tb_retur_jual.tgl_retur BETWEEN '"+pencariandari+"' AND '"+pencariansampai+"'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_returjual.getColumnModel().getColumn(0).setPreferredWidth(5);
                kd_retur=hasil.getString("kd_retur");
                nonota=hasil.getString("nomernota");
                id_brg=hasil.getString("idbarang");
                nm_brg=hasil.getString("namabarang");
                hrg_jual=hasil.getString("harga_jual");
                jmlh=hasil.getString("jumlah_brgkembali");
                tgl_retur=hasil.getString("tglkembali");
                keterangan=hasil.getString("ket");
                String data[]={nmrtostr,kd_retur,nonota,id_brg,nm_brg,hrg_jual,jmlh
                ,tgl_retur,keterangan};   
                modeltbbrgreturjual.addRow(data);
            }  
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
    
    public void cetaksemualaporanretur(){
       try {
           InputStream fileditunjuk = getClass().getResourceAsStream("/main_sisteme/laporanreturpenjualan.jrxml");
           JasperDesign jasperDesign = JRXmlLoader.load(fileditunjuk);
           JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
           JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, koneksi.membukakoneksi());
           JasperViewer.viewReport(jasperPrint, false);
         }catch (JRException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
         } 
    }
    
    public void cetaklaporanretur(){
     String nonota, id_brg, nm_brg, kd_retur, hrg_jual, keterangan, nmrtostr, tgl_retur,jmlh;
     int nmr =0;
     String setdatenya = "yyyy-MM-dd";
     SimpleDateFormat formatdatenya1 = new SimpleDateFormat(setdatenya);
     SimpleDateFormat formatdatenya2 = new SimpleDateFormat(setdatenya);
     String pencariandari = formatdatenya1.format(jdt_daritgl.getDate());
     String pencariansampai = formatdatenya2.format(jdt_sampaitgl.getDate());
     try{
     String exec_sql = "SELECT tb_detail_returjual.no_urut_kembali,tb_detail_returjual.kd_retur"
        + ", tb_retur_jual.no_nota as nomernota, tb_barang.id_barang as idbarang"
        + ", tb_barang.nama_barang as namabarang, tb_detail_returjual.harga_jual"
        + ", tb_detail_returjual.jumlah_brgkembali, tb_retur_jual.tgl_retur as tglkembali"
        + ", tb_retur_jual.keterangan as ket"
        + " FROM tb_detail_returjual JOIN tb_barang ON "
        + "tb_barang.id_barang = tb_detail_returjual.id_barang JOIN tb_retur_jual ON"
        + " tb_retur_jual.kd_retur = tb_detail_returjual.kd_retur WHERE "
        + " tb_retur_jual.tgl_retur BETWEEN '"+pencariandari+"' AND '"+pencariansampai+"'";
     HashMap hashinpt = new HashMap();
     hashinpt.put("tgl_dari", pencariandari);
     hashinpt.put("tgl_sampai", pencariansampai);
     JRDesignQuery buatquery = new JRDesignQuery();
     buatquery.setText(exec_sql);
     InputStream fileditunjuk = getClass().getResourceAsStream("/main_sisteme/laporanreturpenjualan.jrxml");
     //File fileditunjuk = new File("src/main_sisteme/laporanreturpenjualan.jrxml");
     JasperDesign jasperDesign = JRXmlLoader.load(fileditunjuk);
     jasperDesign.setQuery(buatquery);
     JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
     JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hashinpt, koneksi.membukakoneksi());
     JasperViewer.viewReport(jasperPrint, false);
     }catch(JRException e){}
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
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_returjual = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jlbl_username = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jdt_sampaitgl = new com.toedter.calendar.JDateChooser();
        jdt_daritgl = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        jLabel3.setText("Laporan Transaksi Retur Penjualan");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 29, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 110));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Rentang Tanggal");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel_returjual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode Retur", "No Nota", "Id Barang", "Nama Barang", "Harga Jual", "Jumlah Kembali", "Tanggal Retur", "Keterangan"
            }
        ));
        jScrollPane1.setViewportView(tabel_returjual);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 780, 230));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 800, 260));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-back-arrow-24.png"))); // NOI18N
        jButton1.setText("Kembali");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, -1, -1));

        jlbl_username.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_username.setText("-");
        jPanel2.add(jlbl_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 20, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-search-32.png"))); // NOI18N
        jButton2.setText("Cari");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, -1));
        jPanel2.add(jdt_sampaitgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 100, -1));
        jPanel2.add(jdt_daritgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 100, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-print-32.png"))); // NOI18N
        jButton3.setText("Cetak");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, -1));

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-search-32.png"))); // NOI18N
        jButton4.setText("Refresh");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 111, 830, 370));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //  gantipasswordn();
        if(jdt_daritgl.getDate()==null&&jdt_sampaitgl.getDate()==null)
        {
            tampilbrgretur();
        }
        else if(jdt_daritgl.getDate()==null||jdt_sampaitgl.getDate()==null)
        {
            tampilbrgretur();
        }
        else
        {
            pencariantanggal();
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(jdt_daritgl.getDate()==null&&jdt_sampaitgl.getDate()==null)
        {
            cetaksemualaporanretur();
        }
        else if(jdt_daritgl.getDate()==null||jdt_sampaitgl.getDate()==null)
        {
            cetaksemualaporanretur();
        }
        else
        {
            cetaklaporanretur();
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        tampilbrgretur();
        jdt_daritgl.setCalendar(null);
        jdt_sampaitgl.setCalendar(null);
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
            java.util.logging.Logger.getLogger(FormLaporanReturJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLaporanReturJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLaporanReturJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLaporanReturJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLaporanReturJual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdt_daritgl;
    private com.toedter.calendar.JDateChooser jdt_sampaitgl;
    private javax.swing.JLabel jlbl_username;
    private javax.swing.JTable tabel_returjual;
    // End of variables declaration//GEN-END:variables
}
