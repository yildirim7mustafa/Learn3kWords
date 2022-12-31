package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class IDoNotKnowListActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView getWord;
    private Button pass_button,iLearnedButton,backButton;

    List<Word> myDoNotKnownList = new ArrayList<>();
    FirebaseAuth firebaseAuth;
    String auth = firebaseAuth.getInstance().getCurrentUser().getUid();
    Word value;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference knownListPush = database.getReference("Users").child(auth).child("known_words");
    DatabaseReference unknownListPush = database.getReference("Users").child(auth).child("unknown_words");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ido_not_know_list);

        pass_button = (Button) findViewById(R.id.iDoNotKnowListPassButton);
        pass_button.setOnClickListener(this);

        iLearnedButton = (Button) findViewById(R.id.iDoNotKnowListLearnedButton);
        iLearnedButton.setOnClickListener(this);

        backButton = (Button) findViewById(R.id.iDoNotKnowListBackButton);
        backButton.setOnClickListener(this);

        getWord = (TextView) findViewById(R.id.iDoNotKnowListTextView);

        getWordsList();
        setKey();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iDoNotKnowListPassButton:
                getRandomElement(myDoNotKnownList);
                break;

            case R.id.iDoNotKnowListLearnedButton:
                knownListPush.push().setValue(value);
                deleteKnownWord();
                myDoNotKnownList.remove(value);
                getRandomElement(myDoNotKnownList);
                startActivity(new Intent(this,IDoNotKnowListActivity.class));
                break;

            case R.id.iDoNotKnowListBackButton:
                startActivity(new Intent(this,ChooseWordsList.class));
                break;
        }
    }

    private void deleteKnownWord() {
        unknownListPush.child(value.getWord_key()).removeValue();
    }

    public void getWordsList(){
        unknownListPush.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    Word word = d.getValue(Word.class);
                    myDoNotKnownList.add(word);
                }
                getRandomElement(myDoNotKnownList);

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
        unknownListPush.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d: snapshot.getChildren()){
                    Word word = d.getValue(Word.class);
                    String key = d.getKey();
                    word.setWord_key(key);

                    HashMap hashMap = new HashMap();
                    hashMap.put("word_key",key);
                    unknownListPush.child(key).updateChildren(hashMap);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}