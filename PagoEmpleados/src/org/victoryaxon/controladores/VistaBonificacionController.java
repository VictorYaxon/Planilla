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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import org.victoryaxon.bean.Bonificacion;
import org.victoryaxon.bean.Empleado;
import org.victoryaxon.db.Conexion;
import org.victoryaxon.sistema.Principal;

public class VistaBonificacionController implements Initializable {  
    private Principal principal;
    @FXML private ImageView imgRetorno;
    @FXML private TableView tblBonificacion;
    @FXML private TableColumn colidBonificacion;
    @FXML private TableColumn colBonificacionEmpleado;
    @FXML private TableColumn colFechaBonificacion;
    @FXML private TableColumn colIdEmpleado;
    @FXML private ComboBox cmbEmpleado;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private TextField txtBonificacion;
    ObservableList<Empleado> listaEmpleado;
    Bonificacion bonificacion;
    public void seleccionarElemento(){
        try {
            Bonificacion bonificacion =(Bonificacion)tblBonificacion.getSelectionModel().selectedItemProperty().get();
            txtBonificacion.setText(String.valueOf(bonificacion.getBonificacionEmpleado()));
            cmbEmpleado.setValue(bonificacion.getNombre());
            
        } catch (Exception e) {
            
        }
    }
    
    public void AgregarActivado(){
        visible();
        campos();
        btnEditar.setDisable(true);
    }
    
    public void eliminar(){
    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Registro", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblBonificacion.getSelectionModel().getSelectedItem() !=null){
                Bonificacion bonificaion =(Bonificacion)tblBonificacion.getSelectionModel().selectedItemProperty().get();
                try {
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarBonificaion(?)}");
                    procedimiento.setInt("idBonificacion",bonificaion.getIdBonificacion());
                    procedimiento.execute();
                    actualizarDatos();
                    JOptionPane.showMessageDialog(null, "Registro eliminado Satisfactoriamente","Empleado",JOptionPane.INFORMATION_MESSAGE);
                    desactivarCampos();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Perdone no se ha podido eliminar el registro","Empleado",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un registro","Empleado",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    
    public void modificar(){
        if (tblBonificacion.getSelectionModel().getSelectedItem() !=null) { 
            try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarBonificacion(?,?,?)}");
                Bonificacion bonificacion =(Bonificacion)tblBonificacion.getSelectionModel().selectedItemProperty().get();
                procedimiento.setInt("idBonificacion",bonificacion.getIdBonificacion());
                procedimiento.setString("BonificacionEmpleado",txtBonificacion.getText());
                procedimiento.setInt("idEmpleado",((Empleado)cmbEmpleado.getValue()).getIdEmpleado());
                procedimiento.execute();
                actualizarDatos();  
                JOptionPane.showMessageDialog(null, "Registro modificado Satisfactoriamente","Bonificacion",JOptionPane.INFORMATION_MESSAGE);
                desactivarCampos();
                
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Proveedor","Bonificacion",JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro","Bonificacion",JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    public void retornarVentana(){
        principal.ventanaMenu();
    }
    
    public void editarActivado(){
        btnEditar.setDisable(false);
        btnAgregar.setDisable(true);
        visible();
        campos();
    }
    
    public void visible(){
        bloqueo();
        txtBonificacion.setDisable(false);
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
        tblBonificacion.setItems(getBonificacion());
        colBonificacionEmpleado.setCellValueFactory(new PropertyValueFactory<Bonificacion,String>("BonificacionEmpleado"));
        colFechaBonificacion.setCellValueFactory(new PropertyValueFactory<Bonificacion,String>("fechaBonificacion"));
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<Bonificacion,String>("nombre"));
        colidBonificacion.setCellValueFactory(new PropertyValueFactory<Bonificacion,Integer>("idBonificacion"));
        tblBonificacion.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    public ObservableList getBonificacion(){
        ObservableList listarBonificaciones = null;
        ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call listarBonificacion}");
        try{
            ArrayList<Bonificacion> lista =new ArrayList<Bonificacion>();
            while(resultado.next()){
                lista.add(new Bonificacion(resultado.getInt("idBonificacion"),
                        resultado.getDouble("BonificacionEmpleado"),
                        resultado.getString("fechaBonificacion"),
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
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarBonificacion(?,?)}");
                procedimiento.setInt("idEmpleado",((Empleado)cmbEmpleado.getValue()).getIdEmpleado());
                procedimiento.setString("BonificacionEmpleado", txtBonificacion.getText());
                procedimiento.execute();
                desactivarCampos();
                actualizarDatos();
                JOptionPane.showMessageDialog(null, "Registro almacenado Satisfactoriamente","Bonificacion",JOptionPane.INFORMATION_MESSAGE);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void bloqueo(){
        System.err.println("");
        System.err.println(":-:-:-:-:-:-:-:-:-: Recuerde que cada campo tiene Caracteres especificos :-:-:-:-:-:-:-:-:-:"); 
        System.err.println("");
        txtBonificacion.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
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
        txtBonificacion.clear();
        cmbEmpleado.setValue(null);
    }
    
    private void desactivarCampos() {
        txtBonificacion.setDisable(true);
        cmbEmpleado.setDisable(true);
    }
    
}
