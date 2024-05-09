package com.example.allaskeresoportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddJobActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddJobActivity.class.getName();
    private EditText addJobNameTextView;
    private EditText addJobPaymentTextView;
    private EditText addJobShortDescTextView;
    private EditText addJobLongDescTextView;

    private AndroidJobAdapter androidJobAdapter;
    private String loggedUserEmail;
    private FirebaseFirestore database;
    private CollectionReference databaseItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        loggedUserEmail = getIntent().getStringExtra("LOGGEDUSEREMAIL");
        if(!Objects.equals(loggedUserEmail, "")) {
            Log.e(LOG_TAG, "loggedUserName: " + loggedUserEmail);
        } else {
            Log.e(LOG_TAG, "bas auth!");
            finish();
        }

        addJobNameTextView = findViewById(R.id.addJobNameTextView);
        addJobPaymentTextView = findViewById(R.id.addJobPaymentTextView);
        addJobShortDescTextView = findViewById(R.id.addJobShortDescTextView);
        addJobLongDescTextView = findViewById(R.id.addJobLongDescTextView);

        database = FirebaseFirestore.getInstance();
        databaseItems = database.collection("Jobs");
    }

    public void addJobToDatabase(View view) {
        try {
            String jobName = addJobNameTextView.getText().toString();
            String jobPaymentStr = addJobPaymentTextView.getText().toString();
            String jobShortDesc = addJobShortDescTextView.getText().toString();
            String jobLongDesc = addJobLongDescTextView.getText().toString();
            int jobPayment = -1;

            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(date);

            try {
                jobPayment = Integer.parseInt(jobPaymentStr);
            } catch (Exception e) {
                Snackbar.make(findViewById(R.id.add_job_page), "Szám értéket adj meg a fizetéshez.", Snackbar.LENGTH_LONG).show();
            }

            if(jobName.isEmpty()) {
                Snackbar.make(findViewById(R.id.add_job_page), "A munka nevének kitöltése kötelező", Snackbar.LENGTH_LONG).show();
                return;
            }

            if(jobPayment == -1) {
                Snackbar.make(findViewById(R.id.add_job_page), "a fizetés kitöltése kötelező", Snackbar.LENGTH_LONG).show();
                return;
            }

            AndroidJobEntity current = new AndroidJobEntity(
                    jobName,
                    loggedUserEmail,
                    formattedDate,
                    jobPayment,
                    jobShortDesc,
                    jobLongDesc
            );

            databaseItems.add(current);
            Intent intent = new Intent(this, LoggedUserMainPage.class);
            startActivity(intent);
        } catch (Exception e) {
            Snackbar.make(findViewById(R.id.add_job_page), "valós értékeket adj meg", Snackbar.LENGTH_LONG).show();
        }
    }

    public void backToLoggedUserMainPage(View view) {
        finish();
    }
}