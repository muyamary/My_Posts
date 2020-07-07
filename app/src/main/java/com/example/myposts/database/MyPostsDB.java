package com.example.myposts.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myposts.interfaces.PostDao;
import com.example.myposts.interfaces.UserDao;
import com.example.myposts.models.Post;
import com.example.myposts.models.User;

@Database(entities = {User.class, Post.class}, version=1, exportSchema = false)

public abstract class MyPostsDB extends RoomDatabase {
    private static MyPostsDB instance;

    public static MyPostsDB getDatabase(final Application application)
    {
        if(instance==null)
        {
            synchronized (MyPostsDB.class)
            {
                if(instance==null)
                {
                    instance= Room.databaseBuilder(application.getApplicationContext(),
                            MyPostsDB.class,"my_posts_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }

        }
        return instance;

    }
    public abstract UserDao userDao();

    public abstract PostDao postDao();
}
