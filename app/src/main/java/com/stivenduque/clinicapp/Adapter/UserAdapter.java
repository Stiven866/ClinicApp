package com.stivenduque.clinicapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stivenduque.clinicapp.Entidades.User;
import com.stivenduque.clinicapp.R;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{

    List<User> userList;
    public UserAdapter(List<User> userList){
        this.userList = userList;
    }



    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {

        holder.tvName.setText(userList.get(position).getName().toString());
        holder.tvPhone.setText(userList.get(position).getPhone().toString());
        holder.tvDocument.setText(userList.get(position).getId().toString());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder{

        TextView tvDocument, tvName,tvPhone;
        public UserHolder(View itemView) {
            super(itemView);

            tvDocument = itemView.findViewById(R.id.tv_document_my_medics);
            tvName = itemView.findViewById(R.id.tv_name_my_medics);
            tvPhone = itemView.findViewById(R.id.tv_phone_my_medics);
        }
    }
}
