package org.victoryaxon.controladores;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import org.victoryaxon.bean.Igss;
import org.victoryaxon.db.Conexion;
import org.victoryaxon.sistema.Principal;
/**
 * FXML Controller class
 *
 * @author viaro-participant
 */
public class ControladorIgss implements Initializable {
private Principal principal;
    @FXML private TextField txtAnio;
    @FXML private TextField txtCuota;
    @FXML private ImageView imgRetorno;
    @FXML private DatePicker datepicker;
    @FXML private TableView tblIgss; 
    @FXML private TableColumn colIdIgss;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colAnio;
    @FXML private TableColumn colCuota;
    public void initialize(URL url, ResourceBundle rb) {
        imgRetorno.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/regresa.png").toString()));
        actualizarDatos();
    }
    public void Agregar(){
        //System.out.println(String.valueOf(datepicker.getValue().getYear()));
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public Principal getPrincipal() {
        return principal;
    }public void actualizarDatos(){ 
        tblIgss.setItems(getIgss());
        colIdIgss.setCellValueFactory(new PropertyValueFactory<Igss, Integer>("idIgss"));
        colAnio.setCellValueFactory(new PropertyValueFactory<Igss, String>("anio"));
        colCuota.setCellValueFactory(new PropertyValueFactory<Igss, String>("Cuotaigss"));
        tblIgss.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    public void guardar(){
        try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarIgss(?,?)}");
                procedimiento.setString("anio",txtAnio.getText());
                procedimiento.setString("CuotaIgss", txtCuota.getText());
                procedimiento.execute();
                desactivarCampos();
                actualizarDatos();
                JOptionPane.showMessageDialog(null, "Registro almacenado Satisfactoriamente","Compra",JOptionPane.INFORMATION_MESSAGE);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void eliminar(){
    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Registro", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblIgss.getSelectionModel().getSelectedItem() !=null){
                Igss igss =(Igss)tblIgss.getSelectionModel().selectedItemProperty().get();
                try {
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarIgss(?)}");
                    procedimiento.setInt("idIgss",igss.getIdIgss());
                    procedimiento.execute();
                    actualizarDatos();
                    JOptionPane.showMessageDialog(null, "Registro eliminado Satisfactoriamente","Empleado",JOptionPane.INFORMATION_MESSAGE);
                    desactivarCampos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Perdone no se ha podido eliminar el registro","Empleado",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un registro","Empleado",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    public void modificar(){
    if (tblIgss.getSelectionModel().getSelectedItem() !=null) { 
            try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_modificarIgss(?,?,?)}");
                Igss igss =(Igss)tblIgss.getSelectionModel().selectedItemProperty().get();
                procedimiento.setInt("idIgss",igss.getIdIgss());
                procedimiento.setString("anio",txtAnio.getText());
                procedimiento.setString("CuotaIgss", txtCuota.getText());
                procedimiento.execute();
                desactivarCampos();
                actualizarDatos();  
                JOptionPane.showMessageDialog(null, "Registro modificado Satisfactoriamente","Igss",JOptionPane.INFORMATION_MESSAGE);                
        } catch (Exception e) {
            e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro","Igss",JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    public ObservableList getIgss(){
        ObservableList listaUsuarios = null;
        ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_listarIgss}");
        try{
            ArrayList<Igss> lista =new ArrayList<Igss>();
            while(resultado.next()){
                lista.add(new Igss(resultado.getInt("idIgss"),
                        resultado.getString("anio"),
                        resultado.getString("Cuotaigss")));
            }
            listaUsuarios = FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listaUsuarios;
    }
    public void visible (){   
        txtAnio.setDisable(false);
        txtCuota.setDisable(false);
        bloqueo();
    }
    public void bloqueo(){
        txtAnio.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent eventoA) {
                    if (!eventoA.getCharacter().matches("[0-9]")) {
                        eventoA.consume();
                    }
                }
            });   
        txtCuota.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent eventoA) {
                    if (!eventoA.getCharacter().matches("[0-9.]")) {
                        eventoA.consume();
                    }
                }
            });
    }
    public void seleccionarElemento(){
        try {
            Igss igss =(Igss)tblIgss.getSelectionModel().selectedItemProperty().get();
            txtAnio.setText(igss.getAnio());
            txtCuota.setText(igss.getCuotaigss());
                    
        } catch (Exception e) {
            
        }
    }
    public void retornarVentana(){
        principal.ventanaMenu();
    }
    private void desactivarCampos() {
        txtAnio.setDisable(true);
        txtCuota.setDisable(true);
    }
    private void activarCampos() {
        txtAnio.setDisable(false);
        txtCuota.setDisable(false);
    }
}