
package org.victoryaxon.bean;

/**
 *
 * @author viaro-participant
 */
public class SalarioOrdinario {
private int idSalarioOrdinario;
private String anio;        
private String CuotaSalarioOrdinario;

    public SalarioOrdinario() {
    }

    public SalarioOrdinario(int idSalarioOrdinario, String anio, String CuotaSalarioOrdinario) {
        this.idSalarioOrdinario = idSalarioOrdinario;
        this.anio = anio;
        this.CuotaSalarioOrdinario = CuotaSalarioOrdinario;
    }

    public int getIdSalarioOrdinario() {
        return idSalarioOrdinario;
    }

    public void setIdSalarioOrdinario(int idSalarioOrdinario) {
        this.idSalarioOrdinario = idSalarioOrdinario;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getCuotaSalarioOrdinario() {
        return CuotaSalarioOrdinario;
    }

    public void setCuotaSalarioOrdinario(String CuotaSalarioOrdinario) {
        this.CuotaSalarioOrdinario = CuotaSalarioOrdinario;
    }

    @Override
    public String toString() {
        return anio;
    }
}
