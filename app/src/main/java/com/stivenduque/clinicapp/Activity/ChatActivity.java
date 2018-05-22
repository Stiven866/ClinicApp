package com.stivenduque.clinicapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.stivenduque.clinicapp.Adapter.AdapterMessages;
import com.stivenduque.clinicapp.Entidades.MessageSend;
import com.stivenduque.clinicapp.Entidades.MessageToReceive;
import com.stivenduque.clinicapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private CircleImageView fotoPerfil;
    private TextView tvName;
    private RecyclerView rvMessages;
    private EditText etMessages;
    private Button btnMessagesSend;
    private AdapterMessages adapterMessages;
    private ImageButton btnSendFoto;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL= 2;
    private String fotoPerfilCadena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fotoPerfil = findViewById(R.id.foto_perfil);
        tvName= findViewById(R.id.tv_name);
        rvMessages = findViewById(R.id.rv_messages);
        etMessages = findViewById(R.id.et_messages);
        btnMessagesSend = findViewById(R.id.btn_messages_send);
        btnSendFoto= findViewById(R.id.btn_send_foto);
        fotoPerfilCadena ="";

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");//Sala de chat(nombre)
        firebaseStorage = FirebaseStorage.getInstance();

        adapterMessages = new AdapterMessages(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMessages.setLayoutManager(linearLayoutManager);
        rvMessages.setAdapter(adapterMessages);

        btnMessagesSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new MessageSend(etMessages.getText().toString(),tvName.getText().toString(), fotoPerfilCadena, "1", ServerValue.TIMESTAMP));
                etMessages.setText("");
            }
        });


        btnSendFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,"Seleciona una foto"),PHOTO_SEND);

            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(intent,"Seleciona una foto"),PHOTO_PERFIL);

            }
        });

        adapterMessages.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageToReceive messages = dataSnapshot.getValue(MessageToReceive.class);
                adapterMessages.addMessage(messages);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setScrollbar(){
        rvMessages.scrollToPosition(adapterMessages.getItemCount()-1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK){
            Uri uri = data.getData();
            storageReference = firebaseStorage.getReference("imagenes_chat");//imagenes chat
            final StorageReference fotoReferencia = storageReference.child(uri.getLastPathSegment());
            fotoReferencia.putFile(uri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri uri = taskSnapshot.getDownloadUrl();
                    MessageSend messages  =new MessageSend("Stiven te ha enviado una foto",uri.toString(),tvName.getText().toString(),fotoPerfilCadena ,"2",ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(messages);
                }
            });

        }else if(requestCode == PHOTO_PERFIL && resultCode == RESULT_OK){
            Uri uri = data.getData();
            storageReference = firebaseStorage.getReference("foto_perfil");//imagenes chat
            final StorageReference fotoReferencia = storageReference.child(uri.getLastPathSegment());
            fotoReferencia.putFile(uri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri uri = taskSnapshot.getDownloadUrl();
                    fotoPerfilCadena = uri.toString();
                    MessageSend messages  =new MessageSend("Stiven ha actualizado su foto de perfil",uri.toString(),tvName.getText().toString(),fotoPerfilCadena ,"2",ServerValue.TIMESTAMP);
                    databaseReference.push().setValue(messages);
                    Glide.with(ChatActivity.this).load(uri.toString()).into(fotoPerfil);
                }
            });
        }
    }
}
