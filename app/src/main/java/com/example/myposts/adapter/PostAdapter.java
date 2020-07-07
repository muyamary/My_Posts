package com.example.myposts.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myposts.data.PostFunctions;
import com.example.myposts.databinding.PostItemLayoutBinding;
import com.example.myposts.interfaces.RecyclerviewItem;
import com.example.myposts.models.Post;
import com.example.myposts.models.User;
import com.example.myposts.ui.CreatePostActivity;
import com.example.myposts.ui.MainActivity;
import com.example.myposts.ui.UpdatePostActivity;
import com.example.myposts.utils.MyUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;


    private RecyclerviewItem<Post> listener;
    public List<Post> posts = new ArrayList<>();
    private PostFunctions postFunctions;
    private UpdatePostActivity updatepostActivity;
    public PostAdapter(Context context, RecyclerviewItem<Post> listener) {
        this.context = context;
        this.listener = listener;
    }

    public PostAdapter(Context context) {
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        PostItemLayoutBinding mBinding = PostItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostAdapter.ViewHolder(mBinding);



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User loggedInUser = MyUtils.getPersistedUser(context);
        Post post = posts.get(position);
       postFunctions=new PostFunctions();
       updatepostActivity=new UpdatePostActivity();
        holder.mBinding.tvTitle.setText(post.getTitle());
        holder.mBinding.tvBody.setText(post.getBody());
        holder.mBinding.tvDatePosted.setText("Posted:"+post.getDate());

        holder.mBinding.postedBy.setText(post.getPostedBy().getFullname());
       if(updatepostActivity.myrow>0)
       {
           Toast.makeText(context, "ChingDate updated"+updatepostActivity.myrow+updatepostActivity.df.format(new Date()), Toast.LENGTH_SHORT).show();
           //holder.mBinding.tvDateUpdated.setText(updatepostActivity.df.format(new Date()));
       }
        if (loggedInUser.getEmail().equals(post.getPostedBy().getEmail())) {
            
        } else {
            holder.mBinding.btnDeletePost.setVisibility(View.GONE);
            holder.mBinding.btnEditPost.setVisibility(View.GONE);

        }
        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(post);
            }

        });
        holder.mBinding.btnDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFunctions.deletePost(post);
                //Toast.makeText(context, "Deleted post at position"+position+posts, Toast.LENGTH_SHORT).show();
                posts.remove(position);
                notifyDataSetChanged();


            }
        });
        holder.mBinding.btnEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //postEdit=postFunctions.fetchPost(position);
               // Toast.makeText(context, "Post Retrieved Title: "+post.getTitle(), Toast.LENGTH_SHORT).show();
                Bundle bundle=new Bundle();
                Intent intent=new Intent(context, UpdatePostActivity.class);
                bundle.putString("pID",post.getPostId()+"");
                bundle.putString("Title",post.getTitle());
                bundle.putString("Body",post.getBody());
                bundle.putString("Time",post.getDate());
                intent.putExtras(bundle);
                context.startActivity(intent);


            }
        });
    }




    @Override
    public int getItemCount() {
        return posts.size();
    }
    static final class ViewHolder extends RecyclerView.ViewHolder{
    PostItemLayoutBinding mBinding;
    ViewHolder(@NonNull PostItemLayoutBinding binding) {
        super(binding.getRoot());
        mBinding = binding;

    }
}

    public void setData(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }
}
