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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import org.victoryaxon.bean.Igss;
import org.victoryaxon.bean.SalarioOrdinario;
import org.victoryaxon.db.Conexion;
import org.victoryaxon.sistema.Principal;

/**
 * FXML Controller class
 *
 * @author viaro-participant
 */
public class ControladorSalarioOrdinario implements Initializable {
    private Principal principal;
    @FXML private ImageView imgRetorno;
    @FXML private TextField txtAnio;
    @FXML private TextField txtCuota;
    @FXML private TableView tblSalarioOrdinario;
    @FXML private TableColumn colIdSalario;
    @FXML private TableColumn colAnio;
    @FXML private TableColumn colCuota;       
    
    
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public void actualizarDatos(){
        imgRetorno.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/regresa.png").toString()));
        tblSalarioOrdinario.setItems(getSalarioOrdinario());
        colIdSalario.setCellValueFactory(new PropertyValueFactory<SalarioOrdinario, Integer>("idSalarioOrdinario"));
        colAnio.setCellValueFactory(new PropertyValueFactory<SalarioOrdinario, String>("anio"));
        colCuota.setCellValueFactory(new PropertyValueFactory<SalarioOrdinario, String>("CuotaSalarioOrdinario"));
        tblSalarioOrdinario.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
    }
    public ObservableList getSalarioOrdinario(){
        ObservableList listaUsuarios = null;
        ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_listarSalarioOrdinario}");
        try{
            ArrayList<SalarioOrdinario> lista =new ArrayList<SalarioOrdinario>();
            while(resultado.next()){
                lista.add(new SalarioOrdinario(resultado.getInt("idSalarioOrdinario"),
                        resultado.getString("anio"),
                        resultado.getString("CuotaSalarioOrdinario")));
            }
            listaUsuarios = FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listaUsuarios;
    }
    
    public void guardar(){
    try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarSalarioOrdinario(?,?)}");
                procedimiento.setString("anio",txtAnio.getText());
                procedimiento.setString("CuotaSalarioOrdinario", txtCuota.getText());
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
            if(tblSalarioOrdinario.getSelectionModel().getSelectedItem() !=null){
                SalarioOrdinario salario =(SalarioOrdinario)tblSalarioOrdinario.getSelectionModel().selectedItemProperty().get();
                try {
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarSalarioOrdinario(?)}");
                    procedimiento.setInt("idSalarioOrdinario",salario.getIdSalarioOrdinario());
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
    if (tblSalarioOrdinario.getSelectionModel().getSelectedItem() !=null) { 
            try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_modificarSalarioOrdinairo(?,?,?)}");
                SalarioOrdinario salario =(SalarioOrdinario)tblSalarioOrdinario.getSelectionModel().selectedItemProperty().get();
                procedimiento.setInt("idSalarioOrdinario",salario.getIdSalarioOrdinario());
                procedimiento.setString("anio",txtAnio.getText());
                procedimiento.setString("CuotaSalarioOrdinario", txtCuota.getText());
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
    public void retornarVentana(){
        principal.ventanaMenu();
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
            SalarioOrdinario salario =(SalarioOrdinario)tblSalarioOrdinario.getSelectionModel().selectedItemProperty().get();
            txtAnio.setText(salario.getAnio());
            txtCuota.setText(salario.getCuotaSalarioOrdinario());
                    
        } catch (Exception e) {
            
        }
    }

    private void desactivarCampos() {
        txtAnio.setDisable(true);
        txtCuota.setDisable(true);
    }
    private void activarCampos() {
        txtAnio.setDisable(false);
        txtCuota.setDisable(false);
    }        
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarDatos();
    }    
    
}
