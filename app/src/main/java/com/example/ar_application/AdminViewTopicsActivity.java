package com.example.ar_application;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ar_application.adapters.TopicsAdapter;
import com.example.ar_application.database.DatabaseHelper;
import com.example.ar_application.models.Topic;
import java.util.List;
import android.view.*;
import android.widget.*;

public class AdminViewTopicsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTopics;
    private TopicsAdapter topicsAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_topics);

        recyclerViewTopics = findViewById(R.id.recyclerViewTopics);
        recyclerViewTopics.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        loadTopics();
    }

    private void loadTopics() {
        List<Topic> topics = databaseHelper.getAllTopics();

        if (topics.isEmpty()) {
            Toast.makeText(this, "No topics available", Toast.LENGTH_SHORT).show();
        } else {
            topicsAdapter = new TopicsAdapter(topics, this);
            recyclerViewTopics.setAdapter(topicsAdapter);
        }
    }

}
