package com.example.myposts.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myposts.data.UserFunctions;
import com.example.myposts.databinding.ActivityUpdateUserBinding;
import com.example.myposts.models.User;

public class UpdateUserActivity extends AppCompatActivity {
    private ActivityUpdateUserBinding mBinding;
    private UserFunctions userFunctions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityUpdateUserBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar=getSupportActionBar();
                if(actionBar!=null)
                {
                    actionBar.setTitle("Update User Details");
                    actionBar.setElevation(0f);
                }

                userFunctions=UserFunctions.getInstance(getApplication());
                mBinding.txtUfname.setText(MainActivity.userFetched.getFullname());
                mBinding.txtUemail.setText(MainActivity.userFetched.getEmail());
                mBinding.txtUphone.setText(MainActivity.userFetched.getPhoneNo());
                mBinding.txtUDOB.setText(MainActivity.userFetched.getDob());
                mBinding.btnUpdateUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User user=new User(
                        mBinding.txtUfname.getText().toString().trim(),
                        mBinding.txtUemail.getText().toString().trim(),
                        mBinding.txtUphone.getText().toString().trim(),
                        mBinding.txtUDOB.getText().toString().trim());
                        user.setId(MainActivity.userFetched.getId());
                        user.setPassword(MainActivity.userFetched.getPassword());

                        int myRows=userFunctions.updateUser(user);
                        Toast.makeText(getApplicationContext(),"User: "+user+"And rows are"+myRows,Toast.LENGTH_LONG).show();
                        if(myRows>0) {
                            startActivity(new Intent(UpdateUserActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                });

    }
}
