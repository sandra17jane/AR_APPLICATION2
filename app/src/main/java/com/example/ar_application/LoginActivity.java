package com.example.ar_application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail, loginPassword;
    private TextView signupRedirectText, forgotPassword;
    private Button loginButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signUpRedirectText);
        forgotPassword = findViewById(R.id.forgotpassword);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Login Button Click Listener
        loginButton.setOnClickListener(v -> handleLogin());

        // Redirect to SignUp Activity
        signupRedirectText.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignupActivity.class))
        );

        // Forgot Password Click Listener
        forgotPassword.setOnClickListener(v -> resetPassword());
    }

    private void handleLogin() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (validateInput(email, password)) {
            loginButton.setEnabled(false); // Prevent multiple clicks
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        // Get the authenticated user's email
                        String userEmail = auth.getCurrentUser().getEmail();
                        checkUserRole(userEmail);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(LoginActivity.this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        loginButton.setEnabled(true);
                    });
        }
    }

    private void checkUserRole(String userEmail) {
        if (userEmail == null) {
            Toast.makeText(this, "Error: No email found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userEmail.equalsIgnoreCase("admin@gmail.com")) { // Admin login
            Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
        } else { // Normal user login
            Toast.makeText(this, "User Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, Home.class));
        }
        finish();
    }

    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            loginEmail.setError("Email cannot be empty");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("Please enter a valid email");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            loginPassword.setError("Password cannot be empty");
            return false;
        }
        return true;
    }

    private void resetPassword() {
        String email = loginEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter your email to reset password", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
