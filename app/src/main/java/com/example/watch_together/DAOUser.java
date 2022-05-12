package com.example.watch_together;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOUser {

    private DatabaseReference databaseReference;

    public DAOUser(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(User.class.getSimpleName());
    }

    public Task<Void> add(User user){
//        if(user  == null){
//            //TODO throw exception
//        }
        Log.d("dDB", "MadeItToDAOUser.java");
        Log.d("dDB", user.getUsername() + " " + user.getEmail());
        return databaseReference.push().setValue(user);


    }


}
