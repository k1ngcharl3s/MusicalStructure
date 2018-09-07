package com.example.k1ngcharl3s.musicalstructure;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView tupac=  findViewById(R.id.tupac);
        tupac.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent ajrIntent = new Intent(MainActivity.this, TupacActivity.class);
                startActivity(ajrIntent);
            }
        });
        ImageView christinaaguilera =  findViewById(R.id.christinaaguilera);
        christinaaguilera.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent christinaaguileraIntent = new Intent(MainActivity.this, ChristinaAguileraActivity.class);
                startActivity(bebeRexhaIntent);
            }
        });
        ImageView evanescene=  findViewById(R.id.evanescene);
        bts.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent evansceneIntent = new Intent(MainActivity.this, EvanesceneActivity.class);
                startActivity(evansceneIntent);
            }
        });
        ImageView isleybrothers = findViewById(R.id.isleybrothers);
        pink.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent isleybrothersIntent = new Intent(MainActivity.this, IsleyBrothersActivity.class);
                startActivity(isleybrothersIntent);
            }
        });

    }

}
