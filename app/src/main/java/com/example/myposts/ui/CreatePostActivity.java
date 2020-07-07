package com.example.myposts.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myposts.adapter.PostAdapter;
import com.example.myposts.data.PostFunctions;
import com.example.myposts.databinding.ActivityCreatePostBinding;
import com.example.myposts.databinding.ActivityMainBinding;
import com.example.myposts.interfaces.RecyclerviewItem;
import com.example.myposts.models.Post;
import com.example.myposts.models.User;
import com.example.myposts.utils.MyUtils;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreatePostActivity extends AppCompatActivity    {
    private ActivityCreatePostBinding mBinding;
    private PostFunctions postFuctions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setTitle("Create Post");
            actionBar.setElevation(0f);
        }
        postFuctions=new PostFunctions();

        mBinding.btnSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    User user = MyUtils.getPersistedUser(getApplicationContext());
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Post post = new Post(
                            mBinding.txtPosttitle.getText().toString().trim(),
                            mBinding.txtPostbody.getText().toString().trim(),
                            df.format(new Date()),
                            user

                    );

                    long created = postFuctions.savePost(post);
                    if (created > 0) {
                        startActivity(new Intent(CreatePostActivity.this, MainActivity.class));
                        finish();
                    }

                } else {
                    Snackbar.make(v, "Fix the errors above", Snackbar.LENGTH_LONG).show();
                }

            }


        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateInputs() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mBinding.txtPosttitle.getText().toString().trim())) {
            isValid = false;
            mBinding.txtPosttitle.setError("Required.");
        } else {
            mBinding.txtPosttitle.setError(null);
        }

        if (TextUtils.isEmpty(mBinding.txtPostbody.getText().toString().trim())) {
            isValid = false;
            mBinding.txtPostbody.setError("Required.");
        } else {
            mBinding.txtPostbody.setError(null);
        }

        return isValid;
    }


}
