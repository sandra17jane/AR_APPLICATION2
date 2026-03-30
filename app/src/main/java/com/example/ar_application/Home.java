package com.example.ar_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ar_application.adapters.TaskAdapter;
import com.example.ar_application.adapters.TopicsAdapter;
import com.example.ar_application.database.DatabaseHelper;
import com.example.ar_application.models.Task;
import com.example.ar_application.models.Topic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerViewTopics, recyclerViewTasks;
    private TopicsAdapter topicsAdapter;
    private TaskAdapter tasksAdapter;
    private List<Task> tasksList;
    private DatabaseHelper databaseHelper;
    private TextView tvGreeting;
    private NestedScrollView scrollable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components
        tvGreeting = findViewById(R.id.tv_greeting);
        recyclerViewTopics = findViewById(R.id.recyclerViewTopics);
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        scrollable=findViewById(R.id.scrollable);

        // Set Layout Managers
        recyclerViewTopics.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        // Initialize Database Helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize Lists and Adapters
        tasksList = new ArrayList<>();
        tasksAdapter = new TaskAdapter(tasksList, this);
        recyclerViewTasks.setAdapter(tasksAdapter);

        // Load Data
        loadUserName();
        loadTopics();
        loadTasks();


    }

    private void loadUserName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("Users").document(user.getUid());

            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String username = documentSnapshot.getString("username");
                    tvGreeting.setText("Hello, " + (username != null ? username : "User!"));
                } else {
                    tvGreeting.setText("Hello, User!");
                }
            }).addOnFailureListener(e -> {
                Log.e("Firebase", "Failed to retrieve username", e);
                tvGreeting.setText("Hello, User!");
            });
        }
    }

    private void loadTopics() {
        List<Topic> topics = databaseHelper.getAllTopics();
        topicsAdapter = new TopicsAdapter(topics, this);
        recyclerViewTopics.setAdapter(topicsAdapter);
    }

    private void loadTasks() {
        tasksList.clear();
        tasksList.addAll(databaseHelper.getAllTasks());
        tasksAdapter.notifyDataSetChanged();
    }

    // Methods for launching AR activities
    public void openKeyAR(View view) {

        startActivity(new Intent(this, ARkeyActivity.class));
    }

     public void opencpuAR(View view) {
        startActivity(new Intent(this, ARCPUActivity.class));
    }

   public void openrigAR(View view) {
        startActivity(new Intent(this, ARRigActivity.class));
    }
    public void openmotherAR(View view) {
        startActivity(new Intent(this, ARMotherboardActivity.class));
    }
     public void openramAR(View view) {
        startActivity(new Intent(this, ARRamActivity.class));
    }
    public void openstoreAR(View view) {
        startActivity(new Intent(this, ARStorageActivity.class));
    }
    public void openprocessorAR(View view) {
        startActivity(new Intent(this, ARProcessorActivity.class));
    }
    public void openlaptopAR(View view) {
        startActivity(new Intent(this, ARLaptopActivity.class));
    }


    public void openQuizPage(View view) {
        startActivity(new Intent(this, QuizActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks(); // Refresh tasks when returning to the home screen
    }
}
