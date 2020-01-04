package com.example.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chat.ui.notifications.NotificationsFragment;

public class Publish extends Activity {
    private static final int COMPLETED = 0;
    private static final int COMPLETE = 1;
    private static String url_publish = "http://172.20.10.12:5000/publish";

    private String jsonData;
    String data;
    private TextView content;
    JSONParser jsonParser= new JSONParser();
    private Button button1;
    private Button button2;
    String str = "";
    LinearLayout llt;
    LayoutInflater inflater;
    private EditText et;
    private Button button;
    private Button button4;
    String mstr;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish);
        button = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        et = findViewById(R.id.textView4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mstr = et.getText().toString();
                new Login().execute();
                Intent myin = new Intent(Publish.this, QqMainActivity.class);

                startActivity(myin);
            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现一个可以跳转界面的实例
                Intent myin = new Intent(Publish.this,QqMainActivity.class);
                //开启意图，并设置请求码是0，相当于设置一个监听或者中断
                //这个中断将在运行到setResult(结果码,意图实例);这样的代码返回来
                //请求码用于判定返回的意图传到哪里，
                startActivity(myin);

            }
        });
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
                url = url_publish + "?content=" + mstr;
                jsonData = jsonParser.makeHttpRequest(url,"POST");


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
