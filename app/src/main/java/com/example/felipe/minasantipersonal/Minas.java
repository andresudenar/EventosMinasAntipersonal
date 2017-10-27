package com.example.felipe.minasantipersonal;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by felipe on 17/10/17.
 */

public class Minas {

    @SerializedName("ano")
    @Expose
    private String ano;
    @SerializedName("codigodanedepartamento")
    @Expose
    private String codigodanedepartamento;
    @SerializedName("codigodanemunicipio")
    @Expose
    private String codigodanemunicipio;
    @SerializedName("departamento")
    @Expose
    private String departamento;
    @SerializedName("evento")
    @Expose
    private String evento;
    @SerializedName("latitudcabecera")
    @Expose
    private String latitudcabecera;
    @SerializedName("longitudcabecera")
    @Expose
    private String longitudcabecera;
    @SerializedName("mes")
    @Expose
    private String mes;
    @SerializedName("municipio")
    @Expose
    private String municipio;
    @SerializedName("sitio")
    @Expose
    private String sitio;
    @SerializedName("tipoarea")
    @Expose
    private String tipoarea;
    @SerializedName("tipoevento")
    @Expose
    private String tipoevento;
    @SerializedName("ubicaci_n")
    @Expose
    private UbicaciN ubicaciN;

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCodigodanedepartamento() {
        return codigodanedepartamento;
    }

    public void setCodigodanedepartamento(String codigodanedepartamento) {
        this.codigodanedepartamento = codigodanedepartamento;
    }

    public String getCodigodanemunicipio() {
        return codigodanemunicipio;
    }

    public void setCodigodanemunicipio(String codigodanemunicipio) {
        this.codigodanemunicipio = codigodanemunicipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getLatitudcabecera() {
        return latitudcabecera;
    }

    public void setLatitudcabecera(String latitudcabecera) {
        this.latitudcabecera = latitudcabecera;
    }

    public String getLongitudcabecera() {
        return longitudcabecera;
    }

    public void setLongitudcabecera(String longitudcabecera) {
        this.longitudcabecera = longitudcabecera;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getTipoarea() {
        return tipoarea;
    }

    public void setTipoarea(String tipoarea) {
        this.tipoarea = tipoarea;
    }

    public String getTipoevento() {
        return tipoevento;
    }

    public void setTipoevento(String tipoevento) {
        this.tipoevento = tipoevento;
    }

    public UbicaciN getUbicaciN() {
        return ubicaciN;
    }

    public void setUbicaciN(UbicaciN ubicaciN) {
        this.ubicaciN = ubicaciN;
    }

}
