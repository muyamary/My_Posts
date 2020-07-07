package com.example.myposts.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices={@Index(value = "email", unique = true), @Index(value = "fullname", unique = true)})
public class User {
    @PrimaryKey(autoGenerate =true)
    private int id;
    @ColumnInfo(name="fullname")
    private String fullname;
    @ColumnInfo(name="email")
    private String email;
    @ColumnInfo(name="PhoneNumber")
    private String phoneNo;
    @ColumnInfo(name="DOB")
    private String dob;
    @ColumnInfo(name="password")
    private String password;

public User()
{

}
@Ignore
public User(String fullname,String email,String phoneNo, String dob) {

    this.fullname = fullname;
    this.email = email;
    this.phoneNo = phoneNo;
    this.dob = dob;
}
/*@Ignore
public User(int id,String fullname,String email,String phoneNo, String dob) {
        this.id=id;
        this.fullname = fullname;
        this.email = email;
        this.phoneNo = phoneNo;
        this.dob = dob;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDob() {
        return dob;
    }
    public void setDob(String dob){this.dob=dob;}


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

   @Override
   public String toString() {
       return "User{" +
               "id=" + id +
               ", fullname='" + fullname + '\'' +
               ", email='" + email + '\'' +
               ", phone number='" + phoneNo + '\'' +
               ", DOB='" + dob + '\'' +
               ", password='" + password + '\'' +
               '}';
   }
}
