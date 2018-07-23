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


public class LoginActivity extends AppCompatActivity {
    Button button_register;
    Button button_login;
    private String email, password;
    EditText editText_email, editText_password;
    FirebaseAuth auth;
    ProgressBar progressBar;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, ExpanceManager.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        button_register = (Button) findViewById(R.id.button_register);
        button_login = (Button) findViewById(R.id.button_login);
        editText_email = (EditText) findViewById(R.id.edittext_emailaddress);
        editText_password = (EditText) findViewById(R.id.edittext_input_password);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsers();
            }
        });
    }

    private void loginUsers() {
        email = editText_email.getText().toString().trim();
        password = editText_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            editText_email.setError("Please Provide email addresss");
            editText_email.requestFocus();

        } else if (!email.equalsIgnoreCase(EMAIL_PATTERN)) {
            editText_email.setError("Please provide a valid email address");
            editText_email.requestFocus();
        }

        else if (TextUtils.isEmpty(password)) {
            editText_password.setError("Please Provide Your Password");
            editText_password.requestFocus();

        } else {
            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() < 6) {
                                    editText_password.setError(getString(R.string.minimum_password));
                                } else {
                                    Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Intent intent = new Intent(LoginActivity.this, ExpanceManager.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
    }
}
