package com.example.chatbot.presenter;

import com.example.chatbot.bean.MessageBean;
import com.example.chatbot.bean.PostMesssage;
import com.example.chatbot.bean.ReplyMessage;

/**
 * 创建日期：
 * <p>
 * 描述：presenter接口
 */
public interface SentMessagePresenter {

    /**获取用户编辑框输入内容，把内容传递到model处理
     * @param s 用户输入内容
     */
    public void getEtMessage(String s);

    /**从model返回message类，更新ui
     * @param messageBean model返回messgebean类
     */
    public void returnMessage(MessageBean messageBean);
}
