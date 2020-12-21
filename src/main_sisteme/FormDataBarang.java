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
public class FormDataBarang extends javax.swing.JFrame {

    /**
     * Creates new form FormDataBarang
     */
    public FormDataBarang() {
        initComponents();
        jtxt_idkat.hide();
        lihat_barang();
        pil_kategori();
        tomboledit(false);
        tombolhapus(false);
    }
    
    public void auto_id(){
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
            auto_id();
            jtxt_nmbrg.setText("");
            jtxt_caribrg.setText("");
            jtxt_hrgbeli.setText("");
            jtxt_hrgjual.setText("");
            jtxt_spekbrg.setText("");
            jtxt_idkat.setText("");
            jtxt_stokbrg.setText("");
            jcb_pilkategori.setSelectedItem("Silahkan Pilih");
            tomboledit(false);
            tombolhapus(false);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
    
    public void pil_kategori(){
         //untuk menampilkan data yang berasal dari tabel kategori agar bisa tampil di combobox
     String idkat, nmkat, nmrtostr ;
    int nmr=0;
    try {
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT * FROM tb_kategori";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmkat=hasil.getString("nama_kategori");
                jcb_pilkategori.addItem(nmkat);
            }  
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
    
    public void tambah_barang(){
         //untuk menambahkan data barang ke tabel barang
        String idbrg = jtxt_idbrg.getText();
        String nmbrg = jtxt_nmbrg.getText();
        String spec = jtxt_spekbrg.getText();
        int stock = 0;
//        int stock = Integer.valueOf(jtxt_stokbrg.getText());
        String idkategori = jtxt_idkat.getText();
        int hargabeli = Integer.valueOf(jtxt_hrgbeli.getText());
        int hargajual = Integer.valueOf(jtxt_hrgjual.getText());     
       // String ntlp = jcb_pilihakses.getSelectedItem();
        int cek_id = 0;
        int konfirmasi_tambah = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menambahkan data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
      
      if(konfirmasi_tambah==0)
      {
        if(!"".equals(idbrg) && !"".equals(nmbrg) && !"".equals(hargabeli) && !"".equals(hargajual)
                && !"".equals(spec) && !"".equals(stock) && !"Silahkan Pilih".equals(jcb_pilkategori.getSelectedItem())){
            
            try {
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql ="SELECT COUNT(id_barang) as count FROM tb_barang WHERE id_barang='"+idbrg+"'";
                ResultSet hasil = sttment.executeQuery(exec_sql);
                hasil.next();
                cek_id = hasil.getInt("count");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
            
                if(cek_id == 0)
                {
                    try {
                        Connection con = koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        String exec_sql = "insert into tb_barang(id_barang, id_kategori , nama_barang, harga_beli, harga_jual, spesifikasi"
                                + ", stok)VALUES('" + idbrg + "', '" + idkategori
                                + "', '" + nmbrg + "', '" + hargabeli+ "' , '" + hargajual + "', '" + spec 
                                + "', '" + stock + "')";
                        sttment.executeUpdate(exec_sql);
                        JOptionPane.showMessageDialog(null, "Berhasil menyimpan data.");
                        jtxt_nmbrg.setText("");
                        jtxt_caribrg.setText("");
                        jcb_pilkategori.setSelectedItem("Silahkan Pilih");
                        jtxt_stokbrg.setText("");
                        lihat_barang();
                        //auto_id();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Barang ini sudah pernah disimpan.", 
                    "Gagal Disimpan", JOptionPane.ERROR_MESSAGE);
                }
                    
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat data yang masih kosong.");
        }
      }
    }
    
     public void hapus_barang(){
          //untuk menghapus data yang berasal dari tabel barang
        int konfirmasi_hapus = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi_hapus==0) {
            try {
                String idbrg = jtxt_idbrg.getText();
//                String nmusr = jtxt_nmauser.getText();
//                String paswd = jtxt_paswrduser.getText();
               // String ntlp = jtxt_notlpsupp.getText();
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql = "DELETE FROM tb_barang WHERE id_barang = '" + idbrg + "'";
                sttment.executeUpdate(exec_sql);
                JOptionPane.showMessageDialog(null, "Berhasil menghapus data.");
                lihat_barang();
                auto_id();
                jtxt_nmbrg.setText("");
                jtxt_hrgbeli.setText("");
                jtxt_hrgjual.setText("");
                jtxt_spekbrg.setText("");
                jtxt_stokbrg.setText("");
                jcb_pilkategori.setSelectedItem("Silahkan Pilih");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
        }
    }
     
     public void edit_barang(){
          //untuk mengganti data yang berasal dari tabel barang
        String idbrg = jtxt_idbrg.getText();
        String nmbrg = jtxt_nmbrg.getText();
        String spekbrg = jtxt_spekbrg.getText();
        String idkategori = jtxt_idkat.getText();
        int stock = Integer.valueOf(jtxt_stokbrg.getText());
        double hargabeli = Double.valueOf( jtxt_hrgbeli.getText());
        double hargajual = Double.valueOf( jtxt_hrgjual.getText());  
        //String ntlp = jtxt_notlpsupp.getText();
        int c_kode = 0;
        int konfirmasi_edit = JOptionPane.showConfirmDialog(null, "Anda yakin ingin mengganti data ini?", "Konfirmasi", 
        JOptionPane.OK_CANCEL_OPTION);
      
      if(konfirmasi_edit==0)
      {
        if(!"".equals(idbrg) && !"".equals(nmbrg) && !"".equals(hargabeli) && !"".equals(hargajual)
        && !"".equals(spekbrg) && !"".equals(stock) 
        && !"Silahkan Pilih".equals(jcb_pilkategori.getSelectedItem())){
                    try {
                        Connection con = koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        String exec_sql = "UPDATE tb_barang SET id_kategori='"+idkategori+"' "
                                + ", nama_barang='"+nmbrg+"' "
                                + ", harga_beli='"+hargabeli+"' "
                                + ", harga_jual='"+hargajual+"' "
                                + ", spesifikasi='"+spekbrg+"' "
                                + ", stok='"+stock+"' "
                                + " WHERE id_barang ='"+idbrg+"'";
                        sttment.executeUpdate(exec_sql);
                        JOptionPane.showMessageDialog(null, "Berhasil menyimpan perubahan data.");
                        jtxt_nmbrg.setText("");
                        jtxt_hrgbeli.setText("");
                        jtxt_hrgjual.setText("");
                        jcb_pilkategori.setSelectedItem("Silahkan Pilih");
                        jtxt_spekbrg.setText("");
                        jtxt_stokbrg.setText("");
                        lihat_barang();
                        auto_id();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    }
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat data yang masih kosong.");
        }
      }
    }
     
     public void pencarian_barang(){
          //untuk menampilkan data yang berasal dari tabel barang dengan lebih spesifik dengan pencarian
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
                jtxt_nmbrg.setText("");
                jtxt_hrgbeli.setText("");
                jtxt_hrgjual.setText("");
                jtxt_spekbrg.setText("");
                jtxt_stokbrg.setText("");
                jcb_pilkategori.setSelectedItem("Silahkan Pilih");
           }else{
            JOptionPane.showMessageDialog(null, "Data barang tidak ditemukan.");
            lihat_barang();
            }
            jtxt_caribrg.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
     
     public void tomboledit(boolean a){
         //untuk mengatur bisa digunakan/tidak digunakan tombol
       jbttn_edit.setEnabled(a);
    }
    
    public void tombolhapus(boolean a){
        //untuk mengatur bisa digunakan/tidak digunakan tombol
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

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jtxt_idbrg = new javax.swing.JTextField();
        jtxt_nmbrg = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtxt_caribrg = new javax.swing.JTextField();
        jbttn_cari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_brg = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jbttn_tambah = new javax.swing.JButton();
        jbttn_edit = new javax.swing.JButton();
        jbttn_refresh = new javax.swing.JButton();
        jbttn_hapus = new javax.swing.JButton();
        jbttn_kembali = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jtxt_hrgbeli = new javax.swing.JTextField();
        jtxt_idkat = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jcb_pilkategori = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxt_spekbrg = new javax.swing.JTextArea();
        jtxt_hrgjual = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jtxt_stokbrg = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(168, 81));

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

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("ID Barang");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jtxt_idbrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_idbrg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtxt_idbrgMouseReleased(evt);
            }
        });
        jtxt_idbrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_idbrgActionPerformed(evt);
            }
        });
        jPanel5.add(jtxt_idbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 143, -1));

        jtxt_nmbrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_nmbrg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jtxt_nmbrgMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtxt_nmbrgMouseReleased(evt);
            }
        });
        jtxt_nmbrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_nmbrgActionPerformed(evt);
            }
        });
        jtxt_nmbrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxt_nmbrgKeyReleased(evt);
            }
        });
        jPanel5.add(jtxt_nmbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 143, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Pilih Kategori");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jtxt_caribrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_caribrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_caribrgActionPerformed(evt);
            }
        });
        jPanel5.add(jtxt_caribrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 143, -1));

        jbttn_cari.setIcon(new javax.swing.ImageIcon("E:\\sinau java\\NetBeans Projects\\sistem_persediaan_barang\\src\\main_sisteme\\image\\icons8-search-32.png")); // NOI18N
        jbttn_cari.setText("Cari");
        jbttn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_cariActionPerformed(evt);
            }
        });
        jPanel5.add(jbttn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        tabel_brg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomer", "Id Barang", "Nama Barang", "Kategori", "Harga Beli", "Harga Jual", "Spesifikasi", "Stok"
            }
        ));
        tabel_brg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_brgMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_brg);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 680, 116));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbttn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-add-24.png"))); // NOI18N
        jbttn_tambah.setText("Tambah");
        jbttn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_tambahActionPerformed(evt);
            }
        });
        jPanel6.add(jbttn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 11, -1, -1));

        jbttn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-edit-32.png"))); // NOI18N
        jbttn_edit.setText("Edit");
        jbttn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_editActionPerformed(evt);
            }
        });
        jPanel6.add(jbttn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 52, -1, -1));

        jbttn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-refresh-32.png"))); // NOI18N
        jbttn_refresh.setText("Refresh");
        jbttn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_refreshActionPerformed(evt);
            }
        });
        jPanel6.add(jbttn_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 11, -1, -1));

        jbttn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-trash-32.png"))); // NOI18N
        jbttn_hapus.setText("Hapus");
        jbttn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_hapusActionPerformed(evt);
            }
        });
        jPanel6.add(jbttn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 52, -1, -1));

        jbttn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-back-arrow-24.png"))); // NOI18N
        jbttn_kembali.setText("Kembali");
        jbttn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_kembaliActionPerformed(evt);
            }
        });
        jPanel6.add(jbttn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 81, -1, -1));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 220, 120));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Harga Beli");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Stok");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jtxt_hrgbeli.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_hrgbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_hrgbeliActionPerformed(evt);
            }
        });
        jPanel5.add(jtxt_hrgbeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 143, -1));

        jtxt_idkat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_idkat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_idkatActionPerformed(evt);
            }
        });
        jPanel5.add(jtxt_idkat, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 50, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Nama Barang");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jcb_pilkategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Silahkan Pilih" }));
        jcb_pilkategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_pilkategoriActionPerformed(evt);
            }
        });
        jPanel5.add(jcb_pilkategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 140, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Harga Jual");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jtxt_spekbrg.setColumns(20);
        jtxt_spekbrg.setRows(5);
        jScrollPane1.setViewportView(jtxt_spekbrg);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, -1, -1));

        jtxt_hrgjual.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_hrgjual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_hrgjualActionPerformed(evt);
            }
        });
        jPanel5.add(jtxt_hrgjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 143, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Spesifikasi");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        jtxt_stokbrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_stokbrg.setText("0");
        jtxt_stokbrg.setEnabled(false);
        jtxt_stokbrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_stokbrgActionPerformed(evt);
            }
        });
        jPanel5.add(jtxt_stokbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 143, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1136, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxt_idbrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_idbrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_idbrgActionPerformed

    private void jtxt_nmbrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_nmbrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_nmbrgActionPerformed

    private void jtxt_caribrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_caribrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_caribrgActionPerformed

    private void jbttn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_cariActionPerformed
        pencarian_barang();
    }//GEN-LAST:event_jbttn_cariActionPerformed

    private void tabel_brgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_brgMouseClicked
        DefaultTableModel dt_tbbrg= (DefaultTableModel)tabel_brg.getModel();
        int selecttedrow = tabel_brg.getSelectedRow();
        //        jTextField2.setTextdt_tbkat.getValueAt(selecttedrow, 0).toString());
        jtxt_idbrg.setText(dt_tbbrg.getValueAt(selecttedrow, 1).toString());
        jtxt_nmbrg.setText(dt_tbbrg.getValueAt(selecttedrow, 2).toString());
        jcb_pilkategori.setSelectedItem(dt_tbbrg.getValueAt(selecttedrow, 3).toString());
        jtxt_hrgbeli.setText(dt_tbbrg.getValueAt(selecttedrow, 4).toString());
        jtxt_hrgjual.setText(dt_tbbrg.getValueAt(selecttedrow, 5).toString());
        jtxt_spekbrg.setText(dt_tbbrg.getValueAt(selecttedrow, 7).toString());
        jtxt_stokbrg.setText(dt_tbbrg.getValueAt(selecttedrow, 6).toString());
        tomboledit(true);
        tombolhapus(true);
        
    }//GEN-LAST:event_tabel_brgMouseClicked

    private void jbttn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_tambahActionPerformed
        tambah_barang();
    }//GEN-LAST:event_jbttn_tambahActionPerformed

    private void jbttn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_editActionPerformed
        edit_barang();
    }//GEN-LAST:event_jbttn_editActionPerformed

    private void jbttn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_refreshActionPerformed
        lihat_barang();
    }//GEN-LAST:event_jbttn_refreshActionPerformed

    private void jbttn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_hapusActionPerformed
        hapus_barang();
    }//GEN-LAST:event_jbttn_hapusActionPerformed

    private void jbttn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_kembaliActionPerformed
        this.setVisible(false);
        //        new MenuHome().setVisible(true);
    }//GEN-LAST:event_jbttn_kembaliActionPerformed

    private void jtxt_hrgbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_hrgbeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_hrgbeliActionPerformed

    private void jtxt_idkatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_idkatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_idkatActionPerformed

    private void jcb_pilkategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_pilkategoriActionPerformed
        String nmkat = jcb_pilkategori.getSelectedItem().toString();
        if(!nmkat.equals("")){
            try {
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql = "SELECT id_kategori FROM tb_kategori WHERE nama_kategori='"+nmkat+"'";
                ResultSet hasil = sttment.executeQuery(exec_sql);
                if(hasil.next()){
                    jtxt_idkat.setText(hasil.getString("id_kategori"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
        }else{
            jtxt_idkat.setText("");
        }
        if(nmkat=="Silahkan Pilih"){
         jtxt_idkat.setText("");
        }
    }//GEN-LAST:event_jcb_pilkategoriActionPerformed

    private void jtxt_hrgjualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_hrgjualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_hrgjualActionPerformed

    private void jtxt_stokbrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_stokbrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_stokbrgActionPerformed

    private void jtxt_nmbrgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_nmbrgKeyReleased
       
    }//GEN-LAST:event_jtxt_nmbrgKeyReleased

    private void jtxt_nmbrgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_nmbrgMouseExited
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jtxt_nmbrgMouseExited

    private void jtxt_nmbrgMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_nmbrgMouseReleased
        // TODO add your handling code here:
//         String nmbrgsama = jtxt_nmbrg.getText();
//        try {
//            Connection con = koneksi.membukakoneksi();
//            Statement sttment = con.createStatement();
//            String exec_sql = "SELECT nama_barang FROM tb_barang WHERE nama_barang = '"+nmbrgsama+"'";
//            ResultSet hasil = sttment.executeQuery(exec_sql);
//           if(hasil.next())
//           {
//               JOptionPane.showMessageDialog(null, "Nama Barang sudah ada di database");
//           }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error " + e);
//        }
    }//GEN-LAST:event_jtxt_nmbrgMouseReleased

    private void jtxt_idbrgMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_idbrgMouseReleased
        // TODO add your handling code here:
         String idbrgsama = jtxt_idbrg.getText();
        try {
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT id_barang FROM tb_barang WHERE id_barang = '"+idbrgsama+"'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
           if(hasil.next())
           {
               JOptionPane.showMessageDialog(null, "Id Barang sudah ada di database");
           }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }//GEN-LAST:event_jtxt_idbrgMouseReleased

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
            java.util.logging.Logger.getLogger(FormDataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDataBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbttn_cari;
    private javax.swing.JButton jbttn_edit;
    private javax.swing.JButton jbttn_hapus;
    private javax.swing.JButton jbttn_kembali;
    private javax.swing.JButton jbttn_refresh;
    private javax.swing.JButton jbttn_tambah;
    private javax.swing.JComboBox<String> jcb_pilkategori;
    private javax.swing.JTextField jtxt_caribrg;
    private javax.swing.JTextField jtxt_hrgbeli;
    private javax.swing.JTextField jtxt_hrgjual;
    private javax.swing.JTextField jtxt_idbrg;
    private javax.swing.JTextField jtxt_idkat;
    private javax.swing.JTextField jtxt_nmbrg;
    private javax.swing.JTextArea jtxt_spekbrg;
    private javax.swing.JTextField jtxt_stokbrg;
    private javax.swing.JTable tabel_brg;
    // End of variables declaration//GEN-END:variables
}
