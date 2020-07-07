package com.example.myposts.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myposts.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE email=:email AND password=:password")
    User login(String email, String password);

    @Query("SELECT * FROM user WHERE email=:email")
    User fetchUser(String email);

    @Query("SELECT * FROM user WHERE 1")
    List<User> fetchUsers();

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    long saveUser(User user);

    @Update(onConflict=OnConflictStrategy.REPLACE)
    Integer updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM user WHERE 1")
    void deleteUsers();



}
