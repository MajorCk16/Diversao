package com.example.younglings.diversao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


public class Navigator extends AppCompatActivity {

 public static   ImageView profilepic;
 LinearLayout cdfood,cdcommunity,cdevents,cdaccomodation;
 ListView listView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        profilepic= (ImageView)(findViewById(R.id.profilepic));

        cdaccomodation=(LinearLayout)(findViewById(R.id.cdaccomodation));

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdClick();
            }
        });

        cdaccomodation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdClick();
            }
        });






    }
    void cdClick(){

        Log.d("button clicked: ","True");

        Intent myIntent = new Intent(getApplicationContext(), ListsActivity.class);
        startActivity(myIntent);
    }
}
