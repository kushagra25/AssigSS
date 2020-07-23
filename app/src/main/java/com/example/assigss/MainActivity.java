package com.example.assigss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import Fragments.SignInFragment;
import Fragments.SignUpFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView objectBottomNavigationView;
    SignUpFragment objectSignUpFragment;

    SignInFragment objectSignInFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeObject();
        replaceFragments(objectSignUpFragment);
        objectBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.itemSignUp:
                        replaceFragments(objectSignUpFragment);
                    case R.id.itemSignIn:
                        replaceFragments(objectSignInFragment);
                        return true;
                    default:
                            return false;
                }

            }
        });
    }

    private void replaceFragments(Fragment objectFragment)
    {
        try
        {
            FragmentTransaction objectFragmentTransaction=getSupportFragmentManager().beginTransaction();
            objectFragmentTransaction.replace(R.id.container,objectFragment).commit();
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeObject()
    {
        try
        {
            objectBottomNavigationView=findViewById(R.id.BNV);
            objectSignUpFragment=new SignUpFragment();

            objectSignInFragment=new SignInFragment();

        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}