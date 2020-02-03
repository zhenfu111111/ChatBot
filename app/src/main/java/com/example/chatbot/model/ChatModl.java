package com.example.chatbot.model;

import com.example.chatbot.bean.MessageBean;
import com.example.chatbot.bean.MyCalendar;
import com.example.chatbot.bean.MyHttpHelper;
import com.example.chatbot.bean.MyRobot;
import com.example.chatbot.bean.PostMesssage;
import com.example.chatbot.bean.ReplyMessage;
import com.example.chatbot.presenter.ChatPresenter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 创建日期：20200126
 * <p>
 * 描述：model，处理数据
 */
public class ChatModl implements SentMessageModl {

    private String json;
    private ChatPresenter chatPresenter;
    private ReplyMessage replyMessage;
    String replyJson;

    public ChatModl(ChatPresenter chatPresenter) {
        this.chatPresenter=chatPresenter;
    }

    /**
     * 向服务端发送消息
     * @param message 用户输入内容
     */
    @Override
    public void sentMessage(String message) {
        //根据输入信息构建对象生成json提交服务器
        PostMesssage mess = new PostMesssage();
        mess.setSpoken(message);
        mess.setUserid("xiaoming");
        chatPresenter.returnMessage(new MessageBean(0,message,new MyCalendar().getTime()));

        final Gson gson = new Gson();
        json = gson.toJson(mess);
      new Thread(new Runnable() {
          @Override
          public void run() {
              replyJson = MyHttpHelper.sendJsonPost(json);//服务端返回信息replyJson
              replyMessage = gson.fromJson(replyJson, ReplyMessage.class);
              String mess=replyMessage.getData().getInfo().getText();
              chatPresenter.returnMessage(new MessageBean(1,mess,new MyCalendar().getTime()));

          }
      }).start();


    }

}
