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
public class MenuHomeAdmin extends javax.swing.JFrame {

    /**
     * Creates new form MenuHomeAdmin
     */
    main_sisteme.koneksi koneksinya = new main_sisteme.koneksi();
    main_sisteme.SesiPemilik PemilikSession = new main_sisteme.SesiPemilik();
    main_sisteme.SesiAdmin AdminSession = new main_sisteme.SesiAdmin();
    public MenuHomeAdmin() {
        initComponents();
        set_nama();
        tampilbrgkeluar();
        tampilbrgmsk();
        tampilbrgretur();
    }
    
    public void set_nama(){
    jlbl_username.setText(AdminSession.GetU_nama_user());
    jlbl_jenis_akses.setText(AdminSession.GetU_jenis_akses());
    }
    
    public void tampilbrgkeluar(){
     String nonota, idbrg, nmbrg, nmrtostr, katbrg, hrgbl, hrgjl, spek, stok, nourut, tgl_keluar;
      int nmr=0;
    try {
            Object [] baris = {"No","Nomer Nota", "Tanggal Keluar","No Urut keluar","Id Barang",
                "Nama Barang","Harga Jual", "Jumlah Keluar"};
            DefaultTableModel modeltbbrg;
            modeltbbrg = new DefaultTableModel(null, baris);
            tabel_brgkeluar.setModel(modeltbbrg);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT tb_detail_brgkeluar.no_urut_keluar, tb_detail_brgkeluar.no_nota"
                    + ", tb_barang.id_barang as idbarang, tb_barang.nama_barang as namabarang"
                    + ", tb_barang.harga_jual as hargajual, tb_brg_keluar.tgl_keluar as tglkeluar"
                    + ", tb_barang.stok as stock, tb_detail_brgkeluar.jumlah_brgkeluar"
                    + " FROM tb_detail_brgkeluar join tb_barang ON "
                    + "tb_barang.id_barang = tb_detail_brgkeluar.id_barang join tb_brg_keluar ON"
                    + " tb_brg_keluar.no_nota = tb_detail_brgkeluar.no_nota "
                    + "ORDER by tb_detail_brgkeluar.no_urut_keluar desc limit 5";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_brgkeluar.getColumnModel().getColumn(0).setPreferredWidth(5);
                nonota=hasil.getString("no_nota");
                tgl_keluar=hasil.getString("tglkeluar");
                nourut=hasil.getString("no_urut_keluar");
                idbrg=hasil.getString("idbarang");
                nmbrg=hasil.getString("namabarang");
                hrgjl=hasil.getString("hargajual");
                stok=hasil.getString("jumlah_brgkeluar");
                String data[]={nmrtostr,nonota,tgl_keluar,nourut,idbrg,nmbrg,hrgjl,stok};   
                modeltbbrg.addRow(data);
            }  
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
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
                    + " tb_retur_jual.kd_retur = tb_detail_returjual.kd_retur"
                    + " ORDER by tb_detail_returjual.no_urut_kembali desc limit 5";
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
    
     public void tampilbrgmsk(){
      String nofaktur, id_brg, nm_brg, no_urut, hrg_beli, stok, nmrtostr, tgl_msk;
      int nmr =0;
      try {
            Object [] baris = {"No","Nomer Faktur", "Tanggal Masuk","No Urut Masuk","Id Barang",
                "Nama Barang","Harga Beli", "Jumlah Masuk"};
            DefaultTableModel modeltbbrg;
            modeltbbrg = new DefaultTableModel(null, baris);
            tabel_brgmasuk.setModel(modeltbbrg);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT tb_detail_brgmsk.no_urut, tb_detail_brgmsk.no_faktur"
                    + ", tb_barang.id_barang as idbarang, tb_barang.nama_barang as namabarang"
                    + ", tb_barang.harga_beli as hargabeli, tb_brg_msk.tgl_masuk as tglmasuk"
                    + ", tb_barang.stok as stock, tb_detail_brgmsk.jumlah_brgmsk"
                    + " FROM tb_detail_brgmsk join tb_barang ON "
                    + "tb_barang.id_barang = tb_detail_brgmsk.id_barang join tb_brg_msk ON"
                    + " tb_brg_msk.no_faktur = tb_detail_brgmsk.no_faktur "
                    + "ORDER by tb_detail_brgmsk.no_urut desc limit 5";
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
                modeltbbrg.addRow(data);
            }  
   
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
        jlbl_username = new javax.swing.JLabel();
        jlbl_jenis_akses = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jlbl_jenisakses = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_brgkeluar = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_brgmasuk = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabel_returjual = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jbtn_dtspp = new javax.swing.JButton();
        jbtn_dtkategori = new javax.swing.JButton();
        jbtn_dtbrg = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1010, 650));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(1040, 74));

        jlbl_username.setText("-");

        jlbl_jenis_akses.setText("-");

        jLabel1.setText("Username");

        jlbl_jenisakses.setText("Jenis Akses");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlbl_jenisakses)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlbl_jenis_akses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jlbl_username, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(885, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbl_username)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbl_jenis_akses)
                    .addComponent(jlbl_jenisakses))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/logo_ocomp2_finalfix.jpg"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 340, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel_brgkeluar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nomer Nota", "Tanggal Keluar", "No Urut Keluar", "Id Barang", "Nama Barang", "Harga Jual", "Jumlah Keluar"
            }
        ));
        jScrollPane2.setViewportView(tabel_brgkeluar);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 630, 120));

        tabel_brgmasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nomer Faktur", "Tanggal Masuk", "No Urut Masuk", "Id Barang", "Nama Barang", "Harga Beli", "Jumlah Masuk"
            }
        ));
        jScrollPane1.setViewportView(tabel_brgmasuk);
        if (tabel_brgmasuk.getColumnModel().getColumnCount() > 0) {
            tabel_brgmasuk.getColumnModel().getColumn(0).setPreferredWidth(9);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 630, 120));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel2.setText("5 Transaksi Terakhir Barang Keluar");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel4.setText("5 Transaksi Terakhir Barang Masuk");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, -1));

        tabel_returjual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode Retur", "No Nota", "Id Barang", "Nama Barang", "Harga Jual", "Jumlah Kembali", "Tanggal Retur", "Keterangan"
            }
        ));
        jScrollPane4.setViewportView(tabel_returjual);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 630, 130));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("5 Transaksi Terakhir Retur Penjualan");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 103, 630, 530));

        jbtn_dtspp.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jbtn_dtspp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-delivered-64.png"))); // NOI18N
        jbtn_dtspp.setText(" Lihat Data Supplier");
        jbtn_dtspp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_dtsppActionPerformed(evt);
            }
        });
        getContentPane().add(jbtn_dtspp, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 550, 320, 50));

        jbtn_dtkategori.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jbtn_dtkategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-category-50.png"))); // NOI18N
        jbtn_dtkategori.setText(" Lihat Data Kategori");
        jbtn_dtkategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_dtkategoriActionPerformed(evt);
            }
        });
        getContentPane().add(jbtn_dtkategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 320, 50));

        jbtn_dtbrg.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jbtn_dtbrg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-box-64.png"))); // NOI18N
        jbtn_dtbrg.setText(" Lihat Data Barang");
        jbtn_dtbrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_dtbrgActionPerformed(evt);
            }
        });
        getContentPane().add(jbtn_dtbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 320, 50));

        jMenu1.setText("Data Master");

        jMenuItem1.setText("Data Barang");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Data Kategori");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Data Suplier");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Transaksi");

        jMenuItem5.setText("Barang Masuk");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setText("Barang Keluar");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem7.setText("Retur Jual Barang");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Laporan");

        jMenuItem8.setText("Laporan Barang Masuk");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem9.setText("Laporan Barang Keluar");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setText("Laporan Retur Jual Barang");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuItem4.setText("Laporan Stok Barang ");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Pengaturan");

        jMenuItem13.setText("Petunjuk");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);

        jMenuItem11.setText("Ganti Password");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuItem12.setText("Log Out");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        this.setVisible(false);
        new FormLogin().setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        this.setVisible(true);
        new gantipassword_admn().setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        this.setVisible(true);
        new FormLaporanBrgMsk().setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        this.setVisible(true);
        new FormReturPenjualan().setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        this.setVisible(true);
        new FormTransKeluarAdmn().setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        this.setVisible(true);
        new FormTransMasukAdmn().setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.setVisible(true);
        new Formdtsplr_admin().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.setVisible(true);
        new Formdtktgri_admin().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.setVisible(true);
        new Formdtbrg_admin().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        this.setVisible(true);
        new FormLaporanBrgKeluar().setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        this.setVisible(true);
        new FormLaporanStokBarang().setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        this.setVisible(true);
       new FormLaporanReturJual().setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jbtn_dtsppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_dtsppActionPerformed
        // TODO add your handling code here:
        this.setVisible(true);
        new Formdtsplr_admin().setVisible(true);
    }//GEN-LAST:event_jbtn_dtsppActionPerformed

    private void jbtn_dtbrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_dtbrgActionPerformed
        // TODO add your handling code here:
        this.setVisible(true);
        new Formdtbrg_admin().setVisible(true);
    }//GEN-LAST:event_jbtn_dtbrgActionPerformed

    private void jbtn_dtkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_dtkategoriActionPerformed
        // TODO add your handling code here:
        this.setVisible(true);
        new Formdtktgri_admin().setVisible(true);
    }//GEN-LAST:event_jbtn_dtkategoriActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        this.setVisible(true);
        new FormPetunjuk().setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

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
            java.util.logging.Logger.getLogger(MenuHomeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuHomeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuHomeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuHomeAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuHomeAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton jbtn_dtbrg;
    private javax.swing.JButton jbtn_dtkategori;
    private javax.swing.JButton jbtn_dtspp;
    private javax.swing.JLabel jlbl_jenis_akses;
    private javax.swing.JLabel jlbl_jenisakses;
    private javax.swing.JLabel jlbl_username;
    private javax.swing.JTable tabel_brgkeluar;
    private javax.swing.JTable tabel_brgmasuk;
    private javax.swing.JTable tabel_returjual;
    // End of variables declaration//GEN-END:variables
}
