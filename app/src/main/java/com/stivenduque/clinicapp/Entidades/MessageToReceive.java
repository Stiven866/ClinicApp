package com.stivenduque.clinicapp.Entidades;

import android.util.Log;

public class MessageToReceive extends Messages{
    private Long hour;

    public MessageToReceive(Long hour) {
        this.hour = hour;
    }

    public MessageToReceive() {
    }

    public MessageToReceive(String messages, String urlFoto, String name, String fotoPerfil, String type_message, Long hour) {
        super(messages, urlFoto, name, fotoPerfil, type_message);
        this.hour = hour;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }
}
