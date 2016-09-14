package org.victoryaxon.controladores;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.victoryaxon.bean.Empleado;
import org.victoryaxon.bean.Usuario;
import org.victoryaxon.db.Conexion;
import org.victoryaxon.sistema.Principal;
/**
 * FXML Controller class
 *
 * @author viaro-participant
 */
public class ControladorEmpleado implements Initializable {
    private Principal principal;
    private Usuario usuario;
    private Empleado empleado;
    @FXML private ComboBox cmbEstado;
    @FXML private DatePicker datepicker;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtBonificacion;
    @FXML private TextField txtFechaInicio;
    @FXML private Button btnAgregar;
    @FXML private Button btnEditar;
    @FXML private ImageView imgRetorno;
    @FXML private Button btnEliminar;
    @FXML private TableView tblEmpleado; 
    @FXML private TableColumn colIdEmpleado;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colApellido;
    @FXML private TableColumn colFechaInicio;
    @FXML private TableColumn colEstado;
    @FXML private TableColumn colEncargado;
    public void initialize(URL url, ResourceBundle rb) {
        imgRetorno.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/regresa.png").toString()));
        actualizarDatos();
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public Principal getPrincipal() {
        return principal;
    }
    public void actualizarDatos(){ 
        cmbEstado.setItems(options);
        tblEmpleado.setItems(getUsuarios());
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("idEmpleado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("Nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("Apellido"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<Empleado, String>("FechaInicio"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("Estado"));
        colEncargado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("idUsuario"));
        tblEmpleado.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    public ObservableList getUsuarios(){
        ObservableList listaUsuarios = null;
        ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_ListarEmpleados}");
        try{
            ArrayList<Empleado> lista =new ArrayList<Empleado>();
            while(resultado.next()){
                lista.add(new Empleado(resultado.getInt("idEmpleado"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getString("FechaInicio"),
                        resultado.getString("Estado"),
                        resultado.getString("idUsuario")));
            }
            listaUsuarios = FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listaUsuarios;
    }
    
    public void seleccionarElemento(){
        try {
            Empleado empleado =(Empleado)tblEmpleado.getSelectionModel().selectedItemProperty().get();
            System.out.println("ID: " + empleado.getIdEmpleado());
            txtApellido.setText(empleado.getApellido());
            txtNombre.setText(empleado.getNombre());
            txtFechaInicio.setText(empleado.getFechaInicio());
            cmbEstado.setValue(empleado.getEstado());
        } catch (Exception e) {

        }
    }
    ObservableList<String> options = 
        FXCollections.observableArrayList("Activo","Inactivo");
    final ComboBox comboBox = new ComboBox(options);
    
    public void retornarVentana(){
        principal.ventanaMenu();
    } 
    
    public void agregando(){
        limpiarCampos();
        visible();
    
    }
    
    public void visible(){
        txtApellido.setDisable(false);
        txtNombre.setDisable(false);
        cmbEstado.setDisable(false);
        datepicker.setDisable(false);
    }
    
    public void datos(){
        txtApellido.setText(empleado.getApellido());
        txtNombre.setText(empleado.getNombre());
        cmbEstado.setValue(empleado.getEstado());
        datepicker.setValue(LocalDate.now());
    }
    public void guardar(){
        try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?)}");
                procedimiento.setString("Nombre",txtNombre.getText());
                procedimiento.setString("Apellido", txtApellido.getText());
                procedimiento.setString("Estado", (String) cmbEstado.getValue());
                procedimiento.setString("FechaInicio",datepicker.getValue().toString());
                procedimiento.setInt("idUsuario", 1);
                procedimiento.execute();
                desactivarCampos();
                actualizarDatos();
                JOptionPane.showMessageDialog(null, "Registro almacenado Satisfactoriamente","Compra",JOptionPane.INFORMATION_MESSAGE);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modificar(){
        if (tblEmpleado.getSelectionModel().getSelectedItem() !=null) { 
            try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarEmpleado(?,?,?,?,?)}");
                Empleado empleado =(Empleado)tblEmpleado.getSelectionModel().selectedItemProperty().get();
                procedimiento.setInt("idEmpleado",empleado.getIdEmpleado());
                procedimiento.setString("Nombre",txtNombre.getText());
                procedimiento.setString("Apellido", txtApellido.getText());
                procedimiento.setString("Estado", (String)cmbEstado.getValue());
                procedimiento.setInt("idUsuario",1);
                procedimiento.execute();
                desactivarCampos();
                actualizarDatos();  
                JOptionPane.showMessageDialog(null, "Registro modificado Satisfactoriamente","Empleado",JOptionPane.INFORMATION_MESSAGE);                
        } catch (Exception e) {
            e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro","Empleado",JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    public void desactivarCampos(){
     txtApellido.setDisable(true);
     txtNombre.setDisable(true);
     cmbEstado.setDisable(true);
     datepicker.setDisable(true);
    }
    
    public void limpiarCampos(){
    txtApellido.clear();
    txtNombre.clear();
    cmbEstado.setValue(null);
    datepicker.setValue(null);
    }
    
    public void eliminar(){
    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Registro", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblEmpleado.getSelectionModel().getSelectedItem() !=null){
                Empleado empleado =(Empleado)tblEmpleado.getSelectionModel().selectedItemProperty().get();
                try {
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarEmpleados(?)}");
                    procedimiento.setInt("idEmpleado",empleado.getIdEmpleado());
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
    
}