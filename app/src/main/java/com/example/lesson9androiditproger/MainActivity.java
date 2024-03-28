package com.example.lesson9androiditproger;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.List;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private List<SavedItem> savedItemsList;
    public SavedItemsAdapter adapter;

    private EditText eventName_field, dateOrPassword_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventName_field = findViewById(R.id.eventName_field);
        dateOrPassword_field = findViewById(R.id.dateOrPassword_field);

        // Инициализация данных (пример, заглушка)
        savedItemsList = new ArrayList<>();

        // Настройка RecyclerView
        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SavedItemsAdapter(savedItemsList);
        getData();
        recyclerView.setAdapter(adapter);
    }

    public class SavedItem {
        private String eventName;
        private String dateOrPassword;

        public SavedItem(String eventName, String dateOrPassword) {
            this.eventName = eventName;
            this.dateOrPassword = dateOrPassword;
        }
        public String getEventName() {
            return eventName;
        }

        public String getDateOrPassword() {
            return dateOrPassword;
        }
    }
    public void saveData (View view){

        String eventName = eventName_field.getText().toString();
        String dateOrPassword = dateOrPassword_field.getText().toString();

        // Додаємо новий елемент у список(!)який вже є в класі onCreate
        savedItemsList.add(new SavedItem(eventName, dateOrPassword));

        // Оновлюємо адаптер
        adapter.notifyDataSetChanged();

        try {
            FileOutputStream fileOutput = openFileOutput("user_data.txt", MODE_PRIVATE);
            String s = "";
            for (int i = 0; i < adapter.savedItems.size() ; i++) {
                s += (adapter.savedItems.get(i).eventName + "." + adapter.savedItems.get(i).dateOrPassword) + "\n";
            }

            fileOutput.write(s.getBytes());
            fileOutput.close();

          eventName_field.setText("");
         dateOrPassword_field.setText("");

            Toast.makeText(this, "Текст збережено", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        }

            public void getData (){
        try {
            FileInputStream fileInput = openFileInput("user_data.txt");
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader bR = new BufferedReader(reader);
           String lines = "";
           while ((lines = bR.readLine()) != null) {
               if (lines.isEmpty()) {
                   return;
               }
               String[] wordsepar = lines.split("\\.");
               if (wordsepar.length < 2) {
                   continue;
               }
               String evname = wordsepar[0];
               String dpass = wordsepar[1];
               SavedItem item = new SavedItem(evname, dpass);
               adapter.savedItems.add(item);
           }

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
    public class SavedItemsAdapter extends RecyclerView.Adapter<SavedItemsAdapter.ViewHolder> {

        private List<SavedItem> savedItems;

        public SavedItemsAdapter(List<SavedItem> savedItems) {
            this.savedItems = savedItems;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_gpt, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            SavedItem item = savedItems.get(position);
            holder.eventNameTextView.setText(item.getEventName());
            holder.dateOrPasswordTextView.setText(item.getDateOrPassword());

            // Обробник натискання кнопки видалення
            holder.buttonToDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        savedItems.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);
                        saveDataToInternalStorage(); // Оновити дані в файлі після видалення
                    }
            }
        });

        }

        private void saveDataToInternalStorage() {
            try {
                FileOutputStream fileOutput = openFileOutput("user_data.txt", MODE_PRIVATE);
                StringBuilder s = new StringBuilder();
                for (SavedItem item : savedItems) {
                    s.append(item.getEventName()).append(".").append(item.getDateOrPassword()).append("\n");
                }
                fileOutput.write(s.toString().getBytes());
                fileOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

               @Override
        public int getItemCount() {
            return savedItems.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            TextView eventNameTextView;
            TextView dateOrPasswordTextView;
            Button buttonToDelete;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                eventNameTextView = itemView.findViewById(R.id.eventNameTextView);
                dateOrPasswordTextView = itemView.findViewById(R.id.dateOrPasswordTextView);
                buttonToDelete = itemView.findViewById(R.id.buttonToDelete);
            }
        }
    }

}


