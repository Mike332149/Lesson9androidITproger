package com.example.lesson9androiditproger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText user_name_field, user_bio_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_name_field = findViewById(R.id.user_name_field);
        user_bio_field = findViewById(R.id.user_bio_field);
    }

    public void saveData (View view){
        String user_name = user_name_field.getText().toString();
        String user_bio = user_bio_field.getText().toString();

        try {
            FileOutputStream fileOutput = openFileOutput("user_data.txt",MODE_PRIVATE);
            fileOutput.write((user_name + ". " + user_bio).getBytes());
            fileOutput.close();

            user_name_field.setText("");
            user_bio_field.setText("");
            Toast.makeText(this, "Текст збережено", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void getData (View view){
        try {
            FileInputStream fileInput = openFileInput("user_data.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader bR = new BufferedReader(reader);

           StringBuilder stringBuilder = new StringBuilder();
            String lines = "";
           while ((lines = bR.readLine()) != null) {
               stringBuilder.append(lines).append("\n");
           }
           Toast.makeText(this, stringBuilder,Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void goContacts (View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }
    public void goWeb (View view) {
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }
}