package com.example.chatbot.bean;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.R;
import com.example.chatbot.view.MainActivity;

import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：20200202
 * <p>
 * 描述：recyclerview的adaper
 */
public class MyRecyclerAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int post=0;
    private static final int reply=1;//判断信息类型,0为用户输入，1为服务器返回
    private List<MessageBean> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private Uri PostURI=null;

    public MyRecyclerAdaper(List<MessageBean> list) {
        this.list = list;
    }


    public void setPostURI(Uri uri){
        this.PostURI=uri;
    }


    /**更新item
     * @param List 变化后数组
     */
    public void update(List<MessageBean> List){
        this.list=List;
        notifyItemInserted(list.size()-1);

    }

    /**根据信息类型判断view类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        MessageBean messageBean=list.get(position);
        if (messageBean.getType()==post){
            return post;
        }else if (messageBean.getType()==reply){
            return reply;
        }
        return super.getItemViewType(position);
    }

    /**根据信息类型引入xml布局文件传递给viewholder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context==null){
            context=parent.getContext();
        }

        if (layoutInflater==null){
            layoutInflater=LayoutInflater.from(context);
        }

        View view;

        if (viewType==post){
            view=layoutInflater.inflate(R.layout.mine_chat_view,parent,false);
            return new MyRecyclerAdaper.postViewHolder(view);
        }else if (viewType==reply){
            view=layoutInflater.inflate(R.layout.robot_chat_view,parent,false);
            return new MyRecyclerAdaper.replyViewHolder(view);
        }
        return null;
    }

    /**设置各项ui显示
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageBean messageBean=list.get(position);
        String mess = messageBean.getMessage();
        String date=messageBean.getDate();

        //判断两条消息间隔小于10min取消显示日期消息
        boolean removTime=false;
        int day = messageBean.getDay();
        int time = messageBean.getTime();
        if (position>=1){
            MessageBean messageBean_last = list.get(position - 1);
            int day1 = messageBean_last.getDay();
            int time1 = messageBean_last.getTime();
            if (day==day1&&((time-time1)<10)){
                removTime=true;
            }

        }

        if (holder instanceof MyRecyclerAdaper.postViewHolder){
            ((MyRecyclerAdaper.postViewHolder) holder).textView.setText(mess);
            if (removTime){
                ((MyRecyclerAdaper.postViewHolder) holder).date.setText("");
            }else {
                ((MyRecyclerAdaper.postViewHolder) holder).date.setText(date);
            }

            if (PostURI!=null){
                ((postViewHolder) holder).imageView.setImageURI(PostURI);
            }

        }

        if (holder instanceof MyRecyclerAdaper.replyViewHolder){
            ((MyRecyclerAdaper.replyViewHolder) holder).textView.setText(mess);

            if (removTime){
                ((MyRecyclerAdaper.replyViewHolder) holder).date.setText("");
            }else {
                ((MyRecyclerAdaper.replyViewHolder) holder).date.setText(date);
            }

           /* Resources resources = context.getResources();
            Drawable drawable = resources.getDrawable(R.drawable.bh16);
            ((replyViewHolder) holder).imageView.setBackground(drawable);*/

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class postViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private TextView date;
        private postViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.mine_iv);
            textView=itemView.findViewById(R.id.mine_tv);
            date=itemView.findViewById(R.id.mine_tv_date);
        }
    }

    class replyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private TextView date;
        private replyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.robot_iv);
            textView=itemView.findViewById(R.id.robot_tv);
            date=itemView.findViewById(R.id.robot_tv_date);
        }
    }

}
