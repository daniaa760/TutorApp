package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

import static java.security.AccessController.getContext;

public class TutorProfileActivity extends AppCompatActivity {

    private ImageView ivProfileImage;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference reference;
    private Button bnUpdate;
    private EditText etBio, etClasses;
    private String bio, classes, userID, email, firstName, lastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        bnUpdate = findViewById(R.id.bnUpdate);
        etBio = findViewById(R.id.etBio);
        etClasses = findViewById(R.id.etClasses);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        reference = FirebaseDatabase.getInstance().getReference("Tutors");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        bnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });

        RetrieveUserInfo();


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // create a user object
                Tutor userProfile = snapshot.getValue(Tutor.class);

                if (userProfile != null) {
                    // a user has these attributes
                    firstName = userProfile.firstName;
                    lastName = userProfile.lastName;
                    email = userProfile.email;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void RetrieveUserInfo() {
        bio = etBio.getText().toString();
        classes = etClasses.getText().toString();

        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()  && (snapshot.hasChild("bio")) && (snapshot.hasChild("classes")) && (snapshot.hasChild("image"))){
                    String retrieveBio = snapshot.child("bio").getValue().toString();
                    String retrieveClasses = snapshot.child("classes").getValue().toString();

                    etBio.setHint(retrieveBio);
                    etClasses.setHint(retrieveClasses);
                }
                else if(snapshot.exists()  && (snapshot.hasChild("bio"))  && (snapshot.hasChild("classes"))){
                    String retrieveBio = snapshot.child("bio").getValue().toString();
                    String retrieveClasses = snapshot.child("classes").getValue().toString();

                    etBio.setHint(retrieveBio);
                    etClasses.setHint(retrieveClasses);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Update profile here", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void UpdateProfile() {
        bio = etBio.getText().toString();
        classes = etClasses.getText().toString();

        HashMap<String, String> profileMap = new HashMap<>();
        profileMap.put("bio", bio);
        profileMap.put("classes", classes);
        profileMap.put("email", String.valueOf(email));
        profileMap.put("firstName", String.valueOf(firstName));
        profileMap.put("lastName", String.valueOf(lastName));

        reference.child(userID).setValue(profileMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),
                                    "Profile updated.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            String message = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Error" + message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
        //  }
    }

    private void choosePicture(){
        /*
        Create file variable, store image into file variable
         */
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData(); // gets file
            ivProfileImage.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Uploaded successfully.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to Upload.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) progressPercent + "%");
                    }
                });
    }
}
