package com.example.myposts.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;

import com.example.myposts.data.UserFunctions;
import com.example.myposts.databinding.ActivityRegisterBinding;
import com.example.myposts.models.User;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding mBinding;
    private static final String tag="RegisterActivity";
    private Calendar calender;
    private UserFunctions  userFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRegisterBinding.inflate(getLayoutInflater());

        setContentView(mBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Registration");
            actionBar.setElevation(0f);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        userFunctions=UserFunctions.getInstance(getApplication());
        mBinding.haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));


            }
        });
        //tthe code is okay  now  we can test it by  running on the device
        calender = Calendar.getInstance();

        mBinding.txtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //you dont need  to check id since you are not using onclicklistener interface.


                final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {

                    calender.set(Calendar.YEAR, year);
                    calender.set(Calendar.MONTH, monthOfYear);
                    calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    updateSelectedDob();
                };
                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, date, calender
                        .get(Calendar.YEAR), calender.get(Calendar.MONTH),
                        calender.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(new Date(calender.getTimeInMillis()).getTime());
                dialog.show();
            }
        });
        mBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidInput())
                {
                    User user=new User(mBinding.txtFname.getText().toString().trim(),
                            mBinding.txtEmail.getText().toString().trim(),
                            mBinding.txtPhone.getText().toString().trim(),
                            mBinding.txtDOB.getText().toString().trim()
                    );
                    user.setPassword(mBinding.txtRegisterPassword.getText().toString().trim());
                    Log.d(tag,"onClick: "+user.toString());
                    Long rowId=userFunctions.saveUser(user);
                    if(rowId>0)
                    {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                }
                else
                {
                    Snackbar.make(v,"*Required cannot be empty",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    //GO BACK TO PREVIOUS ACTIVITY WHEN YOU PRESS BACK ARROW BUTTON
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateSelectedDob() {
        String dateFormat = "dd/MM/yyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());

        mBinding.txtDOB.setText(sdf.format(calender.getTime()));
    }

    private boolean ValidInput() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mBinding.txtFname.getText().toString().trim())) {
            isValid = false;
            mBinding.txtFname.setError("Required");
        } else {
            isValid = true;
            mBinding.txtFname.setError(null);
        }
        if (TextUtils.isEmpty(mBinding.txtEmail.getText().toString().trim())) {
            isValid = false;
            mBinding.txtEmail.setError("Required");
        } else {
            isValid = true;
            mBinding.txtEmail.setError(null);
        }
        if (TextUtils.isEmpty(mBinding.txtPhone.getText().toString().trim())) {
            isValid = false;
            mBinding.txtPhone.setError("Required");
        } else {
            isValid = true;
            mBinding.txtPhone.setError(null);
        }
        if (TextUtils.isEmpty(mBinding.txtDOB.getText().toString().trim())) {
            isValid = false;
            mBinding.txtDOB.setError("Required");
        } else {
            isValid = true;
            mBinding.txtDOB.setError(null);
        }
        if (TextUtils.isEmpty(mBinding.txtRegisterPassword.getText().toString().trim())) {
            isValid = false;
            mBinding.txtRegisterPassword.setError("Required");
        } else {
            isValid = true;
            mBinding.txtRegisterPassword.setError(null);
        }
        if (TextUtils.isEmpty(mBinding.txtRegisterConfirmPassword.getText().toString().trim())) {
            isValid = false;
            mBinding.txtRegisterConfirmPassword.setError("Required");
        } else {
            isValid = true;
            mBinding.txtRegisterConfirmPassword.setError(null);
        }

        if(!Pattern.matches(String.valueOf(Patterns.EMAIL_ADDRESS), mBinding.txtEmail.getText().toString().trim()))
        {
            isValid=false;
            mBinding.txtEmail.setError("Email format incorrect");
        }
        else
        {
            isValid=true;
            mBinding.txtEmail.setError(null);
        }
        if(!TextUtils.equals(mBinding.txtRegisterPassword.getText().toString().trim(), mBinding.txtRegisterConfirmPassword.getText().toString().trim()))
        {
            isValid=false;
            mBinding.txtRegisterConfirmPassword.setError("Password not matching");
        }
        else
        {
            isValid = true;
            mBinding.txtRegisterConfirmPassword.setError(null);
        }

        return isValid;
    }
}














