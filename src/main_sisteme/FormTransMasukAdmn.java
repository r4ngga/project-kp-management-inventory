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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Rangga
 */
public class FormTransMasukAdmn extends javax.swing.JFrame {

    /**
     * Creates new form FormTransMasukAdmn
     */
    
    main_sisteme.koneksi koneksinya = new main_sisteme.koneksi();
    main_sisteme.SesiPemilik PemilikSession = new main_sisteme.SesiPemilik();
    main_sisteme.SesiAdmin AdminSession = new main_sisteme.SesiAdmin();
    public FormTransMasukAdmn() {
        initComponents();
        pilsupplier();
        jtxt_idsupp.hide();
        jtxt_baristrpilih.hide();
        tombolkurang(false);
        tomboledit(false);
        set_namauser();
        hitungtotalpembeliansemua();
    }
    
    public void pilsupplier(){
    String nmsupp;
    int nmr=0;
    try {
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT * FROM tb_suplier";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmsupp=hasil.getString("nama_suplier");
                jcb_pilsupp.addItem(nmsupp);
            }  
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
    
    public void penomorenauto(){
    int nomerawal = 1;
    int x = 1;
    int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menambah data transaksi?",
            "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
            ;
//            int nextnomer = Integer.parseInt(jtxt_nomer.getText());
//            if(nextnomer>nomerawal)
//            {
//                nomerawal++;
//                jtxt_nomer.setText(Integer.toString(nomerawal));
//            }else{
//                do
//                {
//                    nomerawal++;
//                    jtxt_nomer.setText(Integer.toString(nomerawal));
//                }while(konfirmasi<x);
//            }
        }

    }
    
    public void set_namauser(){
     jlbl_nmuser.setText(AdminSession.GetU_nama_user());
     jlbl_setidusernya.setText(AdminSession.GetU_id_user());
    }
    
    public void tambahbarang(){
     int nmr = 1;
     String nofaktur = jtxt_nofktur.getText();
     String idbrgnya = jtxt_idbrg.getText();
     String nmbrgnya = jlbl_nmbrg.getText();
     int hrgbrg = Integer.valueOf(jtxt_hrgbelibrg.getText());
     int jmlhbrg = Integer.valueOf(jtxt_jmlh.getText());
     int totalbrgnya = Integer.valueOf(jtxt_total.getText());
      
        if(!"".equals(idbrgnya) && !"".equals(nmbrgnya) && !"".equals(jmlhbrg) && !"".equals(hrgbrg)
           && !"".equals(totalbrgnya) && !"".equals(nofaktur)){
                //untuk menambahkan barang ke tabel sementara
                    Object[] row = { idbrgnya, nmbrgnya, hrgbrg, jmlhbrg, totalbrgnya };
                    DefaultTableModel model = (DefaultTableModel) tabel_brgmsk.getModel();
                    model.addRow(row);
                jtxt_idbrg.setText("");
                jlbl_nmbrg.setText("-");
                jtxt_hrgbelibrg.setText("");
                jtxt_jmlh.setText("");
                jtxt_total.setText("");         
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
        }
    }
    
    public void kurangibarang(){
     int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
            String baris= Integer.toString(tabel_brgmsk.getSelectedRow());
            jtxt_baristrpilih.setText(baris);
            DefaultTableModel model = (DefaultTableModel) tabel_brgmsk.getModel();
            int row = Integer.parseInt(jtxt_baristrpilih.getText());
            model.removeRow(row);
        }
    }
    
    public void caribarang(){
        String idbrgnya = jtxt_idbrg.getText();
        String nmbrgnya = jlbl_nmbrg.getText();
        String hrgbrg = jtxt_hrgbelibrg.getText();
        String cari =jtxt_idbrg.getText();
        if(!idbrgnya.equals("")){

                //--------- Cek In jtable
            int idygsama = 0;
            DefaultTableModel model = (DefaultTableModel) tabel_brgmsk.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                if(idbrgnya.equals(tabel_brgmsk.getModel().getValueAt(i, 1).toString())){
                    idygsama = 1;
                }
            }
            
            
            if(idygsama == 0){
                try {
                   Connection con = koneksi.membukakoneksi();
                   Statement sttment = con.createStatement();
                   String exec_sql = "Select nama_barang, harga_beli from tb_barang where id_barang like '" + cari + "'" +
                   "or nama_barang like '%" + cari + "%' ";
                  ResultSet hasil = sttment.executeQuery(exec_sql);
                    if(hasil.next()){
                        idbrgnya=hasil.getString("nama_barang");
                        jlbl_nmbrg.setText(idbrgnya);
                        hrgbrg=hasil.getString("harga_beli");
                        jtxt_hrgbelibrg.setText(hrgbrg);
                    }else{
                        JOptionPane.showMessageDialog(null, "Barang tidak ditemukan / belum ada di database.");
                         jtxt_idbrg.setText("");
                         jlbl_nmbrg.setText("-");
                         jtxt_hrgbelibrg.setText("");
                        jtxt_jmlh.setText("");
                        jtxt_total.setText("");;
                    }
                    //konek.closekoneksi();
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
            jtxt_hrgbelibrg.setText("");
           
        }
    }
    
    public void namaheadertabel(){
    Object [] baris = {"Id Barang","Nama Barang", "Harga Beli",
                "Jumlah","Total"};
    DefaultTableModel modeltbbrg;
    modeltbbrg = new DefaultTableModel(null, baris);
    tabel_brgmsk.setModel(modeltbbrg);
    }
    
    public void hitungtotalpembeliansemua(){
     int rowsCount = tabel_brgmsk.getRowCount();
        int totalnya = 0;
        for(int i = 0; i < rowsCount; i++){
            totalnya = totalnya+Integer.parseInt(tabel_brgmsk.getValueAt(i, 4).toString());
        }
        set_pembeliantotal.setText(Integer.toString(totalnya));
    }
    
    public void hitunghargajumlah(){
//    double jmlhbrg = Double.parseDouble(String.valueOf(jtxt_jmlh.getText()));
//    double hrgbrg  = Double.parseDouble(jtxt_hrgbelibrg.getText());
    int jmlhbrg = Integer.parseInt(String.valueOf(jtxt_jmlh.getText()));
    int hrgbrg  = Integer.parseInt(jtxt_hrgbelibrg.getText());
    int total   = 0;
    if(!"".equals(jmlhbrg) && !"".equals(hrgbrg) )
    {
        total = (hrgbrg * jmlhbrg);
        String hasil = Integer.toString(total);
        jtxt_total.setText(hasil);
        
    }else{
         JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
    }
//    Integer.valueOf(jtxt_total.setText(total));
    
    }
    
    
    public void tombolkurang(boolean a){
        jbttn_kurangi.setEnabled(a);
    }
    
    public void tomboledit(boolean a){
      // jbttn_edit.setEnabled(a);
    }
    
    public void kosongkantabel(){
        DefaultTableModel model = (DefaultTableModel) tabel_brgmsk.getModel();
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
    
     public void simpanbarangmasuk(){
     String tglmasuk = jdt_tglbrgmsk.getDateFormatString();
     String setdatenya = "yyyy-MM-dd";
     SimpleDateFormat formatdatenya = new SimpleDateFormat(setdatenya);
     String dateinputnya = String.valueOf(formatdatenya.format(jdt_tglbrgmsk.getDate()));
//      String date = new SimpleDateFormat("yyyy-MM-dd")
//                             .format(new Date(request.getParameter("date")));
     //String row_idpelanggan = txtid_pelanggan.getText();
     String idusernya = jlbl_setidusernya.getText();
     String nmusernya = jlbl_nmuser.getText();
     String idsupplier = jtxt_idsupp.getText();
     String nofaktur = jtxt_nofktur.getText();
     int hasiltotalhrgbeli = Integer.parseInt(set_pembeliantotal.getText()) ;
     String idbrg, kode, id_brgmsk;
     Integer stok, tidak_ditemukan, totalhrgabeli, kosong = 0;
     String totalharga,jumlah,hrgbli;
        
     DefaultTableModel model = (DefaultTableModel) tabel_brgmsk.getModel();
     int rowCount = model.getRowCount();
         if(!"".equals(tglmasuk) && !"".equals(idusernya) && !"".equals(nmusernya) 
         && !"".equals(nofaktur) && !"".equals(idsupplier)){
                //------- Memasukan pada tabel transaksi lihat [tb_barang_] dan mengeluarkan id terakhir
            try {
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql = "INSERT INTO tb_brg_msk(no_faktur, id_suplier, id_user, tgl_masuk, hasil_totalhrgbeli)VALUES('"
                + nofaktur + "', '" +  idsupplier+ "', '" + idusernya  + "', '"+dateinputnya+"', '" + hasiltotalhrgbeli + "')"; 
                sttment.executeUpdate(exec_sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
            try {
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql ="SELECT MAX(no_faktur) as max FROM tb_brg_msk";
                ResultSet hasil = sttment.executeQuery(exec_sql); 
                while(hasil.next()) 
                {
                   id_brgmsk = hasil.getString("max");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }          
            for (int i = 0; i < rowCount; i++) {
                tidak_ditemukan = 0;
                stok = 0;
                idbrg = (tabel_brgmsk.getModel().getValueAt(i, 0).toString());
                hrgbli = (tabel_brgmsk.getModel().getValueAt(i, 2).toString());
                jumlah = (tabel_brgmsk.getModel().getValueAt(i, 3).toString());
                totalharga = (tabel_brgmsk.getModel().getValueAt(i, 4).toString());
                    //------- Menjumlahkan stok dengan data jumlah
                try {
                    Connection con = koneksi.membukakoneksi();
                    Statement sttment = con.createStatement();
                    String exec_sql = "SELECT stok FROM tb_barang WHERE id_barang = '" + idbrg + "'";
                    ResultSet hasil = sttment.executeQuery(exec_sql);
                   // sttment.executeUpdate(exec_sql);
                   while(hasil.next())
                   {
                       if (hasil.getRow() == 1){
                        stok = Integer.parseInt(jumlah) + hasil.getInt("stok");
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
                        sttment.executeUpdate("INSERT INTO tb_detail_brgmsk(id_barang, no_faktur, "
                          + "harga_beli, jumlah_brgmsk, total_harga_beli) VALUES ('" 
                          + idbrg + "', '" + nofaktur+ "', '" + hrgbli + "', '" + Integer.parseInt(jumlah) + "', '" 
                          + Integer.parseInt(totalharga)+ "')");
                        kosong = 1;;
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                }else{
//                    JOptionPane.showMessageDialog(null, "Sistem tidak menemukan barang dengan Id Barang = " + idbrg + " Gagal Disimpan");
                }             
                    //------- Opsi jika terdapat barang yang belum satupun di masukan
                if(kosong == 0){
                    try {
                        Connection con = koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        sttment.executeUpdate("DELETE FROM  tb_brg_msk WHERE no_faktur = '" + nofaktur + "'");
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
         jdt_tglbrgmsk.setDateFormatString("");
         jtxt_nofktur.setText("");
         jtxt_baristrpilih.setText("");
         jtxt_idbrg.setText("");
         jtxt_idsupp.setText("");
         jtxt_jmlh.setText("");
         jtxt_hrgbelibrg.setText("");
         jtxt_total.setText("");
         set_pembeliantotal.setText("-");
         jcb_pilsupp.setSelectedItem("Silahkan Pilih");
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
        jbttn_cari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_brgmsk = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jtxt_jmlh = new javax.swing.JTextField();
        jtxt_baristrpilih = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jlbl_nmuser = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jbttn_clear = new javax.swing.JButton();
        jbttn_simpan = new javax.swing.JButton();
        jbttn_kurangi = new javax.swing.JButton();
        jbttn_tmbh = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jtxt_nofktur = new javax.swing.JTextField();
        jcb_pilsupp = new javax.swing.JComboBox<>();
        jtxt_hrgbelibrg = new javax.swing.JTextField();
        jdt_tglbrgmsk = new com.toedter.calendar.JDateChooser();
        jtxt_idsupp = new javax.swing.JTextField();
        jbttn_kembali = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jlbl_setidusernya = new javax.swing.JLabel();
        jlbl_idusernya = new javax.swing.JLabel();
        set_pembeliantotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jlbl_idbrg = new javax.swing.JLabel();
        jlbl_nmbrg = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_setbrg = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(790, 443));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(168, 81));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 23)); // NOI18N
        jLabel8.setText("Transaksi Barang Masuk");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(576, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("No Faktur");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

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
        jPanel2.add(jtxt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 250, 80, -1));

        jtxt_idbrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_idbrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxt_idbrgFocusLost(evt);
            }
        });
        jtxt_idbrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_idbrgActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_idbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 140, -1));

        jbttn_cari.setIcon(new javax.swing.ImageIcon("E:\\sinau java\\NetBeans Projects\\sistem_persediaan_barang\\src\\main_sisteme\\image\\icons8-search-32.png")); // NOI18N
        jbttn_cari.setText("Cari");
        jbttn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_cariActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, -1, -1));

        tabel_brgmsk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "Nama Barang", "Harga Belil", "Jumlah", "Total"
            }
        ));
        tabel_brgmsk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_brgmskMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_brgmsk);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 750, 116));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Total");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 230, -1, -1));

        jtxt_jmlh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_jmlh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_jmlhActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_jmlh, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 250, 70, -1));

        jtxt_baristrpilih.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_baristrpilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_baristrpilihActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_baristrpilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 430, 40, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("ID Barang");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, -1, -1));

        jlbl_nmuser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_nmuser.setText("-");
        jPanel2.add(jlbl_nmuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 100, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Nama Barang");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Jumlah");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 230, -1, -1));

        jbttn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-trash-32.png"))); // NOI18N
        jbttn_clear.setText("Hapus Semua");
        jbttn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_clearActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, 120, -1));

        jbttn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-save-all-32.png"))); // NOI18N
        jbttn_simpan.setText("Simpan");
        jbttn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_simpanActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, -1, -1));

        jbttn_kurangi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbttn_kurangi.setText("-");
        jbttn_kurangi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_kurangiActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_kurangi, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 250, 40, -1));

        jbttn_tmbh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbttn_tmbh.setText("+");
        jbttn_tmbh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_tmbhActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_tmbh, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 40, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Harga Beli");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 230, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Nama Supplier");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("User saat ini");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jtxt_nofktur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_nofktur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtxt_nofkturMouseReleased(evt);
            }
        });
        jtxt_nofktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_nofkturActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_nofktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 143, -1));

        jcb_pilsupp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Silahkan Pilih" }));
        jcb_pilsupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_pilsuppActionPerformed(evt);
            }
        });
        jPanel2.add(jcb_pilsupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 140, -1));

        jtxt_hrgbelibrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_hrgbelibrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_hrgbelibrgActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_hrgbelibrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 250, 90, -1));
        jPanel2.add(jdt_tglbrgmsk, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 140, -1));

        jtxt_idsupp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_idsupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_idsuppActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_idsupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 40, -1));

        jbttn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-back-arrow-24.png"))); // NOI18N
        jbttn_kembali.setText("Kembali");
        jbttn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_kembaliActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 430, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("Tanggal Barang Masuk");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, -1, -1));

        jlbl_setidusernya.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_setidusernya.setText("-");
        jPanel2.add(jlbl_setidusernya, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 70, -1));

        jlbl_idusernya.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_idusernya.setText("ID User");
        jPanel2.add(jlbl_idusernya, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, -1, -1));

        set_pembeliantotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        set_pembeliantotal.setText("-");
        jPanel2.add(set_pembeliantotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 400, 100, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Pembelian Total");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, -1, -1));

        jlbl_idbrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_idbrg.setText("-");
        jPanel2.add(jlbl_idbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 100, -1));

        jlbl_nmbrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_nmbrg.setText("-");
        jPanel2.add(jlbl_nmbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 180, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Pencarian");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 60, 20));

        tabel_setbrg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "Nama Barang", "Harga Beli", "Stok"
            }
        ));
        tabel_setbrg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_setbrgMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_setbrg);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 420, 100));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 67, 830, 480));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxt_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_totalMouseClicked
        hitunghargajumlah();
        tombolkurang(true);
    }//GEN-LAST:event_jtxt_totalMouseClicked

    private void jtxt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_totalActionPerformed

    private void jtxt_idbrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxt_idbrgFocusLost
        
    }//GEN-LAST:event_jtxt_idbrgFocusLost

    private void jtxt_idbrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_idbrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_idbrgActionPerformed

    private void jbttn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_cariActionPerformed
        caribarang();
        jtxt_jmlh.setText("");
        jtxt_total.setText("");
    }//GEN-LAST:event_jbttn_cariActionPerformed

    private void tabel_brgmskMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_brgmskMouseClicked
        DefaultTableModel dt_tbtransmsk= (DefaultTableModel)tabel_brgmsk.getModel();
        int selecttedrow = tabel_brgmsk.getSelectedRow();
        //String baris= Integer.toString(tabel_brgmsk.getSelectedRow());
        //            String baris_str = jtxt_baristrpilih.getText();
        //            int baris = Integer.parseInt(baris_str);
        jtxt_baristrpilih.setText(Integer.toString(selecttedrow) );
        //        jTextField2.setTextdt_tbkat.getValueAt(selecttedrow, 0).toString());
        //        jtxt_baristrpilih.setText(dt_tbtransmsk.getValueAt(selecttedrow, 0).toString());
        jtxt_idbrg.setText(dt_tbtransmsk.getValueAt(selecttedrow, 0).toString());
        jlbl_idbrg.setText(dt_tbtransmsk.getValueAt(selecttedrow, 1).toString());
        jtxt_hrgbelibrg.setText(dt_tbtransmsk.getValueAt(selecttedrow, 2).toString());
        jtxt_jmlh.setText(dt_tbtransmsk.getValueAt(selecttedrow, 3).toString());
        jtxt_total.setText(dt_tbtransmsk.getValueAt(selecttedrow, 4).toString());
    }//GEN-LAST:event_tabel_brgmskMouseClicked

    private void jtxt_jmlhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_jmlhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_jmlhActionPerformed

    private void jtxt_baristrpilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_baristrpilihActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_baristrpilihActionPerformed

    private void jbttn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_clearActionPerformed
        //        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //        Calendar cal = Calendar.getInstance();
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus semua baris, "
            + "dan semua inputan ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
            kosongkantabel();
            kosongtabelbantuan();
            jtxt_baristrpilih.setText("");
            jtxt_hrgbelibrg.setText("");
            jtxt_idbrg.setText("");
            jtxt_idsupp.setText("");
            jcb_pilsupp.setSelectedItem("Silahkan pilih");
            jtxt_jmlh.setText("");
            jtxt_total.setText("");
            jtxt_nofktur.setText("");
            jdt_tglbrgmsk.setCalendar(null);
            jdt_tglbrgmsk.cleanup();

        }
        //        new MenuHome().setVisible(true);
    }//GEN-LAST:event_jbttn_clearActionPerformed

    private void jbttn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_simpanActionPerformed
        simpanbarangmasuk();
        jtxt_baristrpilih.setText("");
        jtxt_hrgbelibrg.setText("");
        jtxt_idbrg.setText("");
        jtxt_idsupp.setText("");
        jtxt_jmlh.setText("");
        jtxt_total.setText("");
        jtxt_nofktur.setText("");
        jdt_tglbrgmsk.setCalendar(null);
        jcb_pilsupp.setSelectedItem("Silahkan pilih");
        kosongtabelbantuan();
    }//GEN-LAST:event_jbttn_simpanActionPerformed

    private void jbttn_kurangiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_kurangiActionPerformed
        // TODO add your handling code here:
        kurangibarang();
    }//GEN-LAST:event_jbttn_kurangiActionPerformed

    private void jbttn_tmbhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_tmbhActionPerformed
        tambahbarang();
        tombolkurang(true);
        hitungtotalpembeliansemua();
    }//GEN-LAST:event_jbttn_tmbhActionPerformed

    private void jtxt_nofkturMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_nofkturMouseReleased
        // TODO add your handling code here:
        String faktursama = jtxt_nofktur.getText();
        try {
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT no_faktur FROM tb_brg_msk WHERE no_faktur = '"+faktursama+"'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            if(hasil.next())
            {
                JOptionPane.showMessageDialog(null, "No faktur sudah ada di database");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }//GEN-LAST:event_jtxt_nofkturMouseReleased

    private void jtxt_nofkturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_nofkturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_nofkturActionPerformed

    private void jcb_pilsuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_pilsuppActionPerformed
        String nmsupp = jcb_pilsupp.getSelectedItem().toString();
        if(!nmsupp.equals("")){
            try {
                Connection con = koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql = "SELECT id_suplier FROM tb_suplier WHERE nama_suplier='"+nmsupp+"'";
                ResultSet hasil = sttment.executeQuery(exec_sql);
                if(hasil.next()){
                    jtxt_idsupp.setText(hasil.getString("id_suplier"));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
        }else{
            jtxt_idsupp.setText("");
        }
        if(nmsupp=="Silahkan Pilih"){
            jtxt_idsupp.setText("");
        }
    }//GEN-LAST:event_jcb_pilsuppActionPerformed

    private void jtxt_hrgbelibrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_hrgbelibrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_hrgbelibrgActionPerformed

    private void jtxt_idsuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_idsuppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_idsuppActionPerformed

    private void jbttn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_kembaliActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jbttn_kembaliActionPerformed

    private void tabel_setbrgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_setbrgMouseClicked
        // TODO add your handling code here:
        DefaultTableModel dt_setbrg= (DefaultTableModel)tabel_setbrg.getModel();
        int selecttedrow = tabel_setbrg.getSelectedRow();
        //String baris= Integer.toString(tabel_brgmsk.getSelectedRow());
        //            String baris_str = jtxt_baristrpilih.getText();
        //            int baris = Integer.parseInt(baris_str);
        jtxt_baristrpilih.setText(Integer.toString(selecttedrow) );
        //        jTextField2.setTextdt_tbkat.getValueAt(selecttedrow, 0).toString());
        //        jtxt_baristrpilih.setText(dt_tbtransmsk.getValueAt(selecttedrow, 0).toString());
        jlbl_idbrg.setText(dt_setbrg.getValueAt(selecttedrow, 0).toString());
        jlbl_nmbrg.setText(dt_setbrg.getValueAt(selecttedrow, 1).toString());
        jtxt_hrgbelibrg.setText(dt_setbrg.getValueAt(selecttedrow, 2).toString());
        //       jtxt_s.setText(dt_setbrg.getValueAt(selecttedrow, 3).toString());
        //        jtxt_setstokbrg.setText(dt_setbrg.getValueAt(selecttedrow, 4).toString());
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
            java.util.logging.Logger.getLogger(FormTransMasukAdmn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTransMasukAdmn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTransMasukAdmn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTransMasukAdmn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTransMasukAdmn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JButton jbttn_cari;
    private javax.swing.JButton jbttn_clear;
    private javax.swing.JButton jbttn_kembali;
    private javax.swing.JButton jbttn_kurangi;
    private javax.swing.JButton jbttn_simpan;
    private javax.swing.JButton jbttn_tmbh;
    private javax.swing.JComboBox<String> jcb_pilsupp;
    private com.toedter.calendar.JDateChooser jdt_tglbrgmsk;
    private javax.swing.JLabel jlbl_idbrg;
    private javax.swing.JLabel jlbl_idusernya;
    private javax.swing.JLabel jlbl_nmbrg;
    private javax.swing.JLabel jlbl_nmuser;
    private javax.swing.JLabel jlbl_setidusernya;
    private javax.swing.JTextField jtxt_baristrpilih;
    private javax.swing.JTextField jtxt_hrgbelibrg;
    private javax.swing.JTextField jtxt_idbrg;
    private javax.swing.JTextField jtxt_idsupp;
    private javax.swing.JTextField jtxt_jmlh;
    private javax.swing.JTextField jtxt_nofktur;
    private javax.swing.JTextField jtxt_total;
    private javax.swing.JLabel set_pembeliantotal;
    private javax.swing.JTable tabel_brgmsk;
    private javax.swing.JTable tabel_setbrg;
    // End of variables declaration//GEN-END:variables
}
