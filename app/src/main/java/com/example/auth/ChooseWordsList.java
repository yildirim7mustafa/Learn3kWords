package com.example.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseWordsList extends AppCompatActivity implements View.OnClickListener {

    private Button goWordsActivity,goIKnowListActivity,goIDoNotKnowActivity,goBackActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_words_list);

        goWordsActivity = (Button) findViewById(R.id.goWordsActivity);
        goWordsActivity.setOnClickListener(this);

        goIKnowListActivity = (Button) findViewById(R.id.goIKnowActivity);
        goIKnowListActivity.setOnClickListener(this);

        goIDoNotKnowActivity = (Button) findViewById(R.id.goIDoNotKnowActivity);
        goIDoNotKnowActivity.setOnClickListener(this);

        goBackActivity = (Button) findViewById(R.id.goBackMain);
        goBackActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goWordsActivity:
                startActivity(new Intent(this,WordsActivity.class));
                break;

            case R.id.goIKnowActivity:
                startActivity(new Intent(this,IKnowListActivity.class));
                break;

            case R.id.goIDoNotKnowActivity:
                startActivity(new Intent(this,IDoNotKnowListActivity.class));
                break;

            case R.id.goBackMain:
                startActivity(new Intent(this,ProfileActivity.class));
                break;
        }

    }
}