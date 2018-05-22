package com.stivenduque.clinicapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stivenduque.clinicapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderMessages extends RecyclerView.ViewHolder{
    private TextView tvName;
    private TextView tvMessages;
    private TextView tvHour;
    private CircleImageView fotoMensajePerfil;
    private ImageView fotoMensaje;


    public HolderMessages(View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tv_name_message);
        tvMessages = itemView.findViewById(R.id.tv_message_message);
        tvHour = itemView.findViewById(R.id.tv_hour_message);
        fotoMensajePerfil = itemView.findViewById(R.id.foto_perfil_message);
        fotoMensaje = itemView.findViewById(R.id.message_foto);

    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public TextView getTvMessages() {
        return tvMessages;
    }

    public void setTvMessages(TextView tvMessages) {
        this.tvMessages = tvMessages;
    }

    public TextView getTvHour() {
        return tvHour;
    }

    public void setTvHour(TextView tvHour) {
        this.tvHour = tvHour;
    }

    public CircleImageView getFotoMensajePerfil() {
        return fotoMensajePerfil;
    }

    public void setFotoMensajePerfil(CircleImageView fotoMensaje) {
        this.fotoMensajePerfil = fotoMensaje;
    }

    public ImageView getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(ImageView fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }
}

