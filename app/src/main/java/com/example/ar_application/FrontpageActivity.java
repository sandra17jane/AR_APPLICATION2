package com.example.ar_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;




public class FrontpageActivity extends AppCompatActivity {
    private Button btnlogin, btnsignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);

        btnlogin = findViewById(R.id.btnlogin);
        btnsignin = findViewById(R.id.btnsignin);

        btnlogin.setOnClickListener(v -> startActivity(new Intent(FrontpageActivity.this, LoginActivity.class)));
        btnsignin.setOnClickListener(v -> startActivity(new Intent(FrontpageActivity.this, SignupActivity.class)));

    }
}
