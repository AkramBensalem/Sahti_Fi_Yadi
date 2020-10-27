package com.gadg.sahtifiyadi.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gadg.sahtifiyadi.R;
import com.gadg.sahtifiyadi.login.addUtilisateur.AddUtilisateurActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    public EditText mail;
    public EditText pass, repeatPass, mobile, fullname;
   public FirebaseAuth firebaseAuth;
   private ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullname=(EditText) findViewById(R.id.full_name);
        pass=(EditText)findViewById(R.id.write_password);
        repeatPass = (EditText)findViewById(R.id.confrim_password);
        mobile = (EditText)findViewById(R.id.phone_number);
        mail = (EditText)findViewById(R.id.AddressEmail_deux);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void login(View view) {

        final String email =this.mail.getText().toString().trim();
        final String password =this.pass.getText().toString();
        final String name =this.fullname.getText().toString();
        final String phone =this.mobile.getText().toString();
        final String passRepeat = this.repeatPass.getText().toString();


        if (TextUtils.isEmpty(email)|| TextUtils.isEmpty(password) || TextUtils.isEmpty(passRepeat) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)){
            Toast.makeText(this,"un champ est vide",Toast.LENGTH_LONG).show();
        }else if (! password.equals(passRepeat)){
            Toast.makeText(this,"Vous devrez revérifier votre mot de pass",Toast.LENGTH_LONG).show();
        } else{

            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Creation de compte");
            progressDialog.setMessage("S'il vous plait attendez un peu ...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar);
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"s'incrire avec sucessé",Toast.LENGTH_LONG).show();
                        FirebaseUser user1=firebaseAuth.getCurrentUser();
                        user1.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>(){
                            public void onComplete( Task<Void> task){
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Vérifiez votre boit mail",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), AddUtilisateurActivity.class);
                                    intent.putExtra("NAME",name);
                                    intent.putExtra("EMAIL",email);
                                    intent.putExtra("PHONE",phone);
                                    startActivity(intent);
                                }else{
                                    String error=task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }else{
                        String error=task.getException().getMessage();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }

}
