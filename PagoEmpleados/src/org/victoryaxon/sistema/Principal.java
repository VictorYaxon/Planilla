package org.victoryaxon.sistema;
import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.victoryaxon.controladores.ControladorEmpleado;
import org.victoryaxon.controladores.ControladorIgss;
import org.victoryaxon.controladores.ControladorLogin;
import org.victoryaxon.controladores.ControladorMenu;
import org.victoryaxon.controladores.ControladorPlanilla;
import org.victoryaxon.controladores.ControladorRegistro;
import org.victoryaxon.controladores.ControladorRetencion;
import org.victoryaxon.controladores.ControladorSalarioOrdinario;
import org.victoryaxon.controladores.ControladorUsuarios;
import org.victoryaxon.controladores.VistaBonificacionController;
public class Principal extends Application {
    private Stage escenarioPrincipal;
    @Override
    public void start(Stage escenarioPrincipal){
        try {
            this.escenarioPrincipal = escenarioPrincipal;
            this.escenarioPrincipal.setTitle(".:.Planilla.:.");
            this.escenarioPrincipal.initStyle(StageStyle.UNDECORATED);
            ventanaLogin();
            this.escenarioPrincipal.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Initializable cambiarEscena(String nombreFXML, int ancho, int alto){
        FXMLLoader cargar = new FXMLLoader();
        try {
            InputStream archivoFXML = Principal.class.getResourceAsStream(nombreFXML);
            cargar.setBuilderFactory(new JavaFXBuilderFactory());
            cargar.setLocation(Principal.class.getResource(nombreFXML));
            AnchorPane panel=cargar.load(archivoFXML);
            Scene nuevaEscena = new Scene(panel,ancho,alto);
            escenarioPrincipal.setScene(nuevaEscena);
            nuevaEscena.getStylesheets().add("/org/victoryaxon/style/StyleSheet.css");
            escenarioPrincipal.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return(Initializable) cargar.getController();
    } 
    public static void main(String[] args) {
            launch(args);
    }
    public void ventanaUsuarios(){
        ControladorUsuarios usuarios = (ControladorUsuarios)cambiarEscena("/org/victoryaxon/vista/VistaUsuarios.fxml",824,657); 
        usuarios.setPrincipal(this);
    }
    
    public void ventanaPlanilla(){
        ControladorPlanilla planilla = (ControladorPlanilla)cambiarEscena("/org/victoryaxon/vista/VistaPlanilla.fxml",1100,670); 
        planilla.setPrincipal(this);
    }
    
    public void ventanaRegistro(){
        ControladorRegistro registro = (ControladorRegistro)cambiarEscena("/org/victoryaxon/vista/VistaRegistro.fxml",296,339); 
        registro.setPrincipal(this);
    }
    
    
    public void ventanaEmpleados(){
        ControladorEmpleado empleado = (ControladorEmpleado)cambiarEscena("/org/victoryaxon/vista/VistaEmpleado.fxml",824,657); 
        empleado.setPrincipal(this);
    }
    
    public void ventanaBonificacion(){
        VistaBonificacionController bonificacion = (VistaBonificacionController)cambiarEscena("/org/victoryaxon/vista/VistaBonificacion.fxml",824,657);
        bonificacion.setPrincipal(this);
    }
    
    public void ventanaRetencion(){
        ControladorRetencion retencion = (ControladorRetencion)cambiarEscena("/org/victoryaxon/vista/VistaRetencion.fxml",824,657);
        retencion.setPrincipal(this);
    }
    
    public void ventanaIgss(){
        ControladorIgss igss = (ControladorIgss)cambiarEscena("/org/victoryaxon/vista/VistaIgss.fxml",824,657); 
        igss.setPrincipal(this);
    }
    public void ventanaSalarioOrdinario(){
        ControladorSalarioOrdinario salario = (ControladorSalarioOrdinario)cambiarEscena("/org/victoryaxon/vista/VistaSalarioOrdinario.fxml",824,657); 
        salario.setPrincipal(this);
    }
    public void ventanaLogin (){
        ControladorLogin login = (ControladorLogin)cambiarEscena("/org/victoryaxon/vista/VistaLogin.fxml",371,270); 
        login.setPrincipal(this);
    }
    public void ventanaMenu (){
        ControladorMenu menu = (ControladorMenu)cambiarEscena("/org/victoryaxon/vista/VistaMenu.fxml",600,400); 
        menu.setPrincipal(this);
    }
    public Stage getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    public void setEscenarioPrincipal(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}