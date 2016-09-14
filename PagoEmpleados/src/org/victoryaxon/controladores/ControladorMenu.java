package org.victoryaxon.controladores;

import java.net.URL;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.victoryaxon.sistema.Principal;

/**
 * FXML Controller class
 *
 * @author viaro-participant
 */

public class ControladorMenu implements Initializable {
private Principal principal;
@FXML private ImageView imgUsuarios;
@FXML private ImageView imgSalario;
@FXML private ImageView imgBonificacion;
@FXML private ImageView imgEmpleado;
@FXML private ImageView imgPlanilla;
@FXML private ImageView imgRetencion;
@FXML private ImageView imgIgss;
@FXML private ImageView imgLogout;

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    
    public void ventanaUsuarios(){
        principal.ventanaUsuarios();
    }
     
    public void ventanaEmpleados(){
        principal.ventanaEmpleados();
    }
    
    public void ventanaSalarioOrdinario(){
        principal.ventanaSalarioOrdinario();
    }
    
    public void ventanaPlanilla(){
        principal.ventanaPlanilla();
    }
    
    public void logout(){
        principal.ventanaLogin();
    }
    
    public void ventanaBonificacion(){
        principal.ventanaBonificacion();
    }
    
    public void ventanaRetencion(){
        principal.ventanaRetencion();
    }
    
    public void ventanaIgss(){
        principal.ventanaIgss();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgUsuarios.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/usuario.png").toString()));
        imgEmpleado.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/empleado.png").toString()));
        imgSalario.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/Salario.png").toString()));
        imgBonificacion.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/Bonificacion.png").toString()));
        imgIgss.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/Igss.png").toString()));
        imgRetencion.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/ISR.png").toString()));
        imgPlanilla.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/planilla.png").toString()));
        imgLogout.setImage(new Image(Principal.class.getResource("/org/victoryaxon/recursos/logout.png").toString()));
        
        
    }
    public void salir(){
    System.exit(0);
    }
}
