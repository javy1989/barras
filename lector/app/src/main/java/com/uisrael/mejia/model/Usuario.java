package com.uisrael.mejia.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String usuario;
    private String pwd;

    public Usuario() {

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
