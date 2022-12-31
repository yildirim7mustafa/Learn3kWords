package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class IKnowListActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView getWord;
    private Button pass_button,iForgotButton,backButton;

    List<Word> myKnownList = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    String auth = firebaseAuth.getInstance().getCurrentUser().getUid();
    Word value;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference knownListPush = database.getReference("Users").child(auth).child("known_words");
    DatabaseReference unknownListPush = database.getReference("Users").child(auth).child("unknown_words");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iknow_list);

        pass_button = (Button) findViewById(R.id.iKnowListPassButton);
        pass_button.setOnClickListener(this);

        iForgotButton = (Button) findViewById(R.id.iKnowListForgotButton);
        iForgotButton.setOnClickListener(this);

        backButton = (Button) findViewById(R.id.iKnowListBackButton);
        backButton.setOnClickListener(this);

        getWord = (TextView) findViewById(R.id.iKnowListTextView);

        getWordsList();
        setKey();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iKnowListPassButton:
                getRandomElement(myKnownList);
                break;

            case R.id.iKnowListForgotButton:
                unknownListPush.push().setValue(value);
                deleteKnownWord();
                myKnownList.remove(value);
                getRandomElement(myKnownList);
                startActivity(new Intent(this,IKnowListActivity.class));
                break;

            case R.id.iKnowListBackButton:
                startActivity(new Intent(this,ChooseWordsList.class));
                break;
        }
    }

    private void deleteKnownWord() {
        knownListPush.child(value.getWord_key()).removeValue();
    }

    public void getWordsList(){
        knownListPush.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    Word word = d.getValue(Word.class);
                    myKnownList.add(word);
                }
                getRandomElement(myKnownList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getRandomElement(List<Word> list)
    {
        Random rand = new Random();
        value = list.get(rand.nextInt(list.size()));
        String tempValue = value.getWord_english();
        getWord.setText(tempValue);
        return tempValue;
    }

    public void setKey(){
        knownListPush.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren()){
                    Word word = d.getValue(Word.class);
                    String key = d.getKey();
                    word.setWord_key(key);

                   HashMap hashMap = new HashMap();
                   hashMap.put("word_key",key);
                    knownListPush.child(key).updateChildren(hashMap);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}