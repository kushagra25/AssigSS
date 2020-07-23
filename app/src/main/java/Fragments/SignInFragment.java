package Fragments;

import android.content.Intent;
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

import com.example.assigss.HomePage;
import com.example.assigss.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    private View objectSignInFragment;
    private FirebaseAuth objectFirebaseAuth;

    private EditText userEmailET,userPasswordET;
    private Button signInBtn;

    private ProgressBar objectProgressBar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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

    private void initializeVariables()
    {
        try
        {
            objectFirebaseAuth=FirebaseAuth.getInstance();
            userEmailET=objectSignInFragment.findViewById(R.id.signInEmailET);

            userPasswordET=objectSignInFragment.findViewById(R.id.signInPasswordET);
            signInBtn=objectSignInFragment.findViewById(R.id.signInBtn);

            objectProgressBar=objectSignInFragment.findViewById(R.id.signInProgressBar);
            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signInUser();

                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void signInUser()
    {
        try
        {
            if (!userEmailET.getText().toString().isEmpty() && !userPasswordET.getText().toString().isEmpty())
            {
                if (objectFirebaseAuth!=null)
                {
                    objectProgressBar.setVisibility(View.INVISIBLE);
                    signInBtn.setEnabled(false);

                    objectFirebaseAuth.signInWithEmailAndPassword(userEmailET.getText().toString(),userPasswordET.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(getActivity().getApplicationContext(), HomePage.class));
                            objectProgressBar.setVisibility(View.INVISIBLE);

                            signInBtn.setEnabled(true);
                            getActivity().finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            objectProgressBar.setVisibility(View.INVISIBLE);

                            signInBtn.setEnabled(true);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
            else
            {
                Toast.makeText(getContext(), "Please Fill Both Fields", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectSignInFragment=inflater.inflate(R.layout.fragment_sign_in, container, false);
        initializeVariables();

        return objectSignInFragment;
    }
}