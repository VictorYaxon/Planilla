package org.victoryaxon.bean;

/**
 *
 * @author viaro-participant
 */
public class Bonificacion {
private int idBonificacion;
private Double BonificacionEmpleado;
private String fechaBonificacion;
private String nombre;

    public Bonificacion() {
    }

    public Bonificacion(int idBonificacion, Double BonificacionEmpleado, String fechaBonificacion, String nombre) {
        this.idBonificacion = idBonificacion;
        this.BonificacionEmpleado = BonificacionEmpleado;
        this.fechaBonificacion = fechaBonificacion;
        this.nombre = nombre;
    }

    public int getIdBonificacion() {
        return idBonificacion;
    }

    public void setIdBonificacion(int idBonificacion) {
        this.idBonificacion = idBonificacion;
    }

    public Double getBonificacionEmpleado() {
        return BonificacionEmpleado;
    }

    public void setBonificacionEmpleado(Double BonificacionEmpleado) {
        this.BonificacionEmpleado = BonificacionEmpleado;
    }

    public String getFechaBonificacion() {
        return fechaBonificacion;
    }

    public void setFechaBonificacion(String fechaBonificacion) {
        this.fechaBonificacion = fechaBonificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}
