package com.example.watch_together.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watch_together.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link loginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class loginFragment extends Fragment {

    View view;

    private FirebaseAuth fireBaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private Button loginButton;
    private EditText email;
    private EditText password;
    private TextView forgotPasswordLink;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public loginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static loginFragment newInstance(String param1, String param2) {
        loginFragment fragment = new loginFragment();
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

    /**
     *
     * In the onCreateView method, the view of the fragment gets created.
     * First, an instance of the FirebaseAuth gets instantiated with the getInstance() method of
     * the FirebaseAuth class.
     * Then an AuthStateListener() gets instantiated that listens for AuthState changes, in order to
     * finish the LoginRegistrationActivity and create an Intent to launch the MainActivity, once the user's
     * account is logged in or registered. It also checks if the user has already logged in with his account before
     * so that the MainActivity can be launched immediately when opening the app.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fireBaseAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    return;
                }
            }
        };
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);


         //instantiating all the ui elements contained in the loginFragment.

        loginButton = (Button) view.findViewById(R.id.loginButton);
        email = (EditText) view.findViewById(R.id.email_edit_text);
        password = (EditText) view.findViewById(R.id.password_edit_text);
        forgotPasswordLink = (TextView) view.findViewById(R.id.forgotPsswrd);

        /*
          Adding an OnClickListener to the loginButton, that gets the user's email and password
          from the EditText elements in the loginFragment and uses the instance of FireBaseAuth to
          sign in the user with his email and password (fireBaseAuth.signInWithEmailAndPassword()
          and an OnCompleteListener is added to display a toast notifying the user in the case of
          a login error/failure.
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String finalEmail = email.getText().toString();
                final String finalPassword = password.getText().toString();
                fireBaseAuth.signInWithEmailAndPassword(finalEmail, finalPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "login_error", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });

        /*
          an OnClickListener is added to the forgotPasswordLink TextView, so that the user can
          click on it in case he has forgotten his password.
          For now, this only shows a toast on the screen with the text "Reset Password".
          TODO : add the functionality for password reset.
         */
        forgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = "Reset Password";
                Context context = getActivity().getApplicationContext();
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();
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