package br.edu.ifsul.aedesapp.Modelo;

import java.io.Serializable;

public class UsuarioAPI implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String token;
    private String senha;

    @Override
    public String toString() {
        return "UsuarioAPI{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", senha='" + senha + '\'' +
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
