package com.example.chatbot.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.admin.SystemUpdatePolicy;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.chatbot.R;
import com.example.chatbot.bean.DbBean;
import com.example.chatbot.bean.ListViewAdaper;
import com.example.chatbot.bean.MyApplication;

import org.xutils.DbManager;

import java.util.ArrayList;

public class TimeSelectActivity extends AppCompatActivity {

    private ListView mListview;
    private MyApplication myApplication;
    private DbManager dbManage;
    private DbBean mDbBean;
    private int[] ints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_select);

        mListview = findViewById(R.id.lv_time);

        myApplication = (MyApplication) getApplication();
        dbManage = myApplication.getDbManage();
        mDbBean = new DbBean(dbManage);
        ints = mDbBean.selectAllDay();
        ArrayList<Integer> integers = intToInteger(ints);
        ListViewAdaper adaper = new ListViewAdaper(this, integers);
        mListview.setAdapter(adaper);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("days",ints[position]);
                intent.putExtras(bundle);
                setResult(2,intent);
                finish();
            }
        });
    }

    private ArrayList<Integer> intToInteger(int[] ints){
        ArrayList<Integer> integers = new ArrayList<>();
        for(int i=0;i<ints.length;i++){
            integers.add(new Integer(ints[i]));
        }
        return integers;
    }

}
