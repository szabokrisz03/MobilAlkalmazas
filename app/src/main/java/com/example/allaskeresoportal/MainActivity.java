package com.example.allaskeresoportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final int REGISTER_KEY = 99110033;
    private static final int LOGIN_KEY = 111333555;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    public void showRegisterPage(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        registerIntent.putExtra("KEY", REGISTER_KEY);
        startActivity(registerIntent);
    }

    public void showLoginPage(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.putExtra("LOGIN_KEY", LOGIN_KEY);
        startActivity(loginIntent);
    }

    public void guestLogin(View view) {
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
}