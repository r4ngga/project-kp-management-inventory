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

/**
 *
 * @author Rangga
 */
public class FormLaporanBrgMsk extends javax.swing.JFrame {

    /**
     * Creates new form FormLaporanBrgMsk
     */
    public FormLaporanBrgMsk() {
        initComponents();
        tampilbrgmsk();
    }
    
    public void tampilbrgmsk(){
      String nofaktur, id_brg, nm_brg, no_urut, hrg_beli, stok, nmrtostr, tgl_msk;
      int nmr =0;
      try {
            Object [] baris = {"No","Nomer Faktur", "Tanggal Masuk","No Urut Masuk","Id Barang",
                "Nama Barang","Harga Beli", "Jumlah Masuk"};
            DefaultTableModel modeltbbrgmsk;
            modeltbbrgmsk = new DefaultTableModel(null, baris);
            tabel_brgmasuk.setModel(modeltbbrgmsk);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT tb_detail_brgmsk.no_urut, tb_detail_brgmsk.no_faktur"
                    + ", tb_barang.id_barang as idbarang, tb_barang.nama_barang as namabarang"
                    + ", tb_barang.harga_beli as hargabeli, tb_brg_msk.tgl_masuk as tglmasuk"
                    + ", tb_barang.stok as stock, tb_detail_brgmsk.jumlah_brgmsk"
                    + " FROM tb_detail_brgmsk join tb_barang ON "
                    + "tb_barang.id_barang = tb_detail_brgmsk.id_barang join tb_brg_msk ON"
                    + " tb_brg_msk.no_faktur = tb_detail_brgmsk.no_faktur";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_brgmasuk.getColumnModel().getColumn(0).setPreferredWidth(5);
                nofaktur=hasil.getString("no_faktur");
                tgl_msk=hasil.getString("tglmasuk");
                no_urut=hasil.getString("no_urut");
                id_brg=hasil.getString("idbarang");
                nm_brg=hasil.getString("namabarang");
                hrg_beli=hasil.getString("hargabeli");
                stok=hasil.getString("jumlah_brgmsk");
                String data[]={nmrtostr,nofaktur,tgl_msk,no_urut,id_brg,nm_brg,hrg_beli,stok};   
                modeltbbrgmsk.addRow(data);
            }  
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
     }
    
    public void pencariantanggal(){
     String nofaktur, id_brg, nm_brg, no_urut, hrg_beli, stok, nmrtostr, tgl_msk;
     int nmr =0;
     String setdatenya = "yyyy-MM-dd";
     SimpleDateFormat formatdatenya1 = new SimpleDateFormat(setdatenya);
     String pencariandari = String.valueOf(formatdatenya1.format(jdt_daritgl.getDate()));
     String pencariansampai = String.valueOf(formatdatenya1.format(jdt_sampaitgl.getDate()));
      try {
            Object [] baris = {"No","Nomer Faktur", "Tanggal Masuk","No Urut Masuk","Id Barang",
                "Nama Barang","Harga Beli", "Jumlah Masuk"};
            DefaultTableModel modeltbbrgmsk;
            modeltbbrgmsk = new DefaultTableModel(null, baris);
            tabel_brgmasuk.setModel(modeltbbrgmsk);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT tb_detail_brgmsk.no_urut, tb_detail_brgmsk.no_faktur"
                    + ", tb_barang.id_barang as idbarang, tb_barang.nama_barang as namabarang"
                    + ", tb_barang.harga_beli as hargabeli, tb_brg_msk.tgl_masuk as tglmasuk"
                    + ", tb_barang.stok as stock, tb_detail_brgmsk.jumlah_brgmsk"
                    + " FROM tb_detail_brgmsk join tb_barang ON "
                    + "tb_barang.id_barang = tb_detail_brgmsk.id_barang join tb_brg_msk ON"
                    + " tb_brg_msk.no_faktur = tb_detail_brgmsk.no_faktur WHERE"
                    + " tb_brg_msk.tgl_masuk BETWEEN '"+pencariandari+"' AND '"+pencariansampai+"' ";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_brgmasuk.getColumnModel().getColumn(0).setPreferredWidth(5);
                nofaktur=hasil.getString("no_faktur");
                tgl_msk=hasil.getString("tglmasuk");
                no_urut=hasil.getString("no_urut");
                id_brg=hasil.getString("idbarang");
                nm_brg=hasil.getString("namabarang");
                hrg_beli=hasil.getString("hargabeli");
                stok=hasil.getString("jumlah_brgmsk");
                String data[]={nmrtostr,nofaktur,tgl_msk,no_urut,id_brg,nm_brg,hrg_beli,stok};   
                modeltbbrgmsk.addRow(data);
            }  
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
    
    public void cetaksemualaporan(){
     try {
           InputStream fileditunjuk = getClass().getResourceAsStream("/main_sisteme/laporanbrgmsk.jrxml");
           JasperDesign jasperDesign = JRXmlLoader.load(fileditunjuk);
           JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
           JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, koneksi.membukakoneksi());
           JasperViewer.viewReport(jasperPrint, false);
         }catch (JRException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
         } 
    }
    
    public void cetaklaporanbrgmsk(){
     String nofaktur, id_brg, nm_brg, no_urut, hrg_beli, stok, nmrtostr, tgl_msk;
     int nmr =0;
     String setdatenya = "yyyy-MM-dd";
     SimpleDateFormat formatdatenya1 = new SimpleDateFormat(setdatenya);
     SimpleDateFormat formatdatenya2 = new SimpleDateFormat(setdatenya);
     String pencariandari = formatdatenya1.format(jdt_daritgl.getDate());
     String pencariansampai = formatdatenya1.format(jdt_sampaitgl.getDate());
     try{
      String exec_sql = "SELECT tb_detail_brgmsk.no_urut, tb_detail_brgmsk.no_faktur"
                    + ", tb_barang.id_barang as idbarang, tb_barang.nama_barang as namabarang"
                    + ", tb_barang.harga_beli as hargabeli, tb_brg_msk.tgl_masuk as tglmasuk"
                    + ", tb_barang.stok as stock, tb_detail_brgmsk.jumlah_brgmsk"
                    + " FROM tb_detail_brgmsk join tb_barang ON "
                    + "tb_barang.id_barang = tb_detail_brgmsk.id_barang join tb_brg_msk ON"
                    + " tb_brg_msk.no_faktur = tb_detail_brgmsk.no_faktur WHERE"
                    + " tb_brg_msk.tgl_masuk BETWEEN '"+pencariandari+"' AND '"+pencariansampai+"' ";
      HashMap hashinpt = new HashMap();
      hashinpt.put("tgl_dari", pencariandari);
      hashinpt.put("tgl_sampai", pencariansampai);
      JRDesignQuery buatquery = new JRDesignQuery();
      buatquery.setText(exec_sql);
      InputStream fileditunjuk = getClass().getResourceAsStream("/main_sisteme/laporanbrgmsk.jrxml");
      JasperDesign jasperDesign = JRXmlLoader.load(fileditunjuk);
      jasperDesign.setQuery(buatquery);
      JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hashinpt, koneksi.membukakoneksi());
      JasperViewer.viewReport(jasperPrint, false);
     }catch(JRException c){}
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
        tabel_brgmasuk = new javax.swing.JTable();
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
        jLabel3.setText("Laporan Transaksi Barang Masuk");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 29, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 110));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Rentang Tanggal");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel_brgmasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nomer Faktur", "Tanggal Masuk", "No Urut Masuk", "Id Barang", "Nama Barang", "Harga Beli", "Jumlah Masuk"
            }
        ));
        jScrollPane1.setViewportView(tabel_brgmasuk);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 720, 230));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 740, 260));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-refresh-32.png"))); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, -1, -1));

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
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-back-arrow-24.png"))); // NOI18N
        jButton4.setText("Kembali");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 111, 780, 370));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tampilbrgmsk();
        jdt_daritgl.setCalendar(null);
        jdt_sampaitgl.setCalendar(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      //  gantipasswordn();
      if(jdt_daritgl.getDate()==null&&jdt_sampaitgl.getDate()==null)
        {
            tampilbrgmsk();
        }
        else if(jdt_daritgl.getDate()==null || jdt_sampaitgl.getDate()==null)
        {
            tampilbrgmsk();
        }
        else{
            pencariantanggal();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(jdt_daritgl.getDate()==null&&jdt_sampaitgl.getDate()==null)
        {
            cetaksemualaporan();
        }
        else if(jdt_daritgl.getDate()==null || jdt_sampaitgl.getDate()==null)
        {
            cetaksemualaporan();
        }
        else{
            cetaklaporanbrgmsk();
        }
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
            java.util.logging.Logger.getLogger(FormLaporanBrgMsk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLaporanBrgMsk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLaporanBrgMsk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLaporanBrgMsk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLaporanBrgMsk().setVisible(true);
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
    private javax.swing.JTable tabel_brgmasuk;
    // End of variables declaration//GEN-END:variables
}
