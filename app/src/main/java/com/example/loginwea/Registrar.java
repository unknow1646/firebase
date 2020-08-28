package com.example.loginwea;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonRegister;

    //VARIABLES DE LOS DATOS QUE VAMOS A REGISTRAR
    private String name = "";
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        mEditTextName= (EditText) findViewById(R.id.RegNombre);
        mEditTextEmail= (EditText) findViewById(R.id.RegEmail);
        mEditTextPassword= (EditText) findViewById(R.id.RegPass);
        mButtonRegister = (Button) findViewById(R.id.btnRegRegistrar);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mEditTextName.getText().toString();
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if(password.length()>=6){
                        registerUser();
                    }
                    else {
                        Toast.makeText(Registrar.this, "La contraseña debe tener 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(Registrar.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("name",name);
                    map.put("email",email);
                    map.put("password",password);

                    String id= mAuth.getCurrentUser().getUid();


                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(Registrar.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registrar.this, MainActivity.class));
                                finish();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(Registrar.this, "CAMPOS INGRESADOS INCORRECTOS", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void onClick(View v){
       switch (v.getId()) {

           case R.id.btnRegCancelar:
               Intent i=new Intent(Registrar.this,MainActivity.class);
               startActivity(i);
               finish();
               break;
           }
       }

    }



