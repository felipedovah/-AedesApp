package br.edu.ifsul.aedesapp.Modelo;

import java.io.Serializable;
import java.util.List;

public class PrefeituraAPI implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String codigo;

    @Override
    public String toString() {
        return "PrefeituraAPI{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
