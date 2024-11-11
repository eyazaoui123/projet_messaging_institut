package com.example.student_application;

import android.content.Intent;
import android.os.Bundle;
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

public class EmailsActivity extends AppCompatActivity {
    private DatabaseReference channelsRef;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<String> list;
    ArrayList<Event> list1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emails);
        Intent intent = getIntent();
        String name="user";

        if (intent != null) {
            name = intent.getStringExtra("name");
            }
        Toast.makeText(EmailsActivity.this, name,
                Toast.LENGTH_SHORT).show();
        recyclerView=findViewById(R.id.rvv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list1=new ArrayList<>();
        myAdapter=new MyAdapter(this,list1);
        recyclerView.setAdapter(myAdapter);

        list=new ArrayList<>();
        databaseReference=FirebaseDatabase.getInstance().getReference("users").child(name).child("channels");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String event=dataSnapshot.getValue(String.class);
                    list.add(event);
                }
                Toast.makeText(EmailsActivity.this, list.get(0), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < list.size(); i++) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("channels").child(list.get(i)).child("msg");

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Event event = dataSnapshot.getValue(Event.class);
                                list1.add(event);
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                //Toast.makeText(EmailsActivity.this, list.get(0), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }


}
