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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        TextView t=(TextView) findViewById(R.id.app_name);
//        new Handler(Looper.getMainLooper()) {
//        }.postDelayed(() -> startActivity(new Intent(MainActivity.this, login.class)), 2000);
        String appID = "application-2-sfznr"; // replace this with your App ID
        app = new App(new AppConfiguration.Builder(appID).build());
        Credentials emailPasswordCredentials = Credentials.emailPassword("yashwanth@student.tce.edu","yashw#123@");
        app.loginAsync(emailPasswordCredentials,it->{
            if (it.isSuccess()) {

                Log.v("AUTH", "Successfully authenticated using an email and password.");

                Toast.makeText(MainActivity.this,"Succesfully login",Toast.LENGTH_SHORT).show();
                //  Toast.makeText(LoginActivity.this,customUserData.toString(), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this,it.getError().toString(),Toast.LENGTH_SHORT).show();
                Log.e("AUTH", it.getError().toString());
            }
        });
        User user = app.currentUser();
        MongoClient mongoClient = user.getMongoClient("Application-2");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("madurai");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("raj");
        String txt = (String) t.getText();
        mongoCollection.insertOne(new Document("exam_name",txt)).getAsync(result ->{
            if(result.isSuccess()){
                Toast.makeText(MainActivity.this,"Exam Added",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Error Obtained :" + result.getError().toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }


}