package com.example.student_application;
// MainActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class ListActivity extends AppCompatActivity {

    ListView languageListView;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button add;
    EditText chaine;
    String[] languages = {"idl1", "idl2", "tunivision"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        String name="user";
        if (intent != null) {
            name = intent.getStringExtra("name");
        }
        Toast.makeText(ListActivity.this, name,
                Toast.LENGTH_SHORT).show();
        languageListView = findViewById(R.id.languageListView);
        add=findViewById(R.id.addButt);
        chaine=findViewById(R.id.chainee);

        // Create an ArrayAdapter to populate the ListView with language choices
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, languages);
        // Set the adapter to the ListView
        languageListView.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
/*
add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String content = chaine.getText().toString();
        languages[languages.length]=content;
    }
});*/
        // Set item click listener for the ListView
        String finalName = name;
        languageListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedLanguage = languages[position];
            String idUnique = genererIdUnique();

            reference.child(finalName).child("channels").child(idUnique).setValue(selectedLanguage);

            Toast.makeText(ListActivity.this, "channel selected: " + selectedLanguage,
                    Toast.LENGTH_SHORT).show();
            // You can add more logic here for what to do when a language is selected
        });
    }
    public static String genererIdUnique() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
