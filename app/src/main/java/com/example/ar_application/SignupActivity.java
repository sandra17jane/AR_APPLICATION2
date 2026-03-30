package com.example.ar_application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText signupUsername, signupEmail, signupPhone, signupPassword, reenterSignupPassword;
    private Button signupButton;
    private TextView loginRedirectText;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI components
        signupUsername = findViewById(R.id.signup_username);
        signupEmail = findViewById(R.id.signup_email);
        signupPhone = findViewById(R.id.signup_phone);
        signupPassword = findViewById(R.id.signup_password);
        reenterSignupPassword = findViewById(R.id.reenterSignupPassword);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Signup Button Click Listener
        signupButton.setOnClickListener(v -> handleSignup());

        // Redirect to Login Activity
        loginRedirectText.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void handleSignup() {
        String username = signupUsername.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String phone = signupPhone.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();
        String confirmPassword = reenterSignupPassword.getText().toString().trim();

        if (validateInput(username, email, phone, password, confirmPassword)) {
            signupButton.setEnabled(false); // Prevent multiple clicks
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            saveUserData(user.getUid(), username, email, phone);  // ✅ Fixed method call

                            user.sendEmailVerification()
                                    .addOnSuccessListener(aVoid ->
                                            Toast.makeText(SignupActivity.this, "Verification email sent!", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Log.e("FirebaseAuth", "Email Verification Error", e));

                            Toast.makeText(SignupActivity.this, "Sign-up Successful! Please verify your email.", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(SignupActivity.this, "Signup Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        signupButton.setEnabled(true);
                    });
        }
    }

    private boolean validateInput(String username, String email, String phone, String password, String confirmPassword) {
        if (TextUtils.isEmpty(username)) {
            signupUsername.setError("Username cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Enter a valid email");
            return false;
        }
        if (TextUtils.isEmpty(phone) || phone.length() < 10) {
            signupPhone.setError("Enter a valid phone number");
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6 || !password.equals(confirmPassword)) {
            signupPassword.setError("Passwords must match and be at least 6 characters");
            return false;
        }
        return true;
    }

    // ✅ Corrected saveUserData method
    private void saveUserData(String uid, String username, String email, String phone) {
        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Ensure Firestore instance is initialized

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("uid", uid);
        userMap.put("email", email);
        userMap.put("username", username);
        userMap.put("phone", phone);

        db.collection("Users").document(uid)
                .set(userMap)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "User added successfully!");
                    Toast.makeText(SignupActivity.this, "User data saved!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error adding user", e);
                    Toast.makeText(SignupActivity.this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }


}
