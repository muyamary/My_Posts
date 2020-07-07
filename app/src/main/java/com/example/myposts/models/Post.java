package com.example.myposts.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index( value="title",unique=true)})
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int postId;
    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name="body")
    private String body;
    @ColumnInfo(name="date")
    private String date;
    @Embedded
    private User postedBy;

    @Ignore
    public Post(String title, String body, String date, User postedBy)
    {
        this.title = title;
        this.body = body;
        this.date = date;

        this.postedBy = postedBy;
    }
    public Post()
    {

    }
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }


    @Ignore
    public String toString() {
        return "Post{" +
                "id=" + postId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", date='" + date + '\'' +
                ", postedBy=" + postedBy +
                '}';
    }



}
