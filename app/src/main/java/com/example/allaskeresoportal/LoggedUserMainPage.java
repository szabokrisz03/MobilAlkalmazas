package com.example.allaskeresoportal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedUserMainPage extends AppCompatActivity {

    private static final String LOG_TAG = LoggedUserMainPage.class.getName();
    private FirebaseUser loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_user_main_page);

        loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        if(loggedUser != null) {
            Log.e(LOG_TAG, "loggedUserName: " + loggedUser.getEmail());
            Log.e(LOG_TAG, "loggedUserName: " + loggedUser.getDisplayName());
        } else {
            Log.e(LOG_TAG, "bas auth!");
            finish();
        }
    }
}