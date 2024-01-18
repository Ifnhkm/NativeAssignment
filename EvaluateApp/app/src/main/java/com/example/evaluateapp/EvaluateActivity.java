package com.example.evaluateapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class EvaluateActivity extends AppCompatActivity {

    // Map to store lecturer IDs and corresponding default image resources
    private Map<Integer, Integer> defaultImages;

    // UI components
    private Spinner ratingSpinner1;
    private EditText feedbackEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        // Initialize the map with default images
        initializeDefaultImages();

        // Get lecturer information from Intent
        int lecturerId = getIntent().getIntExtra("LECTURER_ID", -1);
        int lecturerImageResourceId = getDefaultImageResourceId(lecturerId);
        String lecturerName = getIntent().getStringExtra("LECTURER_NAME");

        // Check if the lecturer has already been evaluated
        if (!isLecturerAlreadyEvaluated(lecturerId)) {
            // Initialize UI components
            TextView evaluatedNameTextView = findViewById(R.id.evaluatedNameTextView);
            ratingSpinner1 = findViewById(R.id.ratingSpinner1);
            feedbackEditText = findViewById(R.id.feedbackEditText);
            Button submitEvaluationButton = findViewById(R.id.submitEvaluationButton);
            ImageView evaluatedImageView = findViewById(R.id.evaluatedImageView);

            // Set evaluated lecturer's name and image
            evaluatedNameTextView.setText(lecturerName);
            evaluatedImageView.setImageResource(lecturerImageResourceId);

            // Set up spinner listener
            ratingSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // Handle selection
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // Do nothing
                }
            });

            // Set up submit button listener
            submitEvaluationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle submission, e.g., save data and show a dialog
                    saveAndShowSubmissionDialog(lecturerId);
                }
            });

            // Load saved data for the lecturer (if any)
            loadSavedData(lecturerId);
        } else {
            // Display a toast or dialog indicating that the lecturer has already been evaluated
            Toast.makeText(getApplicationContext(), "You have already evaluated this lecturer.", Toast.LENGTH_SHORT).show();

            // Navigate back to the lecturer list page
            Intent intent = new Intent(EvaluateActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        }
    }

    // Display a submission confirmation dialog and save data
    private void saveAndShowSubmissionDialog(int lecturerId) {
        // Save data to SharedPreferences
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("rating_" + lecturerId, ratingSpinner1.getSelectedItemPosition());
        editor.putString("feedback_" + lecturerId, feedbackEditText.getText().toString());
        editor.apply();

        // Display a toast message
        Toast toast = Toast.makeText(getApplicationContext(), "Feedback submitted!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        // Create an intent to go back to the lecturer list page
        Intent intent = new Intent(EvaluateActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }

    // Load saved data for the lecturer (if any)
    private void loadSavedData(int lecturerId) {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        int savedRating = preferences.getInt("rating_" + lecturerId, -1);
        String savedFeedback = preferences.getString("feedback_" + lecturerId, "");

        if (savedRating != -1) {
            // Set saved rating and feedback
            ratingSpinner1.setSelection(savedRating);
            feedbackEditText.setText(savedFeedback);
        }
    }

    // Initialize the map with default images
    private void initializeDefaultImages() {
        defaultImages = new HashMap<>();
        defaultImages.put(1827, R.drawable.amier);
        defaultImages.put(2314, R.drawable.hisyam);
        defaultImages.put(5234, R.drawable.irfan);
        defaultImages.put(9743, R.drawable.akmal);
        // Add more entries if needed
    }

    // Get the default image resource ID based on the lecturer ID
    private int getDefaultImageResourceId(int lecturerId) {
        // Check if the lecturer ID exists in the map, return default if not found
        return defaultImages.containsKey(lecturerId) ? defaultImages.get(lecturerId) : R.drawable.amier;
    }

    // Check if the lecturer has already been evaluated
    private boolean isLecturerAlreadyEvaluated(int lecturerId) {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        return preferences.contains("rating_" + lecturerId) && preferences.contains("feedback_" + lecturerId);
    }
}
