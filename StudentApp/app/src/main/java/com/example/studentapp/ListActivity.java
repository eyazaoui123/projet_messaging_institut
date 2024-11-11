package com.example.studentapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListActivity extends AppCompatActivity {

    RecyclerView languageListView;
    MyAdapterList myAdapter;
    private DatabaseReference channelsRef;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    List<String> list;
    ArrayList<Event> list1;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button add;
    EditText chaine;
    private MyAdapterList adapter;
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
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        databaseReference=FirebaseDatabase.getInstance().getReference("channels").child("list");
        String finalName = name;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String event=dataSnapshot.getValue(String.class);
                    list.add(event);
                }
                databaseReference=FirebaseDatabase.getInstance().getReference("users");
                Toast.makeText(ListActivity.this, list.get(0), Toast.LENGTH_SHORT).show();
                adapter = new MyAdapterList(list);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new MyAdapterList.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String clickedItem = list.get(position);
                        Toast.makeText(ListActivity.this, "Element cliqué : " + clickedItem, Toast.LENGTH_SHORT).show();
                        String idUnique = genererIdUnique();

                        databaseReference.child(finalName).child("channels").child(clickedItem).setValue(clickedItem);


                    }
                });


                //Toast.makeText(EmailsActivity.this, list.get(0), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

        //add=findViewById(R.id.addButt);
        //chaine=findViewById(R.id.chainee);
//
        // Create an ArrayAdapter to populate the ListView with language choices
        /*
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
});*//*
        // Set item click listener for the ListView
      /*  String finalName = name;
        languageListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedLanguage = languages[position];
            String idUnique = genererIdUnique();

            reference.child(finalName).child("channels").child(idUnique).setValue(selectedLanguage);

            Toast.makeText(ListActivity.this, "channel selected: " + selectedLanguage,
                    Toast.LENGTH_SHORT).show();
            // You can add more logic here for what to do when a language is selected
        });*/

    public static String genererIdUnique() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
