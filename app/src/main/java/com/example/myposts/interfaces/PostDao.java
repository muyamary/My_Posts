package com.example.myposts.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myposts.models.Post;
import com.example.myposts.models.User;

import java.util.List;

@Dao public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long savePost(Post post);

    @Update(onConflict=OnConflictStrategy.REPLACE)
    Integer updatePost(Post post);

    @Query("SELECT * FROM post WHERE postId=:postid")
    Post fetchPost(int postid);

    @Query("SELECT * FROM post WHERE 1")
    List<Post> fetchPosts();

    @Delete
    void deletePost(Post post);

    @Query("DELETE FROM post where 1")
    void deletePosts();


}
