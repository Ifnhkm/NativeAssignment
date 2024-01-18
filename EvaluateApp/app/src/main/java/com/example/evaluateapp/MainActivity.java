package com.example.evaluateapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Lecturer> lecturers;
    private LecturerAdapter adapter;
    TextView userName;
    Button logout;
    GoogleSignInClient gClient;
    GoogleSignInOptions gOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        userName = findViewById(R.id.userName);

        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);

        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (gAccount != null) {
            String gName = gAccount.getDisplayName();
            userName.setText(gName);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a confirmation dialog before logging out
                showLogoutConfirmationDialog();
            }
        });

        lecturers = new ArrayList<>();
        adapter = new LecturerAdapter(this, lecturers);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Lecturer lecturer1 = new Lecturer(1827, "Dr Amier Siddiq", "Sembang Muhong", R.drawable.amier);
        Lecturer lecturer2 = new Lecturer(2314, "Dr Hisyamuddin", "Sembang Politik", R.drawable.hisyam);
        Lecturer lecturer3 = new Lecturer(5234, "Dr Irfan", "Sembang Santai", R.drawable.irfan);
        Lecturer lecturer4 = new Lecturer(9743, "Dr Akmal", "Sembang Kencang", R.drawable.akmal);

        lecturers.add(lecturer1);
        lecturers.add(lecturer2);
        lecturers.add(lecturer3);
        lecturers.add(lecturer4);

        adapter.notifyDataSetChanged();

        // Set item click listener for updating ratings
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showRatingDialog(position);
            }
        });
    }

    // Function to show the logout confirmation dialog
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User confirmed, perform logout
                        performLogout();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User canceled logout, do nothing
                    }
                })
                .show();
    }

    // Function to perform logout
    private void performLogout() {
        gClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    // Moved outside onCreate
    private void showRatingDialog(final int position) {
        Lecturer selectedLecturer = lecturers.get(position);

        // Check if the lecturer has already been evaluated
        if (!selectedLecturer.isEvaluated()) {
            // Launch EvaluateActivity with lecturer information
            Intent intent = new Intent(MainActivity.this, EvaluateActivity.class);
            intent.putExtra("LECTURER_ID", selectedLecturer.getId());
            intent.putExtra("LECTURER_NAME", selectedLecturer.getName());
            intent.putExtra("LECTURER_IMAGE", selectedLecturer.getImageResourceId());
            startActivity(intent);
        } else {
            // Display a toast or dialog indicating that the lecturer has already been evaluated
            Toast.makeText(getApplicationContext(), "You have already evaluated this lecturer.", Toast.LENGTH_SHORT).show();
        }
    }
}
