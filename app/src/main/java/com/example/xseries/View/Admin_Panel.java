package com.example.xseries.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xseries.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_Panel extends AppCompatActivity {

    EditText etSignInEmail, etSignInPassword;
    Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        etSignInEmail = findViewById(R.id.editTextTextEmailAddress);
        etSignInPassword = findViewById(R.id.editTextTextPassword);


        sign_in = findViewById(R.id.sign_in);
//        String email = editTextTextEmailAddress.getText().toString();
//        String pass = editTextTextPassword.getText().toString();


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etSignInPassword.getText().toString().length() < 6) {
                    etSignInPassword.setError("password minimum contain 6 character");
                    etSignInPassword.requestFocus();
                } else if (etSignInPassword.getText().toString().equals("")) {
                    etSignInPassword.setError("please enter password");
                    etSignInPassword.requestFocus();
                } else if (etSignInEmail.getText().toString().equals("")) {
                    etSignInEmail.setError("please enter email address");
                    etSignInEmail.requestFocus();
                } else {

                    String emailAddress = etSignInEmail.getText().toString().trim();
                    String password = etSignInPassword.getText().toString().trim();

                    DatabaseReference fetchLogin = FirebaseDatabase.getInstance().getReference().child("Users").child("Admin");
                    fetchLogin.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String gmail = snapshot.child("gmail").getValue().toString();
                            String pass = snapshot.child("password").getValue().toString();


                            Log.d("LOGINCHK", "onDataChange: gmailInput: " + emailAddress);
                            Log.d("LOGINCHK", "onDataChange: gmailInput: " + password);
                            Log.d("LOGINCHK", "onDataChange: gmail: " + gmail);
                            Log.d("LOGINCHK", "onDataChange: password: " + pass);


                            if (emailAddress.equals(gmail) && password.equals(pass)) {
                                Toast.makeText(Admin_Panel.this, "admin", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Admin_Panel.this, "not admin", Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


/*
                if ((TextUtils.isEmpty(email)) && (TextUtils.isEmpty(pass))) {
                    editTextTextEmailAddress.setError("Email cannot be empty");
                    editTextTextPassword.setError("Password cannot be empty");

                } else {
                    Toast.makeText(Admin_Panel.this, "is Admin", Toast.LENGTH_SHORT).show();
                }
*/
            }
        });

    }
}