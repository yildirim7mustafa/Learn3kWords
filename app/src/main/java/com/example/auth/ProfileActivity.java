package com.example.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button words_button, challange_button,stats_button,logout_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        words_button = (Button) findViewById(R.id.words_button);
        words_button.setOnClickListener(this);

        challange_button = (Button) findViewById(R.id.challange_button);
        challange_button.setOnClickListener(this);

        stats_button = (Button) findViewById(R.id.stats_button);
        stats_button.setOnClickListener(this);

        logout_button = (Button) findViewById(R.id.logout_button);
        logout_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.words_button:
                startActivity(new Intent(this,ChooseWordsList.class));
                break;
            case R.id.challange_button:
                startActivity(new Intent(this,ChallangeActivity.class));
                break;
            case R.id.stats_button:
                startActivity(new Intent(this,StatsActivity.class));
                break;
            case R.id.logout_button:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }

    }
}