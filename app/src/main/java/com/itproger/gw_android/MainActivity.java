package com.itproger.gw_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private EditText link,link_name;
    private Button btn;
    private DataBase dataBase;
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<String> arrayAdapter;
    private TextView link_name_row;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        link = findViewById(R.id.link);
        link_name = findViewById(R.id.link_name);
        btn = findViewById(R.id.btn);
        dataBase = new DataBase(this);
        list = findViewById(R.id.list);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        link_name_row = findViewById(R.id.link_name_row);

        loadAllTask();


                if(link.getText().equals("") && link_name.getText().equals("")){
                    btn.setText("Неверная ссылка");
                }else if(link_name.getText().equals("")){
                    btn.setText("Сокращенная ссылка неверная!");
                }else {
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                    String link_var = String.valueOf(link.getText());
                    String link_name_var = String.valueOf(link_name.getText());
                    dataBase.insertData(link_var, link_name_var);
                    loadAllTask();
                        }
                    });

                }

//        link_name_row.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                View parent = (View) view.getParent();
//                TextView textView = parent.findViewById(R.id.link_name_row);
//                String task = String.valueOf(textView.getText());
//                dataBase.deleteData(task);
//                loadAllTask();
//
//
//                return true;
//            }
//        });



    }
/////////////LOAD ALL
    private void loadAllTask() {
        ArrayList<String> allTask = dataBase.getAllTask();
        if(arrayAdapter == null){
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.link_list_row, R.id.link_name_row, allTask);
            list.setAdapter(arrayAdapter);
            link.setText("");
            link_name.setText("");
        }else{
            arrayAdapter.clear();
            arrayAdapter.addAll(allTask);
            arrayAdapter.notifyDataSetChanged();
        }
   }


}