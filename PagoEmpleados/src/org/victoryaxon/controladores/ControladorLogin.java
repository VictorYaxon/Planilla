package org.victoryaxon.controladores;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.victoryaxon.bean.Usuario;
import org.victoryaxon.db.Conexion;
import org.victoryaxon.sistema.Principal;
public class ControladorLogin implements Initializable {
@FXML private ImageView imgLogin;
Usuario usuario;
@FXML private Button btnLogin;
@FXML private TextField txtLogin;
@FXML private PasswordField txtPassword;
    private Principal principal;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    imgLogin.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/login.png").toString()));
    }
    public void ventanaMenu(){
       principal.ventanaMenu();
    }
    public void ventanaRegistro(){
       principal.ventanaRegistro();
    }
    public void login(){
        try {
            if (ValidandoLogin()) {
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AutenticarUsuario(?,?)}");
            procedimiento.setString("username",txtLogin.getText());
            procedimiento.setString("userPassword",txtPassword.getText());
            procedimiento.execute();
            ResultSet rs = procedimiento.getResultSet();
            if(rs != null) {
                while(rs.next()) {
                    usuario = new Usuario(rs.getInt("idUsuario"), 
                            rs.getString("Nombre"), 
                            rs.getString("Apellido"),
                            rs.getString("Username"),
                            rs.getString("Password"));
                }
            }
            if(usuario != null) {
                JOptionPane.showMessageDialog(null, "¡¡¡¡ Bienvenido(a) "+usuario.getNombre()+" "+ usuario.getApellido()+" !!!!","Login",JOptionPane.INFORMATION_MESSAGE);
                ventanaMenu();
            } else {
                System.out.println("Usuario Incorrecto");
                JOptionPane.showMessageDialog(null, "Error al Iniciar Sesion Usuario no Identificado","Login",JOptionPane.ERROR_MESSAGE);   
            }
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    public boolean ValidandoLogin(){
    if (txtLogin.getText() == null || txtLogin.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre de usuario","Usuarios",JOptionPane.ERROR_MESSAGE);
            txtLogin.requestFocus();
            return false;
    }
    if (txtPassword.getText() == null || txtPassword.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese una contraseña","Usuarios",JOptionPane.ERROR_MESSAGE);
            txtPassword.requestFocus();
            return false;
    }
    return true;
    }
    public void cerrar(){
        System.exit(0);
    }
    public Principal getPrincipal() {
        return principal;
    }
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
}