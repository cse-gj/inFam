package com.sample.infam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private Button ChildButton;
    private Button ParentButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ChildButton = (Button) findViewById(R.id.welcome_child);
        ParentButton = (Button) findViewById(R.id.welcome_parent);

        ChildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterChildIntent = new Intent(WelcomeActivity.this, ChildLogin.class);
                startActivity(LoginRegisterChildIntent);
            }
        });

        ParentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterParentIntent = new Intent(WelcomeActivity.this, ParentLogin.class);
                startActivity((LoginRegisterParentIntent));
            }
        });

    }
}