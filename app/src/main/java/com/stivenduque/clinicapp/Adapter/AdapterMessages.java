package com.stivenduque.clinicapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.stivenduque.clinicapp.Entidades.MessageToReceive;
import com.stivenduque.clinicapp.Entidades.Messages;
import com.stivenduque.clinicapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterMessages extends RecyclerView.Adapter<HolderMessages>{
    private List<MessageToReceive> messagesList = new ArrayList<>();
    private Context context;

    public AdapterMessages( Context context) {

        this.context = context;
    }

    public void addMessage(MessageToReceive messages){
        messagesList.add(messages);
        notifyItemInserted(messagesList.size());

    }

    @Override
    public HolderMessages onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_messages,parent, false);
        return new HolderMessages(view);
    }

    @Override
    public void onBindViewHolder(HolderMessages holder, int position) {
        holder.getTvName().setText(messagesList.get(position).getName());
        holder.getTvMessages().setText(messagesList.get(position).getMessages());

        if(messagesList.get(position).getType_message().equals("2")){
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getTvMessages().setVisibility(View.VISIBLE);
            Glide.with(context).load(messagesList.get(position).getUrlFoto()).into(holder.getFotoMensaje());
        }else if (messagesList.get(position).getType_message().equals("1")){
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getTvMessages().setVisibility(View.VISIBLE);
        }

        if (messagesList.get(position).getFotoPerfil().isEmpty()){
            holder.getFotoMensajePerfil().setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(messagesList.get(position).getFotoPerfil()).into(holder.getFotoMensajePerfil());
        }
        Long codHour = messagesList.get(position).getHour();
        Date date = new Date(codHour);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");

        holder.getTvHour().setText(simpleDateFormat.format(date));


    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
}
