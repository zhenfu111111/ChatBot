package com.example.chatbot.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.chatbot.R;

public class SplashScreenActiviy extends Activity {

    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen_activiy);
        tv_time = findViewById(R.id.tv_time);

        MyCountDownTimer timer = new MyCountDownTimer(5000, 1000);
        timer.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000*5);


    }
    class MyCountDownTimer extends CountDownTimer {
        //millisInFuture:倒计时的总数,单位毫秒
        //例如 millisInFuture=1000;表示1秒
        //countDownInterval:表示间隔多少毫秒,调用一次onTick方法()
        //例如: countDownInterval =1000;表示每1000毫秒调用一次onTick()
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        public void onFinish() {
            tv_time.setText("开始跳转...");
        }
        public void onTick(long millisUntilFinished) {
            tv_time.setText("倒计时    " + millisUntilFinished / 1000 + "S");
        }
    }



}
