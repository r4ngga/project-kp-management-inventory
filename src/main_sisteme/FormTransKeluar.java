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
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author Rangga
 */
public class FormTransKeluar extends javax.swing.JFrame {

    /**
     * Creates new form FormTransKeluar
     */
    //untuk memanggil kelas konesi dan sessi
    main_sisteme.koneksi koneksinya = new main_sisteme.koneksi();
    main_sisteme.SesiPemilik PemilikSession = new main_sisteme.SesiPemilik();
    main_sisteme.SesiAdmin AdminSession = new main_sisteme.SesiAdmin();
    
    public FormTransKeluar() {
        initComponents();
        set_namauser();
        tombolkurang(false);
        hitungtotalpengeluaransemua();
        jtxt_baristrpilih.hide();
    }
    
public void tambahbarang(){
int nmr = 1;
String nomernota = jtxt_nomernota.getText();
String idbrgnya = jlbl_idbrg.getText();
String nmbrgnya = jlbl_nmbrg.getText();
int hrgbrgbeli = Integer.valueOf(jtxt_hrgbeli.getText());
int hrgbrgjual = Integer.valueOf(jtxt_hrgjual.getText());
int jmlhbrg = Integer.parseInt(jtxt_jmlh.getText());
int jmlhstoktersisa = Integer.parseInt(jtxt_setstokbrg.getText());
int totalbrgnya = Integer.valueOf(jtxt_total.getText());
      
if(!"".equals(idbrgnya) && !"".equals(nmbrgnya) && !"".equals(jmlhbrg) && !"".equals(hrgbrgbeli)
           && !"".equals(totalbrgnya) && !"".equals(nomernota) && !"".equals(hrgbrgjual)){
            if(jmlhbrg <= jmlhstoktersisa)
            {
                //untuk menambahkan barang ke tabel sementara
                Object[] row = { idbrgnya, nmbrgnya, hrgbrgbeli, hrgbrgjual, jmlhbrg, totalbrgnya };
                DefaultTableModel model = (DefaultTableModel) tabel_brgkeluar.getModel();
                model.addRow(row);
                jtxt_idbrg.setText("");
                jlbl_nmbrg.setText("-");
                jlbl_idbrg.setText("-");
                jtxt_hrgbeli.setText("");
                jtxt_jmlh.setText("");
                jtxt_total.setText(""); 
                jtxt_hrgjual.setText("");
            }else{
                JOptionPane.showMessageDialog(null, "Jumlah melebihi stok barang / stok barang sudah habis.");
            }
                
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
        }
        
    }
  
    
     public void caribarang(){
         //untuk mencari barang yang akan ditambahkan
        String idbrgnya = jtxt_idbrg.getText();
        String cari = jtxt_idbrg.getText();
          String idbrg, nmbrgnya, hrgbrgjual, hrgbrgbeli,stoktersisa;
        if(!idbrgnya.equals("")){
                //--------- Cek In jtable
            int idygsama = 0;
            DefaultTableModel model = (DefaultTableModel) tabel_brgkeluar.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                if(idbrgnya.equals(tabel_brgkeluar.getModel().getValueAt(i, 1).toString())){
                    idygsama = 1;
                }
            }
            if(idygsama == 0){
                try {
                    Object [] baris = {"ID Barang","Nama Barang","Harga Beli","Harga Jual","Stok"};
                DefaultTableModel modeltbbrg;
                modeltbbrg = new DefaultTableModel(null, baris);
                tabel_setbrg.setModel(modeltbbrg);
                   Connection con = koneksi.membukakoneksi();
                   Statement sttment = con.createStatement();
                   String exec_sql = "Select id_barang, nama_barang"
                           + " from tb_barang where id_barang like '" + cari + "' or nama_barang like '%" + cari + "%'";
                  ResultSet hasil = sttment.executeQuery(exec_sql);
                  //ResultSet res = sttment.executeQuery(exec_sql);
                    if(hasil.next()){
                       String exec_sql2 = "Select id_barang, nama_barang, harga_jual, harga_beli, stok"
                       + " from tb_barang where id_barang like '" + cari + "' or nama_barang like '%" + cari + "%'";
                        ResultSet hasil2 = sttment.executeQuery(exec_sql2);
                       while(hasil2.next())
                       {
                         idbrg=hasil2.getString("id_barang");
                        nmbrgnya=hasil2.getString("nama_barang");
                        hrgbrgbeli=hasil2.getString("harga_beli");          
                        hrgbrgjual = hasil2.getString("harga_jual");
                        stoktersisa=hasil2.getString("stok");
                        String data[]={idbrg,nmbrgnya,hrgbrgbeli,hrgbrgjual,stoktersisa};
                        modeltbbrg.addRow(data);
                       }
                    }else{
                        JOptionPane.showMessageDialog(null, "Barang tidak ditemukan / belum ada di database.");
                         jtxt_idbrg.setText("");
                         jlbl_nmbrg.setText("-");
                         jtxt_hrgjual.setText("");
                        jtxt_jmlh.setText("");
                        jtxt_total.setText("");
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
                } 
            }else{
                JOptionPane.showMessageDialog(null, "Barang sudah pernah ditambahkan.");
               jtxt_idbrg.setText("");
            }
        }else{
            jtxt_idbrg.setText("");
            jlbl_nmbrg.setText("-");
            jtxt_hrgjual.setText("");
            jtxt_total.setText("");
            jtxt_hrgbeli.setText("");
            jtxt_setstokbrg.setText("");
            
           
        }
    }
     
     public void hitunghargajumlah(){
    int jmlhbrg = Integer.parseInt(String.valueOf(jtxt_jmlh.getText()));
    int hrgbrgjual  = Integer.parseInt(jtxt_hrgjual.getText());
    int total   = 0;
    if(!"".equals(jmlhbrg) && !"".equals(hrgbrgjual) )
    {
        total = (hrgbrgjual * jmlhbrg);
        String hasil = Integer.toString(total);
        jtxt_total.setText(hasil);
        
    }else{
         JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
    }
   }
     
      public void namaheadertabel(){
    Object [] baris = {"Id Barang","Nama Barang", "Harga Beli","Harga Jual",
                "Jumlah","Total"};
    DefaultTableModel modeltbbrg;
    modeltbbrg = new DefaultTableModel(null, baris);
    tabel_brgkeluar.setModel(modeltbbrg);
    }
     
     public void hitungtotalpengeluaransemua(){
         //untuk menghitung hasil seluruh total 
     int rowsCount = tabel_brgkeluar.getRowCount();
        int totalnya = 0;
        for(int i = 0; i < rowsCount; i++){
            totalnya = totalnya+Integer.parseInt(tabel_brgkeluar.getValueAt(i, 5).toString());
        }
        jlbl_hasilsemuatotal.setText(Integer.toString(totalnya));
    }
     
     public void kurangibarang(){
         //mengurangi barang dari tabel sementara
     int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
            String baris= Integer.toString(tabel_brgkeluar.getSelectedRow());
            jtxt_baristrpilih.setText(baris);
            DefaultTableModel model = (DefaultTableModel) tabel_brgkeluar.getModel();
            int row = Integer.parseInt(jtxt_baristrpilih.getText());
            model.removeRow(row);
        }
    }
     
     public void set_namauser(){
         //untuk set nama sessi yang dipanggil
     jlbl_nmuser.setText(PemilikSession.GetU_nama_user());
     jlbl_idusernya.setText(PemilikSession.GetU_id_user());
    }
     
     public void tombolkurang(boolean a){
         //untuk mengaktifkan fungsi tombol kurang
        jbttn_kurangi.setEnabled(a);
    }

      public void kosongkantabel(){
          //untuk menghapus isi semua tabel
        DefaultTableModel model = (DefaultTableModel) tabel_brgkeluar.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
      
      public void kosongtabelbantuan(){
      DefaultTableModel model = (DefaultTableModel) tabel_setbrg.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
      }
      
     public void simpanbarangkeluar(){
         //untuk menyimpan data transaksi keluar
     String tglmasuk = jdt_tglbrgkeluar.getDateFormatString();
     String setdatenya = "yyyy-MM-dd";
     SimpleDateFormat formatdatenya = new SimpleDateFormat(setdatenya);
     String dateinputnya = String.valueOf(formatdatenya.format(jdt_tglbrgkeluar.getDate()));
     String idusernya = jlbl_idusernya.getText();
     String nmusernya = jlbl_nmuser.getText();
     String nomernota = jtxt_nomernota.getText();
     int hasiltotalhrgjual = Integer.parseInt(jlbl_hasilsemuatotal.getText()) ;
     String idbrg, kode, id_brgkeluar;
     Integer stok, tidak_ditemukan, totalhrgajual, kosong = 0;
     String totalharga,jumlah,hrgbli,hrgjual;
        
     DefaultTableModel model = (DefaultTableModel) tabel_brgkeluar.getModel();
     int rowCount = model.getRowCount();
        
if(!"".equals(tglmasuk) && !"".equals(idusernya) && !"".equals(nmusernya) 
&& !"".equals(nomernota)){
            try {
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql = "INSERT INTO tb_brg_keluar(no_nota, id_user, tgl_keluar, hasil_totalhrgjual)VALUES('"
                + nomernota + "', '" +  idusernya+ "', '"+dateinputnya+"', '" + hasiltotalhrgjual + "')"; 
                sttment.executeUpdate(exec_sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
            try {
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql ="SELECT MAX(no_nota) as max FROM tb_brg_keluar";
                ResultSet hasil = sttment.executeQuery(exec_sql); 
                while(hasil.next()) 
                {
                   id_brgkeluar = hasil.getString("max");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
            for (int i = 0; i < rowCount; i++) {
                tidak_ditemukan = 0;
                stok = 0;
                idbrg = (tabel_brgkeluar.getModel().getValueAt(i, 0).toString());
                hrgbli = (tabel_brgkeluar.getModel().getValueAt(i, 2).toString());
                hrgjual = (tabel_brgkeluar.getModel().getValueAt(i, 3).toString());
                jumlah = (tabel_brgkeluar.getModel().getValueAt(i, 4).toString());
                totalharga = (tabel_brgkeluar.getModel().getValueAt(i, 5).toString());
                    //------- Menngurangi stok dengan data jumlah
                try {
                    Connection con = koneksi.membukakoneksi();
                    Statement sttment = con.createStatement();
                    String exec_sql = "SELECT stok FROM tb_barang WHERE id_barang = '" + idbrg + "'";
                    ResultSet hasil = sttment.executeQuery(exec_sql);
                   while(hasil.next())
                   {
                       if (hasil.getRow() == 1){
                        stok = hasil.getInt("stok") - Integer.parseInt(jumlah);
                        } else {
                           tidak_ditemukan = 1;
                        }
                   }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
                } 
            
                if(tidak_ditemukan == 0){       
                        //------- Mengupdate jumlah stok barang
                    try {
                        Connection con = koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        sttment.executeUpdate("UPDATE tb_barang SET stok='" + stok 
                        + "' WHERE id_barang = '" + idbrg + "'");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                    
                        //------- Memasukan pada table transaksi detail
                    try {
                        Connection con = koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        sttment.executeUpdate("INSERT INTO tb_detail_brgkeluar(id_barang, no_nota, "
                          + "harga_beli, harga_jual, jumlah_brgkeluar, total_harga_jual) VALUES ('" 
                          + idbrg + "', '" + nomernota+ "', '" + hrgbli + "', '" + hrgjual + "','" 
                          + Integer.parseInt(jumlah) + "', '" + Integer.parseInt(totalharga)+ "')");
                        kosong = 1;
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                }else{
//                    JOptionPane.showMessageDialog(null, "Sistem tidak menemukan barang dengan Id Barang = " + idbrg  + " Gagal Disimpan");
                }
                
                    //------- Opsi jika terdapat barang yang belum satupun di masukan
                if(kosong == 0){
                    try {
                        Connection con = koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        sttment.executeUpdate("DELETE FROM tb_brg_keluar WHERE no_nota = '" + nomernota + "'");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                }else{
                }
            }
            JOptionPane.showMessageDialog(null, "Berhasil menyimpan data transaksi");
            
//            try {
//                HashMap hash = new HashMap();
//                hash.put("nm_supplier", cmbid_pelanggan.getSelectedItem().toString());
//                hash.put("id", id_barang_masuk);
//                        
//                File file = new File("src/report/report_transaksiMasuk.jrxml");
//                JasperDesign jasperDesign = JRXmlLoader.load(file);
//                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, konek.openkoneksi());
//                JasperViewer.viewReport(jasperPrint, false);
//            }catch (ClassNotFoundException | JRException e) {
//                JOptionPane.showMessageDialog(null, "Error " + e);
//            }
            //this.hide();
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
        }
         namaheadertabel();
         kosongkantabel();
         jdt_tglbrgkeluar.setDateFormatString("");
         jtxt_nomernota.setText("");
         jtxt_baristrpilih.setText("");
         jtxt_idbrg.setText("");
         jtxt_hrgbeli.setText("");
         jtxt_jmlh.setText("");
         jtxt_hrgjual.setText("");
         jtxt_total.setText("");
         jtxt_setstokbrg.setText("");
         
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
        jtxt_total = new javax.swing.JTextField();
        jtxt_idbrg = new javax.swing.JTextField();
        jbttn_caribrg = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_brgkeluar = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jtxt_jmlh = new javax.swing.JTextField();
        jtxt_hrgbeli = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jlbl_idusernya = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jbttn_kembali = new javax.swing.JButton();
        jbttn_simpan = new javax.swing.JButton();
        jbttn_kurangi = new javax.swing.JButton();
        jbttn_tambah = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jlbl_nmbrg = new javax.swing.JLabel();
        jlbl_nmuser = new javax.swing.JLabel();
        jtxt_nomernota = new javax.swing.JTextField();
        jdt_tglbrgkeluar = new com.toedter.calendar.JDateChooser();
        jtxt_baristrpilih = new javax.swing.JTextField();
        jbttn_clearralll = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jlbl_hasilsemuatotal = new javax.swing.JLabel();
        jtxt_hrgjual = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jtxt_setstokbrg = new javax.swing.JTextField();
        jlbl_idbrg = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_setbrg = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(168, 81));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Transaksi Barang Keluar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(541, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 80));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("No Nota");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jtxt_total.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtxt_totalMouseClicked(evt);
            }
        });
        jtxt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_totalActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, 90, -1));

        jtxt_idbrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_idbrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_idbrgActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_idbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 140, -1));

        jbttn_caribrg.setIcon(new javax.swing.ImageIcon("E:\\sinau java\\NetBeans Projects\\sistem_persediaan_barang\\src\\main_sisteme\\image\\icons8-search-32.png")); // NOI18N
        jbttn_caribrg.setText("Cari");
        jbttn_caribrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_caribrgActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_caribrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, -1, -1));

        tabel_brgkeluar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "Nama Barang", "Harga Beli", "Harga Jual", "Jumlah", "Total"
            }
        ));
        tabel_brgkeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_brgkeluarMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_brgkeluar);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 770, 116));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Total");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 190, -1, -1));

        jtxt_jmlh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_jmlh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_jmlhActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_jmlh, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 210, 50, -1));

        jtxt_hrgbeli.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_hrgbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_hrgbeliActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_hrgbeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 90, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("ID Barang");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, -1));

        jlbl_idusernya.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_idusernya.setText("-");
        jPanel2.add(jlbl_idusernya, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 140, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Nama Barang");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Tanggal Barang Keluar");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Jumlah");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 190, -1, -1));

        jbttn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-back-arrow-24.png"))); // NOI18N
        jbttn_kembali.setText("Kembali");
        jbttn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_kembaliActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, -1, -1));

        jbttn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-save-all-32.png"))); // NOI18N
        jbttn_simpan.setText("Simpan");
        jbttn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_simpanActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

        jbttn_kurangi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbttn_kurangi.setText("-");
        jbttn_kurangi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_kurangiActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_kurangi, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 210, 40, -1));

        jbttn_tambah.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbttn_tambah.setText("+");
        jbttn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_tambahActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 210, 40, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Harga Beli");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("User Saat ini");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("ID User");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jlbl_nmbrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_nmbrg.setText("-");
        jPanel2.add(jlbl_nmbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 150, 20));

        jlbl_nmuser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_nmuser.setText("-");
        jPanel2.add(jlbl_nmuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 140, -1));

        jtxt_nomernota.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_nomernota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtxt_nomernotaMouseReleased(evt);
            }
        });
        jtxt_nomernota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_nomernotaActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_nomernota, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 143, -1));
        jPanel2.add(jdt_tglbrgkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 160, -1));
        jPanel2.add(jtxt_baristrpilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 410, 40, -1));

        jbttn_clearralll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-trash-32.png"))); // NOI18N
        jbttn_clearralll.setText("Hapus Semua");
        jbttn_clearralll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_clearralllActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_clearralll, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, 130, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Penjualan Total");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 380, -1, -1));

        jlbl_hasilsemuatotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_hasilsemuatotal.setText("-");
        jPanel2.add(jlbl_hasilsemuatotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 380, 100, -1));

        jtxt_hrgjual.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_hrgjual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_hrgjualActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_hrgjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 210, 90, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("Harga Jual");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 190, -1, -1));
        jPanel2.add(jtxt_setstokbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 40, -1));

        jlbl_idbrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_idbrg.setText("-");
        jlbl_idbrg.setToolTipText("");
        jPanel2.add(jlbl_idbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 60, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Pencarian");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 60, 20));

        tabel_setbrg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "Nama Barang", "Harga Beli", "Harga Jual", "Stok"
            }
        ));
        tabel_setbrg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_setbrgMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_setbrg);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 100, 420, 80));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 810, 450));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxt_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_totalMouseClicked
        hitunghargajumlah();
        tombolkurang(true);
    }//GEN-LAST:event_jtxt_totalMouseClicked

    private void jtxt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_totalActionPerformed
        //        hitunghargajumlah();
    }//GEN-LAST:event_jtxt_totalActionPerformed

    private void jtxt_idbrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_idbrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_idbrgActionPerformed

    private void jbttn_caribrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_caribrgActionPerformed
        caribarang();
    }//GEN-LAST:event_jbttn_caribrgActionPerformed

    private void tabel_brgkeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_brgkeluarMouseClicked
        DefaultTableModel dt_tbtranskeluar= (DefaultTableModel)tabel_brgkeluar.getModel();
        int selecttedrow = tabel_brgkeluar.getSelectedRow();
        //String baris= Integer.toString(tabel_brgmsk.getSelectedRow());
        //            String baris_str = jtxt_baristrpilih.getText();
        //            int baris = Integer.parseInt(baris_str);
        jtxt_baristrpilih.setText(Integer.toString(selecttedrow) );
        //        jTextField2.setTextdt_tbkat.getValueAt(selecttedrow, 0).toString());
        //        jtxt_baristrpilih.setText(dt_tbtransmsk.getValueAt(selecttedrow, 0).toString());
        jtxt_idbrg.setText(dt_tbtranskeluar.getValueAt(selecttedrow, 0).toString());
        jlbl_nmbrg.setText(dt_tbtranskeluar.getValueAt(selecttedrow, 1).toString());
        jtxt_hrgbeli.setText(dt_tbtranskeluar.getValueAt(selecttedrow, 2).toString());
        jtxt_jmlh.setText(dt_tbtranskeluar.getValueAt(selecttedrow, 3).toString());
        jtxt_total.setText(dt_tbtranskeluar.getValueAt(selecttedrow, 4).toString());
    }//GEN-LAST:event_tabel_brgkeluarMouseClicked

    private void jtxt_jmlhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_jmlhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_jmlhActionPerformed

    private void jtxt_hrgbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_hrgbeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_hrgbeliActionPerformed

    private void jbttn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_kembaliActionPerformed
        this.setVisible(false);
        //        new MenuHome().setVisible(true);
    }//GEN-LAST:event_jbttn_kembaliActionPerformed

    private void jbttn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_simpanActionPerformed
        // TODO add your handling code here:
        simpanbarangkeluar();
        jtxt_idbrg.setText("");
        jlbl_nmbrg.setText("-");
        jtxt_hrgbeli.setText("");
        jtxt_jmlh.setText("");
        jtxt_total.setText("");
        jtxt_hrgjual.setText("");
        jlbl_hasilsemuatotal.setText("-");
        jtxt_baristrpilih.setText("");
        jtxt_nomernota.setText("");
        jdt_tglbrgkeluar.setCalendar(null);
        kosongtabelbantuan();
    }//GEN-LAST:event_jbttn_simpanActionPerformed

    private void jbttn_kurangiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_kurangiActionPerformed
        kurangibarang();
    }//GEN-LAST:event_jbttn_kurangiActionPerformed

    private void jbttn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_tambahActionPerformed
        tambahbarang();
        tombolkurang(true);
        hitungtotalpengeluaransemua();
    }//GEN-LAST:event_jbttn_tambahActionPerformed

    private void jtxt_nomernotaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_nomernotaMouseReleased
        // TODO add your handling code here:
        String notasama = jtxt_nomernota.getText();
        try {
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT no_nota FROM tb_brg_keluar WHERE no_nota = '"+notasama+"'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            if(hasil.next())
            {
                JOptionPane.showMessageDialog(null, "Nota sudah ada di database");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }//GEN-LAST:event_jtxt_nomernotaMouseReleased

    private void jtxt_nomernotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_nomernotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_nomernotaActionPerformed

    private void jbttn_clearralllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_clearralllActionPerformed
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus semua baris, "
            + "dan semua inputan ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
            kosongkantabel();
            jtxt_baristrpilih.setText("");
            jtxt_hrgbeli.setText("");
            jtxt_idbrg.setText("");
            //jtxt_idsupp.setText("");
            jdt_tglbrgkeluar.setCalendar(null);
            jtxt_jmlh.setText("");
            jtxt_total.setText("");
            jtxt_nomernota.setText("");
            jtxt_setstokbrg.setText("");
            kosongtabelbantuan();
        }
    }//GEN-LAST:event_jbttn_clearralllActionPerformed

    private void jtxt_hrgjualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_hrgjualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_hrgjualActionPerformed

    private void tabel_setbrgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_setbrgMouseClicked
        // TODO add your handling code here:
        DefaultTableModel dt_setbrg= (DefaultTableModel)tabel_setbrg.getModel();
        int selecttedrow = tabel_setbrg.getSelectedRow();
        jtxt_baristrpilih.setText(Integer.toString(selecttedrow) );
        jlbl_idbrg.setText(dt_setbrg.getValueAt(selecttedrow, 0).toString());
        jlbl_nmbrg.setText(dt_setbrg.getValueAt(selecttedrow, 1).toString());
        jtxt_hrgbeli.setText(dt_setbrg.getValueAt(selecttedrow, 2).toString());
        jtxt_hrgjual.setText(dt_setbrg.getValueAt(selecttedrow, 3).toString());
        jtxt_setstokbrg.setText(dt_setbrg.getValueAt(selecttedrow, 4).toString());
    }//GEN-LAST:event_tabel_setbrgMouseClicked

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
            java.util.logging.Logger.getLogger(FormTransKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTransKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTransKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTransKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTransKeluar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbttn_caribrg;
    private javax.swing.JButton jbttn_clearralll;
    private javax.swing.JButton jbttn_kembali;
    private javax.swing.JButton jbttn_kurangi;
    private javax.swing.JButton jbttn_simpan;
    private javax.swing.JButton jbttn_tambah;
    private com.toedter.calendar.JDateChooser jdt_tglbrgkeluar;
    private javax.swing.JLabel jlbl_hasilsemuatotal;
    private javax.swing.JLabel jlbl_idbrg;
    private javax.swing.JLabel jlbl_idusernya;
    private javax.swing.JLabel jlbl_nmbrg;
    private javax.swing.JLabel jlbl_nmuser;
    private javax.swing.JTextField jtxt_baristrpilih;
    private javax.swing.JTextField jtxt_hrgbeli;
    private javax.swing.JTextField jtxt_hrgjual;
    private javax.swing.JTextField jtxt_idbrg;
    private javax.swing.JTextField jtxt_jmlh;
    private javax.swing.JTextField jtxt_nomernota;
    private javax.swing.JTextField jtxt_setstokbrg;
    private javax.swing.JTextField jtxt_total;
    private javax.swing.JTable tabel_brgkeluar;
    private javax.swing.JTable tabel_setbrg;
    // End of variables declaration//GEN-END:variables
}
