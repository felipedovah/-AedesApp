package br.edu.ifsul.aedesapp.Modelo;

import android.content.Intent;

import java.io.Serializable;
import java.util.Arrays;

public class Denuncia implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long _id;
    private Integer usuario_id;
    private String token;
    //private String data;
    private Double latitude;
    private Double longitude;
    private String observacoes;
    private Integer prefeitura_id;
    private byte[] imagem;

    public Denuncia() {
    }

    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }

    @Override
    public String toString() {
        return "Denuncia{" +
                "_id=" + _id +
                ", token='" + token + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", observacoes='" + observacoes + '\'' +
                ", prefeitura_id=" + prefeitura_id +
                ", imagem=" + imagem +
                '}';
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getPrefeitura_id() {
        return prefeitura_id;
    }

    public void setPrefeitura_id(Integer prefeitura_id) {
        this.prefeitura_id = prefeitura_id;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
