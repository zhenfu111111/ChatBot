package com.example.chatbot.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.R;
import com.example.chatbot.bean.MessageBean;
import com.example.chatbot.bean.MyRecyclerAdaper;
import com.example.chatbot.bean.PostMesssage;
import com.example.chatbot.bean.ReplyMessage;
import com.example.chatbot.presenter.ChatPresenter;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener ,PostView{

    RecyclerView rv;
    EditText et_Input;
    Button bt_Input;

    private ChatPresenter ISentMessagePresenter;
    private ArrayList<MessageBean> myList;
    private MyRecyclerAdaper mAdaper;
    private int updateUI=0;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what==updateUI){
                mAdaper.update(myList);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
    }

    private void initData() {
        myList = new ArrayList<>();
        ISentMessagePresenter = new ChatPresenter(this);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.lv);
        et_Input=findViewById(R.id.et_input);
        bt_Input=findViewById(R.id.bt_input);
        bt_Input.setOnClickListener(this);

        initData();

        //设置recyclerview
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);//设置布局方向
        rv.setLayoutManager(manager);

        mAdaper = new MyRecyclerAdaper(myList);
        rv.setAdapter(mAdaper);


    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_input){
            if (et_Input.getText()!=null&&et_Input.getText().length()>0){
                String inputMessage=et_Input.getText().toString().trim();
                ISentMessagePresenter.getEtMessage(inputMessage);//传递输入内容到presenter
                et_Input.setText("");
            }else {
                Toast.makeText(MainActivity.this,"输入内容为空",Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * @param messageBean presenter调用，获取model数据更新ui
     */
    @Override
    public void getMessage(MessageBean messageBean) {
        myList.add(messageBean);
        Message message = new Message();
        message.what=updateUI;
        handler.sendMessage(message);

    }

}
