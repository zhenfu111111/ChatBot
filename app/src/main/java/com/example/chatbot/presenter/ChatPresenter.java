package com.example.chatbot.presenter;

import com.example.chatbot.bean.MessageBean;
import com.example.chatbot.bean.PostMesssage;
import com.example.chatbot.bean.ReplyMessage;
import com.example.chatbot.model.ChatModl;
import com.example.chatbot.view.PostView;

import java.net.URI;

/**
 * 创建日期：20200126
 * <p>
 * 描述：presenter
 */
public class ChatPresenter implements SentMessagePresenter {
    private ChatModl IsentMessageModl;
    private PostView postView;

    public ChatPresenter(PostView postView) {
        this.postView=postView;
        IsentMessageModl=new ChatModl(this);
    }

    /**
     * 获取用户输入内容，传递到model发送到服务器
     * @param s 输入内容
     */
    @Override
    public void getEtMessage(String s) {
        IsentMessageModl.sentMessage(s);
    }

    /**返回model信息到ui更新ui
     * @param messageBean model返回messgebean类
     */
    @Override
    public void returnMessage(MessageBean messageBean) {
        postView.getMessage(messageBean);

    }

}
