package com.example.chatbot.bean;

/**
 * 创建日期：20200123
 * <p>
 * 描述：发送消息类。用于gson生成json文件
 */
public class PostMesssage {

    /**
     * spoken : 姚明多高啊？
     * appid : xiaosi
     * userid : user
     */

    private String spoken;
    private String appid=MyRobot.api_key;
    private String userid;

    public String getSpoken() {
        return spoken;
    }

    public void setSpoken(String spoken) {
        this.spoken = spoken;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
