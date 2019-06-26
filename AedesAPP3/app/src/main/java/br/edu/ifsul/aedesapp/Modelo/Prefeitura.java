package br.edu.ifsul.aedesapp.Modelo;

import java.io.Serializable;
import java.util.List;

public class Prefeitura implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long _id;
    private List<Denuncia> denuncias;
    private String nome;

    @Override
    public String toString() {
        return "Denuncia "+ _id + ":\nStatus: " + nome ;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public List<Denuncia> getDenuncias() {
        return denuncias;
    }

    public void setDenuncias(List<Denuncia> denuncias) {
        this.denuncias = denuncias;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
