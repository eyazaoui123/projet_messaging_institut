package com.example.studentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button sendButton,consultButton,List,Events;
    TextView logoutRedirectText,User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        User=findViewById(R.id.user);
        String name="user";
        if (intent != null) {
            name = intent.getStringExtra("name");
            String email = intent.getStringExtra("email");
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");}
        User.setText(name);
        Toast.makeText(MainActivity.this, name,
                Toast.LENGTH_SHORT).show();


        sendButton = findViewById(R.id.sen_button);
        Events=findViewById(R.id.even_button);
        List = findViewById(R.id.list_button);
        //consultButton = findViewById(R.id.consult_button);
        logoutRedirectText=findViewById(R.id.logRedirectText);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = User.getText().toString();  // Get the text from the TextView
                Intent intent = new Intent(MainActivity.this, EmailsActivity.class);

                intent.putExtra("name", text);

                startActivity(intent);
            }
        });
        logoutRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventsActivity.class);
                startActivity(intent);
            }
        });
        String finalName = name;
      /*  consultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                intent.putExtra("name", finalName);
                startActivity(intent);
            }
        });*/
        List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("name", finalName);
                startActivity(intent);
            }
        });

    }
}