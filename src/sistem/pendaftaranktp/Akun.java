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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Akun {
    private IntegerProperty nomorNik;

    public Akun(int nomorNik) {
        this.nomorNik=new SimpleIntegerProperty(nomorNik);
    }

    public Integer getNomorNik() {
        return nomorNik.get();
    }

    public void setNomorNik(int nomorNik) {
        this.nomorNik.set(nomorNik);
    }
    
    public IntegerProperty nomorNikProperty(){
        return nomorNik;
    }
}

