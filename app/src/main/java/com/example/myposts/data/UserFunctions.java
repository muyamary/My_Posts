package com.example.myposts.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.room.Database;

import com.example.myposts.database.MyPostsDB;
import com.example.myposts.models.User;

import java.util.concurrent.ExecutionException;

public class UserFunctions {
    private static UserFunctions instance;
    private Application application;
    public static UserFunctions getInstance(Application application)
    {
        if(instance==null)
        {
            instance=new UserFunctions();
            instance.application=application;
        }
        return instance;
    }

    public User login(String email, String password)
    {
        User user;
        try {
            user=new LoginAsyncTask(application,email,password).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }
    public User fetchUser(String email)
    {
        User user;
        try {
            user=new FetchUserAsyncTask(application,email).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public long saveUser(User user)
    {
        long rowId=-1;
        try {
            rowId=new SaveUserAsyncTask(application).execute(user).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return rowId;
    }

    //update user
    public int updateUser(User user)
    {
      int rows=-1;
        try {
            rows=new UpdateUserAsyncTask(application).execute(user).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();

        }
        return rows;

    }

    //class to authenticate user

    private static final class LoginAsyncTask extends AsyncTask<Void, Void,User>
    {
        private String email;
        private String password;
        private  MyPostsDB mydatabase;
        public LoginAsyncTask(Application application, String email, String password)
        {
            this.email=email;
            this.password =password;
            mydatabase=MyPostsDB.getDatabase(application);
        }
        @Override
        protected User doInBackground(Void... voids)
        {
            return mydatabase.userDao().login(email,password);

        }

    }


    //class to  save user
    private static final class SaveUserAsyncTask extends AsyncTask<User,Void,Long>
    {
                private  MyPostsDB mydatabase;
                private Application application;
        public SaveUserAsyncTask(Application application)
        {
            this.application=application;
            mydatabase=MyPostsDB.getDatabase(application);
        }
        @Override
        protected Long doInBackground(User... args)
        {
            return mydatabase.userDao().saveUser(args[0]);

        }
        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            if (aLong > 0) {
                Toast.makeText(application.getApplicationContext(), "Account was created successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(application.getApplicationContext(), "Error occurred while creating your account. Please try again.", Toast.LENGTH_SHORT).show();

            }

        }

    }
    private static final class UpdateUserAsyncTask extends AsyncTask<User,Void,Integer> {
        private MyPostsDB mydatabase;
        private Application application;

        public UpdateUserAsyncTask(Application application) {
            this.application = application;
            mydatabase = MyPostsDB.getDatabase(application);
        }

        @Override
        protected Integer doInBackground(User... users) {
            return mydatabase.userDao().updateUser(users[0]);
        }

        @Override
        protected void onPostExecute(Integer myR) {
            super.onPostExecute(myR);
            if (myR > 0) {
                Toast.makeText(application.getApplicationContext(), "Account Row:"+myR+" was Updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(application.getApplicationContext(), "Error occurred while updating your account, Rows+"+myR+". Please try again.", Toast.LENGTH_SHORT).show();

            }
        }
    }


    //Fetch user
    private static final class FetchUserAsyncTask extends AsyncTask<Void,Void,User>
    {
        private String email;
        private  MyPostsDB mydatabase;
        private Application application;
        public FetchUserAsyncTask(Application application,String email)
        {
            this.application=application;
            this.email=email;
            mydatabase=MyPostsDB.getDatabase(application);
        }
        @Override
        protected User doInBackground(Void... voids) {
            return mydatabase.userDao().fetchUser(email);
        }
    }


    }

