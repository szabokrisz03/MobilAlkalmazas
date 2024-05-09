package com.example.allaskeresoportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private int registerKey = 0;
    private FirebaseAuth mAuth;

    EditText registerUsernameTextField;
    EditText registerPasswordTextField;
    EditText registerPasswordAgainTextField;
    EditText EmailAddressTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerKey = getIntent().getIntExtra("KEY", 0);
        if(registerKey != 99110033) {
            finish();
        }

        registerUsernameTextField = findViewById(R.id.registerUsernameTextField);
        registerPasswordTextField = findViewById(R.id.registerPasswordTextField);
        registerPasswordAgainTextField = findViewById(R.id.registerPasswordAgainTextField);
        EmailAddressTextView = findViewById(R.id.EmailAddressTextView);

        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(View view) {
        String userName = registerUsernameTextField.getText().toString();
        String password = registerPasswordTextField.getText().toString();
        String passwordConfirm = registerPasswordAgainTextField.getText().toString();
        String email = EmailAddressTextView.getText().toString();

        if(!password.equals(passwordConfirm)) {
            Snackbar.make(findViewById(R.id.register_page), "A jelszavak nem egyeznek", Snackbar.LENGTH_LONG).show();
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    goToLoggedUserMainPage();
                } else {
                    Snackbar.make(findViewById(R.id.register_page), "Hiba a regisztráció során", Snackbar.LENGTH_LONG).show();
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