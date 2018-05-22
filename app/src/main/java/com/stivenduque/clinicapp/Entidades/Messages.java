package com.stivenduque.clinicapp.Entidades;

public class Messages {
    private String messages;
    private String urlFoto;
    private String name;
    private String fotoPerfil;
    private String type_message;

    public Messages() {
    }

    public Messages(String messages, String urlFoto, String name, String fotoPerfil, String type_message) {
        this.messages = messages;
        this.urlFoto = urlFoto;
        this.name = name;
        this.fotoPerfil = fotoPerfil;
        this.type_message = type_message;
    }

    public Messages(String messages, String name, String fotoPerfil, String type_message) {
        this.messages = messages;
        this.name = name;
        this.fotoPerfil = fotoPerfil;
        this.type_message = type_message;

    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getType_message() {
        return type_message;
    }

    public void setType_message(String type_message) {
        this.type_message = type_message;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
