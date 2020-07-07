package com.example.myposts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myposts.R;
import com.example.myposts.adapter.PostAdapter;
import com.example.myposts.data.PostFunctions;
import com.example.myposts.data.UserFunctions;
import com.example.myposts.databinding.ActivityMainBinding;
import com.example.myposts.interfaces.RecyclerviewItem;
import com.example.myposts.models.Post;
import com.example.myposts.models.User;
import com.example.myposts.utils.MyUtils;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements RecyclerviewItem<Post> {
    private ActivityMainBinding mBinding;
    private PostAdapter postAdapter;
    private PostFunctions postFunctions;
    private UserFunctions userFunctions;
    private UpdateUserActivity userActivity;
    private Context context;
    public static User userFetched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
           actionBar.setTitle("My Posts");
           actionBar.setElevation(0f);
        }
        userFunctions=UserFunctions.getInstance(getApplication());
        userActivity=new UpdateUserActivity();
        postFunctions=PostFunctions.getInstance(getApplication());
        postAdapter = new PostAdapter(this, this);
        showPosts();


    }
    private void showPosts() {
        postAdapter.setData(postFunctions.fetchPosts());
        mBinding.postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.postsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.postsRecyclerView.setAdapter(postAdapter);
        mBinding.postsRecyclerView.setHasFixedSize(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_create_post) {
            startActivity(new Intent(MainActivity.this, CreatePostActivity.class));
        }
        else if (id ==R.id.action_ClearAll) {
            //navigate to profile page
            Toast.makeText(getApplicationContext(),"Deleting Posts",Toast.LENGTH_LONG).show();
            postFunctions.deletePosts();
            postAdapter.posts.clear();
            postAdapter.notifyDataSetChanged();

            return true;

        }
        else if (id == R.id.action_profile) {

           User userLoggedIn = MyUtils.getPersistedUser(this);

           userFetched= userFunctions.fetchUser(userLoggedIn.getEmail());
            Toast.makeText(getApplicationContext(),"User"+userFetched,Toast.LENGTH_LONG).show();
           if(userFetched!=null)
            {
                startActivity(new Intent(MainActivity.this, UpdateUserActivity.class));
            }

            }
        else if (id == R.id.action_logout) {     logout();  }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        new MaterialStyledDialog.Builder(this)
                .setTitle("Sign Out")
                .setDescription("Are you sure you want to Sign Out?")
                .setIcon(R.drawable.ic_info_black_24dp)
                .setPositiveText("Sign Out")
                .onPositive((dialog, which) -> {

                    Toast.makeText(getApplicationContext(), "You are now signed out.", Toast.LENGTH_SHORT).show();
                    MyUtils.logoutUser(getApplicationContext());
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    dialog.dismiss();

                })
                .setNegativeText("Cancel")
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

   @Override
    protected void onStart() {
        super.onStart();
        if (!MyUtils.isLoggedIn(this)) {
            Toast.makeText(this, "Login Required", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onItemClicked(Post post) {
        Toast.makeText(this, "Title "+post.getTitle()+" POSTED BY "+post.getPostedBy().getFullname(), Toast.LENGTH_LONG ).show();

    }
}
