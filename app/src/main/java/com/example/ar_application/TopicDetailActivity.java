package com.example.ar_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class TopicDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvContent;
    private ImageView topicImage;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        // 🏷️ Initialize Views
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        topicImage = findViewById(R.id.topicImage);
        btnBack = findViewById(R.id.btnBack);

        // 🔙 Back Button Action
        btnBack.setOnClickListener(v -> finish());

        // ✅ Retrieve Data from Intent
        Intent intent = getIntent();
        if (intent != null) {
            tvTitle.setText(intent.getStringExtra("component"));
            tvContent.setText(intent.getStringExtra("content"));

            // 🖼️ Load Image using Glide (if URL is available)
            String imageUrl = intent.getStringExtra("imageUrl");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(this).load(imageUrl).into(topicImage);
            } else {
                topicImage.setImageResource(R.drawable.placeholder_image);
            }
        }
    }
}
