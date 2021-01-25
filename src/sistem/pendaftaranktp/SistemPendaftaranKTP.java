/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistem.pendaftaranktp;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author kmbrps
 */
public abstract class SistemPendaftaranKTP {
    private IntegerProperty nomorAntrian;
    private StringProperty nama;
    private StringProperty alamat;
    private ArrayList<Akun> akunn;

    public SistemPendaftaranKTP(Integer nomorAntrian, String nama, String alamat,
            ArrayList<Akun> akunn) {
        this.nomorAntrian=new SimpleIntegerProperty(nomorAntrian);
        this.nama= new SimpleStringProperty(nama);
        this.alamat=new SimpleStringProperty(alamat);
        this.akunn = akunn;
    }
    public SistemPendaftaranKTP(Integer nomorAntrian, String nama, String alamat, Akun akun) {
        akunn = new ArrayList<>();
        this.nomorAntrian=new SimpleIntegerProperty(nomorAntrian);
        this.nama=new SimpleStringProperty(nama);
        this.alamat=new SimpleStringProperty(alamat);
        this.akunn.add(akun);
        }

    public Integer getNomorAntrian() {
        return nomorAntrian.get();
    }

    public void setNomorAntrian(Integer nomorAntrian) {
        this.nomorAntrian.set(nomorAntrian);
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String alamat) {
        this.alamat.set(alamat);
    }


    public ArrayList<Akun> getAkunn() {
        return akunn;
    }

    public void setAkunn(ArrayList<Akun> akunn) {
        this.akunn = akunn;
    }
    
    public IntegerProperty nomorAntrianProperty(){
        return nomorAntrian;
    }
    public StringProperty namaProperty(){
        return nama;
    }
    public StringProperty alamatProperty(){
        return alamat;
    }
}

