/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistem.pendaftaranktp;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author kmbrps
 */
public class SistemKTPGUIController implements Initializable {

   @FXML
    private TextField tfNomorAntrian;

    @FXML
    private TextField tfNama;

    @FXML
    private TextField tfAlamat;

    @FXML
    private ComboBox<String> cbGender;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private TextField tfNomorNik;

    @FXML
    private Button btnAddSistemPendaftaranKTP;

    @FXML
    private Button btnReload;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<Pendaftar> tblPendaftarKTP;

    @FXML
    private TableColumn<Pendaftar, Integer> colNomorAntrian;

    @FXML
    private TableColumn<Pendaftar, String> colNama;

    @FXML
    private TableColumn<Pendaftar, String> colAlamat;

    @FXML
    private TableColumn<Pendaftar, String> colGender;

    @FXML
    private TableColumn<Pendaftar, String> colBirthdate;

    @FXML
    private TableView<Akun> tblAkun;

    @FXML
    private TableColumn<Akun, Integer> colNoNik;

    @FXML
    private TextField tfNewNomorAntrian;

    @FXML
    private TextField tfNewNoNik;

    @FXML
    private Button btnAddAkun;

    @FXML
    private Label lblDBStatus;
    
    @FXML
    private Label lblSaveStatus;
    
    private SistemDataModel ahdm;

    @FXML
    void handleAddAkunButton(ActionEvent event) {
     
        try {
            Akun acc =  new Akun(Integer.parseInt(tfNewNoNik.getText()));
            
            ahdm.addAkun(Integer.parseInt(tfNewNomorAntrian.getText()),acc);          
            viewDataAkun(Integer.parseInt(tfNewNomorAntrian.getText()));
            btnReload.fire();
            
        } catch (SQLException ex) {
            Logger.getLogger(SistemKTPGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleAddAntrianButton(ActionEvent event) {
        LocalDate ld = dpBirthdate.getValue();
        String birthdate = String.format("%d-%02d-%02d", ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
        Pendaftar antrian = new Pendaftar(Integer.parseInt(tfNomorAntrian.getText()),
                tfNama.getText(),
                tfAlamat.getText(),
                cbGender.getSelectionModel().getSelectedItem(),
                birthdate,
                new Akun(Integer.parseInt(tfNomorNik.getText())));
        try {
            ahdm.addSistemPendaftaranKTP(antrian);
            lblSaveStatus.setText("Antrian berhasil dibuat");
            btnReload.fire();
            btnClear.fire();
            
        } catch (SQLException ex) {
            lblSaveStatus.setText("Antrian gagal dibuat");
            Logger.getLogger(SistemKTPGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    void handleClearButton(ActionEvent event) {
        try {
            tfNomorAntrian.setText("" + ahdm.nextSistemPendaftaranKTP());
            tfNomorNik.setText(tfNomorAntrian.getText() + "01");
            tfNama.setText("");
            tfAlamat.setText("");
            cbGender.getSelectionModel().clearSelection();
            dpBirthdate.setValue(LocalDate.of(LocalDate.now().getYear() - 17, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));
        } catch (SQLException ex) {
            Logger.getLogger(SistemKTPGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @FXML
    void handleReloadButton(ActionEvent event) {
        ObservableList<Pendaftar> data = ahdm.getPendaftars();
        colNomorAntrian.setCellValueFactory(new PropertyValueFactory<>("nomorAntrian"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colBirthdate.setCellValueFactory(new PropertyValueFactory<>("TTL"));
        tblPendaftarKTP.setItems(null);
        tblPendaftarKTP.setItems(data);
        btnAddAkun.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> gender = FXCollections.observableArrayList("Laki-laki", "Perempuan");
        cbGender.setItems(gender);
        try {
            ahdm = new SistemDataModel("MYSQL");
            lblDBStatus.setText(ahdm.conn == null ? "Not Connected" : "Connected");
            btnClear.fire();
            btnReload.fire();
        } catch (SQLException ex) {
            Logger.getLogger(SistemKTPGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tblPendaftarKTP.getSelectionModel().selectedIndexProperty().addListener(listener->{
            if (tblPendaftarKTP.getSelectionModel().getSelectedItem()!=null){
                Pendaftar antrian =  tblPendaftarKTP.getSelectionModel().getSelectedItem();
                viewDataAkun(antrian.getNomorAntrian());
                btnAddAkun.setDisable(false);
                tfNewNomorAntrian.setText(""+antrian.getNomorAntrian());
                try {
                    tfNewNoNik.setText(""+ahdm.nextNomorNik(antrian.getNomorAntrian()));
                } catch (SQLException ex) {
                    Logger.getLogger(SistemKTPGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
    
    public void viewDataAkun(int nomorAntrian){
        ObservableList<Akun> data = ahdm.getAkunn(nomorAntrian);
        colNoNik.setCellValueFactory(new PropertyValueFactory<>("noNik"));
        tblAkun.setItems(null);
        tblAkun.setItems(data);
    }

}

