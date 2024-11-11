package com.example.student_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class AboutActivity extends AppCompatActivity {
TextView Retour,user;
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

Button send,image;
    FirebaseDatabase database;
    DatabaseReference reference;
    String imageURL="hgyfytfuhiu";

EditText emailTo,emailContent,obj;
    Uri uri;
    ImageView uploadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Retour=findViewById(R.id.retour_button);
        emailContent=findViewById(R.id.email_content);
        obj=findViewById(R.id.email_obj);
        send=findViewById(R.id.send_button);
        uploadImage = findViewById(R.id.image);
        Intent intent = getIntent();
        String uniqueId ;
        String name="user";
uploadImage.setBackgroundResource(R.drawable.upload);
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(AboutActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        emailTo=findViewById(R.id.email_to);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                saveData();
            }}
        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });


        Retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                        startActivity(intent);


            }
        });
    }



    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("AndroidImages")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
        builder.setCancelable(false);
        //    builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();

                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public  void uploadData(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //reference = database.getReference("channels");
        String too="444";

        String content = emailContent.getText().toString().trim();
        String to = emailTo.getText().toString();
        String object=obj.getText().toString().trim();

        if(to.equals("idl1@gmail.com")){too="idl1"; }
        if(to.equals("idl2@gmail.com")){too="idl2";}
        if(to.equals("tunivision@gmail.com")){too="tunivision";}
        if(to.equals("actia@gmail.com")){too="actia";}
        if(to.equals("isi@gmail.com")){too="channel";}

        //String object=obj.getText().toString().trim();
        String uniqueIdmsg = UUID.randomUUID().toString();
        String uniqueIdchan = UUID.randomUUID().toString();
        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z'", Locale.FRANCE);
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String currentDate = iso8601Format.format(new Date());
        Message message = new Message( imageURL,content,sdf1.format(timestamp),object);
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.

        FirebaseDatabase.getInstance().getReference("channels").child(too).child("msg").push()
                .setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AboutActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AboutActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
