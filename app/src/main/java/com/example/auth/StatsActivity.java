package com.example.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    static int sayac = 0;
    private Button goProfile_button;
    private TextView nameText, wordsCountText,stat1,stat2,stat3,stat4,stat5,statPoint1,statPoint2,statPoint3,statPoint4,statPoint5;
    int value=-5;
    List<Stat> statList = new ArrayList<>();
    List<UserIdandUserName> usersIdandName = new ArrayList<>();
    List<Stat> showingStats = new ArrayList<>();
    Stat[] stats = new Stat[5];

    FirebaseAuth firebaseAuth;
    String auth = firebaseAuth.getInstance().getCurrentUser().getUid(); // giris yapılan kullanıcısının idsini veriyor
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference query = database.getReference("Users").child(auth).child("known_words"); // giris yapılan kullanıcı kelimelerini geziyor
    DatabaseReference myRef = database.getReference("Users"); // userları geziyo
    Map<String,Object> info = new HashMap<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        stat1 = (TextView) findViewById(R.id.statName1);
        stat2 = (TextView) findViewById(R.id.statName2);
        stat3 = (TextView) findViewById(R.id.statName3);
        stat4 = (TextView) findViewById(R.id.statName4);
        stat5 = (TextView) findViewById(R.id.statName5);

        statPoint1 = (TextView) findViewById(R.id.statPoint1);
        statPoint2 = (TextView) findViewById(R.id.statPoint2);
        statPoint3 = (TextView) findViewById(R.id.statPoint3);
        statPoint4 = (TextView) findViewById(R.id.statPoint4);
        statPoint5 = (TextView) findViewById(R.id.statPoint5);


        goProfile_button = (Button) findViewById(R.id.goProfile2_button);
        goProfile_button.setOnClickListener(this);

        getUsersID();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              getUserStats(usersIdandName);
            }
        }, 3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Collections.sort(statList);
                Collections.reverse(statList);
                //print(statList);
                for(int i = 0; i < 5; i++){
                stats[i] = statList.get(i);
                }
                stat1.setText(stats[0].getFullName());
                stat2.setText(stats[1].getFullName());
                stat3.setText(stats[2].getFullName());
                stat4.setText(stats[3].getFullName());
                stat5.setText(stats[4].getFullName());

                statPoint1.setText(String.valueOf(stats[0].getWordCount()));
                statPoint2.setText(String.valueOf(stats[1].getWordCount()));
                statPoint3.setText(String.valueOf(stats[2].getWordCount()));
                statPoint4.setText(String.valueOf(stats[3].getWordCount()));
                statPoint5.setText(String.valueOf(stats[4].getWordCount()));
            }
        }, 3300);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goProfile2_button:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }

    void getUsersID(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d: snapshot.getChildren()){
                    User user = d.getValue(User.class);
                    UserIdandUserName userIdandUserName = new UserIdandUserName();
                    userIdandUserName.setUserId(d.getKey());
                    userIdandUserName.setUserName(user.getFullName());
                    usersIdandName.add(userIdandUserName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    List<Stat> getUserStats(List<UserIdandUserName> list){
        List<Stat> myStats =new ArrayList<>();
        for(UserIdandUserName userIdandUserName:list){
            String id = userIdandUserName.getUserId();
            myRef.child(id).child("known_words").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot d:snapshot.getChildren()){
                        sayac++;

                    }
                    Stat stat = new Stat();
                    stat.setFullName(userIdandUserName.getUserName());
                    stat.setWordCount(sayac);
                    sayac = 0;
                    statList.add(stat);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return myStats;
    }

    void print(List<Stat> stats){
        for(Stat stat:stats){
            Log.e("id",stat.getFullName());
            Log.e("value",String.valueOf(stat.getWordCount()));
        }
    }
}

/*

public int getPoint(){

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                     sayac++;
                     d.getKey();
                }

                //Log.e("**************",String.valueOf(sayac));
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Stat stat = new Stat();
                        stat.setFullName();
                        stat.setWordCount(sayac);
                        statList.add(stat);
                    }
                }, 10000);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return sayac;
    }

List<String> uids = new ArrayList<>();
      System.out.println("selamaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
              getStats(getWords());
              print(getUsersPoint(getWords()));

private void getStats(List<String> stats){
        for(String stat:stats){
        Log.e("Message getStat*******************************************",stat);
        }
        }
private List<String> getWords(){
        List<String> myList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot snapshot) {

        for(DataSnapshot d:snapshot.getChildren()){
        User user = d.getValue(User.class);
        uids.add(d.getKey());
                    */
/*query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int sayac = 0;
                        for(DataSnapshot d2:snapshot.getChildren()){
                            sayac++;
                        }
//                        Log.e("ids",user.fullName);
//                        Log.e("uid",d.getKey());
//                        Log.e("sayac", String.valueOf(sayac));
//                        String temp = user.fullName;
//                        myList.add(temp);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*//*

        }
        }
@Override
public void onCancelled(@NonNull DatabaseError error) {

        }
        });
        return myList;
        }

private List<Stat> getUsersPoint(List<String> myList){
        List<Stat> stats = new ArrayList<>();
        for(String id:myList){
        DatabaseReference query = database.getReference("Users").child(id).child("known_words");
        query.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot snapshot) {
        int sayac = 0;
        for (DataSnapshot d:snapshot.getChildren()){
        sayac++;
        }
        Stat stat = new Stat();
        stat.setFullName(id);
        stat.setWordCount(sayac);
        stats.add(stat);
        }

@Override
public void onCancelled(@NonNull DatabaseError error) {

        }
        });
        }
        return stats;
        }

private void print(List<Stat> stats){
        for(Stat stat:stats){
        System.out.println(stat.getFullName() + stat.getWordCount()+ "********************************************");

        }
        }
        }
        }
  */
