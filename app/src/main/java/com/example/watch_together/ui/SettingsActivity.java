package com.example.watch_together.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.watch_together.R;
import com.example.watch_together.Utills.DisFavUtil;
import com.example.watch_together.Utills.WatchTogether;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private EditText email;
    private EditText password;

    private Button setEmailButton;
    private Button setPasswordButton;
    private Button resetFavoritesButton;
    private Button resetDismissedButton;
    private Button backButton;
    private Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setEmailButton = findViewById(R.id.setEmailButton);
        setPasswordButton = findViewById(R.id.setPasswordButton);
        resetFavoritesButton = findViewById(R.id.buttonRF);
        resetDismissedButton = findViewById(R.id.buttonRD);
        backButton = findViewById(R.id.buttonBA);
        logOutButton = findViewById(R.id.buttonLO);

        password = findViewById(R.id.changePassword);
        email = findViewById(R.id.changeEmailAddress);

        setEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmail = email.getText().toString();
                if (newEmail.equals(""))
                    Toast.makeText(getApplicationContext(),"empty", Toast.LENGTH_LONG).show();
                else {
                    user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(getApplicationContext(),"something went wrong", Toast.LENGTH_LONG).show();
                            else {
                                Log.d("dDB", "email updated");
                                Toast.makeText(getApplicationContext(),"email updated", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        setPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = password.getText().toString();
                if (newPassword.equals(""))
                    Toast.makeText(getApplicationContext(),"empty", Toast.LENGTH_LONG).show();
                else {
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(getApplicationContext(),"something went wrong", Toast.LENGTH_LONG).show();
                            else {
                                Log.d("dDB", "password updated");
                                Toast.makeText(getApplicationContext(),"password updated", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        resetFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                new DisFavUtil().resetFavourites(getApplicationContext(), firebaseAuth.getCurrentUser().getUid());
                Toast.makeText(getApplicationContext(),"reset favorites", Toast.LENGTH_LONG).show();
            }
        });

        resetDismissedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                new DisFavUtil().resetDismissed(getApplicationContext(), firebaseAuth.getCurrentUser().getUid());
                Toast.makeText(getApplicationContext(),"reset dismissed", Toast.LENGTH_LONG).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginRegistrationActivity.class);
                startActivity(intent);
            }
        });

    }
}