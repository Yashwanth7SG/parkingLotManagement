package com.example.parkinglotmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.bson.Document;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;


public class MainActivity extends AppCompatActivity {
    public static App app;
    public static AtomicReference<User> user;
    private static String email ="yashwanth@student.tce.edu";
    private static String password="yashw#123@";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        TextView t= findViewById(R.id.app_name);

        String appID = "application-2-sfznr"; // replace this with your App ID
        String txt= (String) t.getText();

        app = new App(new AppConfiguration.Builder(appID).build());
        Credentials emailPasswordCredentials = Credentials.emailPassword("sksganeshbabu12467@gmail.com","Yashwanth");
        app.loginAsync(emailPasswordCredentials,it->{
            if (it.isSuccess()) {
                user.set(app.currentUser());
                Log.v("AUTH", "Successfully authenticated using an email and password.");

                Toast.makeText(MainActivity.this,"Succesfully login",Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(MainActivity.this,it.getError().toString(),Toast.LENGTH_SHORT).show();
                Log.e("AUTH", it.getError().toString());
            }
        });

        User user = app.currentUser();
        MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("madurai");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("raj");


        mongoCollection.insertOne(new Document("THE",txt)).getAsync(result ->{
            if(result.isSuccess()){
                Toast.makeText(MainActivity.this,"Exam Added",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Error Obtained :" + result.getError().toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }


}