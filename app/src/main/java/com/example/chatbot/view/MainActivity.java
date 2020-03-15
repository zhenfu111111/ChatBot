package com.example.chatbot.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.R;
import com.example.chatbot.bean.DbBean;
import com.example.chatbot.bean.MessageBean;
import com.example.chatbot.bean.MyApplication;
import com.example.chatbot.bean.MyPhotoBean;
import com.example.chatbot.bean.MyRecyclerAdaper;
import com.example.chatbot.presenter.ChatPresenter;

import org.xutils.DbManager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener ,PostView , View.OnKeyListener {

    private static final int QUERY=1;
    private static final int REQUEST=2;
    private static final int CHOOSE=3;
    private static final int CUT=4;
    private Uri outUri=null;

    RecyclerView rv;
    EditText et_Input;
    Button bt_Input;

    private ChatPresenter ISentMessagePresenter;
    private List<MessageBean> myList;
    private MyRecyclerAdaper mAdaper;
    private int updateUI = 0;
    private int REPLY_ERROR = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == updateUI) {
                mAdaper.update(myList);
                rv.scrollToPosition(mAdaper.getItemCount() - 1);
            } else if (msg.what == REPLY_ERROR) {
                //服务器返回数据失败
                Toast.makeText(getApplicationContext(), "发生了无法预知的错误", Toast.LENGTH_LONG).show();
            }

        }
    };
    private MyApplication myApplication;
    private DbManager dbManage;
    private DbBean mDB;
    private TextView menu;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        initData();
    }

    private void initData() {
        preferences =this.getPreferences(MODE_PRIVATE);
        //preferences = this.getSharedPreferences("myImageUri", MODE_PRIVATE);
        ISentMessagePresenter = new ChatPresenter(this);

        myApplication = (MyApplication) getApplication();
        dbManage = myApplication.getDbManage();
        mDB = new DbBean(dbManage);//获取数据库

        //设置recyclerview
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);//设置布局方向


        myList = mDB.selectAll();//载入所有记录
        rv.setLayoutManager(manager);

        if (myList == null) {
            myList = new ArrayList<>();//首次打开无记录调用此句,防空指针
        }
        mAdaper = new MyRecyclerAdaper(myList,mDB);
        String myUri = preferences.getString("myUri", "");//设置头像
        if (myUri!=""){

            mAdaper.setPostURI(Uri.parse(myUri));
        }
        rv.setAdapter(mAdaper);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.lv);
        et_Input = findViewById(R.id.et_input);
        et_Input.setOnKeyListener(this);
        et_Input.setOnFocusChangeListener(new View.OnFocusChangeListener() {//编辑框获得焦点监听
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                rv.scrollToPosition(mAdaper.getItemCount() - 1);
            }
        });

        et_Input.setFocusable(true);
        et_Input.setFocusableInTouchMode(true);

        bt_Input = findViewById(R.id.bt_input);
        bt_Input.setOnClickListener(this);
        menu = findViewById(R.id.menu);
        menu.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        rv.scrollToPosition(myList.size()-1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_input) {//发送按钮事件
            if (et_Input.getText() != null && et_Input.getText().length() > 0) {
                String inputMessage = et_Input.getText().toString().trim();
                if (TextUtils.isEmpty(inputMessage)){
                    Toast.makeText(MainActivity.this, "输入内容为空", Toast.LENGTH_LONG).show();
                    et_Input.setText("");
                }else {
                    ISentMessagePresenter.getEtMessage(inputMessage);//传递输入内容到presenter
                    et_Input.setText("");
                }

            } else {
                Toast.makeText(MainActivity.this, "输入内容为空", Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.menu) {
            showPopupMenu();
        }
    }

    /** 键盘监听
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_UP){//抬起enter键操作
            if (et_Input.getText() != null && et_Input.getText().length() > 0) {
                String inputMessage = et_Input.getText().toString().trim();
                if (TextUtils.isEmpty(inputMessage)){
                    Toast.makeText(MainActivity.this, "输入内容为空", Toast.LENGTH_LONG).show();
                    et_Input.setText("");
                }else {
                    ISentMessagePresenter.getEtMessage(inputMessage);//传递输入内容到presenter
                    et_Input.setText("");
                }

            } else {
                Toast.makeText(MainActivity.this, "输入内容为空", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    /**ActivityResult监听
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==REQUEST){//查找记录返回
            Bundle bundle = data.getExtras();
            int days = bundle.getInt("days");
            myList = mDB.selectByDay(days);//载入查询记录
            mAdaper=new MyRecyclerAdaper(myList,mDB);
            rv.setAdapter(mAdaper);
            rv.scrollToPosition(0);
        }

        if (requestCode==CHOOSE){//更换自己头像
            if (data!=null){
                Uri uri = data.getData();
                MyPhotoBean myPhotoBean = new MyPhotoBean(this);
                Intent intent = myPhotoBean.CutForPhoto(uri);
                outUri = myPhotoBean.getOutUri();
                startActivityForResult(intent,CUT);
            }
        }

        if (requestCode==CUT){
            if (outUri!=null){
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("myUri",outUri.toString());
                edit.commit();
                mAdaper.setPostURI(outUri);
                rv.setAdapter(mAdaper);
            }else {
                Toast.makeText(this,"设置失败",Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * 创建弹出菜单
     */
    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, menu);
        popupMenu.inflate(R.menu.mymenu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {//菜单监听
                if (item.getItemId() == R.id.findRecord) {//日期查找
                    Intent intent = new Intent(MainActivity.this, TimeSelectActivity.class);
                    startActivityForResult(intent,QUERY);
                    return true;
                }else if (item.getItemId()==R.id.showRecord){//载入所有记录
                    myList = mDB.selectAll();//载入查询记录
                    if (myList==null){
                        myList=new ArrayList<>();
                        Toast.makeText(MainActivity.this,"聊天记录为空",Toast.LENGTH_LONG).show();
                    }
                    mAdaper=new MyRecyclerAdaper(myList,mDB);
                    rv.setAdapter(mAdaper);
                    rv.scrollToPosition(myList.size()-1);
                }else if (item.getItemId()==R.id.changeMyIV){//更换头像
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                    startActivityForResult(intent,CHOOSE);
                }else if (item.getItemId()==R.id.delete){
                    mDB.deleteAll();
                    myList=new ArrayList<>();
                    mAdaper=new MyRecyclerAdaper(myList,mDB);
                    rv.setAdapter(mAdaper);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    /**
     * @param messageBean presenter调用，获取model数据更新ui
     */
    @Override
    public void getMessage(MessageBean messageBean) {
        if (messageBean == null) {
            Message message = new Message();
            message.what = REPLY_ERROR;
            handler.sendMessage(message);
        } else {
            myList.add(messageBean);
            Message message = new Message();
            message.what = updateUI;
            handler.sendMessage(message);
            mDB.update(messageBean);//更新数据库
        }
    }

    /**
     * 自动关闭软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN &&
                getCurrentFocus() != null &&
                getCurrentFocus().getWindowToken() != null) {

            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, event)) {
                hideKeyboard(v.getWindowToken());
                et_Input.clearFocus();//隐藏键盘编辑框失去焦点
            }
        }
        return super.dispatchTouchEvent(event);
    }
    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder windowToken) {
        if (windowToken!=null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(windowToken,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
         * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText和发送按钮时则不能隐藏
         */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationOnScreen(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth()+250;//延长右边边界，包含button按钮区域
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击EditText的事件，忽略它。
                rv.scrollToPosition(mAdaper.getItemCount() - 1);//弹出软键盘刷新显示
            } else {
                return true;
            }
        }
        return false;
    }

}