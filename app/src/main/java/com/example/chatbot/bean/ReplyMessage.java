package com.example.chatbot.bean;

/**
 * 创建日期：20200123
 * <p>
 * 描述：接收消息类  用于gson解析服务器返回的json
 */
public class ReplyMessage {
    /**
     * message : success
     * data : {"type":5000,"info":{"text":"姚明的身高是226厘米"}}
     */

    private String message;
    private DataBean data;

    public ReplyMessage(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * type : 5000
         * info : {"text":"姚明的身高是226厘米"}
         */

        private int type;
        private InfoBean info;

        public DataBean(InfoBean info) {
            this.info = info;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * text : 姚明的身高是226厘米
             */

            private String text;

            public InfoBean(String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
