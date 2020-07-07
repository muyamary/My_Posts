package com.example.myposts.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.myposts.data.UserFunctions;
import com.example.myposts.databinding.ActivityLoginBinding;
import com.example.myposts.models.User;
import com.example.myposts.utils.MyUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding mBinding;
    private UserFunctions userFunctions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Login");
            actionBar.setElevation(0f);
        }
        mBinding.dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        userFunctions=UserFunctions.getInstance(getApplication());

        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidInput())
                {
                    User user=userFunctions.login(mBinding.txtLoginemail.getText().toString().trim(),mBinding.txtLoginpassword.getText().toString().trim());
                    if(user!=null)
                    {
                        MyUtils.persistUser(getApplicationContext(),user);
                        MyUtils.logUser(getApplicationContext());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else
                        {
                            Toast.makeText(getApplicationContext(), "You entered incorrect Username or password", Toast.LENGTH_SHORT).show();
                        }

                }
                else
                {
                    Snackbar.make(v,"Username and password cannot be empty", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean ValidInput() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mBinding.txtLoginemail.getText().toString().trim())) {
            isValid = false;
            mBinding.txtLoginemail.setError("Required");
        } else {
            isValid = true;
            mBinding.txtLoginemail.setError(null);
        }


        if (TextUtils.isEmpty(mBinding.txtLoginpassword.getText().toString().trim())) {
            isValid = false;
            mBinding.txtLoginpassword.setError("Required");
        } else {
            isValid = true;
            mBinding.txtLoginpassword.setError(null);
        }
        if(!Pattern.matches(String.valueOf(Patterns.EMAIL_ADDRESS),mBinding.txtLoginemail.getText().toString().trim()) )
        {
            isValid=false;
            mBinding.txtLoginemail.setError("Wrong Email Format");
        }
        else
        {
            isValid=true;
            mBinding.txtLoginemail.setError(null);
        }
        return isValid;
    }

}
