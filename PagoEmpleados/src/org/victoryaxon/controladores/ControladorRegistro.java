/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.victoryaxon.controladores;

import java.net.URL;
import java.sql.CallableStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.victoryaxon.db.Conexion;
import org.victoryaxon.sistema.Principal;

/**
 *
 * @author VictorYaxon
 */
public class ControladorRegistro implements Initializable{
    @FXML private ImageView imgUsuarios;
    @FXML private TextField txtUsername;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private PasswordField txtContraseña;
    private Principal principal;
    public void cancelar(){
        principal.ventanaLogin();
    }
    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    
    
    public void guardar(){
        try {
            if (validandoUsuario()) {
            CallableStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarUsuarios(?,?,?,?)}");
            procedimiento.setString("Nombre", txtNombre.getText());
            procedimiento.setString("Apellido", txtApellido.getText());
            procedimiento.setString("username", txtUsername.getText());
            procedimiento.setString("userPassword", txtContraseña.getText());
            procedimiento.execute();
            JOptionPane.showMessageDialog(null, "Ahora ingrese su usuario y contraseña en el modulo Login","Usuario",JOptionPane.INFORMATION_MESSAGE);
            cancelar();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al ingresar un Usuario","Usuarios",JOptionPane.ERROR_MESSAGE);
        }
        
    }
public boolean validandoUsuario(){
    if (txtUsername.getText() == null || txtUsername.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre de usuario","Usuarios",JOptionPane.ERROR_MESSAGE);
            txtUsername.requestFocus();
            return false;
    }
    if (txtApellido.getText() == null || txtApellido.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese un Apellido","Usuarios",JOptionPane.ERROR_MESSAGE);
            txtApellido.requestFocus();
            return false;
    }
    if (txtNombre.getText() == null || txtNombre.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese un Nombre","Usuarios",JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return false;
    }
    if (txtContraseña.getText() == null || txtContraseña.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese una contraseña","Usuarios",JOptionPane.ERROR_MESSAGE);
            txtContraseña.requestFocus();
            return false;
    }
    return true;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgUsuarios.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/usuario.png").toString()));
    }

}