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
public class FormSuplier extends javax.swing.JFrame {

    /**
     * Creates new form FormSuplier
     */
    public FormSuplier() {
        initComponents();
        lihat_suplier();
        auto_id();
        tomboledit(false);
        tombolhapus(false);
    }
    
    public void auto_id(){
         //untuk menampilkan auto increment id/kode 
      try{
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT MAX(right(id_suplier,3)) as id_sp from tb_suplier";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                if(hasil.first()==false)
                {
                    jtxt_idsupp.setText("SP001");
                }
                else{
                    hasil.last();
                    int set_id_sp = hasil.getInt(1)+1;
                    String id_sp = String.valueOf(set_id_sp);
                    int id_kat_selanjutnya = id_sp.length();
                    for(int x=0; x<3-id_kat_selanjutnya; x++)
                    {
                        id_sp = "0"+id_sp;
                    }
                    jtxt_idsupp.setText("SP"+id_sp);
                }
            }
      }
      catch(SQLException ex)
      {
          Logger.getLogger(FormKategori.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    public void lihat_suplier(){
        //untuk menampilkan data yang berasal dari tabel suplier
    String idspp, nmspp, almtspp, ntlp, nmrtostr ;
    int nmr=0;
    try {
            Object [] baris = {"Nomer","Id Suplier","Nama Supplier","Alamat","No Telepon"};
            DefaultTableModel modeltbsupp;
            modeltbsupp = new DefaultTableModel(null, baris);
            tabel_supplier.setModel(modeltbsupp);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT * FROM tb_suplier";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_supplier.getColumnModel().getColumn(0).setPreferredWidth(7);
                idspp=hasil.getString("id_suplier");
                nmspp=hasil.getString("nama_suplier");
                almtspp=hasil.getString("alamat");
                ntlp=hasil.getString("no_tlp");
                String data[]={nmrtostr,idspp,nmspp,almtspp,ntlp};   
                modeltbsupp.addRow(data);
            }  
            auto_id();
            jtxt_nmasupp.setText("");
            jtxt_almtsupp.setText("");
            jtxt_notlpsupp.setText("");
            tomboledit(false);
            tombolhapus(false);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
        
    }
    
    public void tambah_suplier(){
        //untuk menambahkan data ke tabel suplier
        String id_spp = jtxt_idsupp.getText();
        String nmspp = jtxt_nmasupp.getText();
        String almtspp = jtxt_almtsupp.getText();
        String ntlp = jtxt_notlpsupp.getText();
        int cek_id = 0;
        int konfirmasi_tambah = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menambahkan data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
      
      if(konfirmasi_tambah==0)
      {
        if(!"".equals(id_spp) && !"".equals(nmspp) && !"".equals(almtspp) && !"".equals(ntlp)){
            
            try {
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql ="SELECT COUNT(id_suplier) as count FROM tb_suplier WHERE id_suplier='"+id_spp+"'";
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
                        String exec_sql = "insert into tb_suplier(id_suplier, nama_suplier, alamat, no_tlp)"
                                + "VALUES('" + id_spp + "', '" + nmspp 
                                + "', '" + almtspp + "', '" + ntlp+ "')";
                        sttment.executeUpdate(exec_sql);
                        JOptionPane.showMessageDialog(null, "Berhasil menyimpan data.");
//                        btnadd.doClick();
//                        konek.closekoneksi();
                        jtxt_nmasupp.setText("");
                        jtxt_almtsupp.setText("");
                        jtxt_notlpsupp.setText("");
                        lihat_suplier();
                        auto_id();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    }
            }else{
                JOptionPane.showMessageDialog(null, "Data Supplier ini sudah pernah disimpan.", 
                 "Gagal Disimpan", JOptionPane.ERROR_MESSAGE);
            }
                    
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat data yang masih kosong.");
        }
      }
    }
    
     public void edit_suplier(){
         //untuk mengedut data yang berasal dari tabel barang
        String id_spp = jtxt_idsupp.getText();
        String nmspp = jtxt_nmasupp.getText();
        String almtspp = jtxt_almtsupp.getText();
        String ntlp = jtxt_notlpsupp.getText();
        int c_kode = 0;
        int konfirmasi_edit = JOptionPane.showConfirmDialog(null, "Anda yakin ingin mengganti data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
      
      if(konfirmasi_edit==0)
      {
        if(!"".equals(id_spp) && !"".equals(nmspp)){
                    try {
                        Connection con = koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        String exec_sql = "UPDATE tb_suplier SET nama_suplier='"+nmspp+"' "
                                + ", alamat='"+almtspp+"' "
                                + ", no_tlp='"+ntlp+"' WHERE id_suplier ='"+id_spp+"'";
                        sttment.executeUpdate(exec_sql);
                        JOptionPane.showMessageDialog(null, "Berhasil menyimpan perubahan data.");
//                        btnadd.doClick();
//                        konek.closekoneksi();
                        jtxt_nmasupp.setText("");
                        jtxt_almtsupp.setText("");
                        jtxt_notlpsupp.setText("");
                        lihat_suplier();
                        auto_id();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    }
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat data yang masih kosong.");
        }
      }
    }
    
    public void hapus_suplier(){
        //untuk menghapus data yang berasal dari tabel suplier
        int konfirmasi_hapus = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi_hapus==0) {
            try {
                String id_spp = jtxt_idsupp.getText();
                String nmspp = jtxt_nmasupp.getText();
                String almtspp = jtxt_almtsupp.getText();
                String ntlp = jtxt_notlpsupp.getText();
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql = "DELETE FROM tb_suplier WHERE id_suplier = '" + id_spp + "'";
                sttment.executeUpdate(exec_sql);
                JOptionPane.showMessageDialog(null, "Berhasil menghapus data.");
                lihat_suplier();
                auto_id();
                jtxt_nmasupp.setText("");
                jtxt_almtsupp.setText("");
                jtxt_notlpsupp.setText("");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
        }
    }
    
        public void pencarian_suplier(){
            //untuk menampilkan data yang berasal dari tabel suplier yang lebih spesifik
        String id_spp, nmspp, nmrtostr, almtspp, ntlp ;
        int nmr=0;
        String cari =jtxt_carisupp.getText();
        try {
            Object [] baris = {"Nomer","Id Suplier","Nama Supplier","Alamat","No Telepon"};
            DefaultTableModel modeltbsupp;
            modeltbsupp = new DefaultTableModel(null, baris);
            tabel_supplier.setModel(modeltbsupp);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "Select * from tb_suplier where id_suplier like '" + cari + "'" +
            "or nama_suplier like '%" + cari + "%'"+
            "or alamat like '%" + cari + "%'"+
            "or no_tlp like '%" + cari + "%'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            if(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_supplier.getColumnModel().getColumn(0).setPreferredWidth(5);
                id_spp=hasil.getString("id_suplier");
                nmspp=hasil.getString("nama_suplier");
                almtspp=hasil.getString("alamat");
                ntlp=hasil.getString("no_tlp");
                String data[]={nmrtostr,id_spp,nmspp,almtspp,ntlp};   
                modeltbsupp.addRow(data);
                jtxt_nmasupp.setText("");
                jtxt_almtsupp.setText("");
                jtxt_notlpsupp.setText("");
            }else{
            JOptionPane.showMessageDialog(null, "Data Supplier yang dicari tidak ditemukan.");
            lihat_suplier();
            }
            jtxt_carisupp.setText("");
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
        jtxt_idsupp = new javax.swing.JTextField();
        jtxt_nmasupp = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtxt_carisupp = new javax.swing.JTextField();
        jbttn_cari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_supplier = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jbttn_tambah = new javax.swing.JButton();
        jbttn_edit = new javax.swing.JButton();
        jbttn_refresh = new javax.swing.JButton();
        jbttn_hapus = new javax.swing.JButton();
        jbttn_kembali = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtxt_almtsupp = new javax.swing.JTextField();
        jtxt_notlpsupp = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("ID Supplier");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 14, -1, -1));

        jtxt_idsupp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_idsupp.setEnabled(false);
        jtxt_idsupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_idsuppActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_idsupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 11, 180, -1));

        jtxt_nmasupp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_nmasupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_nmasuppActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_nmasupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 43, 180, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Nama Supplier");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 46, -1, -1));

        jtxt_carisupp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_carisupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_carisuppActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_carisupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 155, 143, -1));

        jbttn_cari.setIcon(new javax.swing.ImageIcon("E:\\sinau java\\NetBeans Projects\\sistem_persediaan_barang\\src\\main_sisteme\\image\\icons8-search-32.png")); // NOI18N
        jbttn_cari.setText("Cari");
        jbttn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_cariActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 155, -1, -1));

        tabel_supplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomer", "ID Supplier", "Nama Supplier", "Alamat", "Nomer Telepon"
            }
        ));
        tabel_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_supplierMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_supplier);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 196, 573, 116));

        jbttn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-add-24.png"))); // NOI18N
        jbttn_tambah.setText("Tambah");
        jbttn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_tambahActionPerformed(evt);
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
                    .addComponent(jbttn_tambah)
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
                    .addComponent(jbttn_tambah)
                    .addComponent(jbttn_refresh))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbttn_hapus)
                    .addComponent(jbttn_edit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbttn_kembali)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 11, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Alamat");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 78, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Nomer Telepon");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 110, -1, -1));

        jtxt_almtsupp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_almtsupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_almtsuppActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_almtsupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 75, 180, -1));

        jtxt_notlpsupp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_notlpsupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_notlpsuppActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_notlpsupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 107, 180, -1));

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(168, 81));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 27)); // NOI18N
        jLabel8.setText("Data Supplier");

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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxt_idsuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_idsuppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_idsuppActionPerformed

    private void jtxt_nmasuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_nmasuppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_nmasuppActionPerformed

    private void jtxt_carisuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_carisuppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_carisuppActionPerformed

    private void jbttn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_editActionPerformed
        edit_suplier();
    }//GEN-LAST:event_jbttn_editActionPerformed

    private void jbttn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_refreshActionPerformed
        lihat_suplier();
    }//GEN-LAST:event_jbttn_refreshActionPerformed

    private void jbttn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_hapusActionPerformed
        hapus_suplier();
    }//GEN-LAST:event_jbttn_hapusActionPerformed

    private void jbttn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_kembaliActionPerformed
        this.setVisible(false);
        //        new MenuHome().setVisible(true);
    }//GEN-LAST:event_jbttn_kembaliActionPerformed

    private void jtxt_almtsuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_almtsuppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_almtsuppActionPerformed

    private void jtxt_notlpsuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_notlpsuppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_notlpsuppActionPerformed

    private void jbttn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_tambahActionPerformed
        tambah_suplier();
    }//GEN-LAST:event_jbttn_tambahActionPerformed

    private void jbttn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_cariActionPerformed
        pencarian_suplier();
    }//GEN-LAST:event_jbttn_cariActionPerformed

    private void tabel_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_supplierMouseClicked
        DefaultTableModel dt_tbsupp= (DefaultTableModel)tabel_supplier.getModel();
        int selecttedrow = tabel_supplier.getSelectedRow();
//        jTextField2.setTextdt_tbkat.getValueAt(selecttedrow, 0).toString());
        jtxt_idsupp.setText(dt_tbsupp.getValueAt(selecttedrow, 1).toString());
        jtxt_nmasupp.setText(dt_tbsupp.getValueAt(selecttedrow, 2).toString());
        jtxt_almtsupp.setText(dt_tbsupp.getValueAt(selecttedrow, 3).toString());
        jtxt_notlpsupp.setText(dt_tbsupp.getValueAt(selecttedrow, 4).toString());
        tomboledit(true);
        tombolhapus(true);
    }//GEN-LAST:event_tabel_supplierMouseClicked

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
            java.util.logging.Logger.getLogger(FormSuplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormSuplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormSuplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormSuplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormSuplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JButton jbttn_tambah;
    private javax.swing.JTextField jtxt_almtsupp;
    private javax.swing.JTextField jtxt_carisupp;
    private javax.swing.JTextField jtxt_idsupp;
    private javax.swing.JTextField jtxt_nmasupp;
    private javax.swing.JTextField jtxt_notlpsupp;
    private javax.swing.JTable tabel_supplier;
    // End of variables declaration//GEN-END:variables
}
