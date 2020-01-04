package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
//import android.support.v4.app.ActivityCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{
    private Button loginBt;
    private Button  registerBt;
    private EditText qqNo, qqPsw;
    private ProgressDialog pDialog;
    JSONParser jsonParser= new JSONParser();
    private String jsonData;
    private String message;
    private int success;
    private ImageView iv;
    public static String user_name;
    public static String BaseUPL = "";     //服务器url
    private static String url_login = "http://172.20.10.12:5000/login";

    private ImageView imageView;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       loginBt =findViewById(R.id.login);
       qqNo = (EditText) findViewById(R.id.username);
       qqPsw = (EditText) findViewById(R.id.password);

        View view= LayoutInflater.from(this).inflate(
                R.layout.message, null);
        //llt_ = (LinearLayout) findViewById(R.id.combineCtrlm);
        final ImageView imageView= view.findViewById(R.id.imgm);
        imageView.setBackgroundResource(R.mipmap.maomao1);


       loginBt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (qqNo.getText().toString().equals("") || qqPsw.getText().toString().equals("")) {
                   Toast toast = Toast.makeText(getApplicationContext(), "请输入账号密码", Toast.LENGTH_SHORT);
                   Log.println(Log.INFO,"s","ddd");
                   Intent intent = new Intent(MainActivity.this, Chat.class);
                   startActivity(intent);
               } else {
                   Log.println(Log.INFO,"s","uuu");
                   new Login().execute();
               }

           }
       });
    }
    class Login extends AsyncTask<String,String,String>{
        @Override
          protected void onPreExecute() {
                         // TODO Auto-generated method stub
                         super.onPreExecute();
                         pDialog = new ProgressDialog(MainActivity.this);
                         pDialog.setMessage("登陆中 请稍后...");
                         pDialog.setIndeterminate(false);
                         pDialog.setCancelable(true);
                         pDialog.show();
        }
        protected String doInBackground(String... args){

            String str_u = qqNo.getText().toString();
            String str_p = qqPsw.getText().toString();
            String url = url_login + "?username=" + str_u + "&password=" + str_p;
            System.out.println(url);
            try{
                jsonData = jsonParser.makeHttpRequest(url,"POST");

            }catch(Exception e){
                e.printStackTrace();
            }

            try{
                JSONObject jsono = new JSONObject(jsonData);
                success = jsono.getInt("var1");

            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
         protected void onPostExecute(String result) {
                         // TODO Auto-generated method stub

            String str = "" + success;
            Toast toast = Toast.makeText(getApplicationContext(),"登录状态" + str, Toast.LENGTH_LONG);
            toast.show();
            if (success==1){
                System.out.println("dengluchenggong");
                user_name = qqNo.getText().toString();
                Intent intent = new Intent(MainActivity.this, QqMainActivity.class);
               startActivity(intent);
           }
            pDialog.dismiss();
        }
    }

}