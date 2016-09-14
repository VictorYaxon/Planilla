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
import org.victoryaxon.bean.PlanillaConsulta;
import org.victoryaxon.bean.SalarioOrdinario;
import org.victoryaxon.db.Conexion;
import org.victoryaxon.sistema.Principal;

/**
 * FXML Controller class
 *
 * @author VictorYaxon
 */
public class ControladorPlanilla implements Initializable {
    @FXML private TableView tblPlanilla;
    @FXML private TableColumn colIdPlanilla;
    @FXML private TableColumn colNombre;        
    @FXML private TableColumn colApellido;
    @FXML private TableColumn colAnio;
    @FXML private TableColumn colBonificacion;        
    @FXML private TableColumn colSalarioOrdinario;        
    @FXML private TableColumn colTotal;
    @FXML private TableColumn colRetencion;
    @FXML private TableColumn colIgss;
    @FXML private TableColumn colLiquido;
    @FXML private TableColumn colFechaGenerada;        
    @FXML private TableColumn colMes;
    @FXML private Button btnConsultar;
    @FXML private Button btnAgregar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private ComboBox cbmAnio;
    @FXML private ComboBox cbmMes;
    @FXML private ImageView imgRetorno;
    @FXML private TextField txtSueldoOrdinario;
    @FXML private TextField txtBonificacion;
    @FXML private TextField txtRetencion;
    @FXML private TextField txtIgss;     
    private Principal principal;
    ObservableList<SalarioOrdinario> listaAnios;
    String x;
    String y;

    public Principal getPrincipal() {
        return principal;
    }
    
    public void ConsultarActivado(){
        cbmAnio.setDisable(false);
        cbmMes.setDisable(false);
        btnConsultar.setDisable(false);
        btnAgregar.setDisable(true);
    }
    
    public void seleccionarElemento(){
        try {
            PlanillaConsulta planilla =(PlanillaConsulta)tblPlanilla.getSelectionModel().selectedItemProperty().get();
            txtSueldoOrdinario.setText(planilla.getAnio());
            txtBonificacion.setText(planilla.getBonificacionEmpleada().toString());
            txtIgss.setText(planilla.getCuotaIgss().toString());
            txtRetencion.setText(planilla.getRetencionEmpleado().toString());
                    
        } catch (Exception e) {
            
        }
    }
    
    public void consultar(){
        btnAgregar.setDisable(true);
        x = (cbmAnio.getValue()).toString();
        y = (String)cbmMes.getValue();
        System.out.println("ESTE ES "+x);
        System.out.println("ESTE ES "+y);
        
        consultandoDatos();
        tblPlanilla.setDisable(false);
        desactivar();
    }
    
    public void retornarVentana(){
        principal.ventanaMenu();
    }
    
    public void agregando(){
        tblPlanilla.setItems(null);
        cbmAnio.setDisable(false);
        cbmMes.setDisable(false);
        btnAgregar.setDisable(false);
    }
    
    public void guardar(){
    try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AddPlanilla(?,?)}");
                procedimiento.setString("anio",cbmAnio.getValue().toString());
                procedimiento.setString("mes", cbmMes.getValue().toString());
                procedimiento.execute();
                
                JOptionPane.showMessageDialog(null, "Registro almacenado Satisfactoriamente","Planilla",JOptionPane.INFORMATION_MESSAGE);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void modificar(){
    if (tblPlanilla.getSelectionModel().getSelectedItem() !=null) { 
            try {
                CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_modificarPlanilla(?,?,?,?,?)}");
                PlanillaConsulta planilla =(PlanillaConsulta)tblPlanilla.getSelectionModel().selectedItemProperty().get();
                procedimiento.setInt("idPlanilla",planilla.getIdPlanilla());
                procedimiento.setString("SueldoOrdinario",txtSueldoOrdinario.getText());
                procedimiento.setString("Bonificacion", txtBonificacion.getText());
                procedimiento.setString("Retencion", txtRetencion.getText());
                procedimiento.setString("igss", txtIgss.getText());
                procedimiento.execute();
                consultar();
                txtBonificacion.setDisable(true);
                txtIgss.setDisable(true);
                txtRetencion.setDisable(true);
                txtSueldoOrdinario.setDisable(true);
                btnEditar.setDisable(true);
                JOptionPane.showMessageDialog(null, "Registro modificado Satisfactoriamente","Planilla",JOptionPane.INFORMATION_MESSAGE);                
        } catch (Exception e) {
            e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un registro","Planilla",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void eliminar(){
    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro?", "Eliminar Registro", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == 0){
            if(tblPlanilla.getSelectionModel().getSelectedItem() !=null){
                PlanillaConsulta planilla =(PlanillaConsulta)tblPlanilla.getSelectionModel().selectedItemProperty().get();
                try {
                    CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarPlanilla(?)}");
                    procedimiento.setInt("idPlanilla",planilla.getIdPlanilla());
                    procedimiento.execute();
                    consultandoDatos();
                    btnEliminar.setDisable(true);
                    JOptionPane.showMessageDialog(null, "Registro eliminado Satisfactoriamente","Planilla",JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Perdone no se ha podido eliminar el registro","Planilla",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un registro","Empleado",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void visible(){
        bloqueo();
        txtBonificacion.setDisable(false);
        txtIgss.setDisable(false);
        txtRetencion.setDisable(false);
        txtSueldoOrdinario.setDisable(false);
        btnEditar.setDisable(false);
        tblPlanilla.setDisable(false);
        btnAgregar.setDisable(true);
        btnConsultar.setDisable(true);
    }
    
    

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     imgRetorno.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/regresa.png").toString()));
     datosAnios();
    }
   
    
    public void consultandoDatos(){
        tblPlanilla.setItems(getPlanilla(x, y));
        colIdPlanilla.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, Integer>("idPlanilla"));
        colNombre.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, String>("Nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, String>("Apellido"));
        colAnio.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, String>("anio"));
        colBonificacion.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, Double>("BonificacionEmpleada"));
        colSalarioOrdinario.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, Double>("CuotaSalarioOrdinario"));
        colTotal.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, Double>("total"));
        colRetencion.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, Double>("RetencionEmpleado"));
        colIgss.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, Double>("CuotaIgss"));
        colLiquido.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, Double>("liquido"));
        colFechaGenerada.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, String>("fechaGenerada"));
        colMes.setCellValueFactory(new PropertyValueFactory<PlanillaConsulta, String>("mes"));
        tblPlanilla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    public void desactivar(){
       cbmAnio.setDisable(true);
       cbmMes.setDisable(true);
       btnConsultar.setDisable(true);
    }
    
    public ObservableList getPlanilla(String x,String y){
        System.out.println("ESTO ES LO QUE BUSCAS " + x + y);
        ObservableList listaUsuarios = null;
        try{
        CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarPlanilla(?,?)}");
        System.out.println("ESTO ES LO QUE BUSCAS " + x + y);
        procedimiento.setString("anio",x);
        procedimiento.setString("mes",y);
        procedimiento.execute();
            ResultSet resultado = procedimiento.getResultSet();
            ArrayList<PlanillaConsulta> lista =new ArrayList<PlanillaConsulta>();
            while(resultado.next()){
                lista.add(new PlanillaConsulta(resultado.getInt("idPlanilla"),
                        resultado.getString("Nombre"),
                        resultado.getString("Apellido"),
                        resultado.getString("anio"),
                        resultado.getDouble("BonificacionEmpleado"),
                        resultado.getDouble("CuotaSalarioOrdinario"),
                        resultado.getDouble("total"),
                        resultado.getDouble("RetencionEmpleado"),
                        resultado.getDouble("CuotaIgss"),
                        resultado.getDouble("liquido"),
                        resultado.getString("fechaGenerada"),
                        resultado.getString("mes")
                ));
                
            }
            listaUsuarios = FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listaUsuarios;
    }
    public void datosAnios(){
    cbmMes.getItems().addAll(meses);
    listaAnios=FXCollections.observableArrayList();
        try {
            ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_listarAnios}");
            ArrayList<SalarioOrdinario> salario = new ArrayList<SalarioOrdinario>();
            while(resultado.next()){
                salario.add(new SalarioOrdinario(resultado.getInt("idSalarioOrdinario"),resultado.getString("anio"),
                        resultado.getString("CuotaSalarioOrdinario")));
            }
            listaAnios=FXCollections.observableArrayList(salario);   
        } catch (Exception e) {
            e.printStackTrace();
        }
        cbmAnio.getItems().clear();
        cbmAnio.getItems().addAll(listaAnios);
    }
    
    ObservableList<String> meses = 
        FXCollections.observableArrayList("Enero","Febreo","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
    final ComboBox comboBox = new ComboBox(meses);
    
    public void bloqueo(){
        txtBonificacion.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent eventoA) {
                    if (!eventoA.getCharacter().matches("[0-9]")) {
                        eventoA.consume();
                    }
                }
        });
        txtIgss.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent eventoA) {
                    if (!eventoA.getCharacter().matches("[0-9.]")) {
                        eventoA.consume();
                    }
                }
        });
        txtRetencion.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent eventoA) {
                    if (!eventoA.getCharacter().matches("[0-9.]")) {
                        eventoA.consume();
                    }
                }
        });
        txtSueldoOrdinario.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent eventoA) {
                    if (!eventoA.getCharacter().matches("[0-9.]")) {
                        eventoA.consume();
                    }
                }
        });
    }
    
    
}
