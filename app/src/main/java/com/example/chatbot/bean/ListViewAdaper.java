package com.example.chatbot.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chatbot.R;

import java.util.ArrayList;

/**
 * 创建日期：
 * <p>
 * 描述：
 */
public class ListViewAdaper extends BaseAdapter {
    ArrayList<Integer> myList;
    LayoutInflater layoutInflater;//布局装载器对象

    public ListViewAdaper(Context context,ArrayList<Integer> myList) {
        this.myList = myList;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Integer getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //如果view未被实例化过，缓存池中没有对应的缓存
        if (convertView==null){
            viewHolder=new ViewHolder();
            // 只需要将XML转化为View，并不涉及到具体的布局，第二个参数通常设置为null
            convertView=layoutInflater.inflate(R.layout.listview_view,null);

            //对viewHolder的属性进行赋值
            viewHolder.textView=convertView.findViewById(R.id.tv_day);

            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHolder);
        }else {//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder=(ViewHolder) convertView.getTag();
        }
        int days=myList.get(position).intValue();
        String text=""+days/10000+"年"+(days%10000/100)+"月"+(days%100)+"日";//20200216
        viewHolder.textView.setText(text);
        return convertView;
    }

    class ViewHolder{//// ViewHolder用于缓存控件，属性对应item布局文件的控件
        public TextView textView;
    }
}
