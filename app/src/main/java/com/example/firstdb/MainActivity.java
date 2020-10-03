package com.example.firstdb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_add, btn_read;
    EditText text_name, text_numb;
    TextView text_Area;

    DB db =  new DB(getApplicationContext());

    public Boolean checkFields(String name,String numb){
        boolean exist = true;
        if (name.isEmpty()||numb.isEmpty()) {
            exist = false;
            Toast.makeText(this, "Нельзя так, заполните поля", Toast.LENGTH_SHORT).show();
        }
        return exist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);

        btn_add = (Button) findViewById(R.id.button_add);
        btn_read = (Button) findViewById(R.id.button_read);
    }
    public void onClickAdd(View view) {
        text_name = (EditText)findViewById(R.id.Name);
        text_numb = (EditText)findViewById(R.id.numb);

        String name = text_name.getText().toString();
        String num = text_numb.getText().toString();

        SQLiteDatabase database = db.getWritableDatabase();

        if(checkFields(name,num)) {
            ContentValues values = new ContentValues();
            values.put(DB.KEY_NAME,name);
            values.put(DB.KEY_NUM,num);
            long newRowId = database.insert(DB.TABLE_NAME, null, values);
            Toast.makeText(this, "Ну чё, add!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void onClickRead(View view){
        text_Area = (TextView)findViewById(R.id.textView);
        text_Area.setText("В бд есть:\n");

        SQLiteDatabase database = db.getReadableDatabase();


        Cursor cursor = database.query(
                DB.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );
        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DB.KEY_ID));
            itemIds.add(itemId);
            text_Area.append(Long.toString(itemId));
        }
        cursor.close();
        db.close();
    }
}