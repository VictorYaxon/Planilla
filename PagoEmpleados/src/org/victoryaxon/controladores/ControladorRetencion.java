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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import org.victoryaxon.bean.Empleado;
import org.victoryaxon.bean.RetencionISR;
import org.victoryaxon.db.Conexion;
import org.victoryaxon.sistema.Principal;

/**
 * FXML Controller class
 *
 * @author viaro-participant
 */
public class ControladorRetencion implements Initializable {
private Principal principal;
    @FXML private TableView tblRetencion;
    @FXML private ImageView imgRetorno;
    @FXML private TableColumn colIdRetencion;
    @FXML private TableColumn colRetencionEmpleado;
    @FXML private TableColumn colFechaRetencion;
    @FXML private TableColumn colIdEmpleado;
    @FXML private ComboBox cmbEmpleado;
    @FXML private TextField txtRetencion;
    ObservableList<Empleado> listaEmpleado;
    public void seleccionarElemento(){
        try {
            RetencionISR retencion =(RetencionISR)tblRetencion.getSelectionModel().selectedItemProperty().get();
            txtRetencion.setText(String.valueOf(retencion.getRetencionEmpleado()));
            cmbEmpleado.setValue(retencion.getNombre());
            
        } catch (Exception e) {
            
        }
    }
    
    public void AgregarActivado(){
        visible();
        campos();
    
    }
    
    public void retornarVentana(){
        principal.ventanaMenu();
    }
    
    public void eliminar(){
    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Registro", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblRetencion.getSelectionModel().getSelectedItem() !=null){
                RetencionISR retencion =(RetencionISR)tblRetencion.getSelectionModel().selectedItemProperty().get();
                try {
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarRetencion(?)}");
                    procedimiento.setInt("idRetencionISR",retencion.getIdRetencionISR());
                    procedimiento.execute();
                    actualizarDatos();
                    JOptionPane.showMessageDialog(null, "Registro eliminado Satisfactoriamente","Retencion",JOptionPane.INFORMATION_MESSAGE);
                    desactivarCampos();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Perdone no se ha podido eliminar el registro","Retencion",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un registro","Empleado",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    
    public void modificar(){
        if (tblRetencion.getSelectionModel().getSelectedItem() !=null) { 
            try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarRetencion(?,?,?)}");
                RetencionISR retencionISR =(RetencionISR)tblRetencion.getSelectionModel().selectedItemProperty().get();
                procedimiento.setInt("idRetencionISR",retencionISR.getIdRetencionISR());
                procedimiento.setString("RetencionEmpleado",txtRetencion.getText());
                procedimiento.setInt("idEmpleado",((Empleado)cmbEmpleado.getValue()).getIdEmpleado());
                procedimiento.execute();
                actualizarDatos();  
                JOptionPane.showMessageDialog(null, "Registro modificado Satisfactoriamente","Retencion",JOptionPane.INFORMATION_MESSAGE);
                desactivarCampos();
                
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Proveedor","Bonificacion",JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro","Bonificacion",JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    public void editarActivado(){
        visible();
        campos();
    }
    
    public void visible(){
        bloqueo();
        txtRetencion.setDisable(false);
        cmbEmpleado.setDisable(false);
    }
    
    public void actualizarDatos(){
        listaEmpleado=FXCollections.observableArrayList();
        try {
            ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_ListarEmpleados}");
            ArrayList<Empleado> empleado = new ArrayList<Empleado>();
            while(resultado.next()){
                empleado.add(new Empleado(resultado.getInt("idEmpleado"),resultado.getString("Nombre"),
                        resultado.getString("Apellido"),
                        resultado.getString("FechaInicio"),
                        resultado.getString("Estado"),
                        resultado.getString("idUsuario")));
            }
            listaEmpleado=FXCollections.observableArrayList(empleado);   
        } catch (Exception e) {
            e.printStackTrace();
        }
        cmbEmpleado.getItems().clear();
        cmbEmpleado.getItems().addAll(listaEmpleado);
        tblRetencion.setItems(getRetencion());
        colRetencionEmpleado.setCellValueFactory(new PropertyValueFactory<RetencionISR,String>("RetencionEmpleado"));
        colFechaRetencion.setCellValueFactory(new PropertyValueFactory<RetencionISR,String>("fechaRetencion"));
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<RetencionISR,String>("nombre"));
        colIdRetencion.setCellValueFactory(new PropertyValueFactory<RetencionISR,Integer>("idRetencionISR"));
        tblRetencion.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    public ObservableList getRetencion(){
        ObservableList listarBonificaciones = null;
        ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_listarRetencion}");
        try{
            ArrayList<RetencionISR> lista =new ArrayList<RetencionISR>();
            while(resultado.next()){
                lista.add(new RetencionISR(resultado.getInt("idRetencionISR"),
                        resultado.getDouble("RetencionEmpleado"),
                        resultado.getString("fechaRetencion"),
                        resultado.getString("nombre")
                ));
            }
            listarBonificaciones = FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listarBonificaciones;
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public Principal getPrincipal() {
        return principal;
    }
    
    public void guardar(){
        try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarRetencion(?,?)}");
                procedimiento.setInt("idEmpleado",((Empleado)cmbEmpleado.getValue()).getIdEmpleado());
                procedimiento.setString("RetencionEmpleado", txtRetencion.getText());
                procedimiento.execute();
                desactivarCampos();
                actualizarDatos();
                JOptionPane.showMessageDialog(null, "Registro almacenado Satisfactoriamente","Retencion",JOptionPane.INFORMATION_MESSAGE);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void bloqueo(){
        System.err.println("");
        System.err.println(":-:-:-:-:-:-:-:-:-: Recuerde que cada campo tiene Caracteres especificos :-:-:-:-:-:-:-:-:-:"); 
        System.err.println("");
        txtRetencion.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent eventoA) {
                    if (!eventoA.getCharacter().matches("[0-9-]")) {
                        eventoA.consume();
                    }
                }
            }
        );
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgRetorno.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/regresa.png").toString())); 
        actualizarDatos();
   }    
    
    public void campos(){
        txtRetencion.clear();
        cmbEmpleado.setValue(null);
    }
    
    private void desactivarCampos() {
        txtRetencion.setDisable(true);
        cmbEmpleado.setDisable(true);
    }
    
}