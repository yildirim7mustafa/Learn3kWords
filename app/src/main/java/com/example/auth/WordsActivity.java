package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button goProfile_button,iKnowButton,iDoNotKnowButton;
    private TextView getWord;

    List<Word> mainWordList = new ArrayList<>();

    FirebaseAuth firebaseAuth;
    String auth = firebaseAuth.getInstance().getCurrentUser().getUid();


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference knownListPush = database.getReference("Users").child(auth).child("known_words");
    DatabaseReference unknownListPush = database.getReference("Users").child(auth).child("unknown_words");

    Word value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        goProfile_button = (Button) findViewById(R.id.goProfile3_button);
        goProfile_button.setOnClickListener(this);

        iKnowButton = (Button) findViewById(R.id.iKnowButton);
        iKnowButton.setOnClickListener(this);

        iDoNotKnowButton = (Button) findViewById(R.id.iDoNotKnowButton);
        iDoNotKnowButton.setOnClickListener(this);

        getWord = (TextView) findViewById(R.id.getWord);

        addWord();
        getRandomElement(mainWordList);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goProfile3_button:
                startActivity(new Intent(this,ChooseWordsList.class));
                break;

            case R.id.iKnowButton:
                knownListPush.push().setValue(value);
                getRandomElement(mainWordList);

                break;

            case R.id.iDoNotKnowButton:
                unknownListPush.push().setValue(value);
                getRandomElement(mainWordList);
                break;
        }

    }




    public String getRandomElement(List<Word> list)
    {
        Random rand = new Random();
        value = list.get(rand.nextInt(list.size()));
        String tempValue = value.getWord_english();
        getWord.setText(tempValue);
        return tempValue;
     /*
        Random rand = new Random();
        value = list.get(rand.nextInt(list.size()));
        getWord.setText(value);
        return value;
      */
    }

    private void addWord() {
        AssetManager assetsManager = getApplicationContext().getAssets(); // or getBaseContext()
        try{
            InputStream inputStream = assetsManager.open("words.txt");
            Scanner myReader = new Scanner(inputStream);
            while (myReader.hasNextLine()){
                String data = myReader.nextLine();
                Word word = new Word();
                word.setWord_english(data);
                word.setWord_key("");
                mainWordList.add(word);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }
}