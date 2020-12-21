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
public class FormKategori extends javax.swing.JFrame {

    /**
     * Creates new form FormKategori
     */
    public FormKategori() {
        initComponents();
        auto_id();
        lihat_kat();
        tomboledit(false);
        tombolhapus(false);
    }
    
    public void auto_id(){
         //untuk menampilkan auto increment id/kode 
      try{
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT MAX(right(id_kategori,3)) as id_kat from tb_kategori";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                if(hasil.first()==false)
                {
                    jtxt_idkategori.setText("K001");
                }
                else{
                    hasil.last();
                    int set_id_kat = hasil.getInt(1)+1;
                    String id_kat = String.valueOf(set_id_kat);
                    int id_kat_selanjutnya = id_kat.length();
                    for(int x=0; x<3-id_kat_selanjutnya; x++)
                    {
                        id_kat = "0"+id_kat;
                    }
                    jtxt_idkategori.setText("K"+id_kat);
                }
            }
      }
      catch(SQLException ex)
      {
          Logger.getLogger(FormKategori.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    public void tambah_kat(){
         //untuk menambahkann data ke tabel kategori
        String id_kategori = jtxt_idkategori.getText();
        String nma_kategori = jtxt_nmkategori.getText();
        int cek_id = 0;
        int konfirmasi_tambah = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menambahkan data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
      
      if(konfirmasi_tambah==0)
      {
        if(!"".equals(id_kategori) && !"".equals(nma_kategori)){
           try {
             Connection con = koneksi.membukakoneksi();
             Statement sttment = con.createStatement();
             String exec_sql ="SELECT COUNT(id_kategori) as count FROM tb_kategori WHERE id_kategori='"+id_kategori+"'";
             ResultSet hasil = sttment.executeQuery(exec_sql);
             hasil.next();
             cek_id = hasil.getInt("count");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
           if(cek_id==0)
           {
            try {
              Connection con = koneksi.membukakoneksi();
              Statement sttment = con.createStatement();
              String exec_sql = "insert into tb_kategori(id_kategori, nama_kategori)VALUES('" + id_kategori + "', '" + nma_kategori + "')";
              sttment.executeUpdate(exec_sql);
              JOptionPane.showMessageDialog(null, "Berhasil menyimpan data.");
              jtxt_nmkategori.setText("");
              lihat_kat();
              auto_id();
                 } catch (SQLException e) {
                   JOptionPane.showMessageDialog(null, "Error " + e);
                 }
           }else{
                JOptionPane.showMessageDialog(null, "Data Kategori ini sudah pernah disimpan.", 
                "Gagal Disimpan", JOptionPane.ERROR_MESSAGE);
           }
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat data yang masih kosong.");
        }
      }
    }
    
    public void hapus_kat(){
         //untuk menanghgapus data yang berasal dari tabel kategori
        int konfirmasi_hapus = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi_hapus==0) {
            try {
                String id_kat = jtxt_idkategori.getText();
                String nma_kat = jtxt_nmkategori.getText();
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql = "DELETE FROM tb_kategori WHERE id_kategori = '" + id_kat + "'";
                sttment.executeUpdate(exec_sql);
                JOptionPane.showMessageDialog(null, "Berhasil menghapus data.");
                lihat_kat();
                auto_id();
                jtxt_nmkategori.setText("");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
        }
    }
    
    public void edit_kat(){
         //untuk mengedit data yang berasal dari tabel kategori
        String id_kategori = jtxt_idkategori.getText();
        String nma_kategori = jtxt_nmkategori.getText();
       // int c_kode = 0;
        int konfirmasi_edit = JOptionPane.showConfirmDialog(null, "Anda yakin ingin mengganti data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
      
      if(konfirmasi_edit==0)
      {
        if(!"".equals(id_kategori) && !"".equals(nma_kategori)){
                    try {
                        Connection con = koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        String exec_sql = "UPDATE tb_kategori SET nama_kategori='"+nma_kategori+"' WHERE id_kategori ='"+id_kategori+"'";
                        sttment.executeUpdate(exec_sql);
                        JOptionPane.showMessageDialog(null, "Berhasil menyimpan perubahan data.");
//                        btnadd.doClick();
//                        konek.closekoneksi();
                        jtxt_nmkategori.setText("");
                        lihat_kat();
                        auto_id();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    }
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat data yang masih kosong.");
        }
      }
    }
    
    public void lihat_kat(){
         //untuk menampilkan data yang berasal dari tabel kategori
    String idkat, nmkat, nmrtostr ;
    int nmr=0;
    try {
            Object [] baris = {"Nomer","Id Kategori","Nama Kategori"};
            DefaultTableModel modeltbkat;
            modeltbkat = new DefaultTableModel(null, baris);
            tabel_kategori.setModel(modeltbkat);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT * FROM tb_kategori";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_kategori.getColumnModel().getColumn(0).setPreferredWidth(5);
                idkat=hasil.getString("id_kategori");
                nmkat=hasil.getString("nama_kategori");
                String data[]={nmrtostr,idkat,nmkat};   
                modeltbkat.addRow(data);
            }  
            auto_id();
            jtxt_nmkategori.setText("");
            jtxt_carikategori.setText("");
            tomboledit(false);
            tombolhapus(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
    
    public void pencarian_kat(){
         //untuk menampilkan data yang berasal dari tabel kategori yang lebih spesifik
        String idkat, nmkat, nmrtostr ;
        int nmr=0;
        String cari =jtxt_carikategori.getText();
        try {
            Object [] baris = {"Nomer","Id Kategori","Nama Kategori"};
            DefaultTableModel modeltbkat;
            modeltbkat = new DefaultTableModel(null, baris);
            tabel_kategori.setModel(modeltbkat);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
//            PreparedStatement pstmt = con.prepareStatement(queriy);
//            pstmt.setString(1, id);
            String exec_sql = "Select * from tb_kategori where id_kategori like '" + cari + "'" +
            "or nama_kategori like '" + cari + "'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            if(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_kategori.getColumnModel().getColumn(0).setPreferredWidth(5);
                idkat=hasil.getString("id_kategori");
                nmkat=hasil.getString("nama_kategori");
                String data[]={nmrtostr,idkat,nmkat};   
                modeltbkat.addRow(data);
                jtxt_nmkategori.setText("");
            }else{
            JOptionPane.showMessageDialog(null, "Data Kategori yang dicari tidak ditemukan.");
            lihat_kat();
            }
            jtxt_carikategori.setText("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
    
    public void tomboledit(boolean a){
        //untuk mengatur bisa digunakan/tidak digunakan tombol
       jbttn_edit.setEnabled(a);
    }
    
    public void tombolhapus(boolean a){
       jbttn_hapus.setEnabled(a);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jtxt_idkategori = new javax.swing.JTextField();
        jtxt_nmkategori = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtxt_carikategori = new javax.swing.JTextField();
        jbttn_cari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_kategori = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jbttn_tmbah = new javax.swing.JButton();
        jbttn_edit = new javax.swing.JButton();
        jbttn_refresh = new javax.swing.JButton();
        jbttn_hapus = new javax.swing.JButton();
        jbttn_kembali = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("ID Kategori");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jtxt_idkategori.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_idkategori.setEnabled(false);
        jtxt_idkategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_idkategoriActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_idkategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 143, -1));

        jtxt_nmkategori.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_nmkategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtxt_nmkategoriMouseReleased(evt);
            }
        });
        jtxt_nmkategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_nmkategoriActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_nmkategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 143, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Nama Kategori");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jtxt_carikategori.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_carikategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_carikategoriActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_carikategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 143, -1));

        jbttn_cari.setIcon(new javax.swing.ImageIcon("E:\\sinau java\\NetBeans Projects\\sistem_persediaan_barang\\src\\main_sisteme\\image\\icons8-search-32.png")); // NOI18N
        jbttn_cari.setText("Cari");
        jbttn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_cariActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, -1, -1));

        tabel_kategori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomer", "Id Kategori", "Nama Kategori"
            }
        ));
        tabel_kategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_kategoriMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_kategori);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 370, 116));

        jbttn_tmbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-add-24.png"))); // NOI18N
        jbttn_tmbah.setText("Tambah");
        jbttn_tmbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_tmbahActionPerformed(evt);
            }
        });

        jbttn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-edit-32.png"))); // NOI18N
        jbttn_edit.setText("Edit");
        jbttn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_editActionPerformed(evt);
            }
        });

        jbttn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-refresh-32.png"))); // NOI18N
        jbttn_refresh.setText("Refresh");
        jbttn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_refreshActionPerformed(evt);
            }
        });

        jbttn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-trash-32.png"))); // NOI18N
        jbttn_hapus.setText("Hapus");
        jbttn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_hapusActionPerformed(evt);
            }
        });

        jbttn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-back-arrow-24.png"))); // NOI18N
        jbttn_kembali.setText("Kembali");
        jbttn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_kembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbttn_tmbah)
                    .addComponent(jbttn_edit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbttn_refresh)
                    .addComponent(jbttn_hapus))
                .addGap(39, 39, 39))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jbttn_kembali)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbttn_tmbah)
                    .addComponent(jbttn_refresh))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbttn_hapus)
                    .addComponent(jbttn_edit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbttn_kembali)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, -1, -1));

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(168, 81));
        jPanel1.setPreferredSize(new java.awt.Dimension(168, 81));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 27)); // NOI18N
        jLabel8.setText("Data Kategori");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxt_idkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_idkategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_idkategoriActionPerformed

    private void jtxt_nmkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_nmkategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_nmkategoriActionPerformed

    private void jtxt_carikategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_carikategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_carikategoriActionPerformed

    private void jbttn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_editActionPerformed
        edit_kat();
    }//GEN-LAST:event_jbttn_editActionPerformed

    private void jbttn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_refreshActionPerformed
        lihat_kat();
    }//GEN-LAST:event_jbttn_refreshActionPerformed

    private void jbttn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_hapusActionPerformed
        hapus_kat();
    }//GEN-LAST:event_jbttn_hapusActionPerformed

    private void jbttn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_kembaliActionPerformed
        this.setVisible(false);
        //        new MenuHome().setVisible(true);
    }//GEN-LAST:event_jbttn_kembaliActionPerformed

    private void jbttn_tmbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_tmbahActionPerformed
//        String id_ktgr = txtid.getText();
//        String nm_ktgr = txttemp_kode.getText();
//        int c_kode = 0;
        tambah_kat();
    }//GEN-LAST:event_jbttn_tmbahActionPerformed

    private void tabel_kategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_kategoriMouseClicked
        DefaultTableModel dt_tbkat= (DefaultTableModel)tabel_kategori.getModel();
        int selecttedrow = tabel_kategori.getSelectedRow();
        jtxt_idkategori.setText(dt_tbkat.getValueAt(selecttedrow, 1).toString());
        jtxt_nmkategori.setText(dt_tbkat.getValueAt(selecttedrow, 2).toString());
        tomboledit(true);
        tombolhapus(true);
    }//GEN-LAST:event_tabel_kategoriMouseClicked

    private void jbttn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_cariActionPerformed
        pencarian_kat();
    }//GEN-LAST:event_jbttn_cariActionPerformed

    private void jtxt_nmkategoriMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_nmkategoriMouseReleased
        // TODO add your handling code here:
        String nmkatsama = jtxt_nmkategori.getText();
        try {
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT nama_kategori FROM tb_kategori WHERE nama_kategori = '"+nmkatsama+"'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
           if(hasil.next())
           {
               JOptionPane.showMessageDialog(null, "Nama kategori sudah ada di database");
           }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }//GEN-LAST:event_jtxt_nmkategoriMouseReleased

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
            java.util.logging.Logger.getLogger(FormKategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormKategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormKategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormKategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormKategori().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbttn_cari;
    private javax.swing.JButton jbttn_edit;
    private javax.swing.JButton jbttn_hapus;
    private javax.swing.JButton jbttn_kembali;
    private javax.swing.JButton jbttn_refresh;
    private javax.swing.JButton jbttn_tmbah;
    private javax.swing.JTextField jtxt_carikategori;
    private javax.swing.JTextField jtxt_idkategori;
    private javax.swing.JTextField jtxt_nmkategori;
    private javax.swing.JTable tabel_kategori;
    // End of variables declaration//GEN-END:variables
}
