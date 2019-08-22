package com.example.favouriteapps;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> dataSet = new ArrayList<>();

        ContentResolver resolver = getContentResolver();

        Uri uri = Uri.parse("content://com.example.moviecatalogue.repositories.database.provider");
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
        int data = 0;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("Name"));
            data++;
            dataSet.add(name);
        }

        if(data ==0){
            dataSet.add(getResources().getString(R.string.noData));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSet);
        ((ListView)findViewById(R.id.listFavourite)).setAdapter(adapter);
    }

}
