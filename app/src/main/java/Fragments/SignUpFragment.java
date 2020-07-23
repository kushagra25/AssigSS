package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.assigss.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    View objectSignUpFragment;
    private EditText userEmailET,userPasswordET;

    private FirebaseAuth objectFirebaseAuth;
    private Button objectButton;
    private ProgressBar objectProgressBar;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SignUpFragment() {

    }



    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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

    public void createUser()
    {
        try
        {
              if(!userEmailET.getText().toString().isEmpty() && !userPasswordET.getText().toString().isEmpty())
              {
                  objectProgressBar.setVisibility(View.VISIBLE);
                  objectButton.setEnabled(false);

                  objectFirebaseAuth.createUserWithEmailAndPassword(userEmailET.getText().toString(),userPasswordET.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                      @Override
                      public void onSuccess(AuthResult authResult) {
                          Toast.makeText(getContext(), "User created...", Toast.LENGTH_SHORT).show();
                          objectProgressBar.setVisibility(View.INVISIBLE);

                          objectButton.setEnabled(true);
                          userEmailET.setText("");

                          userPasswordET.setText("");
                          if (objectFirebaseAuth.getCurrentUser()!=null)
                          {
                              objectFirebaseAuth.signOut();
                          }
                      }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {

                          objectProgressBar.setVisibility(View.INVISIBLE);
                          objectButton.setEnabled(true);
                          Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                      }
                  });
              }
              else
              {
                  Toast.makeText(getContext(), "Please fill the fields first", Toast.LENGTH_SHORT).show();
              }
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void checkUserExists()
    {
        try
        {
            if (objectFirebaseAuth!=null && !userEmailET.getText().toString().isEmpty())
            {
                objectProgressBar.setVisibility(View.INVISIBLE);
                objectButton.setEnabled(false);
                objectFirebaseAuth.fetchSignInMethodsForEmail(userEmailET.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean checkResult=!task.getResult().getSignInMethods().isEmpty();

                        if (!checkResult)
                        {
                            createUser();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "User already been created", Toast.LENGTH_SHORT).show();
                            objectProgressBar.setVisibility(View.INVISIBLE);
                            objectButton.setEnabled(true);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        objectProgressBar.setVisibility(View.INVISIBLE);
                        objectButton.setEnabled(true);

                        Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void attachToXML()
    {
        try
        {
            userEmailET=objectSignUpFragment.findViewById(R.id.emailET);
            userPasswordET=objectSignUpFragment.findViewById(R.id.passwordET);

            objectFirebaseAuth=FirebaseAuth.getInstance();
            objectButton=objectSignUpFragment.findViewById(R.id.signUpBtn);

            objectProgressBar=objectSignUpFragment.findViewById(R.id.progressBarSignUp);

            objectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkUserExists();
                }
            });


        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       objectSignUpFragment=inflater.inflate(R.layout.fragment_sign_up2, container, false);
        attachToXML();

       return objectSignUpFragment;
    }
}