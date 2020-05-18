package com.example.loginwea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registrar extends AppCompatActivity implements View.OnClickListener {
    EditText us, pas, nom, ap;
    Button reg, can;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        us =(EditText) findViewById(R.id.RegUser);
        pas =(EditText) findViewById(R.id.RegPass);
        nom =(EditText) findViewById(R.id.RegNombre);
        ap =(EditText) findViewById(R.id.RegApellido);
        reg =(Button) findViewById(R.id.btnRegRegistrar);
        can =(Button) findViewById(R.id.btnRegCancelar);
        reg.setOnClickListener(this);
        can.setOnClickListener(this);
        dao = new daoUsuario(this);

    }

    public void onClick(View v){
       switch (v.getId()) {
           case R.id.btnRegRegistrar:
               Usuario u=new Usuario();
               u.setUsuario(us.getText().toString());
               u.setPassword(pas.getText().toString());
               u.setNombre(nom.getText().toString());
               u.setApellidos(ap.getText().toString());
               if(!u.isNull()){
                   Toast.makeText(this, "Error: campos vacios", Toast.LENGTH_LONG).show();
               }else if (dao.insertUsuario(u)){
                   Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_LONG).show();
                   Intent i2=new Intent(Registrar.this, MainActivity.class);
                   startActivity(i2);
                   finish();
               }else{
                   Toast.makeText(this, "USUARIO REGISTRADO", Toast.LENGTH_LONG).show();
               }

               break;

           case R.id.btnRegCancelar:
               Intent i=new Intent(Registrar.this,MainActivity.class);
               startActivity(i);
               finish();
               break;
           }
       }

    }



