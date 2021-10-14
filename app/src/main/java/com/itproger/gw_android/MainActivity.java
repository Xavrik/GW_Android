package com.itproger.gw_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private EditText link,link_name;
    private Button btn;
    private DataBase dataBase;
    private SharedPreferences sharedPreferences;
    private ArrayAdapter<String> arrayAdapter;
    private TextView link_name_row;
    private RelativeLayout   link_list_row;



    @SuppressLint("ResourceType")
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
        link_list_row = findViewById(R.layout.link_list_row);

        loadAllTask();
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                if(link.getText().toString().equals("")){
                    btn.setText("Неверная ссылка");
                }else if(link_name.getText().toString().equals("")){
                    btn.setText("Сокращенная ссылка неверная!");
                }else {
                    String link_var = String.valueOf(link.getText());
                    String link_name_var = String.valueOf(link_name.getText());
                    Boolean validUser = dataBase.checkLinkName(link_name.getText().toString());
                    if(validUser == true) {
                        link.setText("");
                        link_name.setText("");
                        btn.setText("Такая ссылка есть!");
                    }else{
                        dataBase.insertData(link_var, link_name_var);
                        btn.setText("Готово!");
                        link.setText("");
                        link_name.setText("");
                        loadAllTask();
                    }
                        }
                }
        });





//        link_list_row.setOnLongClickListener(new View.OnLongClickListener() {
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


            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                        long id) {
                String browser = "https://www."+dataBase.findLink(allTask.get(position));
//                    Toast.makeText(getApplicationContext(),"link: " +  allTask.get(position),
//                            Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"link: " +  browser,
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(browser));
                    startActivity(i);

                }
            });
                    list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                           AdapterView parent = (AdapterView) view.getParent();
                            TextView textView = parent.findViewById(R.id.link_name_row);
                            String task = String.valueOf(textView.getText());
                            dataBase.deleteData(task);
                            loadAllTask();


                            return true;
                        }
                    });

        }else{
            arrayAdapter.clear();
            arrayAdapter.addAll(allTask);
            arrayAdapter.notifyDataSetChanged();
        }
   }



}