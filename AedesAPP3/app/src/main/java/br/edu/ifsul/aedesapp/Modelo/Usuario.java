package br.edu.ifsul.aedesapp.Modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long _id;
    private String senha;
    private String token;
    private Integer denuncia_falsa = 0;
    private Prefeitura prefeitura;

    public void atualiza_denuncia_falsa(){
        denuncia_falsa++;
        if(denuncia_falsa == 3){
            //deixar usuario inativo
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "_id=" + _id +
                ", senha='" + senha + '\'' +
                ", token='" + token + '\'' +
                ", denuncia_falsa=" + denuncia_falsa +
                ", prefeitura=" + prefeitura +
                '}';
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getDenuncia_falsa() {
        return denuncia_falsa;
    }

    public void setDenuncia_falsa(Integer denuncia_falsa) {
        this.denuncia_falsa = denuncia_falsa;
    }

    public Prefeitura getPrefeitura() {
        return prefeitura;
    }

    public void setPrefeitura(Prefeitura prefeitura) {
        this.prefeitura = prefeitura;
    }
}
