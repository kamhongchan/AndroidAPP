package com.example.kamhongchan.app_findme;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class contact extends AppCompatActivity {
    private Context mContext = contact.this;
    private ListView contacts;
    private SimpleAdapter list_adapter;
    private SQLiteDatabase db;
    private HashSet<Person> person_set = null;
    private List<Map<String, Object>> datalist;
    private EditText addName;
    private EditText addPhone;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setTitle("Contact Book");

        createDatabase();
        queryData();
        initView();
    }

    /**
     * do query in database and put the values in a HashSet--person_set
     */
    private void queryData() {
        person_set = new HashSet<>();
        SQLiteDatabase db = openOrCreateDatabase("people.db",MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("select * from person",null);
        if(cursor != null){
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone_number = cursor.getString(cursor.getColumnIndex("phoneNumber"));
                Log.d("MainActivity","queryData() -- name = "+ name+", phone_number= " + phone_number);
                person_set.add(new Person(name,phone_number));
            }
        }
    }

    private void initView() {
        addName = (EditText) findViewById(R.id.add_name);
        addPhone = (EditText) findViewById(R.id.add_phone);
        add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add_name = addName.getText().toString();
                String add_phone = addPhone.getText().toString();
                if(!add_name.isEmpty() && !add_phone.isEmpty()) {
                    ContentValues values = new ContentValues();
                    values.put("name", add_name);
                    values.put("phoneNumber", add_phone);
                    db.insert("person", null, values);
                    values.clear();
                    Toast.makeText(mContext, "Add Succeed!", Toast.LENGTH_SHORT).show();
                    addName.setText("");
                    addPhone.setText("");
                    queryData();
                    initListView();
                }else {
                    Toast.makeText(mContext, "Name or Phone Number can not null", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initListView();
    }

    private void initListView(){
        contacts = (ListView) findViewById(R.id.contacts);
        datalist = new ArrayList<>();
        list_adapter = new SimpleAdapter(mContext, getData(), R.layout.activity_item,
                new String[]{"name", "phone"}, new int[]{R.id.name, R.id.phone});
        contacts.setAdapter(list_adapter);
    }

    /**
     * create a database named "people"
     */
    private void createDatabase() {
        DBHelper dbHelper = new DBHelper(mContext,"people.db",null, 1);
        db = dbHelper.getWritableDatabase();
        Log.d("MainActivity","createDatabase() -- createDatabase succeed!");
    }

    private List<Map<String, Object>> getData(){
        if(person_set != null) {
            for(Person p:person_set){
                Log.d("MainActivity","104 ");
                Map<String,Object> map = new HashMap<>();
                map.put("name",p.getName());
                map.put("phone",p.getPhoneNumber());
                Log.d("MainActivity","109 getData name = " + p.getName()+", phone = "+p.getPhoneNumber());
                datalist.add(map);
            }
//            Iterator<Person> it = person_set.iterator();
//            Log.d("MainActivity", "Iterator:" + person_set.size());
//            while (it.hasNext()) {
//                Log.d("MainActivity", "hasNext");
//                Map<String, Object> map = new HashMap<>();
//                Person p = it.next();
//                map.put("name", p.getName());
//                map.put("phone", p.getPhoneNumber());
//                Log.d("MainActivity", "109 getData name = " + p.getName() + ", phone = " + p.getPhoneNumber());
//                datalist.add(map);
//            }
        }
        return datalist;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }



}




