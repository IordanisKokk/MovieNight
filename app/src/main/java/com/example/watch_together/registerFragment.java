package com.example.watch_together;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registerFragment extends Fragment {

    private View view;
    private FirebaseAuth fireBaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    /*
    An object of the class DAOUser is created in order to access the class and its methods, so that
    we can add our users in the Firebase Real Time Database
     */
    private DAOUser daoUser = new DAOUser();

    /*
    This is an object of the model Class user. It is the object of a new user that is about to be created
    in the registerFragment. We instantiate the attributes of the object and then if the registration is
    complete we pass that object to the daoUser class to be added in the Firebase Real Time Database.
     */
    private User newUser;

    private Button signupButton;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmedPassword;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public registerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static registerFragment newInstance(String param1, String param2) {
        registerFragment fragment = new registerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fireBaseAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    return;
                }
            }
        };
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registration, container, false);

        //instantiating all the ui elements contained in the loginFragment.
        signupButton = (Button) view.findViewById(R.id.signUpButton);
        username = (EditText) view.findViewById(R.id.username);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        confirmedPassword = (EditText) view.findViewById(R.id.confirmPassword);

         /*
          Adding an OnClickListener to the signupButton, that gets the user's username, email, password
          and confirmed password from the EditText elements in the loginFragment, checks if the password and
          the confirmed password match and then uses the instance of FireBaseAuth to create a new user with his
          email and password (fireBaseAuth.createUserWithEmailAndPassword() and an OnCompleteListener is added
          to add the user using the DAOUser class and add him to the Firebase Real Time Database when the AuthResult
          task was successful, otherwise a toast is created with the text "sign_up_error" and shown.
         */
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("de", "Clicked");
                final String finalUsername = username.getText().toString();
                final String finalEmail = email.getText().toString();
                final String fPassword = password.getText().toString();
                final String fConfirmedPassword = confirmedPassword.getText().toString();
                if(fPassword.equals(fConfirmedPassword)){
                    final String finalPassword = password.getText().toString();
                    newUser = new User(finalUsername, finalEmail);
                    fireBaseAuth.createUserWithEmailAndPassword(finalEmail, finalPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast toast = Toast.makeText(getActivity(), "sign_up_error", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            Log.d("dDB", "Making User");
                            daoUser.add(newUser);
                        }

                    });


                    /*
                    If the passwords do not match, a toast is created notifying the user about it.
                     */
                }else{
                    Toast toast = Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return view;
    }

    /**
     * onStart() method used to add the AuthStateListener created above on the instance of the
     * FireBaseAuth.
     */
    @Override
    public void onStart() {
        super.onStart();
        fireBaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    /**
     * onStop() method used to remove the AuthStateListener created above on the instance of the
     * FireBaseAuth when the fragment stops.
     */
    @Override
    public void onStop() {
        super.onStop();
        fireBaseAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}