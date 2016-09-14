
package org.victoryaxon.bean;

/**
 *
 * @author viaro-participant
 */
public class Empleado {
    
private int idEmpleado;
private String Nombre; 
private String Apellido;
private String FechaInicio;
private String Estado;
private String idUsuario;

    public Empleado() {
    }

    public Empleado(int idEmpleado, String Nombre, String Apellido, String FechaInicio, String Estado, String idUsuario) {
        this.idEmpleado = idEmpleado;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.FechaInicio = FechaInicio;
        this.Estado = Estado;
        this.idUsuario = idUsuario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return  Nombre + " " + Apellido;
    }
    
}
