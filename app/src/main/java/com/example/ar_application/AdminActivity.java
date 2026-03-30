package com.example.ar_application;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.*;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.ar_application.database.DatabaseHelper;
import com.example.ar_application.models.Topic;
import com.google.firebase.auth.FirebaseAuth;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AdminActivity extends AppCompatActivity {

    private EditText editTextTopic, editTextContent;
    private Button buttonAddTopic, buttonSelectImage;
    private ImageView imagePreview;
    private Uri imageUri;
    private DatabaseHelper databaseHelper;

    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // ✅ Toolbar Setup
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(""); // Removes the title



        databaseHelper = new DatabaseHelper(this);

        // ✅ Initialize UI Components
        editTextTopic = findViewById(R.id.editTextTopic);
        editTextContent = findViewById(R.id.editTextContent);
        buttonAddTopic = findViewById(R.id.buttonAddTopic);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        imagePreview = findViewById(R.id.imagePreview);

        // ✅ Select Image
        buttonSelectImage.setOnClickListener(v -> openFileChooser());

        // ✅ Add Topic
        buttonAddTopic.setOnClickListener(v -> {
            if (imageUri != null) {
                saveImageAndTopic();
            } else {
                saveTopic(null);
            }
        });
    }

    // ✅ Open Gallery to Select Image
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imagePreview.setImageURI(imageUri);
            imagePreview.setVisibility(View.VISIBLE);
        }
    }

    // ✅ Save Image and Topic
    private void saveImageAndTopic() {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File imageFile = new File(getFilesDir(), System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(imageFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

            saveTopic(imageFile.getAbsolutePath());

        } catch (Exception e) {
            Toast.makeText(this, "Image saving failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // ✅ Save Topic to Database
    private void saveTopic(String imagePath) {
        String component = editTextTopic.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();

        if (component.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Enter topic name and content!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = databaseHelper.insertTopic(new Topic(component, content, imagePath));
        if (success) {
            Toast.makeText(this, "Topic Added!", Toast.LENGTH_SHORT).show();
            editTextTopic.setText("");
            editTextContent.setText("");
            imagePreview.setVisibility(View.GONE);
            imageUri = null;
        } else {
            Toast.makeText(this, "Failed to add topic!", Toast.LENGTH_SHORT).show();
        }
    }

    // ✅ Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    // ✅ Handle Menu Item Clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_view_topics) {
            startActivity(new Intent(this, AdminViewTopicsActivity.class));
            return true;
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
