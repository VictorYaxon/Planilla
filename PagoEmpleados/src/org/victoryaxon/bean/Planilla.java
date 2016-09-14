package org.victoryaxon.bean;

/**
 *
 * @author viaro-participant
 */
public class Planilla {

private int idPlanilla;
private int idEmpleado;
private Double BonificacionEmpleado;
private int anio;
private Double CuotaSalarioOrdinario;
private Double total;
private Double RetencionEmpleado;
private Double CuotaIgss;
private Double liquido;
private String fechaGenerada;
private String mes;

    public Planilla(int idPlanilla, int idEmpleado, Double BonificacionEmpleado, int anio, Double CuotaSalarioOrdinario, Double total, Double RetencionEmpleado, Double CuotaIgss, Double liquido, String fechaGenerada, String mes) {
        this.idPlanilla = idPlanilla;
        this.idEmpleado = idEmpleado;
        this.BonificacionEmpleado = BonificacionEmpleado;
        this.anio = anio;
        this.CuotaSalarioOrdinario = CuotaSalarioOrdinario;
        this.total = total;
        this.RetencionEmpleado = RetencionEmpleado;
        this.CuotaIgss = CuotaIgss;
        this.liquido = liquido;
        this.fechaGenerada = fechaGenerada;
        this.mes = mes;
    }

    public int getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(int idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Double getBonificacionEmpleado() {
        return BonificacionEmpleado;
    }

    public void setBonificacionEmpleado(Double BonificacionEmpleado) {
        this.BonificacionEmpleado = BonificacionEmpleado;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Double getCuotaSalarioOrdinario() {
        return CuotaSalarioOrdinario;
    }

    public void setCuotaSalarioOrdinario(Double CuotaSalarioOrdinario) {
        this.CuotaSalarioOrdinario = CuotaSalarioOrdinario;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getRetencionEmpleado() {
        return RetencionEmpleado;
    }

    public void setRetencionEmpleado(Double RetencionEmpleado) {
        this.RetencionEmpleado = RetencionEmpleado;
    }

    public Double getCuotaIgss() {
        return CuotaIgss;
    }

    public void setCuotaIgss(Double CuotaIgss) {
        this.CuotaIgss = CuotaIgss;
    }

    public Double getLiquido() {
        return liquido;
    }

    public void setLiquido(Double liquido) {
        this.liquido = liquido;
    }

    public String getFechaGenerada() {
        return fechaGenerada;
    }

    public void setFechaGenerada(String fechaGenerada) {
        this.fechaGenerada = fechaGenerada;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Planilla() {
    }


}

