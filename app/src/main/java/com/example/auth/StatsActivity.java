package com.example.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button goProfile_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        goProfile_button = (Button) findViewById(R.id.goProfile2_button);
        goProfile_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.goProfile2_button:
            startActivity(new Intent(this,ProfileActivity.class));
            break;
    }
    }
}