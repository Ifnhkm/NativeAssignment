package com.example.evaluateapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

// LecturerAdapter.java
public class LecturerAdapter extends ArrayAdapter<Lecturer> {

    private Context context;

    public LecturerAdapter(Context context, List<Lecturer> lecturers) {
        super(context, 0, lecturers);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Lecturer lecturer = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lecturer, parent, false);
        }

        ImageView lecturerImageView = convertView.findViewById(R.id.lecturerImageView);
        TextView idTextView = convertView.findViewById(R.id.idTextView);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView subjectTextView = convertView.findViewById(R.id.subjectTextView);
        Button evaluateButton = convertView.findViewById(R.id.evaluateButton);

        if (lecturer != null) {
            // Set image resource based on Lecturer's imageResourceId
            lecturerImageView.setImageResource(lecturer.getImageResourceId());

            idTextView.setText("ID: " + lecturer.getId());
            nameTextView.setText("Name: " + lecturer.getName());
            subjectTextView.setText("Course: " + lecturer.getSubject());

            // Set OnClickListener for the Evaluate button
            evaluateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check if the lecturer has already been evaluated
                    if (!lecturer.isEvaluated()) {
                        // Launch EvaluateActivity with lecturer information
                        Intent intent = new Intent(context, EvaluateActivity.class);
                        intent.putExtra("LECTURER_ID", lecturer.getId());
                        intent.putExtra("LECTURER_NAME", lecturer.getName());
                        context.startActivity(intent);
                    } else {
                        // Display a toast or dialog indicating that the lecturer has already been evaluated
                        Toast.makeText(context, "You have already evaluated this lecturer.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return convertView;
    }
}
