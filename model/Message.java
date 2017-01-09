package com.mireal.admin.mireal7.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 04/01/2017.
 */

public class Message implements Serializable, Comparable<Message>{

    private int idt_message;
    private String mail_message;
    private String pseudo_message;
    private String contenu;
    private int nbAime;
    private String date;

    public Message(int idt_message, String mail_message, String pseudo_message, String contenu, int nbAime, String date) {
        this.idt_message = idt_message;
        this.mail_message = mail_message;
        this.pseudo_message = pseudo_message;
        this.contenu = contenu;
        this.nbAime = nbAime;
        this.date = date;
    }

    public int getIdt_message() {
        return idt_message;
    }

    public void setIdt_message(int idt_message) {
        this.idt_message = idt_message;
    }

    public String getMail_message() {
        return mail_message;
    }

    public void setMail_message(String mail_message) {
        this.mail_message = mail_message;
    }

    public String getPseudo_message() {
        return pseudo_message;
    }

    public void setPseudo_message(String pseudo_message) {
        this.pseudo_message = pseudo_message;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getNbAime() {
        return nbAime;
    }

    public void setNbAime(int nbAime) {
        this.nbAime = nbAime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){

        return idt_message + " / " + mail_message + " / " + pseudo_message + " / " + contenu + " / " + nbAime + " / " + date;

    }

    @Override
    public int compareTo(Message message) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/y HH:mm");
        int i=0;
        try {
            Date thisDate = format.parse(this.date);
            Date messageDate = format.parse(message.getDate());
            i=messageDate.compareTo(thisDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }
}
