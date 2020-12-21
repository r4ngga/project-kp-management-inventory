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

/**
 *
 * @author Rangga
 */
public class Formdtbrg_admin extends javax.swing.JFrame {

    /**
     * Creates new form Formdtbrg_admin
     */
    public Formdtbrg_admin() {
        initComponents();
        lihat_barang();
    }
    
    public void lihat_barang(){
         //untuk menampilkan data yang berasal dari tabel barang
    String idbrg, nmbrg, nmrtostr, katbrg, hrgbl, hrgjl, spek, stok ;
    int nmr=0;
    try {
            Object [] baris = {"Nomer","Id Barang","Nama Barang","Kategori","Harga Beli",
            "Harga Jual", "Stok", "Spesifikasi"};
            DefaultTableModel modeltbbrg;
            modeltbbrg = new DefaultTableModel(null, baris);
            tabel_brg.setModel(modeltbbrg);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
//            "SELECT tmbarang.id, tmbarang.kode, tmbarang.nama, tmkategori.nama as kategori, tmbarang.stok, "
//                    + "tmbarang.satuan FROM tmbarang JOIN tmkategori ON tmkategori.id = tmbarang.id_kategori"
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
                idbrg=hasil.getString("id_barang");
                nmbrg=hasil.getString("nama_barang");
                katbrg=hasil.getString("nama_kategori");
                hrgbl=hasil.getString("harga_beli");
                hrgjl=hasil.getString("harga_jual");
                stok=hasil.getString("stok");
                spek=hasil.getString("spesifikasi");
                String data[]={nmrtostr,idbrg,nmbrg,katbrg,hrgbl,hrgjl,stok,spek};   
                modeltbbrg.addRow(data);
            }  
//            auto_id();
//            jtxt_nmbrg.setText("");
//            jtxt_caribrg.setText("");
//            jtxt_hrgbeli.setText("");
//            jtxt_hrgjual.setText("");
//            jtxt_spekbrg.setText("");
//            jtxt_idkat.setText("");
//            jtxt_stokbrg.setText("");
//            jcb_pilkategori.setSelectedItem("Silahkan Pilih");
//            tomboledit(false);
//            tombolhapus(false);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
    
     public void pencarian_barang(){
        String idbrg, nmbrg, nmrtostr, katbrg, hrgbl, hrgjl, spek, stok;
        int nmr=0;
        String cari =jtxt_caribrg.getText();
        try {
            Object [] baris = {"Nomer","Id Barang","Nama Barang","Kategori","Harga Beli",
            "Harga Jual", "Stok", "Spesifikasi"};
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
            if(hasil.next()){
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_brg.getColumnModel().getColumn(0).setPreferredWidth(5);
                idbrg=hasil.getString("id_barang");
                nmbrg=hasil.getString("nama_barang");
                katbrg=hasil.getString("nama_kategori");
                hrgbl=hasil.getString("harga_beli");
                hrgjl=hasil.getString("harga_jual");
                stok=hasil.getString("stok");
                spek=hasil.getString("spesifikasi");
                String data[]={nmrtostr,idbrg,nmbrg,katbrg,hrgbl,hrgjl,stok,spek};   
                modeltbbrg.addRow(data);
//                jtxt_nmbrg.setText("");
//                jtxt_hrgbeli.setText("");
//                jtxt_hrgjual.setText("");
//                jtxt_spekbrg.setText("");
//                jtxt_stokbrg.setText("");
//                jcb_pilkategori.setSelectedItem("Silahkan Pilih");
           }else{
            JOptionPane.showMessageDialog(null, "Data barang tidak ditemukan.");
            lihat_barang();
            }
            jtxt_caribrg.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
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
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jtxt_caribrg = new javax.swing.JTextField();
        jbttn_cari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_brg = new javax.swing.JTable();
        jbttn_kembali = new javax.swing.JButton();
        jbttn_refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 27)); // NOI18N
        jLabel8.setText("Data Barang");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxt_caribrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_caribrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_caribrgActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_caribrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 143, -1));

        jbttn_cari.setIcon(new javax.swing.ImageIcon("E:\\sinau java\\NetBeans Projects\\sistem_persediaan_barang\\src\\main_sisteme\\image\\icons8-search-32.png")); // NOI18N
        jbttn_cari.setText("Cari");
        jbttn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_cariActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        tabel_brg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomer", "ID Barang", "Nama Barang", "Kategori", "Harga Beli", "Harga Jual", "Stok", "Spesifikasi"
            }
        ));
        jScrollPane2.setViewportView(tabel_brg);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 62, 740, 200));

        jbttn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-back-arrow-24.png"))); // NOI18N
        jbttn_kembali.setText("Kembali");
        jbttn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_kembaliActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jbttn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-refresh-32.png"))); // NOI18N
        jbttn_refresh.setText("Refresh");
        jbttn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_refreshActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxt_caribrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_caribrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_caribrgActionPerformed

    private void jbttn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_kembaliActionPerformed
        this.setVisible(false);
        //        new MenuHome().setVisible(true);
    }//GEN-LAST:event_jbttn_kembaliActionPerformed

    private void jbttn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_cariActionPerformed
        pencarian_barang();
    }//GEN-LAST:event_jbttn_cariActionPerformed

    private void jbttn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_refreshActionPerformed
        lihat_barang();
    }//GEN-LAST:event_jbttn_refreshActionPerformed

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
            java.util.logging.Logger.getLogger(Formdtbrg_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Formdtbrg_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Formdtbrg_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Formdtbrg_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Formdtbrg_admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbttn_cari;
    private javax.swing.JButton jbttn_kembali;
    private javax.swing.JButton jbttn_refresh;
    private javax.swing.JTextField jtxt_caribrg;
    private javax.swing.JTable tabel_brg;
    // End of variables declaration//GEN-END:variables
}
