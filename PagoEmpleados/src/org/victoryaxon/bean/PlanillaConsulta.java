package org.victoryaxon.bean;

/**
 *
 * @author viaro-participant
 */
public class PlanillaConsulta {
    private int idPlanilla;
    private String Nombre;
    private String Apellido;
    private String anio;
    private Double BonificacionEmpleada;
    private Double CuotaSalarioOrdinario;
    private Double total;
    private Double RetencionEmpleado;
    private Double CuotaIgss;
    private Double liquido;
    private String fechaGenerada;
    private String mes;

    public PlanillaConsulta() {
    }

    public PlanillaConsulta(int idPlanilla, String Nombre, String Apellido, String anio, Double BonificacionEmpleada, Double CuotaSalarioOrdinario, Double total, Double RetencionEmpleado, Double CuotaIgss, Double liquido, String fechaGenerada, String mes) {
        this.idPlanilla = idPlanilla;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.anio = anio;
        this.BonificacionEmpleada = BonificacionEmpleada;
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

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Double getBonificacionEmpleada() {
        return BonificacionEmpleada;
    }

    public void setBonificacionEmpleada(Double BonificacionEmpleada) {
        this.BonificacionEmpleada = BonificacionEmpleada;
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
    
    

}

