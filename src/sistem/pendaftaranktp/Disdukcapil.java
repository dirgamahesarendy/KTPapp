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
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Disdukcapil extends SistemPendaftaranKTP{
    StringProperty kontak;

    public Disdukcapil(Integer nomorAntrian, String nama, String alamat, String kontak, ArrayList<Akun> akunn) {
        super(nomorAntrian, nama, alamat, akunn);
        this.kontak=new SimpleStringProperty(kontak);
    }

    public Disdukcapil(Integer nomorAntrian, String nama, String alamat, String kontak, Akun akun) {
        super(nomorAntrian, nama, alamat, akun);
        this.kontak.set(kontak);
    }

    public String getKontak() {
        return kontak.get();
    }

    public void setKontak(String kontak) {
        this.kontak.set(kontak);
    }
    public StringProperty kontakProperty() {
        return kontak;
    }
}

