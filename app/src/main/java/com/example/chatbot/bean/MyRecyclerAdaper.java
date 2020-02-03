package com.example.chatbot.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.R;
import com.example.chatbot.view.MainActivity;

import java.util.ArrayList;

/**
 * 创建日期：20200202
 * <p>
 * 描述：recyclerview的adaper
 */
public class MyRecyclerAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int post=0;
    private static final int reply=1;//判断信息类型,0为用户输入，1为服务器返回
    private ArrayList<MessageBean> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyRecyclerAdaper(ArrayList<MessageBean> list) {
        this.list = list;
    }

    /**更新item
     * @param arrayList 变化后数组
     */
    public void update(ArrayList<MessageBean> arrayList){
        this.list=arrayList;
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
        if (holder instanceof MyRecyclerAdaper.postViewHolder){
            ((MyRecyclerAdaper.postViewHolder) holder).textView.setText(mess);
            ((MyRecyclerAdaper.postViewHolder) holder).date.setText(date);
        }

        if (holder instanceof MyRecyclerAdaper.replyViewHolder){
            ((MyRecyclerAdaper.replyViewHolder) holder).textView.setText(mess);
            ((MyRecyclerAdaper.replyViewHolder) holder).date.setText(date);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class postViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public TextView date;
        public postViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.mine_iv);
            textView=itemView.findViewById(R.id.mine_tv);
            date=itemView.findViewById(R.id.mine_tv_date);
        }
    }

    class replyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public TextView date;
        public replyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.robot_iv);
            textView=itemView.findViewById(R.id.robot_tv);
            date=itemView.findViewById(R.id.robot_tv_date);
        }
    }

}
