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
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Rangga
 */
public class FormReturPenjualan extends javax.swing.JFrame {

    /**
     * Creates new form FormReturPenjualan
     */
    public FormReturPenjualan() {
        initComponents();
        //SetCheckBox(7, tabel_returpenjualan);
    }
    
    public void carinomernota(){
    String nonota, idbrg, nmbrg, nmrtostr, katbrg, jmlh, hrgjl, pilih, stok, nourut, tgl_keluar;
    Boolean pilbrg = false ;
    //String cari = jtxt_carinmrnota.getText();
        int nmr=0;
        String cari =jtxt_carinmrnota.getText();
        try {
            Object [] baris = {"No","Nomer Nota", "No Urut keluar","Id Barang",
                "Nama Barang","Harga Jual","Jumlah","Checklist"};
            DefaultTableModel modeltbbrg;
            modeltbbrg = new DefaultTableModel(null, baris);
            tabel_returpenjualan.setModel(modeltbbrg);
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement(); 
            String exec_sql = "SELECT tb_detail_brgkeluar.no_urut_keluar, tb_detail_brgkeluar.no_nota"
                    + ", tb_barang.id_barang, tb_barang.nama_barang, tb_detail_brgkeluar.harga_jual"
                    + ", tb_detail_brgkeluar.jumlah_brgkeluar FROM tb_detail_brgkeluar join tb_barang ON "
                    + "tb_barang.id_barang = tb_detail_brgkeluar.id_barang WHERE  "
                    + "tb_detail_brgkeluar.no_nota like '%" + cari + "%'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next()){
                String [] dataditampilkan = new String[7];
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_returpenjualan.getColumnModel().getColumn(0).setPreferredWidth(5);
                dataditampilkan[0] = nmrtostr;
                dataditampilkan[1] = hasil.getString("no_nota");
                dataditampilkan[2] = hasil.getString("no_urut_keluar");
                dataditampilkan[3] = hasil.getString("id_barang");
                dataditampilkan[4] = hasil.getString("nama_barang");
                dataditampilkan[5] = hasil.getString("harga_jual");
                dataditampilkan[6] = hasil.getString("jumlah_brgkeluar");   
                modeltbbrg.addRow(dataditampilkan);
                SetCheckBox(7, tabel_returpenjualan);
           }
//            if(!hasil.next()){
//            JOptionPane.showMessageDialog(null, "Data barang keluar tidak ditemukan.");
//            //lihat_barang();
//            }
            jtxt_carinmrnota.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
        
    }
    
    public void namaheadertabel(){
    Object [] baris = {"No","Nomer Nota", "No Urut keluar","Id Barang",
                "Nama Barang","Harga Jual","Jumlah","Checklist"};
    DefaultTableModel modeltbbrg;
    modeltbbrg = new DefaultTableModel(null, baris);
    tabel_returpenjualan.setModel(modeltbbrg);
    }
    
    public void kurangibarang(){
    int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
            String baris= Integer.toString(tabel_returpenjualan.getSelectedRow());
            jtxt_baristrpilih.setText(baris);
            DefaultTableModel model = (DefaultTableModel) tabel_returpenjualan.getModel();
            int row = Integer.parseInt(jtxt_baristrpilih.getText());
            model.removeRow(row);
        }
    }
    
     public void SetCheckBox(int kolom, JTable table)
    {
        TableColumn tc = table.getColumnModel().getColumn(kolom);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }
     
      public boolean CheckboxSelected(int row, int column, JTable table)
    {    
        return table.getValueAt(row, column) != null;                       
    }    
      
    public void simpantransaksi(){
      //untuk menyimpan data transaksi keluar
     String tglretur = jdt_tglretur.getDateFormatString();
     String setdatenya = "yyyy-MM-dd";
     SimpleDateFormat formatdatenya = new SimpleDateFormat(setdatenya);
     String dateinputnya = String.valueOf(formatdatenya.format(jdt_tglretur.getDate()));
     String kdretur = jtxt_kdretur.getText();
     String nomernota = jlbl_nonota.getText();
     String keterangan = jtxt_ketretur.getText();
     String idbrg, kode, id_brgretur;
     Integer stok, tidak_ditemukan, notfound, totalhrgajual, stokupdate ,kosong = 0;
     String totalharga,jumlah,hrgbli,hrgjual, nourutkeluar;        
     DefaultTableModel tabelmodel = (DefaultTableModel) tabel_returpenjualan.getModel();
     int rowCount = tabelmodel.getRowCount();       
if(!"".equals(tglretur) && !"".equals(keterangan) && !"".equals(kdretur))
{
 try {
      Connection conn = koneksi.membukakoneksi();
      Statement statement = conn.createStatement();
      String executed_sql = "INSERT INTO tb_retur_jual(kd_retur, no_nota, tgl_retur, keterangan)VALUES('"
      + kdretur + "', '-', '"+dateinputnya+"', '"+keterangan+"')"; 
      statement.executeUpdate(executed_sql);
     } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error " + e);} 
 //while(i<rowCount)
for(int i=0;i<rowCount;i++)
 {
  tidak_ditemukan = 0;
  stok = 0;
  stokupdate = 0;
  notfound = 0; 
  nomernota = (tabel_returpenjualan.getModel().getValueAt(i, 1).toString());
  nourutkeluar = (tabel_returpenjualan.getModel().getValueAt(i, 2).toString());
  idbrg = (tabel_returpenjualan.getModel().getValueAt(i, 3).toString());
  hrgjual = (tabel_returpenjualan.getModel().getValueAt(i, 5).toString());
  jumlah = (tabel_returpenjualan.getModel().getValueAt(i, 6).toString());   
  if( CheckboxSelected(i, 7, tabel_returpenjualan))
  {
   try{
    Connection con = koneksi.membukakoneksi();
    Statement sttment = con.createStatement();
    String exec_sql = "SELECT jumlah_brgkeluar FROM tb_detail_brgkeluar "
    + "WHERE no_urut_keluar = '"+jlbl_nourutkel.getText()+"'";
    ResultSet hasil = sttment.executeQuery(exec_sql);
    if(hasil.getInt("jumlah_brgkeluar")<Integer.parseInt(jumlah))
    {
     //mengeksekusi apabila jumlah barang yang dikembalikan kurang dari sama dengan
     //jumlah barang yg ada di nota penjualan /  barangt keluar   
     try {
         Connection conn = koneksi.membukakoneksi();
         Statement statement = conn.createStatement();
         String executed_sql = "UPDATE tb_retur_jual SET no_nota = '"+nomernota+"' "
         + " WHERE kd_retur = '" +kdretur + "'"; 
         statement.executeUpdate(executed_sql);
         } catch (SQLException e) { }
    try {
        Connection conn = koneksi.membukakoneksi();
        Statement statement = conn.createStatement();
        String execute_sql ="SELECT MAX(kd_retur) as max FROM tb_retur_jual";
        ResultSet hasilnya = statement.executeQuery(execute_sql); 
       while(hasilnya.next()) 
        {
        id_brgretur = hasilnya.getString("max");
        }            
       } catch (SQLException e) { }
                      //------- Menambah stok dengan data jumlah
    try {
         Connection cn = koneksi.membukakoneksi();
         Statement st = cn.createStatement();
         String exec = "SELECT stok FROM tb_barang WHERE id_barang = '" + idbrg + "'";
         ResultSet rest = st.executeQuery(exec);
        while(rest.next())
         {
         if (rest.getRow() == 1){
          stok = rest.getInt("stok") + Integer.parseInt(jumlah);
          } else {
           tidak_ditemukan = 1;
          }
         }
        } catch (SQLException e) {  } 
                
       try{
          Connection conn = koneksi.membukakoneksi();
          Statement stment = conn.createStatement();
          String edtjmlhbrgkel = "SELECT jumlah_brgkeluar FROM tb_detail_brgkeluar "
          + "WHERE no_urut_keluar = '" + nourutkeluar + "'";
          ResultSet reslt = stment.executeQuery(edtjmlhbrgkel);
          while(reslt.next())
          {
           if(reslt.getRow()==1)
            {
              stokupdate = reslt.getInt("jumlah_brgkeluar") - Integer.parseInt(jumlah);
             }else{
              notfound = 1;
             }
           }
          }catch(SQLException e){ JOptionPane.showMessageDialog(null, "Error " + e);}
        //------- Mengupdate jumlah barang keluar pada detail barang keluar
        try {
            Connection kon = koneksi.membukakoneksi();
            Statement statemen = kon.createStatement();
            String execute = "UPDATE tb_detail_brgkeluar SET jumlah_brgkeluar = '"+stokupdate+"' "
            + " WHERE no_urut_keluar = '" + nourutkeluar + "'";
            statemen.executeUpdate(execute);
           } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error " + e);} 
        //------- Mengupdate jumlah stok barang
        try {
            Connection kon = koneksi.membukakoneksi();
            Statement statemen = kon.createStatement();
            statemen.executeUpdate("UPDATE tb_barang SET stok='" + stok 
            + "' WHERE id_barang = '" + idbrg + "'");
            } catch (SQLException e) { }
         //------- Memasukan pada table transaksi detail
        try {
            Connection conect = koneksi.membukakoneksi();
            Statement stt = conect.createStatement();
            stt.executeUpdate("INSERT INTO tb_detail_returjual(kd_retur, "
            + "id_barang, harga_jual, jumlah_brgkembali) VALUES ('" + kdretur + "', '" + idbrg + "', '" 
            + hrgjual + "', '" + jumlah + "')");
            } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error " + e);}
        JOptionPane.showMessageDialog(null, "Berhasil menyimpan data transaksi"+ i++); 
    }
    else{
    //apabila banyak barang yang dikembalikan lebih banyak daripada 
    //jumlah barang yang ada di nota penjualan/barang keluar
    JOptionPane.showMessageDialog(null, "Barang yang dikembalikan lebih banyak daripada barang yang dibeli");
    }
   }catch(SQLException a){}
  }
  else if(CheckboxSelected(i, 7, tabel_returpenjualan) == false)
  {
  i++;
  }
 } 
}else
{
    JOptionPane.showMessageDialog(null, "terdapat inputan yang belum diisi");
}

  }
    
    public void simpanreturbarang(){
         //untuk menyimpan data transaksi keluar
     String tglretur = jdt_tglretur.getDateFormatString();
     String setdatenya = "yyyy-MM-dd";
     SimpleDateFormat formatdatenya = new SimpleDateFormat(setdatenya);
     String dateinputnya = String.valueOf(formatdatenya.format(jdt_tglretur.getDate()));
     String kdretur = jtxt_kdretur.getText();
     String nomernota = jlbl_nonota.getText();
     String keterangan = jtxt_ketretur.getText();
     String idbrg, kode, id_brgretur;
     Integer stok, tidak_ditemukan, notfound, totalhrgajual, stokupdate ,kosong = 0;
     String totalharga,jumlah,hrgbli,hrgjual, nourutkeluar;        
     DefaultTableModel model = (DefaultTableModel) tabel_returpenjualan.getModel();
     int rowCount = model.getRowCount();  
     
if(!"".equals(tglretur) && !"".equals(keterangan) && !"".equals(kdretur)){
    try {
                Connection conn = koneksi.membukakoneksi();
                Statement statement = conn.createStatement();
                String executed_sql = "INSERT INTO tb_retur_jual(kd_retur, no_nota, tgl_retur, keterangan)VALUES('"
                + kdretur + "', '-', '"+dateinputnya+"', '-')"; 
                statement.executeUpdate(executed_sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            } 
   for (int i = 0; i < rowCount; i++)
    {
         tidak_ditemukan = 0;
         stok = 0;
         stokupdate = 0;
         notfound = 0; 
         nomernota = (tabel_returpenjualan.getModel().getValueAt(i, 1).toString());
         nourutkeluar = (tabel_returpenjualan.getModel().getValueAt(i, 2).toString());
         idbrg = (tabel_returpenjualan.getModel().getValueAt(i, 3).toString());
         hrgjual = (tabel_returpenjualan.getModel().getValueAt(i, 5).toString());
         jumlah = (tabel_returpenjualan.getModel().getValueAt(i, 6).toString());  
     if  ( CheckboxSelected(i, 7, tabel_returpenjualan))
      {  
        try{
         Connection con = koneksi.membukakoneksi();
         Statement sttment = con.createStatement();
         String exec_sql = "SELECT jumlah_brgkeluar FROM tb_detail_brgkeluar "
         + "WHERE no_urut_keluar = '"+jlbl_nourutkel.getText()+"'";
         ResultSet hasil = sttment.executeQuery(exec_sql);
         while(hasil.next())
         {
          if(Integer.parseInt(jumlah)>hasil.getInt("jumlah_brgkeluar"))
           {
            //apabila banyak barang yang dikembalikan lebih banyak daripada 
            //jumlah barang yang ada di nota penjualan/barang keluar
             JOptionPane.showMessageDialog(null, "Barang yang dikembalikan lebih banyak daripada barang yang dibeli");
           }
          else
          {
             //mengeksekusi apabila jumlah barang yang dikembalikan kurang dari sama dengan
             //jumlah barang yg ada di nota penjualan /  barangt keluar
            try {
                Connection conn = koneksi.membukakoneksi();
                Statement statement = conn.createStatement();
                String executed_sql = "UPDATE tb_retur_jual SET no_nota = '"+nomernota+"', "
                + "keterangan = '"+keterangan+"' "
                + " WHERE kd_retur = '" +kdretur + "'"; 
                statement.executeUpdate(executed_sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
              try {
                Connection conn = koneksi.membukakoneksi();
                Statement statement = conn.createStatement();
                String execute_sql ="SELECT MAX(kd_retur) as max FROM tb_retur_jual";
                ResultSet hasilnya = statement.executeQuery(execute_sql); 
                while(hasilnya.next()) 
                {
                   id_brgretur = hasilnya.getString("max");
                }            
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
                      //------- Menambah stok dengan data jumlah
                try {
                    Connection cn = koneksi.membukakoneksi();
                    Statement st = cn.createStatement();
                    String exec = "SELECT stok FROM tb_barang WHERE id_barang = '" + idbrg + "'";
                    ResultSet rest = st.executeQuery(exec);
                   // sttment.executeUpdate(exec_sql);
                   while(rest.next())
                   {
                       if (rest.getRow() == 1){
                        stok = rest.getInt("stok") + Integer.parseInt(jumlah);
                        } else {
                           tidak_ditemukan = 1;
                        }
                   }
                    //Integer.parseInt(jumlah);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
                } 
                
                try{
                Connection conn = koneksi.membukakoneksi();
                Statement stment = conn.createStatement();
                String edtjmlhbrgkel = "SELECT jumlah_brgkeluar FROM tb_detail_brgkeluar "
                + "WHERE no_urut_keluar = '" + nourutkeluar + "'";
                ResultSet reslt = stment.executeQuery(edtjmlhbrgkel);
                 while(reslt.next())
                 {
                    if(reslt.getRow()==1)
                    {
                        stokupdate = reslt.getInt("jumlah_brgkeluar") - Integer.parseInt(jumlah);
                    }else{
                        notfound = 1;
                    }
                 }
                }catch(SQLException e){ JOptionPane.showMessageDialog(null, "Error " + e);}
            
                if(tidak_ditemukan == 0){
                
                        //------- Mengupdate jumlah stok barang
                    try {
                        Connection kon = koneksi.membukakoneksi();
                        Statement statemen = kon.createStatement();
                        String execute = "UPDATE tb_detail_brgkeluar SET jumlah_brgkeluar = '"+stokupdate+"' "
                        + " WHERE no_urut_keluar = '" + nourutkeluar + "'";
                        statemen.executeUpdate(execute);
                    } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error " + e);} 
                        //------- Mengupdate jumlah barang keluar pada detail barang keluar
                    try {
                        Connection kon = koneksi.membukakoneksi();
                        Statement statemen = kon.createStatement();
                        statemen.executeUpdate("UPDATE tb_barang SET stok='" + stok 
                        + "' WHERE id_barang = '" + idbrg + "'");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                    
                        //------- Memasukan pada table transaksi detail
                    try {
                        Connection conect = koneksi.membukakoneksi();
                        Statement stt = conect.createStatement();
                        stt.executeUpdate("INSERT INTO tb_detail_returjual(kd_retur, "
                          + "id_barang, harga_jual, jumlah_brgkembali) VALUES ('" + kdretur + "', '" + idbrg + "', '" 
                          + hrgjual + "', '" + jumlah + "')");
                        kosong = 1;
                    } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error " + e);}
                    //JOptionPane.showMessageDialog(null, "Berhasil menyimpan data transaksi"); 
                }  
           }
          } 
             }catch(SQLException e)
                {JOptionPane.showMessageDialog(null, "nota yang dipilih tidak ada dalam database");}  
//         }
           JOptionPane.showMessageDialog(null, "Berhasil menyimpan data transaksi");
     }
//     else{JOptionPane.showMessageDialog(null, "Belum memilih barang yang dikembalikan/retur");}
   }        
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
     jtxt_kdretur.setText("");
     jtxt_carinmrnota.setText("");
     jtxt_ketretur.setText("");
     jlbl_jmlhdikembalikan.setText("");
     jdt_tglretur.setDateFormatString("");
     jlbl_nonota.setText("-");
     jlbl_nmbrg.setText("-");
     jlbl_hrgjual.setText("-");
     jlbl_nourutkel.setText("-");
     jtxt_baristrpilih.setText("");
     jlbl_idbrg.setText("-");
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
        jtxt_carinmrnota = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_returpenjualan = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jlbl_hrgjual = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jlbl_nonota = new javax.swing.JLabel();
        jtxt_kdretur = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtxt_ketretur = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jdt_tglretur = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jtxt_baristrpilih = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jlbl_nmbrg = new javax.swing.JLabel();
        jlbl_nourutkel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jlbl_idbrg = new javax.swing.JLabel();
        jlbl_jmlhdikembalikan = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Kode Retur");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jtxt_carinmrnota.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_carinmrnota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_carinmrnotaActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_carinmrnota, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 140, -1));

        jButton1.setIcon(new javax.swing.ImageIcon("E:\\sinau java\\NetBeans Projects\\sistem_persediaan_barang\\src\\main_sisteme\\image\\icons8-search-32.png")); // NOI18N
        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, -1, -1));

        tabel_returpenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nomer Nota", "No Urut Keluar", "Id Barang", "Nama Barang", "Harga Jual", "Jumlah", "Checklist"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_returpenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_returpenjualanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabel_returpenjualan);
        if (tabel_returpenjualan.getColumnModel().getColumnCount() > 0) {
            tabel_returpenjualan.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 670, 116));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Nomer Nota");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Tanggal Barang Retur");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jlbl_hrgjual.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jlbl_hrgjual.setText("-");
        jPanel2.add(jlbl_hrgjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 90, -1));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-back-arrow-24.png"))); // NOI18N
        jButton6.setText("Kembali");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, -1, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main_sisteme/image/icons8-save-all-32.png"))); // NOI18N
        jButton4.setText("Simpan");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Harga Jual Barang");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, -1, -1));

        jlbl_nonota.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_nonota.setText("-");
        jPanel2.add(jlbl_nonota, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 110, -1));

        jtxt_kdretur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_kdretur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtxt_kdreturMouseReleased(evt);
            }
        });
        jtxt_kdretur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_kdreturActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_kdretur, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 143, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Keterangan");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        jtxt_ketretur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jtxt_ketretur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_ketreturActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_ketretur, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 143, 70));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Pilih Nomer Nota");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));
        jPanel2.add(jdt_tglretur, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 140, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Jumlah yang dikembalikan");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 160, 140, -1));
        jPanel2.add(jtxt_baristrpilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 340, 40, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Nama Barang");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, -1, -1));

        jlbl_nmbrg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_nmbrg.setText("-");
        jPanel2.add(jlbl_nmbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 140, -1));

        jlbl_nourutkel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jlbl_nourutkel.setText("-");
        jPanel2.add(jlbl_nourutkel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, 80, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Id Barang");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, -1, -1));

        jlbl_idbrg.setText("-");
        jPanel2.add(jlbl_idbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 70, -1));

        jlbl_jmlhdikembalikan.setText("-");
        jPanel2.add(jlbl_jmlhdikembalikan, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 60, -1));

        jPanel1.setBackground(new java.awt.Color(204, 153, 0));
        jPanel1.setMinimumSize(new java.awt.Dimension(168, 81));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel8.setText("Retur Penjualan Barang");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtxt_carinmrnotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_carinmrnotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_carinmrnotaActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.setVisible(false);
        //        new MenuHome().setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //memanggil fungsi simpanreturbarang
        simpanreturbarang();
        //simpantransaksi();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jtxt_kdreturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_kdreturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_kdreturActionPerformed

    private void jtxt_ketreturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_ketreturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_ketreturActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        carinomernota();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabel_returpenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_returpenjualanMouseClicked
        DefaultTableModel dt_tbreturjualk= (DefaultTableModel)tabel_returpenjualan.getModel();
        int selecttedrow = tabel_returpenjualan.getSelectedRow();
        jtxt_baristrpilih.setText(Integer.toString(selecttedrow) );
        //        jTextField2.setTextdt_tbkat.getValueAt(selecttedrow, 0).toString());
        jlbl_nonota.setText(tabel_returpenjualan.getValueAt(selecttedrow, 1).toString());
        jlbl_nourutkel.setText(tabel_returpenjualan.getValueAt(selecttedrow, 2).toString());
        jlbl_idbrg.setText(tabel_returpenjualan.getValueAt(selecttedrow, 3).toString());
        jlbl_nmbrg.setText(tabel_returpenjualan.getValueAt(selecttedrow, 4).toString());
        jlbl_hrgjual.setText(tabel_returpenjualan.getValueAt(selecttedrow, 5).toString());
//        jtxt_spekbrg.setText(tabel_returpenjualan.getValueAt(selecttedrow, 7).toString());
        jlbl_jmlhdikembalikan.setText(tabel_returpenjualan.getValueAt(selecttedrow, 6).toString());
    }//GEN-LAST:event_tabel_returpenjualanMouseClicked

    private void jtxt_kdreturMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_kdreturMouseReleased
        // TODO add your handling code here:
         String retursama = jtxt_kdretur.getText();
        try {
            Connection con = koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT kd_retur FROM tb_retur_jual WHERE kd_retur = '"+retursama+"'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
           if(hasil.next())
           {
               JOptionPane.showMessageDialog(null, "Kode retur sudah ada di database");
           }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }//GEN-LAST:event_jtxt_kdreturMouseReleased

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
            java.util.logging.Logger.getLogger(FormReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormReturPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormReturPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdt_tglretur;
    private javax.swing.JLabel jlbl_hrgjual;
    private javax.swing.JLabel jlbl_idbrg;
    private javax.swing.JLabel jlbl_jmlhdikembalikan;
    private javax.swing.JLabel jlbl_nmbrg;
    private javax.swing.JLabel jlbl_nonota;
    private javax.swing.JLabel jlbl_nourutkel;
    private javax.swing.JTextField jtxt_baristrpilih;
    private javax.swing.JTextField jtxt_carinmrnota;
    private javax.swing.JTextField jtxt_kdretur;
    private javax.swing.JTextField jtxt_ketretur;
    private javax.swing.JTable tabel_returpenjualan;
    // End of variables declaration//GEN-END:variables
}
