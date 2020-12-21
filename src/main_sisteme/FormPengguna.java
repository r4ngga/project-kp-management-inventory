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
public class FormPengguna extends javax.swing.JFrame {

    /**
     * Creates new form FormPengguna
     */
    public FormPengguna() {
        initComponents();
        auto_id();
        lihat_pengguna();
        tomboledit(false);
        tombolhapus(false);
    }
    
    public void auto_id(){
         //untuk menampilkan auto increment id/kode 
      try{
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT MAX(right(id_user,3)) as id_usr from tb_user";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                if(hasil.first()==false)
                {
                    jtxt_iduser.setText("U001");
                }
                else{
                    hasil.last();
                    int set_id_usr = hasil.getInt(1)+1;
                    String id_usr = String.valueOf(set_id_usr);
                    int id_usr_selanjutnya = id_usr.length();
                    for(int x=0; x<3-id_usr_selanjutnya; x++)
                    {
                        id_usr = "0"+id_usr;
                    }
                    jtxt_iduser.setText("U"+id_usr);
                }
            }
      }
      catch(SQLException ex)
      {
          Logger.getLogger(FormKategori.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    public void lihat_pengguna(){
        //untuk menampilkan data yang berasal dari tabel pengguna/user
    String iduser, nmusr, nmrtostr, pwduser, jnsaksesuser ;
    int nmr=0;
    try {
            Object [] baris = {"Nomer","Id Pengguna","Nama Pengguna","Password","Jenis Akses"};
            DefaultTableModel modeltb_user;
            modeltb_user = new DefaultTableModel(null, baris);
            tabel_usr.setModel(modeltb_user);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT * FROM tb_user WHERE jenis_akses = 'Admin' ";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_usr.getColumnModel().getColumn(0).setPreferredWidth(5);
                iduser=hasil.getString("id_user");
                nmusr=hasil.getString("nama_user");
                pwduser=hasil.getString("password");
                jnsaksesuser=hasil.getString("jenis_akses");
                String data[]={nmrtostr,iduser,nmusr,pwduser,jnsaksesuser};   
                modeltb_user.addRow(data);
            }  
            auto_id();
            jtxt_nmauser.setText("");
            jtxt_cariuser.setText("");
            jtxt_paswrduser.setText("");
            jcb_pilihakses.setSelectedItem("Silahkan Pilih");
            tomboledit(false);
            tombolhapus(false);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
    public void set_sembunyiakses(){
    if(jcb_pilihakses.getSelectedItem()=="Pemilik")
    {
        tomboledit(false);
        tombolhapus(false);
        tomboltambah(false);
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
     public void tomboltambah(boolean a){
         //untuk mengatur bisa digunakan/tidak digunakan tombol
       jbttn_tambah.setEnabled(a);
    }
    public void tambah_pengguna(){
        //untuk menambahkan data ke tabel pengguna/user
        String id_usr = jtxt_iduser.getText();
        String nmusr = jtxt_nmauser.getText();
        String paswd = jtxt_paswrduser.getText();
       // String ntlp = jcb_pilihakses.getSelectedItem();
        int cek_id = 0;
        int konfirmasi_tambah = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menambahkan data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
      
      if(konfirmasi_tambah==0)
      {
        if(!"".equals(id_usr) && !"".equals(nmusr) && !"".equals(paswd) 
               && !"Silahkan Pilih".equals(jcb_pilihakses.getSelectedItem())){

             try {
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql ="SELECT COUNT(id_user) as count FROM tb_user WHERE id_user='"+id_usr+"'";
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
                        String exec_sql = "insert into tb_user(id_user, nama_user, password, jenis_akses)VALUES('" + id_usr + "', '" + nmusr 
                                + "', '" + paswd + "', '" + jcb_pilihakses.getSelectedItem()+ "')";
                        sttment.executeUpdate(exec_sql);
                        JOptionPane.showMessageDialog(null, "Berhasil menyimpan data.");
                        jtxt_nmauser.setText("");
                        jtxt_paswrduser.setText("");
                        jcb_pilihakses.setSelectedItem("Silahkan Pilih");
                        lihat_pengguna();
                        auto_id();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    }
             }else{
                 JOptionPane.showMessageDialog(null, "Data Pengguna ini sudah pernah disimpan.", 
                 "Gagal Disimpan", JOptionPane.ERROR_MESSAGE);
             }
                    
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat data yang masih kosong.");
        }
      }
    }
    
    public void hapus_pengguna(){
        //untuk menghapus data yang berasal dari tabel user
        int konfirmasi_hapus = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi_hapus==0) {
            try {
                String id_usr = jtxt_iduser.getText();
                String nmusr = jtxt_nmauser.getText();
                String paswd = jtxt_paswrduser.getText();
               // String ntlp = jtxt_notlpsupp.getText();
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql = "DELETE FROM tb_user WHERE id_suplier = '" + id_usr + "'";
                sttment.executeUpdate(exec_sql);
                JOptionPane.showMessageDialog(null, "Berhasil menghapus data.");
                lihat_pengguna();
                auto_id();
                jtxt_nmauser.setText("");
                jtxt_paswrduser.setText("");
                jcb_pilihakses.setSelectedItem("Silahkan Pilih");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
        }
    }
    
    public void edit_pengguna(){
        //untuk mengganti data yang berasal dari tabel user/pengguna
        String id_usr = jtxt_iduser.getText();
        String nmusr = jtxt_nmauser.getText();
        String paswd = jtxt_paswrduser.getText();
        //String ntlp = jtxt_notlpsupp.getText();
        int c_kode = 0;
        int konfirmasi_edit = JOptionPane.showConfirmDialog(null, "Anda yakin ingin mengganti data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
      
      if(konfirmasi_edit==0)
      {
        if(!"".equals(id_usr) && !"".equals(nmusr) && !"".equals(paswd) && 
                !"Silahkan Pilih".equals(jcb_pilihakses.getSelectedItem())
                && !"Pemilik".equals(jcb_pilihakses.getSelectedItem())){
                    try {
                        Connection con = koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        String exec_sql = "UPDATE tb_user SET nama_user='"+nmusr+"' "
                                + ", password='"+paswd+"' "
                                + ", jenis_akses='"+jcb_pilihakses.getSelectedItem()+"' WHERE id_user ='"+id_usr+"'";
                        sttment.executeUpdate(exec_sql);
                        JOptionPane.showMessageDialog(null, "Berhasil menyimpan perubahan data.");
                        jtxt_nmauser.setText("");
                        jtxt_paswrduser.setText("");
                        jcb_pilihakses.setSelectedItem("Silahkan Pilih");
                        lihat_pengguna();
                        auto_id();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    }
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat data yang masih kosong.");
        }
      }
    }
    
     public void pencarian_pengguna(){
         //untuk menampilkan data yang berasal dari tabel pengguna/user lebih spesifik
        
        String idusr, nmusr, nmrtostr, paswd, jns_akses ;
        int nmr=0;
        String cari =jtxt_cariuser.getText();
        try {
            Object [] baris = {"Nomer","Id Pengguna","Nama Pengguna","Password","Jenis Akses"};
            DefaultTableModel modeltb_user;
            modeltb_user = new DefaultTableModel(null, baris);
            tabel_usr.setModel(modeltb_user);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "Select * from tb_user where id_user like '" + cari + "'" +
            "or nama_user like '" + cari + "' AND jenis_akses like 'Admin' ";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            if(hasil.next()){
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_usr.getColumnModel().getColumn(0).setPreferredWidth(5);
                idusr=hasil.getString("id_user");
                nmusr=hasil.getString("nama_user");
                paswd=hasil.getString("password");
                jns_akses=hasil.getString("jenis_akses");
                String data[]={nmrtostr,idusr,nmusr,paswd,jns_akses};   
                modeltb_user.addRow(data);
                jtxt_nmauser.setText("");
                jtxt_cariuser.setText("");
                jtxt_paswrduser.setText("");
                jcb_pilihakses.setSelectedItem("Silahkan Pilih");
           }else{
            JOptionPane.showMessageDialog(null, "Data pengguna tidak ditemukan.");
            lihat_pengguna();
            }
            jtxt_cariuser.setText("");
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
        jLabel9 = new javax.swing.JLabel();
        jtxt_iduser = new javax.swing.JTextField();
        jtxt_nmauser = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtxt_cariuser = new javax.swing.JTextField();
        jbttn_cari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_usr = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jbttn_tambah = new javax.swing.JButton();
        jbttn_edit = new javax.swing.JButton();
        jbttn_refresh = new javax.swing.JButton();
        jbttn_hapus = new javax.swing.JButton();
        jbttn_kembali = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jcb_pilihakses = new javax.swing.JComboBox<>();
        jtxt_paswrduser = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(168, 81));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 27)); // NOI18N
        jLabel8.setText("Data Pengguna");

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

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("ID User");

        jtxt_iduser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_iduser.setEnabled(false);
        jtxt_iduser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_iduserActionPerformed(evt);
            }
        });

        jtxt_nmauser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_nmauser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_nmauserActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Nama User");

        jtxt_cariuser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_cariuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_cariuserActionPerformed(evt);
            }
        });

        jbttn_cari.setIcon(new javax.swing.ImageIcon("E:\\sinau java\\NetBeans Projects\\sistem_persediaan_barang\\src\\main_sisteme\\image\\icons8-search-32.png")); // NOI18N
        jbttn_cari.setText("Cari");
        jbttn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_cariActionPerformed(evt);
            }
        });

        tabel_usr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomer", "ID User", "Nama User", "Password", "Jenis Akses"
            }
        ));
        tabel_usr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_usrMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_usr);

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

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Password");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Jenis Akses");

        jcb_pilihakses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Silahkan Pilih", "Admin", "Pemilik" }));

        jtxt_paswrduser.setText("jPasswordField1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxt_nmauser, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(jtxt_iduser, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(jcb_pilihakses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtxt_paswrduser))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jtxt_cariuser, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jbttn_cari))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jtxt_iduser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jtxt_nmauser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jtxt_paswrduser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jcb_pilihakses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxt_cariuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbttn_cari))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxt_iduserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_iduserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_iduserActionPerformed

    private void jtxt_nmauserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_nmauserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_nmauserActionPerformed

    private void jtxt_cariuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_cariuserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_cariuserActionPerformed

    private void jbttn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_editActionPerformed
        edit_pengguna();
    }//GEN-LAST:event_jbttn_editActionPerformed

    private void jbttn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_refreshActionPerformed
        lihat_pengguna();
        tomboledit(false);
        tombolhapus(false);
        tomboltambah(true);
        jtxt_paswrduser.setEnabled(true);
    }//GEN-LAST:event_jbttn_refreshActionPerformed

    private void jbttn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_hapusActionPerformed
        hapus_pengguna();
    }//GEN-LAST:event_jbttn_hapusActionPerformed

    private void jbttn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_kembaliActionPerformed
        this.setVisible(false);
        //        new MenuHome().setVisible(true);
    }//GEN-LAST:event_jbttn_kembaliActionPerformed

    private void jbttn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_tambahActionPerformed
        tambah_pengguna();
    }//GEN-LAST:event_jbttn_tambahActionPerformed

    private void tabel_usrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_usrMouseClicked
        DefaultTableModel dt_tbusr= (DefaultTableModel)tabel_usr.getModel();
        int selecttedrow = tabel_usr.getSelectedRow();
//        jTextField2.setTextdt_tbkat.getValueAt(selecttedrow, 0).toString());
        jtxt_iduser.setText(dt_tbusr.getValueAt(selecttedrow, 1).toString());
        jtxt_nmauser.setText(dt_tbusr.getValueAt(selecttedrow, 2).toString());
        jtxt_paswrduser.setText(dt_tbusr.getValueAt(selecttedrow, 3).toString());
        jcb_pilihakses.setSelectedItem(dt_tbusr.getValueAt(selecttedrow, 4).toString());
        tomboledit(true);
        tombolhapus(true);
    }//GEN-LAST:event_tabel_usrMouseClicked

    private void jbttn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_cariActionPerformed
        pencarian_pengguna();
//        tomboledit(false);
//        tombolhapus(false);
//        tomboltambah(false);
    }//GEN-LAST:event_jbttn_cariActionPerformed

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
            java.util.logging.Logger.getLogger(FormPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPengguna().setVisible(true);
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
    private javax.swing.JComboBox<String> jcb_pilihakses;
    private javax.swing.JTextField jtxt_cariuser;
    private javax.swing.JTextField jtxt_iduser;
    private javax.swing.JTextField jtxt_nmauser;
    private javax.swing.JPasswordField jtxt_paswrduser;
    private javax.swing.JTable tabel_usr;
    // End of variables declaration//GEN-END:variables
}
