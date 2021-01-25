/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistem.pendaftaranktp;

/**
 *
 * @author Microsoft
 */
import sistem.database.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author didik
 */
public class SistemDataModel {
    public final Connection conn;

    public SistemDataModel(String driver) throws SQLException {
        this.conn = DatabaseHelper.getConnection(driver);
    }
    public void addSistemPendaftaranKTP(Pendaftar antrian) throws SQLException{
        String insertAntrian = "INSERT INTO akun_antrian (no_antrian, nama, alamat)"
                + " VALUES (?,?,?)";
        String insertPendaftar = "INSERT INTO pendaftar (no_antrian, gender, birthdate)"
                + " VALUES (?,?,?)";
        String insertAkun = "INSERT INTO akun (nomor_nik, no_antrian)"
                + " VALUES (?,?)";
        PreparedStatement stmtAntrian = conn.prepareStatement(insertAntrian);
        stmtAntrian.setInt(1, antrian.getNomorAntrian());
        stmtAntrian.setString(2, antrian.getNama());
        stmtAntrian.setString(3, antrian.getAlamat());
        stmtAntrian.execute();
        
        PreparedStatement stmtPendaftar = conn.prepareStatement(insertPendaftar);
        stmtPendaftar.setInt(1, antrian.getNomorAntrian());
        stmtPendaftar.setString(2, antrian.getGender());
        stmtPendaftar.setString(3, antrian.getBirthdate());
        stmtPendaftar.execute();
        
        PreparedStatement stmtAkun = conn.prepareStatement(insertAkun);
        stmtAkun.setInt(1, antrian.getAkunn().get(0).getNomorNik());
        stmtAkun.setInt(2, antrian.getNomorAntrian());
        stmtAkun.execute();
        
    }
    public void addSistemPendaftaranKTP(Disdukcapil antrian) throws SQLException{
        String insertAntrian = "INSERT INTO akun_antrian (no_antrian, nama, alamat)"
                + " VALUES (?,?,?)";
        String insertDisdukcapil = "INSERT INTO disdukcapil (no_antrian, kontak)"
                + " VALUES (?,?)";
        String insertAkun = "INSERT INTO akun (nomor_nik, no_antrian)"
                + " VALUES (?,?)";
        PreparedStatement stmtAntrian = conn.prepareStatement(insertAntrian);
        stmtAntrian.setInt(1, antrian.getNomorAntrian());
        stmtAntrian.setString(2, antrian.getNama());
        stmtAntrian.setString(3, antrian.getAlamat());
        stmtAntrian.execute();
        
        PreparedStatement stmtDisdukcapil = conn.prepareStatement(insertDisdukcapil);
        stmtDisdukcapil.setInt(1, antrian.getNomorAntrian());
        stmtDisdukcapil.setString(2, antrian.getKontak());
        stmtDisdukcapil.execute();
        
        PreparedStatement stmtAkun = conn.prepareStatement(insertAkun);
        stmtAkun.setInt(1, antrian.getAkunn().get(0).getNomorNik());
        stmtAkun.setInt(2, antrian.getNomorAntrian());
        stmtAkun.execute();
    }
    public ObservableList<Pendaftar> getPendaftars(){
        ObservableList<Pendaftar> data = FXCollections.observableArrayList();
        String sql="SELECT `no_antrian`, `nama`,`alamat`, `gender`, `birthdate` "
                + "FROM `sistem_ktp` NATURAL JOIN `pendaftar` "
                + "ORDER BY name";
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()){
                String sqlAkun = "SELECT nomor_nik, balance "
                    + "FROM akun WHERE no_antrian="+rs.getInt(1);
                ResultSet rsAkun = conn.createStatement().executeQuery(sqlAkun);
                ArrayList<Akun> dataAkun = new ArrayList<>();
                while (rsAkun.next()){
                    dataAkun.add(new Akun(rsAkun.getInt(1)));
                }
                data.add(new Pendaftar(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5), dataAkun));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SistemDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }     
        return data;
    }
    public ObservableList<Disdukcapil> getDisdukcapils(){
        ObservableList<Disdukcapil> data = FXCollections.observableArrayList();
        String sql="SELECT `no_antrian`, `nama`,`alamat`, `kontak` "
                + "FROM `sistem_ktp` NATURAL JOIN `disdukcapil` "
                + "ORDER BY name";
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()){
                String sqlAkun = "SELECT nomor_nik "
                    + "FROM account WHERE no_antrian="+rs.getInt(1);
                ResultSet rsAkun = conn.createStatement().executeQuery(sqlAkun);
                ArrayList<Akun> dataAkun = new ArrayList<>();
                while (rsAkun.next()){
                    dataAkun.add(new Akun(rsAkun.getInt(1)));
                }
                data.add(new Disdukcapil(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4), dataAkun));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SistemDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }     
        return data;
    }
    public ObservableList<Akun> getAkunn(int nomorAntrian){
        ObservableList<Akun> data = FXCollections.observableArrayList();
        String sql="SELECT `nomor_nik` "
                + "FROM `akun` "
                + "WHERE no_antrian="+nomorAntrian;
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()){
                data.add(new Akun(rs.getInt(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SistemDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    public int nextSistemPendaftaranKTP() throws SQLException{
        String sql="SELECT MAX(no_antrian) from sistem_ktp";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()){
                return rs.getInt(1)==0?1000001:rs.getInt(1)+1;
            }
        return 1000001;
    }
    public int nextNomorNik(int nomorAntrian) throws SQLException{
        String sql="SELECT MAX(nomor_nik) FROM account WHERE no_antrian="+nomorAntrian;
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()){
                return rs.getInt(1)+1;
            }
        return 0;
    }
    
    public void addAkun(int nomorAntrian, Akun acc) throws SQLException{
        String insertAntrian = "INSERT INTO akun (no_antrian, nomor_nik)"
                + " VALUES (?,?)";
  
        PreparedStatement stmtAntrian = conn.prepareStatement(insertAntrian);
        stmtAntrian.setInt(1, nomorAntrian);
        stmtAntrian.setInt(2, acc.getNomorNik());
        stmtAntrian.execute();
        
    }
}

