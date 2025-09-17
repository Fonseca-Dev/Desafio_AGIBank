package com.example.app_agibank;

import java.io.Serializable;

public class OpcaoItem implements Serializable {
    private String nome;
    private int icone;
    private boolean ativo = true; // por padrão ativo

    public OpcaoItem(String nome, int icone) {
        this.nome = nome;
        this.icone = icone;
    }

    public String getNome() { return nome; }
    public int getIcone() { return icone; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OpcaoItem)) return false;
        return nome.equals(((OpcaoItem)obj).getNome());
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

}
