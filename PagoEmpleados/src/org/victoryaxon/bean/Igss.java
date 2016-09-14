package org.victoryaxon.bean;

/**
 *
 * @author viaro-participant
 */
public class Igss {
private int idIgss;  
private String anio; 
private String Cuotaigss; 

    public Igss() {
    }

    public Igss(int idIgss, String anio, String Cuotaigss) {
        this.idIgss = idIgss;
        this.anio = anio;
        this.Cuotaigss = Cuotaigss;
    }

    public int getIdIgss() {
        return idIgss;
    }

    public void setIdIgss(int idIgss) {
        this.idIgss = idIgss;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getCuotaigss() {
        return Cuotaigss;
    }

    public void setCuotaigss(String Cuotaigss) {
        this.Cuotaigss = Cuotaigss;
    }


}
