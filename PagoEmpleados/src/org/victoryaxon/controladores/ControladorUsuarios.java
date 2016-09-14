package org.victoryaxon.controladores;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.victoryaxon.db.Conexion;
import org.victoryaxon.bean.Usuario;
import org.victoryaxon.sistema.Principal;
public class ControladorUsuarios implements Initializable{
    private Principal principal;
    @FXML private ImageView imgRetorno;
    @FXML private TableView tblUsuarios;
    @FXML private TableColumn colIdUsuario;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colApellido;
    @FXML private TableColumn colUsername;
    @FXML private TableColumn colPassword;
    private Usuario usuario;
    public void initialize(URL url, ResourceBundle rb) {
        imgRetorno.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/regresa.png").toString()));
          actualizarDatos();
    }
    public Principal getPrincipal() {
        return principal;
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    public void actualizarDatos(){ 
        tblUsuarios.setItems(getUsuarios());
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Apellido"));
        colUsername.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Password"));
        tblUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    public ObservableList getUsuarios(){
        ObservableList listaUsuarios = null;
        ResultSet resultado = Conexion.getInstancia().ejecutarProcedimiento("{call sp_ListarUsuarios}");
        try{
            ArrayList<Usuario> lista =new ArrayList<Usuario>();
            while(resultado.next()){
                lista.add(new Usuario(resultado.getInt("idUsuario"),resultado.getString("nombre")
                        ,resultado.getString("apellido"),
                        resultado.getString("username"),
                        resultado.getString("password")));
            }
            listaUsuarios = FXCollections.observableArrayList(lista);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listaUsuarios;
    }
    public void seleccionarElemento(){
        try {
            Usuario usuario =(Usuario)tblUsuarios.getSelectionModel().selectedItemProperty().get();
            System.out.println("ID: " + usuario.getIdUsuario());
        } catch (Exception e) {

        }
    }
    public void retornarVentana(){
        principal.ventanaMenu();
    }  
}