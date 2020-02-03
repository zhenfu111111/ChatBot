package com.example.chatbot.view;

import com.example.chatbot.bean.MessageBean;
import com.example.chatbot.bean.PostMesssage;
import com.example.chatbot.bean.ReplyMessage;

/**
 * 创建日期：20200126
 * <p>
 * 描述：view类接口
 */
public interface PostView {
    /**
     * @param messageBean presenter调用，返回model数据更新ui
     */
    public void getMessage(MessageBean messageBean);

}
