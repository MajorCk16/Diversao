package com.example.younglings.diversao;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.younglings.diversao.MainActivity.database;

public class ListsActivity extends AppCompatActivity {
    private ListView listView;


    ArrayList<String> catagories= new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onStart() {
        super.onStart();
     MainActivity.myRef.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        String value= dataSnapshot.getValue(String.class);
        catagories.add(value);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        MainActivity.database= FirebaseDatabase.getInstance();
        MainActivity.myRef=MainActivity.database.getReference("App");

        listView = (ListView) (findViewById(R.id.newlistview));
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, catagories);
        listView.setAdapter(arrayAdapter);


    }

}


