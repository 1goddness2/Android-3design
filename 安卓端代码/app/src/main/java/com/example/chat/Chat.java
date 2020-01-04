package com.example.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Chat extends Activity {
    private static final int COMPLETED = 0;
    private static final int COMPLETE = 1;
    private static String url_login = "http://172.20.10.12:5000/toChat";
    private static String url_send = "http://172.20.10.12:5000/duihua";
    private String jsonData;
    String data;
    String data_;
    String form;
    private TextView content;
    JSONParser jsonParser= new JSONParser();
    private Button button1;
    private Button button2;
    EditText et;
    private EditText ee;
    String str = "";
    LinearLayout llt;
    LayoutInflater inflater;
    String touxaing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.caht);
        Intent intent = getIntent();
        data = intent.getStringExtra("key");
        data_ = intent.getStringExtra("name");
        form = intent.getStringExtra("form");
        System.out.println("chaundizhi"+data);
        content = (TextView) findViewById(R.id.textView);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.send);
        llt = (LinearLayout) findViewById(R.id.llt1);
        et = (EditText) findViewById(R.id.input_text) ;
        ee = (EditText) findViewById(R.id.editText4);
        ee.setFocusableInTouchMode(false);

        ee.setText(data_);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现一个可以跳转界面的实例
                Intent myin = new Intent(Chat.this,QqMainActivity.class);
                //开启意图，并设置请求码是0，相当于设置一个监听或者中断
                //这个中断将在运行到setResult(结果码,意图实例);这样的代码返回来
                //请求码用于判定返回的意图传到哪里，
                startActivity(myin);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = et.getText().toString();
                if (str == ""){
                    Toast toast = Toast.makeText(getApplicationContext(),"输入内容不可为空", Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    new Send().execute();
                }
            }
        });
        new Login().execute();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() ==  MotionEvent.ACTION_DOWN ) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定当前是否需要隐藏
     */
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
            //return !(ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                try {
                    JSONObject jsono = new JSONObject(jsonData);
                    JSONArray jArr = jsono.getJSONArray("data");
                    System.out.println(jArr);
                    for (int i = 0; i < jArr.length(); i++) {
                        JSONObject obj2 = jArr.getJSONObject(i);
                        String str1 = obj2.getString("typ");
                        String str2 = obj2.getString("record");
                        String str3 = obj2.getString("touxiang");
                        String str4 = obj2.getString("othertouxiang");
                        //content.append(str2);
                        touxaing = str3;
                        System.out.println(str1);
                        if(str1.equals("to")){
                            setr(str2,str3);
                        }else setf(str2,str4);

                    } //UI更改操作
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if (msg.what == COMPLETE) {
                try {
                    System.out.println(form);
                    setr(str,form);
                    et.setText("");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
    public void setr(String str1,String str2){
        View view=LayoutInflater.from(this).inflate(
                R.layout.message2, null);
        //llt_ = (LinearLayout) findViewById(R.id.combineCtrlm);
        final ImageView iv= view.findViewById(R.id.img2);

        if(str2.equals("1")) iv.setBackgroundResource(R.mipmap.chenweiting);
        else if (str2.equals("2")) iv.setBackgroundResource(R.mipmap.luguangzhu);
        else if (str2.equals("3")) iv.setBackgroundResource(R.mipmap.ouyangnana);
        else if (str2.equals("4")) iv.setBackgroundResource(R.mipmap.pengyuyan);
        else if (str2.equals("5")) iv.setBackgroundResource(R.mipmap.wuyanzu);
        else if (str2.equals("6")) iv.setBackgroundResource(R.mipmap.lisa);
        //System.out.println(findViewById(R.id.namem));
        final EditText edit1 = view.findViewById(R.id.namem2);
        edit1.setText(str1);
        edit1.setFocusableInTouchMode(false);
        llt.addView(view);
    }
    public void setf(String str1,String str2){
        View view=LayoutInflater.from(this).inflate(
                R.layout.message, null);
        //llt_ = (LinearLayout) findViewById(R.id.combineCtrlm);
        final ImageView iv= view.findViewById(R.id.imgm);

        if(str2.equals("1")) iv.setBackgroundResource(R.mipmap.chenweiting);
        else if (str2.equals("2")) iv.setBackgroundResource(R.mipmap.luguangzhu);
        else if (str2.equals("3")) iv.setBackgroundResource(R.mipmap.ouyangnana);
        else if (str2.equals("4")) iv.setBackgroundResource(R.mipmap.pengyuyan);
        else if (str2.equals("5")) iv.setBackgroundResource(R.mipmap.wuyanzu);
        else if (str2.equals("6")) iv.setBackgroundResource(R.mipmap.lisa);
        //System.out.println(findViewById(R.id.namem));
        final EditText edit1 = view.findViewById(R.id.namem);
        edit1.setText(str1);
        edit1.setFocusableInTouchMode(false);
        llt.addView(view);
    }
    class Send extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //pDialog = new ProgressDialog(MainActivity.this);
            // pDialog.setMessage("登陆中 请稍后...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }
        protected String doInBackground(String... args){

            try{
                String url = "";
                url = url_send + "?otherId=" + data + "&content=" + str;
                jsonData = jsonParser.makeHttpRequest(url,"GET");
                System.out.println();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                Message message = new Message();
                message.what = COMPLETE;
                handler.sendMessage(message);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //pDialog.dismiss();
            //String str = "" + success;
            //Toast toast = Toast.makeText(getApplicationContext(),"登录状态" + str, Toast.LENGTH_LONG);
            //toast.show();
            //if (success==1){
            //System.out.println("dengluchenggong");
            //user_name = qqNo.getText().toString();
            //Intent intent = new Intent(MainActivity.this, QqMainActivity.class);
            //startActivity(intent);
            // }

        }
    }
    class Login extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //pDialog = new ProgressDialog(MainActivity.this);
            // pDialog.setMessage("登陆中 请稍后...");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }
        protected String doInBackground(String... args){


            try{
                String url = "";
                url = url_login + "?otherId=" + data;
                jsonData = jsonParser.makeHttpRequest(url,"POST");

                try{
                    Message message = new Message();
                    message.what = COMPLETED;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            //pDialog.dismiss();
            //String str = "" + success;
            //Toast toast = Toast.makeText(getApplicationContext(),"登录状态" + str, Toast.LENGTH_LONG);
            //toast.show();
            //if (success==1){
            //System.out.println("dengluchenggong");
            //user_name = qqNo.getText().toString();
            //Intent intent = new Intent(MainActivity.this, QqMainActivity.class);
            //startActivity(intent);
            // }

        }
    }
}
