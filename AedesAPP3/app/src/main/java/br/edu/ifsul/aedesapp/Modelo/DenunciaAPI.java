package br.edu.ifsul.aedesapp.Modelo;

import android.content.Intent;

import java.io.Serializable;
import java.util.Date;

public class DenunciaAPI implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer status;
    private Double latitude;
    private Double longitude;
    private String imagem;
    private String dataCriacao;
    private String observacoes;
    private String protocolo;
    private PrefeituraAPI prefeituraId;
    private UsuarioAPI usuarioId;

    @Override
    public String toString() {
        return "DenunciaAPI{" +
                "id=" + id +
                ", status=" + status +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", imagem='" + imagem + '\'' +
                ", dataCriacao='" + dataCriacao + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", protocolo='" + protocolo + '\'' +
                ", prefeituraId=" + prefeituraId +
                ", usuarioId=" + usuarioId +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public PrefeituraAPI getPrefeituraId() {
        return prefeituraId;
    }

    public void setPrefeituraId(PrefeituraAPI prefeituraId) {
        this.prefeituraId = prefeituraId;
    }

    public UsuarioAPI getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UsuarioAPI usuarioId) {
        this.usuarioId = usuarioId;
    }
}


