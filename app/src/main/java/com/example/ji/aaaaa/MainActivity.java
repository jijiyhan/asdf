package com.example.ji.aaaaa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button nearBtn;
    Button selectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nearBtn = findViewById(R.id.nearest_theater);
        selectBtn= findViewById(R.id.select_theater);

        nearBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentMylocation= new Intent(getApplicationContext(),uLocationBased.class);
                startActivity(intentMylocation);
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentMylocation= new Intent(getApplicationContext(),select.class);
                startActivity(intentMylocation);
            }
        });
    }
}
