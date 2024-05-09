package com.example.allaskeresoportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LoggedUserMainPage extends AppCompatActivity {

    private static final String LOG_TAG = LoggedUserMainPage.class.getName();
    private FirebaseUser loggedUser;
    private RecyclerView recyclerView;
    private ArrayList<AndroidJobEntity> jobList;
    private AndroidJobAdapter androidJobAdapter;
    private FirebaseFirestore database;
    private CollectionReference databaseItems;

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

        recyclerView = findViewById(R.id.androidJobRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        jobList = new ArrayList<>();
        androidJobAdapter = new AndroidJobAdapter(this, jobList);
        recyclerView.setAdapter(androidJobAdapter);

        database = FirebaseFirestore.getInstance();
        databaseItems = database.collection("Jobs");
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getData() {
        jobList.clear();

        databaseItems.orderBy("name", Query.Direction.DESCENDING).limit(15).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                AndroidJobEntity entity = doc.toObject(AndroidJobEntity.class);
                jobList.add(entity);
            }
        });

        androidJobAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.android_job_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchJobId);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView((menuItem));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                androidJobAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addJobButton) {
            Intent intent = new Intent(this, AddJobActivity.class);
            intent.putExtra("LOGGEDUSEREMAIL", loggedUser.getEmail());
            startActivity(intent);
        } else if (id == R.id.settingsButton) {
            return true;
        } else if (id == R.id.logoutButton) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getData();
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void refreshJobList() {
        jobList.clear();
        databaseItems.orderBy("name", Query.Direction.DESCENDING).limit(15).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                AndroidJobEntity entity = doc.toObject(AndroidJobEntity.class);
                jobList.add(entity);
            }
        });

        androidJobAdapter.notifyDataSetChanged();
    }
}