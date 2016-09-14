package org.victoryaxon.bean;
/**
 *
 * @author viaro-participant
 */
public class Usuario {
 private int idUsuario;
 private String Nombre;
 private String Apellido;
 private String Username;
 private String Password;

    public Usuario() {
    }

    public Usuario(int idUsuario, String Nombre, String Apellido, String Username, String Password) {
        this.idUsuario = idUsuario;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Username = Username;
        this.Password = Password;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @Override
    public String toString() {
        return  Nombre ;
    }
}
