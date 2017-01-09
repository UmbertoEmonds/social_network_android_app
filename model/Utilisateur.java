package com.mireal.admin.mireal7.model;

import java.io.Serializable;

/**
 * Created by admin on 05/01/2017.
 */

public class Utilisateur implements Serializable {

    private int idt;
    private String mail_utilisateur;
    private String pseudo;
    private String descripion;

    public Utilisateur(int idt, String mail_utilisateur, String pseudo, String descripion) {
        this.idt = idt;
        this.mail_utilisateur = mail_utilisateur;
        this.pseudo = pseudo;
        this.descripion = descripion;
    }

    public int getIdt() {
        return idt;
    }

    public void setIdt(int idt) {
        this.idt = idt;
    }

    public String getMail_utilisateur() {
        return mail_utilisateur;
    }

    public void setMail_utilisateur(String mail_utilisateur) {
        this.mail_utilisateur = mail_utilisateur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }

    @Override
    public String toString(){

        return idt + " / " + mail_utilisateur + " / " + pseudo + " / " + descripion;

    }
}
