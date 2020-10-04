package com.example.firstdb;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DB db;

    private TextView txtName, txtNum;
    public ListView UsersList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DB(this);
    }

    public void onClickAdd(View view) {
        txtName = (TextView)findViewById(R.id.Name);
        txtNum = (TextView)findViewById(R.id.numb);

        String name = txtName.getText().toString();
        String num = txtNum.getText().toString();

        if (txtName.length()!= 0 || txtNum.length()!=0) {
            AddData(name);
            AddData(num);
            txtName.setText("");
            txtNum.setText("");
        } else {
            toastMessage("Что-то введено или нет");
        }
    }

    public void AddData(String newEntry) {
        boolean insertData = db.addData(newEntry);
        if (insertData) {
            toastMessage("Данные успешно отправлены!");
        } else {
            toastMessage("Едрид мадрид(");
        }
    }

    public void onClickRead(View view){
        UsersList = (ListView) findViewById(R.id.listView);
        Cursor data = db.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        UsersList.setAdapter(adapter);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}