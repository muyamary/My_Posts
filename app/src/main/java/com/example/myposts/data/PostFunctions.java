package com.example.myposts.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.myposts.database.MyPostsDB;
import com.example.myposts.models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PostFunctions {
    private static PostFunctions instance;
    private Application application;

    public static PostFunctions getInstance(Application application){
        if (instance == null) {
            instance = new PostFunctions();
            instance.application = application;
        }

        return instance;
    }

    public long savePost(Post post) {
        //do async task to save post to db
        long rows = -1;
        try {
            rows = new SavePostTask(application).execute(post).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return rows;
    }
    public Integer updatePost(Post post)
    {
        int rows=-1;
        try {
            rows=new UpdatePostTask(application).execute(post).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return rows;
    }
    public void deletePosts()
    {
        new DeletePostsTask(application).execute();
    }

    public void deletePost(Post post)
    {
        new DeletePostTask(application).execute(post);
    }

    public List<Post> fetchPosts(){
        List<Post> posts = new ArrayList<>();
        try {
            posts = new FetchPostsTask(application).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return posts;
    }
    public Post fetchPost(int postId)
    {
        Post post;
        try {
            post=new FetchPostTask(application,postId).execute().get();
        } catch (ExecutionException | InterruptedException e) {

            return null;
        }
        return post;
    }

    private static final class SavePostTask extends AsyncTask<Post, Void, Long> {
        private MyPostsDB db;
        private Application application;
        SavePostTask(Application application){
            this.application = application;
            this.db = MyPostsDB.getDatabase(application);
        }
        @Override
        protected Long doInBackground(Post... args) {
            return db.postDao().savePost(args[0]);
        }

        @Override
        protected void onPostExecute(Long rows) {
            super.onPostExecute(rows);
            if (rows > 0) {
                Toast.makeText(application.getApplicationContext(), "Post Created.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(application.getApplicationContext(), "Error occurred while saving your post.", Toast.LENGTH_SHORT).show();

            }
        }
    }

    //Uppdate Post

    private static final class UpdatePostTask extends AsyncTask<Post, Void, Integer> {
        private MyPostsDB db;
        private Application application;
        UpdatePostTask(Application application){
            this.application = application;
            this.db = MyPostsDB.getDatabase(application);
        }
        @Override
        protected Integer doInBackground(Post... args) {
            return db.postDao().updatePost(args[0]);
        }

        @Override
        protected void onPostExecute(Integer rows) {
            super.onPostExecute(rows);
            if (rows > 0) {
                Toast.makeText(application.getApplicationContext(), "Post Updated Successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(application.getApplicationContext(), "Error occurred while updating your post.", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private static final class FetchPostsTask extends AsyncTask<Void, Void, List<Post>>{
        private MyPostsDB db;
        private Application application;

        FetchPostsTask(Application application){
            this.application = application;
            this.db = MyPostsDB.getDatabase(application);
        }


        @Override
        protected List<Post> doInBackground(Void... voids) {
            return db.postDao().fetchPosts();
        }
    }

    private static final class FetchPostTask extends AsyncTask<Void, Void, Post>
    {
        private MyPostsDB db;
        private Application application;
        private int postId;
        public FetchPostTask(Application application,int postId)
        {
            this.application=application;
            this.db=MyPostsDB.getDatabase(application);
            this.postId=postId;

        }

        @Override
        protected Post doInBackground(Void... voids) {
            return db.postDao().fetchPost(postId);
        }
    }

    private static final class DeletePostsTask extends AsyncTask<Void, Void, Void>{
        private MyPostsDB db;
        private Application application;

        DeletePostsTask(Application application){
            this.application = application;
            this.db = MyPostsDB.getDatabase(application);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            db.postDao().deletePosts();
            return null;
        }
    }

    private static final class DeletePostTask extends AsyncTask<Post, Void, Void>{
        private MyPostsDB db;
        private Application application;

        DeletePostTask(Application application){
            this.application = application;
            this.db = MyPostsDB.getDatabase(application);
        }

        @Override
        protected Void doInBackground(Post... posts) {
            db.postDao().deletePost(posts[0]);
            return null;
        }





        /*@Override
        protected void onPostExecute(Long rows) {
            super.onPostExecute(rows);
            if (rows > 0) {
                Toast.makeText(application.getApplicationContext(), "Post deleted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(application.getApplicationContext(), "Error occurred while deleting your post.", Toast.LENGTH_SHORT).show();

            }
        }*/


    }
}
