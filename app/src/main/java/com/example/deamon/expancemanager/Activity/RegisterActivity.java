package com.example.deamon.expancemanager.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.deamon.expancemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    Button button_login;
    Button button_register, button_reset_password;
    EditText editText_email, editText_password, editText_conformPassword;
    private String email, password, conformpassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);
        editText_email = (EditText) findViewById(R.id.edittext_email);
        editText_password = (EditText) findViewById(R.id.edittext_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        button_reset_password = (Button) findViewById(R.id.button_reset_password);
        editText_conformPassword = (EditText) findViewById(R.id.edittext_conformpassword);
        button_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, ResetPasswordActivity.class);
                startActivity(intent);

            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUsers();
            }
        });
    }

    /*
        Firebaes Users  Registraction Process is starting Now

     */
    private void registerUsers() {
        email = editText_email.getText().toString().trim();
        password = editText_password.getText().toString().trim();
        conformpassword = editText_conformPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editText_email.setError("Please Enter Your Email Address");
            editText_email.requestFocus();

        }else if (!email.equalsIgnoreCase(EMAIL_PATTERN)) {
            editText_email.setError("Please provide Valid email address");
            editText_email.requestFocus();

        }

        else if (TextUtils.isEmpty(password)) {
            editText_password.setError("Please Enter Your Password");
            editText_password.requestFocus();

        } else if (TextUtils.isEmpty(conformpassword)) {
            editText_conformPassword.setError("Conform Password");
            editText_conformPassword.requestFocus();
        } else if (!password.equalsIgnoreCase(conformpassword)) {
            editText_conformPassword.setError("Conform Your Password");
            editText_conformPassword.requestFocus();
        } else {

            progressBar.setVisibility(View.VISIBLE);
            //create user
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }
                        }

                    });
            /*
                Firebase Users Registration Process is complete Now

             */
        }
    }
}
