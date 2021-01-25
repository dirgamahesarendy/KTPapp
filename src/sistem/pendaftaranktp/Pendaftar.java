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

public class Pendaftar extends SistemPendaftaranKTP {

    StringProperty gender;
    StringProperty birthdate;

    public Pendaftar(Integer nomorAntrian, String nama,
            String alamat, String gender, String birthdate, ArrayList<Akun> akunn) {
        super(nomorAntrian, nama, alamat, akunn);
        this.gender = new SimpleStringProperty(gender);
        this.birthdate = new SimpleStringProperty(birthdate);
    }

    public Pendaftar(Integer nomorAntrian, String nama, String alamat,
            String gender, String birthdate, Akun akun) {
        super(nomorAntrian, nama, alamat, akun);
        this.gender = new SimpleStringProperty(gender);
        this.birthdate = new SimpleStringProperty(birthdate);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getBirthdate() {
        return birthdate.get();
    }

    public void setBirthdate(String birthdate) {
        this.birthdate.set(birthdate);
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public StringProperty birthdateProperty() {
        return birthdate;
    }
}

