package com.example.chatbot.model;
import com.example.chatbot.bean.MessageBean;
import com.example.chatbot.bean.MyCalendar;
import com.example.chatbot.bean.MyRobot;
import com.example.chatbot.bean.PostMesssage;
import com.example.chatbot.bean.ReplyMessage;
import com.example.chatbot.presenter.ChatPresenter;
import com.google.gson.Gson;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.URI;

/**
 * 创建日期：20200126
 * <p>
 * 描述：model，处理数据
 */
public class ChatModl implements SentMessageModl {

    private String json;
    private ChatPresenter chatPresenter;
    private ReplyMessage replyMessage;
    //String replyJson;

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
        MyCalendar myCalendar = new MyCalendar();
        chatPresenter.returnMessage(new MessageBean(0,message,myCalendar.getData(),myCalendar.getDay(),myCalendar.getTime()));//将用户输入信息转换为message类传递给presenter

        final Gson gson = new Gson();
        json = gson.toJson(mess);//postmessage类转换为json
     /* new Thread(new Runnable() {
          @Override
          public void run() {
              replyJson = MyHttpHelper.sendJsonPost(json);//服务端返回信息replyJson
              replyMessage = gson.fromJson(replyJson, ReplyMessage.class);
              String mess=replyMessage.getData().getInfo().getText();
              MyCalendar myCalendar = new MyCalendar();
              chatPresenter.returnMessage(new MessageBean(1,mess,myCalendar.getTime(),myCalendar.getDay()));//将返回信息转换为message传递给presenter

          }
      }).start();*/

     String url=MyRobot.url_key;
        RequestParams requestParams = new RequestParams(url);
        //设置post json
        requestParams.setAsJsonContent(true);
        requestParams.setBodyContent(json);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                replyMessage=gson.fromJson(result,ReplyMessage.class);
                String mess=replyMessage.getData().getInfo().getText();
                MyCalendar myCalendar = new MyCalendar();
                chatPresenter.returnMessage(new MessageBean(1,mess,myCalendar.getData(),myCalendar.getDay(),myCalendar.getTime()));//将返回信息转换为message传递给presenter
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                chatPresenter.returnMessage(null);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }



}
