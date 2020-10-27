package com.gadg.sahtifiyadi.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gadg.sahtifiyadi.MainActivity;
import com.gadg.sahtifiyadi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.saveLoginState;
import static com.gadg.sahtifiyadi.utilities.SharedPreferencecUtilities.saveUserInfo;

public class LoginActivity extends AppCompatActivity {


    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar loadingProgressBar;
    private FirebaseAuth firebaseAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loadingProgressBar = findViewById(R.id.loading);
        firebaseAuth=FirebaseAuth.getInstance();


    }


    public void signin(View view) {
        String user=usernameEditText.getText().toString().trim();
        String pass=passwordEditText.getText().toString();
        if (TextUtils.isEmpty(user)|| TextUtils.isEmpty(pass)){
            Toast.makeText(this,"error",Toast.LENGTH_LONG).show();
        }else{
            loadingProgressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        saveUserInfo(getBaseContext(), FirebaseAuth.getInstance().getCurrentUser() != null);
                        loadingProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Connectez-vous avec succès",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else{
                        loadingProgressBar.setVisibility(View.GONE);
                        String error=task.getException().getMessage();
                        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    public void signUp(View view) {
       Intent intent=new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


    public void passwordOublie(View view) {
        final String user=usernameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(user)){
            Toast.makeText(this,"remplir votre mail",Toast.LENGTH_LONG).show();
        }else {
            AlertDialog message = new AlertDialog.Builder(this)
                    .setTitle("Avertissement")
                    .setMessage("Voullez vous vraiment changer votre mot de pass?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loadingProgressBar.setVisibility(View.VISIBLE);

                            firebaseAuth.sendPasswordResetEmail(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getBaseContext(),"vréfier votre mail",Toast.LENGTH_LONG).show();
                                        loadingProgressBar.setVisibility(View.GONE);

                                    }else {
                                        Toast.makeText(getBaseContext(),"echec d'envoyer le mot de passe",Toast.LENGTH_LONG).show();
                                        loadingProgressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                    })
                    .setNegativeButton("Non", null)
                    .setIcon(R.drawable.sign_echec)
                    .show();


        }
    }
}