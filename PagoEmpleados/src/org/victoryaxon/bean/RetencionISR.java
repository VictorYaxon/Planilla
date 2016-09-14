package org.victoryaxon.bean;

/**
 *
 * @author viaro-participant
 */
public class RetencionISR {
private int idRetencionISR;
private Double RetencionEmpleado;
private String fechaRetencion;
private String nombre;

    public RetencionISR() {
    }

    public RetencionISR(int idRetencionISR, Double RetencionEmpleado, String fechaRetencion, String nombre) {
        this.idRetencionISR = idRetencionISR;
        this.RetencionEmpleado = RetencionEmpleado;
        this.fechaRetencion = fechaRetencion;
        this.nombre = nombre;
    }

    public int getIdRetencionISR() {
        return idRetencionISR;
    }

    public void setIdRetencionISR(int idRetencionISR) {
        this.idRetencionISR = idRetencionISR;
    }

    public Double getRetencionEmpleado() {
        return RetencionEmpleado;
    }

    public void setRetencionEmpleado(Double RetencionEmpleado) {
        this.RetencionEmpleado = RetencionEmpleado;
    }

    public String getFechaRetencion() {
        return fechaRetencion;
    }

    public void setFechaRetencion(String fechaRetencion) {
        this.fechaRetencion = fechaRetencion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
}
