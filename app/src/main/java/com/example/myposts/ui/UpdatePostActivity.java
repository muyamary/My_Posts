package com.example.myposts.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myposts.adapter.PostAdapter;
import com.example.myposts.data.PostFunctions;
import com.example.myposts.databinding.ActivityUpdatePostBinding;
import com.example.myposts.interfaces.RecyclerviewItem;
import com.example.myposts.models.Post;
import com.example.myposts.models.User;
import com.example.myposts.utils.MyUtils;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdatePostActivity extends AppCompatActivity implements RecyclerviewItem<Post> {
    ActivityUpdatePostBinding mBinding;
    private PostFunctions postFuctions;
    private Context context;
    Bundle bundle;
    private int pID;
    public SimpleDateFormat df;
    public int myrow=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityUpdatePostBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Create Post");
            actionBar.setElevation(0f);
        }

        postFuctions=PostFunctions.getInstance(getApplication());

        bundle=getIntent().getExtras();
        setforUpdated();
        Toast.makeText(getApplicationContext(), "Myrow initial is: "+myrow, Toast.LENGTH_SHORT).show();
        mBinding.btnUpdatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs())
                {
                    User user = MyUtils.getPersistedUser(getApplicationContext());
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    df.format(new Date());
                    Post post=new Post(mBinding.txtUPosttitle.getText().toString().trim(),
                            mBinding.txtUpostbody.getText().toString().trim(),bundle.getString("Time"),user);
                    post.setPostId(Integer.parseInt(bundle.getString("pID")));

                    int rows=postFuctions.updatePost(post);
                    if(rows>0)
                    {
                        myrow=1;
                        startActivity(new Intent(UpdatePostActivity.this,MainActivity.class));
                       Toast.makeText(getApplicationContext(), "Date updated"+myrow, Toast.LENGTH_SHORT).show();
                        finish();
                    }


                }
                else {
                    Snackbar.make(v, "Fix the errors above", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    public void setforUpdated()
    {
        mBinding.txtUPosttitle.setText(bundle.getString("Title"));
        mBinding.txtUpostbody.setText(bundle.getString("Body"));


        mBinding.btnUpdatePost.setVisibility(View.GONE);
        mBinding.btnUpdatePost.setVisibility(View.VISIBLE);

    }
    private boolean validateInputs() {
        boolean isValid = true;
        if (TextUtils.isEmpty(mBinding.txtUPosttitle.getText().toString().trim())) {
            isValid = false;
            mBinding.txtUPosttitle.setError("Required.");
        } else {
            mBinding.txtUPosttitle.setError(null);
        }

        if (TextUtils.isEmpty(mBinding.txtUpostbody.getText().toString().trim())) {
            isValid = false;
            mBinding.txtUpostbody.setError("Required.");
        } else {
            mBinding.txtUpostbody.setError(null);
        }

        return isValid;
    }


    @Override
    public void onItemClicked(Post post) {

    }
}
