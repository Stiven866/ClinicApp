package com.stivenduque.clinicapp.Entidades;

import java.util.Map;

public class MessageSend extends Messages{
    private Map Hour;

    public MessageSend(Map hour) {
        Hour = hour;
    }

    public MessageSend(String messages, String urlFoto, String name, String fotoPerfil, String type_message, Map hour) {
        super(messages, urlFoto, name, fotoPerfil, type_message);
        Hour = hour;
    }

    public MessageSend(String messages, String name, String fotoPerfil, String type_message, Map hour) {
        super(messages, name, fotoPerfil, type_message);
        Hour = hour;
    }

    public MessageSend() {
    }

    public Map getHour() {
        return Hour;
    }

    public void setHour(Map hour) {
        Hour = hour;
    }
}
