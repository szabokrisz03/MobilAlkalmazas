package com.example.allaskeresoportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private int loginKey = 0;
    private EditText loginUsernameTextField;
    private EditText loginPasswordTextField;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginKey = getIntent().getIntExtra("LOGIN_KEY", 0);
        if(loginKey != 111333555) {
            finish();
        }

        loginUsernameTextField = findViewById(R.id.loginUsernameTextField);
        loginPasswordTextField = findViewById(R.id.loginPasswordTextField);

        mAuth = FirebaseAuth.getInstance();
    }

    public void loginUser(View view) {
        String userName = loginUsernameTextField.getText().toString();
        String password = loginPasswordTextField.getText().toString();

        mAuth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    goToLoggedUserMainPage();
                } else {
                    //TODO: alert
                }
            }
        });
    }

    public void goToLoggedUserMainPage() {
        Intent intent = new Intent(this, LoggedUserMainPage.class);
        startActivity(intent);
    }

    public void backToMainPage(View view) {
        finish();
    }
}